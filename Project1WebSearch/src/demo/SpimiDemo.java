package demo;

import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

import services.extraction.ExtractPosting;
import util.Strings;
import util.file.GeneralFile;
import util.file.RandomAccessFile;
import util.file.TextFile;
import contants.Constants;
import domain.Posting;

public class SpimiDemo {

	public static void main(String[] args) {

		
		// firstAct(); //pre-processing of documents and splitting them into individual random access files.

//		 secondAct(); //generating blocks of inverted indexes

		// merge indexes
		merge();
	}

	private static void secondAct() {
		ArrayList<String> fileNames = GeneralFile
				.getFilesList(Constants.DOCUMENT_LOCATION_ON_DISK);

		ArrayList<Posting> postings = new ArrayList<Posting>();
		// ArrayList<String> fileNamesBlock = new ArrayList<String>();
		int c = 0;
		int indxFileCounter = 0;
		for (String fileName : fileNames) {
			if (c == 0) {
				postings = new ArrayList<Posting>();
			}
			Object obj = RandomAccessFile.read(
					Constants.DOCUMENT_LOCATION_ON_DISK, fileName);
			if (obj instanceof Posting) {
				postings.add((Posting) obj);
			}
			c++;
			if (c >= Constants.BATCH_SIZE) {
				spimiIndex(postings, indxFileCounter++);
				c = 0;
			}
		}
	}

	private static void spimiIndex(ArrayList<Posting> postings,
			int indxFileCounter) {
		TreeMap<String, ArrayList<Integer>> invertedIndex = new TreeMap<String, ArrayList<Integer>>();
		// extract terms
		for (Posting posting : postings) {
			posting.body = Strings.normalize(posting.body, true, true, true);
			String[] terms = posting.body.split(" ");
			terms = Strings.cleanse(terms);
			HashSet<String> tokens = Strings.tokenize(terms);
			// generate inverted index
			invertedIndex = updateInvertedIndex(invertedIndex, tokens,
					posting.id);
		}

		// no need to sort. TreeMap is already sorted
		// write index to file
		writeInvertedIndexToFile(invertedIndex, indxFileCounter);

	}

	private static void writeInvertedIndexToFile(
			TreeMap<String, ArrayList<Integer>> invertedIndex,
			int indxFileCounter) {
		StringBuilder data = new StringBuilder();
		for (Map.Entry entry : invertedIndex.entrySet()) {
			String key = (String) entry.getKey();
			ArrayList<String> value = (ArrayList<String>) entry.getValue();
			data.append(key + "," + value.toString());
			data.append(System.getProperty("line.separator"));
		}
		TextFile.write(Constants.INDIVIDUAL_INDEXES_LOCATION_ON_DISK,
				"indx" + indxFileCounter, data.toString());

	}

	private static boolean merge() {
		String dir = Constants.INDIVIDUAL_INDEXES_LOCATION_ON_DISK;
		ArrayList<String> fileNames = GeneralFile
				.getFilesList(dir);

		try {
			FileReader[] frs = new FileReader[fileNames.size()];
			for(int i=0; i<frs.length; i++){
				frs[i] = new FileReader(dir+fileNames.get(i));
			}
//			FileReader r1 = new FileReader(destination + fileName1);
//			FileReader r2 = new FileReader(destination + fileName2);
			LineNumberReader[] readers = new LineNumberReader[fileNames.size()];
			for(int i=0; i<readers.length; i++){
				readers[i]=new LineNumberReader(frs[i]);
			}
//			LineNumberReader reader1 = new LineNumberReader(r1);
//			LineNumberReader reader2 = new LineNumberReader(r2);
			String[] lines = new String[fileNames.size()];
			for(int i=0; i<lines.length;i++){
				lines[i] = readers[i].readLine();
			}
//			String line1 = reader1.readLine();
//			String line2 = reader2.readLine();
			
			while (lines[0] != null && lines[1] != null) {
				// do magic
				String line1 = lines[0].replaceAll("[", "");
				line1 = line1.replaceAll("]", "");
				String line2 = lines[1];
				line2 = line2.replaceAll("[", "");
				line2 = line2.replaceAll("]", "");
				String[] arr1 = line1.split(",");
				String[] arr2 = line2.split(",");
				String key1 = arr1[0];
				String key2 = arr1[1];
				String doc = "";
				if(key1.equals(key2)){
					doc = key1+",merge";
					lines[0] = readers[0].readLine();
					lines[1] = readers[1].readLine();
				}
				
			}

		} catch (Exception e) {

		}

		return false;
	}

	private static TreeMap<String, ArrayList<Integer>> updateInvertedIndex(
			TreeMap<String, ArrayList<Integer>> invertedIndex,
			HashSet<String> tokens, int docId) {
		for (String token : tokens) {
			ArrayList<Integer> docs = new ArrayList<Integer>();
			if (invertedIndex.containsKey(token)) {
				docs = invertedIndex.get(token);
			}
			docs.add(docId);
			invertedIndex.put(token, docs);
		}
		return invertedIndex;
	}

	/**
	 * Reads all raw files, extracts all articles from them, and saves each
	 * article (as an object) separately into a file.
	 */
	private static void firstAct() {
		ArrayList<String> fileNames = GeneralFile
				.getFilesList(Constants.FILE_LOCATION_ON_DISK);
		ArrayList<Posting> postings = new ArrayList<Posting>();
		for (String fileName : fileNames) {
			postings = ExtractPosting
					.extract(fileName, TextFile.read(fileName));
			for (Posting posting : postings) {
				RandomAccessFile.write(posting,
						contants.Constants.DOCUMENT_LOCATION_ON_DISK,
						new Integer(posting.id).toString());
				// RandomAccessFile.read(
				// contants.Constants.DOCUMENT_LOCATION_ON_DISK,
				// new Integer(posting.id).toString());
			}
		}

	}
}

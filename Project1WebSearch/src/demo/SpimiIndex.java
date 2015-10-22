package demo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

import util.Strings;
import util.file.GeneralFile;
import util.file.RandomAccessFile;
import util.file.TextFile;
import contants.Constants;
import domain.Posting;

public class SpimiIndex {
	public static void main(String[] args) {

		secondAct(); // generating blocks of inverted indexes
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
//			posting.body = Strings.normalize(posting.body, true, true, true);
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

	private static void writeInvertedIndexToFile(
			TreeMap<String, ArrayList<Integer>> invertedIndex,
			int indxFileCounter) {
		StringBuilder data = new StringBuilder();
		for (Map.Entry<String, ArrayList<Integer>> entry : invertedIndex.entrySet()) {
			String key = (String) entry.getKey();
			ArrayList<Integer> value = (ArrayList<Integer>) entry.getValue();
			data.append(key + "," + value.toString());
			data.append(System.getProperty("line.separator"));
		}
		TextFile.write(Constants.INDIVIDUAL_INDEXES_LOCATION_ON_DISK, "indx"
				+ indxFileCounter, data.toString());

	}

}

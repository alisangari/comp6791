package services.spimi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import utility.Strings;
import utility.file.GeneralFile;
import utility.file.RandomAccessFile;
import utility.file.TextFile;
import contract.Constants;
import domain.DocIdTermFrequencyPair;
import domain.Posting;

public class SpimiIndex {
//	public static void main(String[] args) {
//
//		index(); // generating blocks of inverted indexes
//	}

	public static void start() {
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
		spimiIndex(postings, indxFileCounter++);
	}

	private static void spimiIndex(ArrayList<Posting> postings,
			int indxFileCounter) {
		TreeMap<String, ArrayList<DocIdTermFrequencyPair>> invertedIndex = new TreeMap<String, ArrayList<DocIdTermFrequencyPair>>();
		// extract terms
		for (Posting posting : postings) {
			posting.body = Strings.normalize(posting.body, true, true, true);
			String[] terms = posting.body.split(" ");
			terms = Strings.cleanse(terms);
			HashMap<String,Integer> tokens = Strings.tokenize(terms);
			// generate inverted index
			invertedIndex = updateInvertedIndex(invertedIndex, tokens,
					posting.id);
		}

		// no need to sort. TreeMap is already sorted
		// write index to file
		writeInvertedIndexToFile(invertedIndex, indxFileCounter);

	}

	private static TreeMap<String, ArrayList<DocIdTermFrequencyPair>> updateInvertedIndex(
			TreeMap<String, ArrayList<DocIdTermFrequencyPair>> invertedIndex,
			HashMap<String,Integer> tokens, int docId) {
		for (Map.Entry<String, Integer> token : tokens.entrySet()) {
			ArrayList<DocIdTermFrequencyPair> docIdFrequencyPairs = new ArrayList<DocIdTermFrequencyPair>();
			if (invertedIndex.containsKey(token.getKey())) {
				docIdFrequencyPairs = invertedIndex.get(token.getKey());
			}
			docIdFrequencyPairs.add(new DocIdTermFrequencyPair (docId, token.getValue()));
			invertedIndex.put(token.getKey(), docIdFrequencyPairs);
		}
		return invertedIndex;
	}

	private static void writeInvertedIndexToFile(
			TreeMap<String, ArrayList<DocIdTermFrequencyPair>> invertedIndex,
			int indxFileCounter) {
		StringBuilder data = new StringBuilder();
		for (Map.Entry<String, ArrayList<DocIdTermFrequencyPair>> entry : invertedIndex.entrySet()) {
			String key = (String) entry.getKey();
			ArrayList<DocIdTermFrequencyPair> value = (ArrayList<DocIdTermFrequencyPair>) entry.getValue();
			data.append(key + Constants.KEY_VALUE_SEPARATOR + value.toString());
			data.append(System.getProperty("line.separator"));
		}//I expect ->    term | (doc,freq) , (doc,freq) ,...
		TextFile.write(Constants.INDIVIDUAL_INDEXES_LOCATION_ON_DISK, "indx"
				+ indxFileCounter, data.toString());

	}

}

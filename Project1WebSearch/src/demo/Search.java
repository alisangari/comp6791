package demo;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Collections;

import services.bm25.BM25;
import utility.Strings;
import utility.file.RandomAccessFile;
import contract.Constants;
import domain.DocIdBM25Relevance;
import domain.DocIdFrequencyPair;
import domain.Posting;

public class Search {

	public static void main(String[] args) {
		try {
			search("jimmy carter");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static ArrayList<DocIdBM25Relevance> search(String searchStr)
			throws IOException {
		// String searchStr = "jimmy carter";
		String[] searchQuery = searchStr.split(" ");

		ArrayList<DocIdFrequencyPair[]> lists = new ArrayList<>();

		String dirMerged = Constants.MERGED_INDEX_LOCATION_ON_DISK;
		String mergedFileName = "merged";

		for (int i = 0; i < searchQuery.length; i++) {
			FileReader r1 = new FileReader(dirMerged + mergedFileName);
			LineNumberReader reader1 = new LineNumberReader(r1);
			String line1 = reader1.readLine();

			while (line1 != null) {
				String[] arr1 = line1.split("\\"
						+ Constants.KEY_VALUE_SEPARATOR);
				if (arr1[0].equalsIgnoreCase(searchQuery[i])) {

					lists.add(Strings
							.getDocidTermfrequenciesFromString(arr1[1]));
					reader1.close();
					break;
				}
				line1 = reader1.readLine();
			}
		}

		ArrayList<DocIdBM25Relevance> combined = new ArrayList<DocIdBM25Relevance>();
		for (int i = 0; i < lists.size(); i++) {
			combined = combine(combined, lists.get(i));
			combined = order(combined);

		}
		return combined;
	}

	private static ArrayList<DocIdBM25Relevance> order(
			ArrayList<DocIdBM25Relevance> combined) {
		ArrayList<DocIdBM25Relevance> sorted = new ArrayList<DocIdBM25Relevance>();
		Collections.sort(combined);
		for (int i = combined.size() - 1; i >= 0; i--) {
			sorted.add(combined.get(i));
		}
		return sorted;
	}

	private static ArrayList<DocIdBM25Relevance> combine(
			ArrayList<DocIdBM25Relevance> combined,
			DocIdFrequencyPair[] docIdFrequencyPairs) {
		BM25 bm25 = BM25.getInstance();
		for (DocIdFrequencyPair pair : docIdFrequencyPairs) {
			int score = calcScore(pair, bm25, docIdFrequencyPairs.length);
			DocIdBM25Relevance morphedPair = new DocIdBM25Relevance(
					pair.getDocid(), score);

			if (!contains(combined, morphedPair)) {
				combined.add(morphedPair);
			} else {
				update(combined, morphedPair);
			}
		}
		return combined;
	}

	private static int calcScore(DocIdFrequencyPair pair, BM25 bm25, int dft) {

		int k1 = bm25.getK1();
		int tf_td = pair.getFrequency();
		float b = bm25.getB();
		int N = bm25.getN();
		int L_ave = bm25.getL_ave();

		Object obj = RandomAccessFile.read(Constants.DOCUMENT_LOCATION_ON_DISK,
				new Integer(pair.getDocid()).toString());
		if (!(obj instanceof Posting)) {
			return 0;
		}
		int L_d = ((Posting) obj).body.length();

		double top = (k1 + 1) * tf_td;
		double bottom = k1 * ((1 - b) + b * (L_d / L_ave)) + tf_td;
		double firstPart = (N / dft);

		return new Double(firstPart * (top / bottom)).intValue();
	}

	private static ArrayList<DocIdBM25Relevance> update(
			ArrayList<DocIdBM25Relevance> combined, DocIdBM25Relevance pair) {

		for (int i = 0; i < combined.size(); i++) {
			if (combined.get(i).getDocid() == pair.getDocid()) {
				DocIdBM25Relevance p = combined.get(i);
				p.setRelevance(p.getRelevance() + pair.getRelevance());
				combined.set(i, p);
				break;
			}
		}
		return combined;
	}

	private static boolean contains(ArrayList<DocIdBM25Relevance> combined,
			DocIdBM25Relevance pair) {

		for (int i = 0; i < combined.size(); i++) {
			if (combined.get(i).getDocid() == pair.getDocid()) {
				return true;
			}
		}
		return false;
	}

}

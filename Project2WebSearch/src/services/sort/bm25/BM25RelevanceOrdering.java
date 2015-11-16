package services.sort.bm25;

import java.util.ArrayList;
import java.util.Collections;

import utility.file.RandomAccessFile;
import contract.Constants;
import domain.DocIdBM25RelevancePair;
import domain.DocIdTermFrequencyPair;
import domain.Posting;

public class BM25RelevanceOrdering {

	public static ArrayList<String> combineAndSort(
			ArrayList<DocIdTermFrequencyPair[]> lists) {
		ArrayList<DocIdBM25RelevancePair> combined = new ArrayList<DocIdBM25RelevancePair>();
		for (int i = 0; i < lists.size(); i++) {
			/*
			 * combine(param1, param2) combines the list of document that
			 * include more than one query term. This is done by calculating
			 * BM25 Relevance score per document and adding it to the previous
			 * document with the same id. The end result would be a single list
			 * of unique documents and their score in no particular order or
			 * more precisely in order of occurrence.
			 */
			combined = combine(combined, lists.get(i));

			/*
			 * THe list is then ordered ascending by implementing Comparable,
			 * and then reversed ordered to place the most relevant on top of
			 * the list and the list relevant at the bottom.
			 */
			combined = order(combined);

		}
		/*
		 * Here am ArayList of Strings is being prepared to be sent back to the user interface.
		 */
		ArrayList<String> res = new ArrayList<String>();
		for (DocIdBM25RelevancePair pair : combined) {
			res.add(pair.toString());
		}
		return res;
	}

	private static ArrayList<DocIdBM25RelevancePair> order(
			ArrayList<DocIdBM25RelevancePair> combined) {
		ArrayList<DocIdBM25RelevancePair> sorted = new ArrayList<DocIdBM25RelevancePair>();
		Collections.sort(combined);
		for (int i = combined.size() - 1; i >= 0; i--) {
			sorted.add(combined.get(i));
		}
		return sorted;
	}

	private static ArrayList<DocIdBM25RelevancePair> combine(
			ArrayList<DocIdBM25RelevancePair> combined,
			DocIdTermFrequencyPair[] docIdFrequencyPairs) {
		BM25 bm25 = BM25.getInstance();
		for (DocIdTermFrequencyPair pair : docIdFrequencyPairs) {
			double score = calcScore(pair, bm25, docIdFrequencyPairs.length);
			DocIdBM25RelevancePair morphedPair = new DocIdBM25RelevancePair(
					pair.getDocid(), score);

			if (!contains(combined, morphedPair)) {
				combined.add(morphedPair);
			} else {
				update(combined, morphedPair);
			}
		}
		return combined;
	}

	/*
	 * This method calculates the relevance based on BM25 equation.
	 */
	private static double calcScore(DocIdTermFrequencyPair pair, BM25 bm25,
			int dft) {

		float k1 = bm25.getK1();
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

		return Math.log(firstPart * (top / bottom));
	}

	private static ArrayList<DocIdBM25RelevancePair> update(
			ArrayList<DocIdBM25RelevancePair> combined,
			DocIdBM25RelevancePair pair) {

		for (int i = 0; i < combined.size(); i++) {
			if (combined.get(i).getDocid() == pair.getDocid()) {
				DocIdBM25RelevancePair p = combined.get(i);
				p.setRelevance(p.getRelevance() + pair.getRelevance());
				combined.set(i, p);
				break;
			}
		}
		return combined;
	}

	private static boolean contains(ArrayList<DocIdBM25RelevancePair> combined,
			DocIdBM25RelevancePair pair) {

		for (int i = 0; i < combined.size(); i++) {
			if (combined.get(i).getDocid() == pair.getDocid()) {
				return true;
			}
		}
		return false;
	}

}

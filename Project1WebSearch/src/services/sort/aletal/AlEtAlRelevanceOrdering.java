package services.sort.aletal;

import java.util.ArrayList;
import java.util.Collections;

import domain.DocIdTermFrequencyPair;

public class AlEtAlRelevanceOrdering {

	public static ArrayList<String> combineAndSort(ArrayList<DocIdTermFrequencyPair[]> lists) {
		
		ArrayList<DocIdTermFrequencyPair> combined = new ArrayList<DocIdTermFrequencyPair>();
		for (int i = 0; i < lists.size(); i++) {
			combined = combine(combined, lists.get(i));
			combined = order(combined);
		}
		ArrayList<String> res = new ArrayList<String>();
		for(DocIdTermFrequencyPair pair: combined){
			res.add(pair.toString());
		}
		return res;
	}

	private static ArrayList<DocIdTermFrequencyPair> order(
			ArrayList<DocIdTermFrequencyPair> combined) {
		ArrayList<DocIdTermFrequencyPair> sorted = new ArrayList<DocIdTermFrequencyPair>();
		Collections.sort(combined);
		for (int i = combined.size() - 1; i >= 0; i--) {
			sorted.add(combined.get(i));
		}
		return sorted;
	}

	private static ArrayList<DocIdTermFrequencyPair> combine(
			ArrayList<DocIdTermFrequencyPair> combined,
			DocIdTermFrequencyPair[] docIdFrequencyPairs) {

		for (DocIdTermFrequencyPair pair : docIdFrequencyPairs) {
			if (!contains(combined, pair)) {
				combined.add(pair);
			} else {
				update(combined, pair);
			}
		}
		return combined;
	}

	private static ArrayList<DocIdTermFrequencyPair> update(
			ArrayList<DocIdTermFrequencyPair> combined, DocIdTermFrequencyPair pair) {

		for (int i = 0; i < combined.size(); i++) {
			if (combined.get(i).getDocid() == pair.getDocid()) {
				DocIdTermFrequencyPair p = combined.get(i);
				p.setFrequency(p.getFrequency() + pair.getFrequency());
				combined.set(i, p);
				break;
			}
		}
		return combined;
	}

	private static boolean contains(ArrayList<DocIdTermFrequencyPair> combined,
			DocIdTermFrequencyPair pair) {

		for (int i = 0; i < combined.size(); i++) {
			if (combined.get(i).getDocid() == pair.getDocid()) {
				return true;
			}
		}
		return false;
	}

}

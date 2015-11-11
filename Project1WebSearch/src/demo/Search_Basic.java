package demo;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import utility.Strings;
import contract.Constants;
import domain.DocIdFrequencyPair;

public class Search {

	public static void main(String[] args) {
		try {
			search("jimmy carter");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	public static ArrayList<DocIdFrequencyPair> search(String searchStr)
	public static ArrayList<DocIdFrequencyPair> search(String searchStr)
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
		
		ArrayList<DocIdFrequencyPair> combined = new ArrayList<DocIdFrequencyPair>();
		for (int i = 0; i < lists.size(); i++) {
			combined = combine(combined, lists.get(i));
			combined = order(combined);
		}
		return combined;
	}

	private static ArrayList<DocIdFrequencyPair> order(
			ArrayList<DocIdFrequencyPair> combined) {
		ArrayList<DocIdFrequencyPair> sorted = new ArrayList<DocIdFrequencyPair>();
		Collections.sort(combined);
		for (int i = combined.size() - 1; i >= 0; i--) {
			sorted.add(combined.get(i));
		}
		return sorted;
	}

	private static ArrayList<DocIdFrequencyPair> combine(
			ArrayList<DocIdFrequencyPair> combined,
			DocIdFrequencyPair[] docIdFrequencyPairs) {

		for (DocIdFrequencyPair pair : docIdFrequencyPairs) {
			if (!contains(combined, pair)) {
				combined.add(pair);
			} else {
				update(combined, pair);
			}
		}
		return combined;
	}

	private static ArrayList<DocIdFrequencyPair> update(
			ArrayList<DocIdFrequencyPair> combined, DocIdFrequencyPair pair) {

		for (int i = 0; i < combined.size(); i++) {
			if (combined.get(i).getDocid() == pair.getDocid()) {
				DocIdFrequencyPair p = combined.get(i);
				p.setFrequency(p.getFrequency() + pair.getFrequency());
				combined.set(i, p);
				break;
			}
		}
		return combined;
	}

	private static boolean contains(ArrayList<DocIdFrequencyPair> combined,
			DocIdFrequencyPair pair) {

		for (int i = 0; i < combined.size(); i++) {
			if (combined.get(i).getDocid() == pair.getDocid()) {
				return true;
			}
		}
		return false;
	}

}

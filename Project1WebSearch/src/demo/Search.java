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

	public static ArrayList<DocIdFrequencyPair> search(String searchStr)
			throws IOException {
		// String searchStr = "jimmy carter";
		String[] searchQuery = searchStr.split(" ");

		// ArrayList<String[]> lists = new ArrayList<>();
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

					// lists.add(Strings.getDocIdsFromString(arr1[1]));
					lists.add(Strings
							.getDocidTermfrequenciesFromString(arr1[1]));
					// System.out.println(arr1[1]);
					// for(String str:Strings.getDocIdsFromString(arr1[1])){
					// System.out.print(str+"-");
					// }
					// System.out.println();
					reader1.close();
					break;
				}
				line1 = reader1.readLine();
			}
		}
		// ArrayList<String> commons = getResults(lists);
		ArrayList<DocIdFrequencyPair> commons = new ArrayList<DocIdFrequencyPair>();
		for(DocIdFrequencyPair pair: lists.get(0)){
			//BM25.orderByRelevance(lists);
			commons.add(pair);
		}
		// System.out.println("commons: " + commons);
		return commons;
	}



}

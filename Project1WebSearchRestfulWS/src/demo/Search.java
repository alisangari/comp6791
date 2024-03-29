package demo;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashSet;

import utility.Strings;
import contract.Constants;

public class Search {

	public static void main(String[] args) {
	}

	public static ArrayList<String> search(String searchStr) throws IOException {
		// String searchStr = "jimmy carter";
		String[] searchQuery = searchStr.split(" ");

		ArrayList<String[]> lists = new ArrayList<>();

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
					lists.add(Strings.getDocIdsFromString(arr1[1]));
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
		ArrayList<String> commons = getResults(lists);
		// System.out.println("commons: " + commons);
		return commons;
	}

	private static ArrayList<String> getResults(ArrayList<String[]> lists) {
		ArrayList<ArrayList<String>> arrLists = new ArrayList<>();

		for (String[] arr : lists) {
			ArrayList<String> list = new ArrayList<>();
			for (String str : arr) {
				list.add(str);
			}
			arrLists.add((ArrayList<String>) list);
		}
		if (arrLists != null) {
			ArrayList<String> commons = arrLists.get(0);

			for (int i = 1; i < arrLists.size(); i++) {
				commons.retainAll(new HashSet<String>(arrLists.get(i)));
			}
			return commons;
		}
		return new ArrayList<String>();
	}

}

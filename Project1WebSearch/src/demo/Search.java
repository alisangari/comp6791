package demo;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashSet;

import contract.Constants;

public class Search {

	public static void main(String[] args) throws IOException {
		String searchStr = "able ability";
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
					lists.add(arr1[1].split(","));
					System.out.println(arr1[1]);
					reader1.close();
					break;
				}
				line1 = reader1.readLine();
			}
		}
		String commons = getResults(lists);
		System.out.println("commons: " + commons);

	}

	private static String getResults(ArrayList<String[]> lists) {
		ArrayList<ArrayList<String>> arrLists = new ArrayList<>();

		for (String[] arr : lists) {
			ArrayList<String> list = new ArrayList<>();
			for(String str: arr){
				list.add(str);
			}
			arrLists.add((ArrayList<String>) list);
		}

		ArrayList<String> commons = arrLists.get(0);
		for (int i = 1; i < arrLists.size(); i++) {
			commons.retainAll(new HashSet<String>(arrLists.get(i)));
		}
		return commons.toString();
	}

}

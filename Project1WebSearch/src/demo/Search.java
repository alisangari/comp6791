package demo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import constants.Constants;

public class Search {

	public static void main(String[] args) throws IOException {
		String searchStr = "a";
		// String[] searchQuery = searchStr.split(" ");

		String dirMerged = Constants.MERGED_INDEX_LOCATION_ON_DISK;
		String mergedFileName = "merged";

		FileReader r1 = new FileReader(dirMerged + mergedFileName);
		LineNumberReader reader1 = new LineNumberReader(r1);
		String line1 = reader1.readLine();

		while (line1 != null) {
			String[] arr1 = line1.split("\\" + Constants.KEY_VALUE_SEPARATOR);
			if (arr1[0].equalsIgnoreCase(searchStr)) {
				System.out.println(arr1[1]);
				break;
			}
			line1 = reader1.readLine();
		}

	}

}

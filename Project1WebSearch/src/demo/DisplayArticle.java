package demo;

import utility.file.RandomAccessFile;
import contract.Constants;
import domain.Posting;

public class DisplayArticle {

	public static void main(String[] args) {
	}

	public static String display(String fileName) {
		// String fileName = "100";

		Object obj = RandomAccessFile.read(Constants.DOCUMENT_LOCATION_ON_DISK,
				fileName);
		if (obj instanceof Posting) {
			// System.out.println(((Posting) obj).body);
			return ((Posting) obj).body;
		}
		return "";
	}

}

package demo;

import utility.file.RandomAccessFile;
import contract.Constants;
import domain.Posting;

public class DisplayArticle {

	/**
	 * given a document id, this method returns the article for security issue.
	 */
	public static String display(String fileName) {
		Object obj = RandomAccessFile.read(Constants.DOCUMENT_LOCATION_ON_DISK,
				fileName);
		if (obj instanceof Posting) {
			// System.out.println(((Posting) obj).body);
			return ((Posting) obj).body;
		}
		return "";
	}

	public static String displayTitle(String fileName) {
		Object obj = RandomAccessFile.read(Constants.DOCUMENT_LOCATION_ON_DISK,
				fileName);
		if (obj instanceof Posting) {
			// System.out.println(((Posting) obj).body);
			return ((Posting) obj).title;
		}
		return "";
	}

}

package demo;

import util.file.RandomAccessFile;
import constants.Constants;
import domain.Posting;

public class DisplayArticle {

	public static void main(String[] args) {
		
		String fileName = "100";
		
		Object obj = RandomAccessFile.read(
				Constants.DOCUMENT_LOCATION_ON_DISK, fileName);
		if (obj instanceof Posting) {
			System.out.println(((Posting) obj).body);
		}

	}

}

package demo;

import java.util.ArrayList;

import services.extraction.ExtractPosting;
import utility.file.GeneralFile;
import utility.file.RandomAccessFile;
import utility.file.TextFile;
import contract.Constants;
import domain.Posting;

public class ExtractArticles {

	public static void main(String[] args) {
		firstAct(); // pre-processing of documents and splitting them into
		// individual random access files.
	}

	/**
	 * Reads all raw files, extracts all articles from them, and saves each
	 * article (as a Posting object) separately into a file.
	 */
	private static void firstAct() {
		ArrayList<String> fileNames = GeneralFile
				.getFilesList(Constants.FILE_LOCATION_ON_DISK);
		ArrayList<Posting> postings = new ArrayList<Posting>();
		for (String fileName : fileNames) {
			postings = ExtractPosting
					.extract(fileName, TextFile.read(fileName));
			for (Posting posting : postings) {
				RandomAccessFile.write(posting,
						contract.Constants.DOCUMENT_LOCATION_ON_DISK,
						new Integer(posting.id).toString());
			}
		}

	}

}

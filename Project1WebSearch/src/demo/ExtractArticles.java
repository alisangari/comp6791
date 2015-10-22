package demo;

import java.util.ArrayList;

import services.extraction.ExtractPosting;
import util.file.GeneralFile;
import util.file.RandomAccessFile;
import util.file.TextFile;
import contract.Constants;
import domain.Posting;

public class ExtractArticles {

	private static void main(String[] args) {
		firstAct(); // pre-processing of documents and splitting them into
		// individual random access files.
	}

	/**
	 * Reads all raw files, extracts all articles from them, and saves each
	 * article (as an object) separately into a file.
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
				// RandomAccessFile.read(
				// contants.Constants.DOCUMENT_LOCATION_ON_DISK,
				// new Integer(posting.id).toString());
			}
		}

	}

}

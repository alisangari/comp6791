package demo;

import utility.file.RandomAccessFile;
import contract.Constants;
import domain.DocIdBM25RelevancePair;
import domain.DocIdTermFrequencyPair;
import domain.Posting;

public class DisplayArticle {
	
	public static String display(String pairStr, int type){
		String fileName = "";
		if(type==1){//BM25
			fileName = new Integer(new DocIdBM25RelevancePair(pairStr)
			.getDocid()).toString();	
		}
		if(type==2){//AlEtAl
			fileName = new Integer(new DocIdTermFrequencyPair(pairStr)
			.getDocid()).toString();
		}
		return display(fileName);
	}


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

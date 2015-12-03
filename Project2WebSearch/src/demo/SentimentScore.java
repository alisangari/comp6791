package demo;

import java.io.File;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

import services.sentiment.AFINN;
import utility.file.GeneralFile;
import utility.file.TextFile;
import contract.Constants;
import domain.DeptSentimentScorePair;

public class SentimentScore {

	public static void main(String[] args) {
		AFINN afinn = AFINN.getInstance();
		ArrayList<String> depts = new ArrayList<String>();
		SortedSet<DeptSentimentScorePair> comparisonLookupTable = new TreeSet<DeptSentimentScorePair>();

		depts = GeneralFile.getFilesList(Constants.DEPTS_LOCATION_ON_DISK);
		for (String dept : depts) {
			ArrayList<File> docs = new ArrayList<File>();
			GeneralFile.getAllFiles(Constants.DEPTS_LOCATION_ON_DISK + dept,
					docs);
			int totalSentimentScore = 0;
			for (int i = 0; i < docs.size(); i++) {
				if (docs.get(i).isFile()) {
					String article = TextFile.read(docs.get(i));
					int score = afinn.calcSentimentScore(article.split(" "));
					totalSentimentScore += score;
				}
//				System.out.printf(
//						"Total sentiment score for \"%s\" (%d pages) is %d\n",
//						dept, docs.size(), totalSentimentScore);
			}
			comparisonLookupTable.add(new DeptSentimentScorePair(dept, totalSentimentScore));
		}
		System.out.println("The most positive department is "+comparisonLookupTable.last());
		for(DeptSentimentScorePair pair: comparisonLookupTable){
			System.out.printf(
					"Total sentiment score for \"%s\" is %d\n",
					pair.getDept(), pair.getScore());
		}
	}
}

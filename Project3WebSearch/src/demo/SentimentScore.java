package demo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import services.sentiment.AFINN;
import utility.file.GeneralFile;
import utility.file.TextFile;
import contract.Constants;
import domain.DeptSentimentScorePair;
import domain.DocIdFrequencyPair;

public class SentimentScore {

	public static void main(String[] args) {
		indexedDocAnalysis();
		System.out.println("====================");
		directDocAnalysis();
	}

	private static void indexedDocAnalysis() {
		AFINN afinn = AFINN.getInstance();
		ArrayList<String> depts = new ArrayList<String>();
		SortedSet<DeptSentimentScorePair> comparisonLookupTable = new TreeSet<DeptSentimentScorePair>();

		depts = GeneralFile
				.getFilesList(Constants.DEPTS_INDEX_LOCATION_ON_DISK);
		for (String dept : depts) {
			HashMap<String, DocIdFrequencyPair[]> index = TextFile
					.readIndex(Constants.DEPTS_INDEX_LOCATION_ON_DISK + dept
							+ "/" + "fullindex.csv");
			int totalSentimentScore = 0;
			for (Map.Entry entry : index.entrySet()) {
				int score = afinn.sentimentScore((String) entry.getKey());
				if (score != 0) {
					int frequencySum = 0;
//					System.out.println("word:" + (String) entry.getKey());
//					System.out.println(">" + frequencySum);
					DocIdFrequencyPair[] pairs = (DocIdFrequencyPair[]) entry
							.getValue();
					for (int i = 0; i < pairs.length; i++) {
						frequencySum += pairs[i].getFrequency();
//						System.out.println("--" + frequencySum);
					}
//					System.out.println("-->" + frequencySum);
//					System.out.println("score-->" + score);
//					System.out.println("score * frequencySum-->" + score
//							* frequencySum);
					totalSentimentScore += score * frequencySum;
				}
			}
			// System.out.printf(
			// "Total sentiment score for \"%s\" is %d\n",
			// dept, totalSentimentScore);
			comparisonLookupTable.add(new DeptSentimentScorePair(dept,
					totalSentimentScore));
		}
		System.out.println("The most positive department is "
				+ comparisonLookupTable.last());
		System.out.println();
		for (DeptSentimentScorePair pair : comparisonLookupTable) {
			System.out.printf("Total sentiment score for \"%s\" is %d\n",
					pair.getDept(), pair.getScore());
		}
	}

	private static void directDocAnalysis() {
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
				// System.out.printf(
				// "Total sentiment score for \"%s\" (%d pages) is %d\n",
				// dept, docs.size(), totalSentimentScore);
			}
			comparisonLookupTable.add(new DeptSentimentScorePair(dept,
					totalSentimentScore));
		}
		System.out.println("The most positive department is "
				+ comparisonLookupTable.last());
		for (DeptSentimentScorePair pair : comparisonLookupTable) {
			System.out.printf("Total sentiment score for \"%s\" is %d\n",
					pair.getDept(), pair.getScore());
		}

	}
}

package demo;

import java.util.ArrayList;

import services.sentiment.AFINN;
import utility.file.GeneralFile;
import utility.file.TextFile;
import contract.Constants;

public class SentimentScore {

	public static void main(String[] args) {
//		long startTime = System.currentTimeMillis();
		AFINN afinn = AFINN.getInstance();
		ArrayList<String> depts = new ArrayList<String>();
		ArrayList<String> docs = new ArrayList<String>();
		depts = GeneralFile.getFilesList(Constants.DEPTS_LOCATION_ON_DISK);
		for (String dept : depts) {
			docs = GeneralFile.getFilesList(Constants.DEPTS_LOCATION_ON_DISK
					+ dept);

			int totalSentimentScore = 0;
			for (int i = 0; i < docs.size(); i++) {
				String article = TextFile.read(Constants.DEPTS_LOCATION_ON_DISK
						+ dept + "/", docs.get(i));
				int score = afinn.calcSentimentScore(article.split(" "));
				totalSentimentScore += score;
			}
			System.out.printf("Total sentiment score for \"%s\" is %d\n", dept,
					totalSentimentScore);
			// long estimatedTime = System.currentTimeMillis() - startTime;
			// String timeSpent = String.format(
			// "%02d min, %02d sec",
			// TimeUnit.MILLISECONDS.toMinutes(estimatedTime),
			// TimeUnit.MILLISECONDS.toSeconds(estimatedTime)
			// - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
			// .toMinutes(estimatedTime)));
			// System.out.println();
			// System.out.println(timeSpent);
		}
	}
}

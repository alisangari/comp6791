package services.extraction;

import java.util.ArrayList;

import utility.IdGenerator;
import domain.Posting;

public class ExtractPosting {

	public static ArrayList<Posting> extract(String fileName, String fileContent) {
		ArrayList<String> rawArticles = extractRawArticles(fileContent);
		ArrayList<Posting> articles = extractPostings(fileName, rawArticles);
		return articles;
	}

	private static ArrayList<Posting> extractPostings(String fileName, ArrayList<String> rawArticles) {
		ArrayList<Posting> articles = new ArrayList<>();
		for (int i=0; i<rawArticles.size(); i++) {
			int id = IdGenerator.getInstance().getNextIncrementalId();
			String url = fileName;
			String title = extractTitle(rawArticles.get(i));
			String body = extractContent(rawArticles.get(i));
			Posting posting = new Posting(id, url, title, body);
			articles.add(posting);
		}
		return articles;
	}

	private static String extractContent(String str) {
		String s1 = "<BODY>";
		String s2 = "</BODY>";
		int start = -1;
		int end = -1;

		start = str.indexOf(s1) + s1.length();
		end = str.indexOf(s2, start);
		if (end <= start) {
			return "";
		}
		return str.substring(start, end);
	}

	private static String extractTitle(String str) {
		String s1 = "<TITLE>";
		String s2 = "</TITLE>";
		int start = -1;
		int end = -1;

		start = str.indexOf(s1)  + s1.length();
		end = str.indexOf(s2, start);
		if (end <= start) {
			return "";
		}

		return str.substring(start, end);
	}

	private static ArrayList<String> extractRawArticles(String fileContent) {
		ArrayList<String> aList = new ArrayList<>();
		String rawArticle = "";
		String s1 = "<REUTERS";
		String s2 = ">";
		String s3 = "</REUTERS>";
		int start = -1;
		int end = -1;
		int pointer = 0;
		while (pointer > -1) {
			pointer = fileContent.indexOf(s1, pointer);
			if (pointer < 0) {
				break;
			}
			start = fileContent.indexOf(s2, pointer) + 1;
			end = fileContent.indexOf(s3, start);
			pointer = end;

			rawArticle = fileContent.substring(start, end);
			aList.add(rawArticle);
		}

		return aList;
	}
}

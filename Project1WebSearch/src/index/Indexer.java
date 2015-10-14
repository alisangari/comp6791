package index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import article.Article;

public class Indexer {

	public static HashMap<String, ArrayList<Integer>> index(Article article) {

		article.content = normalize(article.content, true, true, true);
		String[] terms = article.content.split(" ");
		terms = Cleanse(terms);
//		terms = normalize(terms, true);
		HashSet<String> tokens = tokenize(terms);
		HashMap<String, ArrayList<Integer>> res = new HashMap<String, ArrayList<Integer>>();

		for (String token : tokens) {
			res.put(token, new ArrayList<Integer>());
		}

		for (String token : tokens) {
			for (int i = 0; i < terms.length; i++) {
				if (token.equals(terms[i])) {
					res.get(token).add(i);
				}
			}
		}

		return res;
	}

	private static String[] Cleanse(String[] terms) {
		String[] temp = new String[terms.length];
		int c = 0;
		for (String str : terms) {
			if (!str.trim().equals("")) {
				temp[c] = str;
				c++;
			}
		}
		String[] res = new String[c];
		for (int i = 0; i < c; i++) {
			res[i] = temp[i];
		}
		return res;
	}

	private static String normalize(String str, boolean toLower,
			boolean removeDigits, boolean removeNonAlphaNumeric) {
		if (removeNonAlphaNumeric) {
			str = str.replaceAll("[,._\\-()\"'&#;]", "");
		}
		if (removeDigits) {
			str = str.replaceAll("[0-9]", "");
		}
		if (toLower) {
			str = str.toLowerCase();
		}
		return str;
	}

	private static String[] normalize(String[] terms, boolean toLower) {
		if (toLower) {
			for (int i = 0; i < terms.length; i++) {
				terms[i] = terms[i].toLowerCase();
			}
		}
		return terms;
	}

	private static HashSet<String> tokenize(String[] terms) {
		HashSet<String> tokens = new HashSet<String>();
		for (String str : terms) {
			tokens.add(str);
		}
		return tokens;
	}
}

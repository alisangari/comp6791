package index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import article.Article;

public class Indexer {

	public static HashMap<String, ArrayList<Integer>> index(Article article) {
		String[] terms = article.content.split(" ");
		terms = normalize(terms);
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

	private static String[] normalize(String[] terms) {
		for (String str : terms) {
			str = str.toLowerCase();
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

package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import domain.Posting;

public class Indexer {

	public static HashMap<String, ArrayList<Integer>> index(Posting article) {

		article.body = normalize(article.body, true, true, true);
		String[] terms = article.body.split(" ");
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


	private static String[] normalize(String[] terms, boolean toLower) {
		if (toLower) {
			for (int i = 0; i < terms.length; i++) {
				terms[i] = terms[i].toLowerCase();
			}
		}
		return terms;
	}

	
}

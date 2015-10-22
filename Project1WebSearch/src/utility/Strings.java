package utility;

import java.util.HashSet;

public class Strings {

	public static String[] cleanse(String[] terms) {
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

	public static String normalize(String str, boolean toLower,
			boolean removeDigits, boolean removeNonAlphaNumeric) {
		if (removeNonAlphaNumeric) {
			str = str.replaceAll("[,._\\-()\"'&#;<>/$*+:]", " ");
		}
		if (removeDigits) {
			str = str.replaceAll("[0-9]", " ");
		}
		if (toLower) {
			str = str.toLowerCase();
		}
		return str;
	}

	public static HashSet<String> tokenize(String[] terms) {
		HashSet<String> tokens = new HashSet<String>();
		for (String str : terms) {
			tokens.add(str);
		}
		return tokens;
	}
}

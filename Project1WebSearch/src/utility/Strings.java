package utility;

import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import services.stopwords.StopWordDictionary;
import domain.DocIdFrequencyPair;

public class Strings {

	// public static String[] getDocIdsFromString(String str){
	// str = str.replaceAll("\\[", "");
	// str = str.replaceAll("\\]", "");
	// String[] ids = str.split(",");
	// for(int i=0; i<ids.length; i++){
	// ids[i] = ids[i].trim();
	// }
	// return ids;
	// }

	public static DocIdFrequencyPair[] getDocidTermfrequenciesFromString(
			String str) {
		str = str.replaceAll("\\[", "");
		str = str.replaceAll("\\]", "");
		DocIdFrequencyPair[] res;
		String[] ids = str.split(",");
		res = new DocIdFrequencyPair[ids.length];
		for (int i = 0; i < ids.length; i++) {
			ids[i] = ids[i].replaceAll("[(]", "");
			ids[i] = ids[i].replaceAll("[)]", "");
			res[i] = new DocIdFrequencyPair(ids[i].trim());
		}
		return res;
	}

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
			boolean removeDigits, boolean removeNonAlphaNumeric, boolean removeAllStopWords) {
		if (removeNonAlphaNumeric) {
			str = str.replaceAll("[,._\\-()\"'&#;<>/$*+:]", " ");
		}
		if (removeDigits) {
			str = str.replaceAll("[0-9]", " ");
		}
		if(removeAllStopWords{
			for(String str: StopWordDictionary.getInstance().getDictionary() ){
			Pattern p = Pattern.compile(str);  // pattern is defined here
			Matcher m = p.matcher(str);
			}
		}
		if (toLower) {
			str = str.toLowerCase();
		}
		return str;
	}

	public static HashMap<String, Integer> tokenize(String[] terms) {
		HashMap<String, Integer> tokens = new HashMap<String, Integer>();
		for (String str : terms) {
			if (tokens.containsKey(str)) {
				int frequency = tokens.get(str) + 1;
				tokens.put(str, frequency);
			} else {
				tokens.put(str, 1);
			}
			// tokens.add(str);
		}
		return tokens;
	}
}

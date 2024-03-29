package utility;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import opennlp.tools.stemmer.PorterStemmer;

import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import services.stopwords.StopWordDictionary;
import domain.DocIdTermFrequencyPair;

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

	public static DocIdTermFrequencyPair[] getDocidTermfrequenciesFromString(
			String str) {
		str = str.replaceAll("\\[", "");
		str = str.replaceAll("\\]", "");
		DocIdTermFrequencyPair[] res;
		String[] ids = str.split(",");
		res = new DocIdTermFrequencyPair[ids.length];
		for (int i = 0; i < ids.length; i++) {
			ids[i] = ids[i].replaceAll("[(]", "");
			ids[i] = ids[i].replaceAll("[)]", "");
			res[i] = new DocIdTermFrequencyPair(ids[i].trim());
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

	public static String[] stem(String[] terms){
		PorterStemmer stem = new PorterStemmer();
		Set<String> stemmedTerms = new HashSet<>();
		for(String str : terms){
			stemmedTerms.add(stem.stem(str));
		}
		String[] res = new String[stemmedTerms.size()];
		return stemmedTerms.toArray(res);
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

	public static HashMap<String, Integer> tokenize(String[] terms) {
		HashMap<String, Integer> tokens = new HashMap<String, Integer>();
		for (String str : terms) {
			if (!StopWordDictionary.getInstance().contains(str)) {
				if (tokens.containsKey(str)) {
					int frequency = tokens.get(str) + 1;
					tokens.put(str, frequency);
				} else {
					tokens.put(str, 1);
				}
			}
		}
		return tokens;
	}
	
	public static String stripOutTags(String html){
		StringBuilder sb = new StringBuilder();
		Document doc = Jsoup.parse(html);
		HtmlToPlainText htmlToPlainText = new HtmlToPlainText();
		Elements elems = doc.getElementsByTag("body");
		for(Element elem: elems){
			sb.append(htmlToPlainText.getPlainText(elem));
		}
		return sb.toString();
	}
}

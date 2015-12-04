package utility;

import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

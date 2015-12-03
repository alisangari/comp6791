package services.stemmer;

import opennlp.tools.stemmer.PorterStemmer;

public class LuceneStemmer {

	public static void main(String[] args) {
		String term = "thoughtful";
		String s = new LuceneStemmer().stemTerm(term);
		System.out.println(s);
	}

	private String stemTerm (String term) {
		PorterStemmer stem = new PorterStemmer();
		 return stem.stem(term);// setCurrent(term);
	}
}

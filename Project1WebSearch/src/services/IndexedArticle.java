package services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import domain.Posting;

public class IndexedArticle implements Serializable {
	Posting article;
	HashMap<String, ArrayList<Integer>> index;
	
	private IndexedArticle(){
		article = new Posting();
		index = new HashMap<String, ArrayList<Integer>>();
	}
	
	public IndexedArticle(Posting article){
		this.article = article;
		index = Indexer.index(article);
	}

	public IndexedArticle(Posting article, HashMap<String, ArrayList<Integer>> index){
		this.article = article;
		this.index = index;
	}

}

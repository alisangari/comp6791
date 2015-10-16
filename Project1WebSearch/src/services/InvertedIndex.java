package services;

import java.util.ArrayList;
import java.util.HashMap;

import domain.Posting;

public class InvertedIndex {

	private HashMap<String, ArrayList<Posting>> invertedIndex;
	
	public InvertedIndex(){
		invertedIndex = new HashMap<String,ArrayList<Posting>>();
	}
	
	public void update(IndexedArticle indexedArticle){
		for (String key : indexedArticle.index.keySet()) {
			if(invertedIndex.containsKey(key)){
				ArrayList<Posting> t = invertedIndex.get(key);
				t.add(indexedArticle.article);
				invertedIndex.replace(key, invertedIndex.replace(key, t));
			}
			else{
				invertedIndex.put(key, new ArrayList<Posting>());
			}
		}

	}
}

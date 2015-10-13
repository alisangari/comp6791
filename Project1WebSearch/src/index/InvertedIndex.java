package index;

import java.util.ArrayList;
import java.util.HashMap;

import article.Article;

public class InvertedIndex {

	private HashMap<String, ArrayList<Article>> invertedIndex;
	
	public InvertedIndex(){
		invertedIndex = new HashMap<String,ArrayList<Article>>();
	}
	
	public void update(IndexedArticle indexedArticle){
		for (String key : indexedArticle.index.keySet()) {
			if(invertedIndex.containsKey(key)){
				ArrayList<Article> t = invertedIndex.get(key);
				t.add(indexedArticle.article);
				invertedIndex.replace(key, invertedIndex.replace(key, t));
			}
			else{
				invertedIndex.put(key, new ArrayList<Article>());
			}
		}

	}
}

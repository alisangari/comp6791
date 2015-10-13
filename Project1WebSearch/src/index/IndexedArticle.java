package index;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import article.Article;

public class IndexedArticle implements Serializable {
	Article article;
	HashMap<String, ArrayList<Integer>> index;
	
	private IndexedArticle(){
		article = new Article();
		index = new HashMap<String, ArrayList<Integer>>();
	}
	
	public IndexedArticle(Article article){
		this.article = article;
		index = Indexer.index(article);
	}

	public IndexedArticle(Article article, HashMap<String, ArrayList<Integer>> index){
		this.article = article;
		this.index = index;
	}

}

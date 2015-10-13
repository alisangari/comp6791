package demo;

import index.IndexedArticle;

import java.util.ArrayList;

import article.Article;
import article.ExtractArticle;
import file.Constants;
import file.MyFileReader;
import file.MyFileWriter;

public class Demo {

	public static void main(String[] args) throws Exception {
		ArrayList<String> fileNames = MyFileReader.getFilesList();
		System.out.println(fileNames);
		ArrayList<Article> articles = new ArrayList<Article>();
		int i = 0;
		for (String fileName : fileNames) {
			i++;
			articles = ExtractArticle.extract(fileName,MyFileReader.readTextFile(fileName));
			ArrayList<String> output = new ArrayList<>();
			int j = 0;
			for (Article article : articles) {
				output.add(article.toString());
			}
			MyFileWriter.write(Constants.FILE_LOCATION_ON_DISK + "output" + i + j + ".csv", output);
		}
		i=0;
		for(Article article: articles){
			IndexedArticle indexed = new IndexedArticle(article);
			i++;
		}
	}
}

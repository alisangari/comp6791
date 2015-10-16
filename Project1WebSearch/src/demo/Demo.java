package demo;

import java.util.ArrayList;

import services.ExtractPosting;
import services.IndexedArticle;
import domain.Posting;
import file.Constants;
import file.MyFileReader;
import file.MyFileWriter;

public class Demo {

	public static void main(String[] args) throws Exception {
		ArrayList<String> fileNames = MyFileReader.getFilesList();
		System.out.println(fileNames);
		ArrayList<Posting> postings = new ArrayList<Posting>();
		int i = 0;
		for (String fileName : fileNames) {
			i++;
			postings = ExtractPosting.extract(fileName,MyFileReader.readTextFile(fileName));
			ArrayList<String> output = new ArrayList<>();
			int j = 0;
			for (Posting article : postings) {
				output.add(article.toString());
			}
			MyFileWriter.write(Constants.FILE_LOCATION_ON_DISK + "output" + i + j + ".csv", output);
		}
		i=0;
		for(Posting article: postings){
			IndexedArticle indexed = new IndexedArticle(article);
			i++;
		}
	}
}

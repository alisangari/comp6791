package demo;

import java.util.ArrayList;

import services.IndexedArticle;
import services.extraction.ExtractPosting;
import util.file.GeneralFile;
import util.file.TextFile;
import domain.Posting;
import file.Constants;
import file.MyFileWriter;

public class Demo {

	public static void main(String[] args) throws Exception {
		ArrayList<String> fileNames = GeneralFile.getFilesList(Constants.FILE_LOCATION_ON_DISK);
		System.out.println(fileNames);
		ArrayList<Posting> postings = new ArrayList<Posting>();
		int i = 0;
		for (String fileName : fileNames) {
			i++;
			postings = ExtractPosting.extract(fileName,TextFile.read(fileName));
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

package demo;

import java.util.ArrayList;

import file.Constants;
import file.FileWrite;
import file.MyFileReader;

public class Demo {

	public static void main(String[] args) throws Exception {
		ArrayList<String> fileNames = MyFileReader.getFilesList();
		System.out.println(fileNames);
		int i = 0;
		for (String fileName : fileNames) {
			i++;
			ArrayList<Article> articles = ExtractArticle.extract(fileName,MyFileReader.readTextFile(fileName));
			ArrayList<String> output = new ArrayList<>();
			int j = 0;
			for (Article article : articles) {
				output.add(article.toString());
			}
			FileWrite.write(Constants.FILE_LOCATION_ON_DISK + "output" + i + j + ".csv", output);
		}
	}
}

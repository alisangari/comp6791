package util.file;

import java.io.File;
import java.util.ArrayList;

import file.Constants;

public class GeneralFile {

	public static ArrayList<String> getFilesList(String dir) {
		ArrayList<String> fileNames = new ArrayList<String>();
		File folder = new File(dir);
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
			fileNames.add(file.getName());
		}
		return fileNames;
	}
}

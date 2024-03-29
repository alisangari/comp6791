package utility.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

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
	
	public static void getAllFiles(String directoryName, ArrayList<File> files) {
	    File directory = new File(directoryName);

	    // get all the files from a directory
	    File[] fList = directory.listFiles();
	    for (File file : fList) {
	        if (file.isFile()) {
	            files.add(file);
	        } else if (file.isDirectory()) {
	        	getAllFiles(file.getAbsolutePath(), files);
	        }
	    }
	}

	public static boolean rename(String oldPathName, String newPathName) {
		File oldFile = new File(oldPathName);
		File newFile = new File(newPathName);

		if (newFile.exists()) {
			delete(newPathName);
		}
		return oldFile.renameTo(newFile);
	}

	public static void delete(String filePathName) {
		File file = new File(filePathName);
		try {
			Files.deleteIfExists(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}

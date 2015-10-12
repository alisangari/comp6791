package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MyFileReader {

	public Map<String, String> readFiles(ArrayList<String> fileNames, int startIndex, int endIndex) {
		Map<String, String> files = new HashMap<String, String>();
		String fileContent = "";
		for (int i = startIndex; i < endIndex; i++) {
			fileContent = readFile(Constants.FILE_LOCATION_ON_DISK + fileNames.get(i));
			if (!fileContent.trim().equalsIgnoreCase("")) {
				files.put(fileNames.get(i), fileContent);
			}
		}
		return files;
	}

	public static String readFile(String fileName) {
		StringBuilder sb = new StringBuilder();
		try {
			Scanner sc = new Scanner(new File(fileName));
			while (sc.hasNextLine()) {
				sb.append(sc.nextLine());
			}
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static File getFile(String fileName) {
		return new File(Constants.FILE_LOCATION_ON_DISK + fileName);
	}

	public static ArrayList<String> getFilesList() {
		ArrayList<String> fileNames = new ArrayList<String>();
		File folder = new File(Constants.FILE_LOCATION_ON_DISK);
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
			fileNames.add(file.getName());
		}
		return fileNames;
	}

	public static String readTextFile(String path) {
		StringBuilder content = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(Constants.FILE_LOCATION_ON_DISK + path))) {
			String line;
			while ((line = br.readLine()) != null) {
				content.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return content.toString();
	}
}

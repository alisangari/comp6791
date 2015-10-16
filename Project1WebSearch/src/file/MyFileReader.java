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

	

	
}

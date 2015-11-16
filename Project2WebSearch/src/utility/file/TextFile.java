package utility.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

import contract.Constants;

public class TextFile {

	public static String read(String path) {
		StringBuilder content = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(
				Constants.FILE_LOCATION_ON_DISK + path))) {
			String line;
			while ((line = br.readLine()) != null) {
				content.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return content.toString();
	}

	public static HashSet<String> readAll(String path) {
		HashSet<String> content = new HashSet<>();
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line;
			while ((line = br.readLine()) != null) {
				content.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return content;
	}

	public static boolean append(String destination, String fileName,
			String data) {
		try {
			File dir = new File(destination);
			if (!dir.exists()) {
				dir.mkdir();
			}

			String fname = destination + fileName;
			FileWriter fw = new FileWriter(fname, true); // the true will append
															// the new data
			fw.write(data);// appends the string to the file
			fw.write(System.getProperty("line.separator"));
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean write(String destination, String fileName, String data) {
		try {
			File dir = new File(destination);
			if (!dir.exists()) {
				dir.mkdir();
			}

			String fname = destination + fileName;
			FileWriter fw = new FileWriter(fname, false); // the true will
															// append
															// the new data
			fw.write(data);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}
}

package utility.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import contract.Constants;
import domain.DocIdFrequencyPair;

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

	public static String read(String path, String file) {
		StringBuilder content = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(path + file))) {
			String line;
			while ((line = br.readLine()) != null) {
				content.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return content.toString();
	}

	public static String read(File file) {
		StringBuilder content = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
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

	public static HashMap<String, Integer> readDic(String path) {
		HashMap<String, Integer> content = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] arr = line.split("\\" + Constants.KEY_VALUE_SEPARATOR);
				String key = arr[0].trim();
				Integer val = new Integer(arr[1].trim());
				content.put(key, val);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
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

	public static HashMap<String, DocIdFrequencyPair[]> readIndex(String file) {
		HashMap<String, DocIdFrequencyPair[]> content = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			if(br.readLine()==null){
				return content;
			}
			while ((line = br.readLine()) != null) {
				line = line.replaceAll("\"", "");
				String[] strs = line.split(",");
				DocIdFrequencyPair[] pairs = new DocIdFrequencyPair[strs.length - 1];
				for (int i = 1; i < strs.length; i++) {
					pairs[i-1] = new DocIdFrequencyPair(strs[i]);
				}
				content.put(strs[0], pairs);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return content;
	}
}

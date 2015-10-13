package file;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MyFileWriter {
	public static boolean write(String fName, String str) {
		try {
			PrintWriter writer;
			writer = new PrintWriter(fName, "UTF-8");
			writer.print(str);
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public static boolean write(String fName, String[] strs) {
		try {
			PrintWriter writer = new PrintWriter(fName, "UTF-8");
			for (int i = 0; i < strs.length; i++) {
				writer.println(strs[i]);
			}
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean write(String fName, ArrayList<String> strs) {
		try {
			PrintWriter writer = new PrintWriter(fName, "UTF-8");
			for (int i = 0; i < strs.size(); i++) {
				writer.println(strs.get(i));
			}
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
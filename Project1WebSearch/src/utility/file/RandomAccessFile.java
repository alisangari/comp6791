package utility.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RandomAccessFile {

	public static boolean write(Object o, String destination, String fileName) {
		File dir = new File(destination);
		try {
			if (!dir.exists()) {
				dir.mkdir();
			}
			FileOutputStream fout = new FileOutputStream(destination+fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(o);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static Object read(String location, String fileName) {
		try {
			FileInputStream streamIn = new FileInputStream(location + fileName);
			ObjectInputStream objectinputstream = new ObjectInputStream(
					streamIn);
			Object obj = objectinputstream.readObject();
			objectinputstream.close();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

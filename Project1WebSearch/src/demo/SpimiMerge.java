package demo;

import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.ArrayList;

import util.file.GeneralFile;
import util.file.TextFile;
import contants.Constants;

public class SpimiMerge {

	public static void main(String[] args) {

		// merge indexes
		merge();
	}

	private static String twoFileMerge(String fileName1, String fileName2) {
		String dirMerged = Constants.MERGED_INDEX_LOCATION_ON_DISK;
		String mergedFileName = "merged";

		try {
			FileReader r1 = new FileReader(fileName1);
			FileReader r2 = new FileReader(fileName2);

			LineNumberReader reader1 = new LineNumberReader(r1);
			LineNumberReader reader2 = new LineNumberReader(r2);

			String line1 = reader1.readLine();
			String line2 = reader2.readLine();

			StringBuilder doc = new StringBuilder();
			int i = -1;
			while (line1 != null && line2 != null) {
				i++;
				// do magic
				line1 = line1.replaceAll("\\[", "");
				line1 = line1.replaceAll("\\]", "");

				line2 = line2.replaceAll("\\[", "");
				line2 = line2.replaceAll("\\]", "");

				String[] arr1 = line1.split(",");
				String[] arr2 = line2.split(",");
				String key1 = arr1[0];
				String key2 = arr2[0];
				// String doc = "";
				if (key1.equals(key2)) {
					doc.append(key1 + ",merge both values");
					doc.append("\n");
					line1 = reader1.readLine();
					line2 = reader2.readLine();
				} else if (key1.compareTo(key2) < 0) {
					doc.append(key1 + ", values 1");
					doc.append("\n");
					line1 = reader1.readLine();
				} else {// if (key1.compareTo(key2) > 0) {
					doc.append(key2 + ", values 2");
					doc.append("\n");
					line2 = reader2.readLine();
				}
				if (i >= Constants.BATCH_SIZE) {
					TextFile.append(dirMerged, mergedFileName, doc.toString());
					i = -1;
					doc = new StringBuilder();
				}
			}
			while (line1 != null) {
				i++;
				doc.append(line1);
				doc.append("\n");
				line1 = reader1.readLine();
				if (i >= Constants.BATCH_SIZE) {
					TextFile.append(dirMerged, mergedFileName, doc.toString());
					i = -1;
					doc = new StringBuilder();
				}
			}
			while (line2 != null) {
				i++;
				doc.append(line2);
				doc.append("\n");
				line2 = reader2.readLine();
				if (i >= Constants.BATCH_SIZE) {
					TextFile.append(dirMerged, mergedFileName, doc.toString());
					i = -1;
					doc = new StringBuilder();
				}
			}
			TextFile.append(dirMerged, mergedFileName, doc.toString());
			reader1.close();
			reader2.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		return dirMerged + mergedFileName;
	}

	private static boolean merge() {
		String dir = Constants.INDIVIDUAL_INDEXES_LOCATION_ON_DISK;
		String dirMerged = Constants.MERGED_INDEX_LOCATION_ON_DISK;
		ArrayList<String> fileNames = GeneralFile.getFilesList(dir);
		String merged = dir + fileNames.get(0);
		String intermediaryFileName = dirMerged + "intermediary";

		// for (int i = 1; i < fileNames.size(); i++) {
		GeneralFile.rename(merged, intermediaryFileName);
		merged = twoFileMerge(intermediaryFileName, dir + fileNames.get(2));
		// }

		return false;
	}

}

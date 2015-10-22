package demo.spimi;

import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.TreeSet;

import util.file.GeneralFile;
import util.file.TextFile;
import constants.Constants;

public class SpimiMerge {

	// public static void main(String[] args) {
	//
	// // merge indexes
	// start();
	// }

	public static boolean start() {
		String dir = Constants.INDIVIDUAL_INDEXES_LOCATION_ON_DISK;
		String dirMerged = Constants.MERGED_INDEX_LOCATION_ON_DISK;
		ArrayList<String> fileNames = GeneralFile.getFilesList(dir);
		String merged = dir + fileNames.get(0);
		String intermediaryFileName = dirMerged + "intermediary";

		for (int i = 1; i < fileNames.size(); i++) {
			GeneralFile.rename(merged, intermediaryFileName);
			merged = twoFileMerge(intermediaryFileName, dir + fileNames.get(i));
		}

		return false;
	}

	private static String twoFileMerge(String fileName1, String fileName2) {
		String dirMerged = Constants.MERGED_INDEX_LOCATION_ON_DISK;
		String mergedFileName = "merged";
		GeneralFile.delete(dirMerged + mergedFileName);

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
				if (line1.isEmpty()) {
					line1 = reader1.readLine();
					continue;
				}if (line2.isEmpty()) {
					line2 = reader2.readLine();
					continue;
				}
				line1 = line1.replaceAll("\\[", "");
				line1 = line1.replaceAll("\\]", "");

				line2 = line2.replaceAll("\\[", "");
				line2 = line2.replaceAll("\\]", "");

					String[] arr1 = line1.split("\\"
							+ Constants.KEY_VALUE_SEPARATOR);
					String[] arr2 = line2.split("\\"
							+ Constants.KEY_VALUE_SEPARATOR);

					String[] valArr1 = arr1[1].split(",");
					String[] valArr2 = arr2[1].split(",");

					String key1 = arr1[0];
					String key2 = arr2[0];

					if (key1.equals(key2)) {
						doc.append(key1 + Constants.KEY_VALUE_SEPARATOR
								+ mergeVals(valArr1, valArr2));// ",merge both values"
						doc.append(System.getProperty("line.separator"));
						line1 = reader1.readLine();
						line2 = reader2.readLine();
					} else if (key1.compareTo(key2) < 0) {
						doc.append(key1 + Constants.KEY_VALUE_SEPARATOR
								+ mergeVals(valArr1, new String[1]));
						doc.append(System.getProperty("line.separator"));
						line1 = reader1.readLine();
					} else {// if (key1.compareTo(key2) > 0) {
						doc.append(key2 + Constants.KEY_VALUE_SEPARATOR
								+ mergeVals(new String[1], valArr2));
						doc.append(System.getProperty("line.separator"));
						line2 = reader2.readLine();
					}
					if (i >= Constants.BATCH_SIZE) {
						TextFile.append(dirMerged, mergedFileName,
								doc.toString());
						i = -1;
						doc = new StringBuilder();
					}
			}
			while (line1 != null) {
				i++;
				doc.append(line1);
				doc.append(System.getProperty("line.separator"));
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
				doc.append(System.getProperty("line.separator"));
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

	private static String mergeVals(String[] valArr1, String[] valArr2) {
		TreeSet<String> vals = new TreeSet<>();
		if (valArr1[0] != null) {
			for (String val : valArr1) {
				vals.add(val);
			}
		}
		if (valArr2[0] != null) {
			for (String val : valArr2) {
				vals.add(val);
			}
		}
		ArrayList<String> res = new ArrayList<>();
		for (String str : vals) {
			res.add(str);
		}
		return res.toString();
	}

}

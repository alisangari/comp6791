package services.sort.bm25;

import java.util.ArrayList;

import utility.file.GeneralFile;
import utility.file.RandomAccessFile;
import contract.Constants;
import domain.DocIdTermFrequencyPair;
import domain.Posting;

public class BM25 {

	private static BM25 instance = null;
	private int L_ave;
	private int N;
	private float k1;
	private float b;

	private BM25() {
		L_ave = calc_L_ave();
		N = calcN();
		k1 = 1.2f;
		b = 0.35f;
	}

	public static BM25 getInstance() {
		if (instance == null) {
			instance = new BM25();
		}
		return instance;
	}

	private int calcN() {
		ArrayList<String> fileNames = GeneralFile
				.getFilesList(Constants.DOCUMENT_LOCATION_ON_DISK);
		return fileNames.size();
	}

	private int calc_L_ave() {

		ArrayList<String> fileNames = GeneralFile
				.getFilesList(Constants.DOCUMENT_LOCATION_ON_DISK);

		int avgLen = 0;
		for (String fileName : fileNames) {
			Object obj = RandomAccessFile.read(
					Constants.DOCUMENT_LOCATION_ON_DISK, fileName);
			if (obj instanceof Posting) {
				avgLen += ((Posting) obj).body.length();
			}
		}

		return avgLen / fileNames.size();
	}

	public int getL_ave() {
		return L_ave;
	}

	public void setL_ave(int l_ave) {
		L_ave = l_ave;
	}

	public int getN() {
		return N;
	}

	public void setN(int n) {
		N = n;
	}

	public float getK1() {
		return k1;
	}

	public void setK1(int k1) {
		this.k1 = k1;
	}

	public float getB() {
		return b;
	}

	public void setB(float b) {
		this.b = b;
	}
}

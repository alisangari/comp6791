package services.stopwords;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import utility.file.TextFile;
import contract.Constants;

public class StopWordDictionary {

	private static StopWordDictionary instance = null;
	private HashSet<String> dict;
	private StopWordDictionary(){
		dict = TextFile.readAll(Constants.STOP_WORDS_LOCATION_ON_DISK+"stopwords");
	}
	
	public static StopWordDictionary getInstance(){
		if (instance==null){
			instance = new StopWordDictionary();
		}
		return instance;
	}
	
	public HashSet<String> getDictionary(){
		return dict;
	}
	
	public boolean contains(String str){
		return dict.contains(str);
	}

	public String[] removeStopWords(String[] strs) {
		HashSet<String> res = new HashSet<>();
		for(String str: strs){
			if(!contains(str)){
				res.add(str);
			}
		}
		if(res.isEmpty()){
			return new String[1];
		}
		String[] arr = new String[res.size()];
		int i=-1;
		for(String s: res){
			i++;
			arr[i]=s;
		}
		return arr;
	}

}

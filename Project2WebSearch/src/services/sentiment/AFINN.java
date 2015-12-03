package services.sentiment;

import java.util.HashMap;

import utility.file.TextFile;
import contract.Constants;

public class AFINN {
	
	private static AFINN instance = null;
	
	HashMap<String, Integer> dic;
	
	private AFINN(){
		dic = TextFile.readDic(Constants.AFINN_DIC_LOCATION_ON_DISK+"afinn");
	}
	
	public static AFINN getInstance(){
		if (instance==null){
			instance = new AFINN();
		}
		return instance;
	}
	
	public int calcSentimentScore(String[] words){
		int score = 0;
		for(int i=0; i<words.length; i++){
			if (dic.containsKey(words[i])){
				score+= dic.get(words[i]);
			}
		}
		return score;
	}
}

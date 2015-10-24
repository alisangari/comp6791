package demo;

import services.spimi.SpimiIndex;
import services.spimi.SpimiMerge;

public class Spimi {

	public static void main(String[] args){
		SpimiIndex.start();
		SpimiMerge.start();
	}
}

package utility;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class IdGenerator {

	private static IdGenerator instance = null;
	private ArrayList<String> ids;
	private int incrementalId;
	
	
	private IdGenerator(){
		this.ids = new ArrayList<String>();
		this.incrementalId = 0;
	}
	public static IdGenerator getInstance(){
		if(instance == null){
			synchronized(IdGenerator.class){
				instance = new IdGenerator();
			}
		}
		return instance;
	}
	
	public int getNextIncrementalId(){
		this.incrementalId++;
		return this.incrementalId;
	}
	
	public String generateUniqueId(){
		String key = generateRandomId();
		while (ids.contains(key)){
			key = generateRandomId();
		}
		return key;
	}
	private String generateRandomId() {
		String alphanumeric = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int length = 10;
		synchronized(instance){
			Random rnd = new Random();
			StringBuilder sb = new StringBuilder(length);
			for (int i = 0; i < length; i++)
				sb.append(alphanumeric.charAt(rnd.nextInt(alphanumeric.length())));
			
			return sb.toString();
		}
	}
	
	public String generateUUID(){
		synchronized(instance){
			UUID uid = UUID.randomUUID();
			return (String.valueOf(uid).substring(24));
		}
	}
}

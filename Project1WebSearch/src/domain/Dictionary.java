package domain;

import java.io.Serializable;
import java.util.ArrayList;

public class Dictionary implements Serializable {

	public ArrayList<Term> dictionary;

	public Dictionary() {
		dictionary = new ArrayList<Term>();
	}

	public String toString() {
		return "TODO";
	}
}

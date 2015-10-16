package domain;

import java.io.Serializable;
import java.util.ArrayList;

public class PostingsList implements Serializable {

	public ArrayList<Posting> postingsList;

	public PostingsList() {
		postingsList = new ArrayList<Posting>();
	}

	public String toString() {
		return "TODO";
	}
}

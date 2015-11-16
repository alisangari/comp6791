package domain;

import java.io.Serializable;

public class Posting implements Serializable {

	public int id;
	public String url;
	public String title;
	public String body;

	public Posting() {}
	
	public Posting(int id, String url, String title, String body) {
		this.id = id;
		this.url = url;
		this.title = title;
		this.body = body;
	}

	public String toString() {
		return id + "," + url + ", " + title + ", " + body;
	}
}

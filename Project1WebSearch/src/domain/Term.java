package domain;

import java.io.Serializable;

public class Term implements Serializable {

	public int id;
	public String value;

	public Term(int id, String value) {
		this.id = id;
		this.value = value;
	}

	public String toString() {
		return id + "," + value;
	}
}

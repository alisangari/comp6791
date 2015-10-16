package domain;

import java.io.Serializable;

public class Token implements Serializable {
	public int termId;
	public String postingId;

	public Token(int termId, String postingId) {
		this.termId = termId;
		this.postingId = postingId;
	}

	public String toString() {
		return termId + "," + postingId;
	}
}

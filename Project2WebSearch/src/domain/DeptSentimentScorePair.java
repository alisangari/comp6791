package domain;

public class DeptSentimentScorePair implements Comparable<DeptSentimentScorePair> {
	// DeptSentimentScorePair: [dept,score]
	// term -> DocIdFrequencyPair, DocIdFrequencyPair, DocIdFrequencyPair

	private String dept;
	private int score;

	public DeptSentimentScorePair() {
		super();
	}

	public DeptSentimentScorePair(String dept, int score) {
		super();
		this.dept = dept;
		this.score = score;
	}

	public String getDept() {
		return dept;
	}

	public int getScore() {
		return score;
	}

	@Override
	public String toString() {
		return "(" + dept + " : " + score + ")";
	}

	@Override
	public int compareTo(DeptSentimentScorePair o) {
		return new Integer(this.score).compareTo(new Integer(o.score));
	}

//	public boolean equals(DeptSentimentScorePair o) {
//		return this.dept == o.dept;
//	}

}

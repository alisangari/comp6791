package domain;

public class DocIdFrequencyPair implements Comparable<DocIdFrequencyPair> {
	// DeptSentimentScorePair: [dept,score]
	// term -> DocIdFrequencyPair, DocIdFrequencyPair, DocIdFrequencyPair

	private String docid;
	private int frequency;

	public DocIdFrequencyPair() {
		super();
	}

	public DocIdFrequencyPair(String docid, int frequency) {
		super();
		this.docid = docid;
		this.frequency = frequency;
	}

	public DocIdFrequencyPair(String pair) {
		super();
		this.docid = pair.split(":")[0];
		this.frequency = new Integer(pair.split(":")[1]);
	}

	public String getDocId() {
		return docid;
	}

	public int getFrequency() {
		return frequency;
	}

	@Override
	public String toString() {
		return "(" + docid + " : " + frequency + ")";
	}

	@Override
	public int compareTo(DocIdFrequencyPair o) {
		return new Integer(this.frequency).compareTo(new Integer(o.frequency));
	}

//	public boolean equals(DeptSentimentScorePair o) {
//		return this.dept == o.dept;
//	}

}

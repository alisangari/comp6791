package domain;

public class DocIdTermFrequencyPair implements Comparable<DocIdTermFrequencyPair> {
	// DocIdFrequencyPair: [doc id,term frequency]
	// term -> DocIdFrequencyPair, DocIdFrequencyPair, DocIdFrequencyPair

	private int docid;
	private int frequency;
	private final String docId_TermFrequency_separator = ";";

	public DocIdTermFrequencyPair() {
		super();
	}

	public DocIdTermFrequencyPair(int docid, int frequency) {
		super();
		this.docid = docid;
		this.frequency = frequency;
	}

	public DocIdTermFrequencyPair(String stringPair) {
		super();
		String[] pair = stringPair.split(docId_TermFrequency_separator);
		this.docid = new Integer(pair[0]).intValue();
		this.frequency = new Integer(pair[1]).intValue();
	}

	public int getDocid() {
		return docid;
	}

	public void setDocid(int docid) {
		this.docid = docid;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	@Override
	public String toString() {
		return "(" + docid + docId_TermFrequency_separator + frequency + ")";
	}

	@Override
	public int compareTo(DocIdTermFrequencyPair o) {
		return new Integer(this.frequency).compareTo(new Integer(o.frequency));
	}

	public boolean equals(DocIdTermFrequencyPair o) {
		return this.docid == o.docid;
	}

}

package domain;

public class DocIdFrequencyPair {
	// DocIdFrequencyPair: [doc id,term frequency]
	// term -> DocIdFrequencyPair, DocIdFrequencyPair, DocIdFrequencyPair

	private int docid;
	private int frequency;
	private final String docId_TermFrequency_separator = ";";

	public DocIdFrequencyPair() {
		super();
	}

	public DocIdFrequencyPair(int docid, int frequency) {
		super();
		this.docid = docid;
		this.frequency = frequency;
	}

	public DocIdFrequencyPair(String stringPair) {
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

}

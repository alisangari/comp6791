package domain;

public class DocIdFrequencyPair {
	// DocIdFrequencyPair: [doc id,term frequency]
	// term -> DocIdFrequencyPair, DocIdFrequencyPair, DocIdFrequencyPair

	private int docid;
	private int frequency;

	public DocIdFrequencyPair() {
		super();
	}

	public DocIdFrequencyPair(int docid, int frequency) {
		super();
		this.docid = docid;
		this.frequency = frequency;
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
		return "(" + docid + "," + frequency + ")";
	}

}

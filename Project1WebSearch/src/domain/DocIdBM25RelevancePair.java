package domain;

public class DocIdBM25RelevancePair implements Comparable<DocIdBM25RelevancePair>{

	private int docid;
	private double relevance;
	private final String docId_TermFrequency_separator = ";";

	public DocIdBM25RelevancePair() {
		super();
	}

	public DocIdBM25RelevancePair(int docid, double relevance) {
		super();
		this.docid = docid;
		this.relevance = relevance;
	}

	public DocIdBM25RelevancePair(String stringPair) {
		super();
		String[] pair = stringPair.split(docId_TermFrequency_separator);
		this.docid = new Integer(pair[0]).intValue();
		this.relevance = new Double(pair[1]).doubleValue();
	}

	public int getDocid() {
		return docid;
	}

	public void setDocid(int docid) {
		this.docid = docid;
	}

	public double getRelevance() {
		return relevance;
	}

	public void setRelevance(double relevance) {
		this.relevance = relevance;
	}

	@Override
	public String toString() {
		return "(" + docid + docId_TermFrequency_separator + String.format("%.02f", relevance) + ")";
	}

	@Override
	public int compareTo(DocIdBM25RelevancePair o) {
		return new Double(this.relevance).compareTo(new Double(o.relevance));
	}

	public boolean equals(DocIdBM25RelevancePair o) {
		return this.docid == o.docid;
	}
}

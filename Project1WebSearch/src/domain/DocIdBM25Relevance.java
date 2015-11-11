package domain;

public class DocIdBM25Relevance implements Comparable<DocIdBM25Relevance>{

	private int docid;
	private int relevance;
	private final String docId_TermFrequency_separator = ";";

	public DocIdBM25Relevance() {
		super();
	}

	public DocIdBM25Relevance(int docid, int relevance) {
		super();
		this.docid = docid;
		this.relevance = relevance;
	}

	public DocIdBM25Relevance(String stringPair) {
		super();
		String[] pair = stringPair.split(docId_TermFrequency_separator);
		this.docid = new Integer(pair[0]).intValue();
		this.relevance = new Integer(pair[1]).intValue();
	}

	public int getDocid() {
		return docid;
	}

	public void setDocid(int docid) {
		this.docid = docid;
	}

	public int getRelevance() {
		return relevance;
	}

	public void setRelevance(int relevance) {
		this.relevance = relevance;
	}

	@Override
	public String toString() {
		return "(" + docid + docId_TermFrequency_separator + relevance + ")";
	}

	@Override
	public int compareTo(DocIdBM25Relevance o) {
		return new Integer(this.relevance).compareTo(new Integer(o.relevance));
	}

	public boolean equals(DocIdBM25Relevance o) {
		return this.docid == o.docid;
	}
}

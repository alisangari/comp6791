"""BMRanking.py

Given an inverted index and an indexer, will perform ranked retrieval based on
a query by computing the BM25 ranking of the list of documents indexed.

Class Ref: https://code.google.com/p/website-clustering/source/browse/trunk/src/ by Marc-Andre Faucher
"""

import math


class BMRanking:
    index   = None
    indexer = None

    def __init__(self, iIndex, rIndexer):
        """ BMRanking
        Assumes both iIndex and rIndexer are populated
        respectively with an index (index) and doc length information (docL)
        """
        self.index = iIndex
        self.indexer = rIndexer
    # inverse document frequency
    def idf(self, term):
        n = float(len(self.indexer.docL))
        df = self.index.df(term)
        return math.log( (n/df), 10 )

    def rsv(self, terms, k=1.2, b=0.75, n=10):
        """
        Return a sorted docId list based on the terms
        """
        k = float(k)
        b = float(b)
        results = {}
        lavg = float(self.indexer.avgL())               # Calculate average doc length
        # print("DocL: ", self.indexer.docL)
        for doc in self.indexer.docL:
            ldoc = float(self.indexer.docL[doc])        # Get doc length
            docrsv = 0.0
            for term in terms:                          # Calculate corresponding rsv
                tf = float(self.index.tf(term, doc))
                docrsv += self.idf(term) * ( ( (k+1.0)*tf ) / ( k*((1.0-b)+b*(ldoc/lavg)) + tf ) )
            results[docrsv] = doc
        # Return list of document ID sorted by decreasing RSV value
        # print(results)
        score = [key for key, value, in sorted(results.iteritems(), reverse=True)]
        documents = [value for key, value, in sorted(results.iteritems(), reverse=True)]
        for i in range(n):
            print "DocID: ", documents[i], "BM25 Score: ", score[i]

        return [documents[i] for i in range(n)]
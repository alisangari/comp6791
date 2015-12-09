import lib.InvertedIndex as ii
import lib.Tokeniser as tk
import lib.WebIndexer as wi




index = ii.InvertedIndex()
indexer = wi.WebIndexer()
tokeniser = tk.Tokeniser()

print "Started creating Index.. ..."
indexer.spimi(index, tokeniser)
print "Index created!"
"""Tokeniser.py

String parser used in information retrieval to tokenise documents and queries.
Performs the following lossy compression on the dictionary:
    - Case folding
    - Number removal
    - Stemming
    - Stop word removal

Tokeniser.tokenise(string) parses string and returns a list of tokens.

Class structure inspired by:
https://code.google.com/p/website-clustering/source/browse/trunk/src/ by Marc-Andre Faucher
"""

import re
# from nltk.stem.porter import PorterStemmer

class Tokeniser:
    numberFilter = True
    caseFolding  = True
    stopWords = []
    stemmer = None
    
    def __init__(self, stopList=None,
                 useNumberFilter=True, useCaseFolding=True,
                 useStopList=False, useStemming=False):
        """ Tokeniser constructor
        """
        self.numberFilter = useNumberFilter
        self.caseFolding  = useCaseFolding

        if useStopList:
            self.stopWords = stopList
        if useStemming:
            # self.stemmer = PorterStemmer()
            self.stemmer = None

    def filterStopWords(self, term):
        """ filter stopwords """
        if term in self.stopWords:
            return False
        return True

    def tokenise(self, string):
        """ Find all terms, case fold, remove stop words, and stem """
        regexTerm = None

        # Number Filtering
        if self.numberFilter:
            regexTerm = re.compile(r'\b[a-zA-Z]+\b')
        else:
            regexTerm = re.compile(r'\b[a-zA-Z0-9]+\b')
        terms = regexTerm.findall(string)

        # Case Folding
        if self.caseFolding:
            terms = [term.lower() for term in terms]

        # Stop word removal
        terms = filter(self.filterStopWords, terms)

        # Stemming
        if self.stemmer is not None:
            terms = [ self.stemmer.stem(term) for term in terms ]
        # print("Printing terms: ")
        # print terms
        return terms
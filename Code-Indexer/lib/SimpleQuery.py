"""SimpleQuery.py

Perform the And Query for given terms and returns list of document ID

"""


class SimpleQuery:
    index   = None
    indexer = None

    def __init__(self, iIndex, rIndexer):
        """
        Assumes both iIndex and rIndexer are populated
        respectively with an index (index) and doc length information (docL)
        """
        self.index = iIndex
        self.indexer = rIndexer

    def queryByTerms(self, terms):
            """
            Return docId list based on the terms
            :param terms: search terms
            :return: list of doc id
            """
            left = None
            list1 = []
            list2 = []
            for term in terms:
                if term == 'and':
                    # print "Skipping AND"
                    continue
                if left is None:
                    left = term
                    list1 = self.index.searchDocByTerm(left,term)
                else:
                    list2 = self.index.searchDocByTerm(left,term)
                    list1 =list(set(list1).intersection(list2))
                    left = term

            return list1
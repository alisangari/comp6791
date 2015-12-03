package demo;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

import services.sort.aletal.AlEtAlRelevanceOrdering;
import services.sort.bm25.BM25RelevanceOrdering;
import utility.Strings;
import contract.Constants;
import domain.DocIdTermFrequencyPair;

public class Search {

	public static void main(String[] args) {
		try {

			System.out.println(search(
					"Democrats' welfare and healthcare reform policies", 1));
			System.out.println(search("Drug company bankruptcies", 1));
			System.out.println(search("George Bush", 1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * This method performs a search using merged index file and returns the
	 * results set ordered by either of the two ordering methods. the default
	 * ordering method is type==1 which is based on BM25. The alternative method
	 * considers inclusion of all query terms and then repetitions of each term.
	 */
	public static ArrayList<String> search(String searchStr, int type)
			throws IOException {
		String[] searchQuery = searchStr.split(" ");
		searchQuery = Strings.stem(searchQuery);
		ArrayList<DocIdTermFrequencyPair[]> lists = new ArrayList<>();
		String dirMerged = Constants.MERGED_INDEX_LOCATION_ON_DISK;
		String mergedFileName = "merged";

		for (int i = 0; i < searchQuery.length; i++) {
			FileReader r1 = new FileReader(dirMerged + mergedFileName);
			LineNumberReader reader1 = new LineNumberReader(r1);
			String line1 = reader1.readLine();

			/*
			 * This loop gathers all documents that contain each query term.
			 * Outcome is a set of lists, each list containing all postings per
			 * query term.
			 */
			while (line1 != null) {
				String[] arr1 = line1.split("\\"
						+ Constants.KEY_VALUE_SEPARATOR);
				if (arr1[0].equalsIgnoreCase(searchQuery[i])) {

					lists.add(Strings
							.getDocidTermfrequenciesFromString(arr1[1]));
					reader1.close();
					break;
				}
				line1 = reader1.readLine();
			}
		}
		/*
		 * Depending on the type, all lists are combined into one and that final
		 * list is ordered by relevance to the search query.
		 * Selected type defines the relevance criteria.
		 */
		if (type == 1) {
			return BM25RelevanceOrdering.combineAndSort(lists);
		} else {
			return AlEtAlRelevanceOrdering.combineAndSort(lists);
		}
	}
}

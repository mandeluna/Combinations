package com.oocl.types;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;

public class DictionaryTests {

	ArrayList<String> myWords = new ArrayList();
	Dictionary<String, Integer> myDictionary = new Dictionary();
	
	/**
	 * Load the word array into our dictionary implementation.
	 * Measure load factor, collisions and maximum collision chains.
	 * 
	 */
	private void testLoadFactor() {
		System.out.println(String.format("Added %d entries with %d collisions - maximum chain = %d",
				myDictionary.numEntries, myDictionary.numCollisions, myDictionary.maximumChain));
	}
	
	/**
	 * Read a list of words from the filename and add them to an array for subsequent tests.
	 * Assume the file name is in a format with lots of verbiage at the beginning
	 * but with the actual words in a tab-delimited as follows:
	 * 
	 * freq		word	PoS		# texts
	 * -----	-----	-----	-----
	 * 22995878	the		at		169011
	 * 11239776	and		cc		168844
	 * 
	 * (Downloaded from http://www.wordfrequency.info on January 15, 2011)
	 * 
	 * This method looks for lines that start with numbers, and assumes
	 * the next token is a string to add to the dictionary.
	 * 
	 * @param wordListFilename
	 * @throws IOException 
	 */
	private void loadWords(String wordListFilename) throws IOException {
		InputStream stream = this.getClass().getResourceAsStream(wordListFilename);
		InputStreamReader streamReader = new InputStreamReader(stream);
		BufferedReader in = new BufferedReader(streamReader);
		
		while (in.ready()) {
			String line = in.readLine();
			String words[] = line.split("\t");
			if (words.length == 4) {
				// check if line starts with a number
				int freq = 0;
				try {
					freq = Integer.parseInt(words[0]);
				}
				catch (NumberFormatException ex) {
					continue;
				}
				if (freq > 0) {
					String word = words[1];
					myDictionary.put(word, freq);
					myWords.add(word);
				}
			}
		}
	}
	
	public void testOperations() {
		myDictionary.put("activist", 1000);
		Integer result = (Integer)myDictionary.get("activist");
		assert(result != null);
		System.out.println("activist = " + result);
	}
	
	public void testLookup() {
		Integer result = (Integer)myDictionary.get("activist");
		assert(result != null);
		System.out.println("activist = " + result);
	}
	
	private void testSimpleSort() {
		String[] unsorted = new String[]{"pear", "apple", "banana"};
		String[]  sorted = qsort(unsorted, unsorted.length);
		for (String word : sorted) {
			System.out.println(word);
		}
	}

	private void testQuicksort500Identical() {
		String[] identical = new String[500];
		for (int i=0; i < identical.length; i++) {
			identical[i] = new String("Zanzibar");
		}
		long start = new Date().getTime(); 
		qsort(identical, identical.length);
		long finish = new Date().getTime();
		System.out.println(String.format("Quicksort sorted array of %d elements in %d ms",
				identical.length, finish - start));
	}

	private void testHeapsort500kIdentical() {
		String[] identical = new String[500000];
		for (int i=0; i < identical.length; i++) {
			identical[i] = new String("Zanzibar");
		}
		long start = new Date().getTime(); 
		heapsort(identical);
		long finish = new Date().getTime();
		System.out.println(String.format("Heapsort sorted array of %d elements in %d ms",
				identical.length, finish - start));
	}

	private void testMergesort500kIdentical() {
		String[] identical = new String[500000];
		for (int i=0; i < identical.length; i++) {
			identical[i] = new String("Zanzibar");
		}
		long start = new Date().getTime(); 
		heapsort(identical);
		long finish = new Date().getTime();
		System.out.println(String.format("Mergesort sorted array of %d elements in %d ms",
				identical.length, finish - start));
	}

	private void testQuicksort() {
		String[] unsorted = (String[])myWords.toArray(new String[0]);
		long start = new Date().getTime(); 
		String[] sorted = qsort(unsorted, unsorted.length);
		long finish = new Date().getTime();
		for (int i=1; i<sorted.length; i++) {
			boolean ordered = sorted[i-1].compareTo(sorted[i]) <= 0;
			assert(ordered == true);
			if (!ordered) {
				System.out.println("failed to sort array");
				return;
			}
		}
		System.out.println(String.format("Quicksort sorted array of %d elements in %d ms",
				sorted.length, finish - start));
	}

	private void testHeapsort() {
		String[] sorted = (String[])myWords.toArray(new String[0]);
		long start = new Date().getTime(); 
		heapsort(sorted);
		long finish = new Date().getTime();
		for (int i=1; i<sorted.length; i++) {
			boolean ordered = sorted[i-1].compareTo(sorted[i]) <= 0;
			assert(ordered == true);
			if (!ordered) {
				System.out.println("failed to sort array");
				return;
			}
		}
		System.out.println(String.format("Heapsort sorted array of %d elements in %d ms",
				sorted.length, finish - start));
	}

	private void testMergesort() {
		String[] unsorted = (String[])myWords.toArray(new String[0]);
		long start = new Date().getTime(); 
		String[] sorted = mergesort(unsorted);
		long finish = new Date().getTime();
		for (int i=1; i<sorted.length; i++) {
			boolean ordered = sorted[i-1].compareTo(sorted[i]) <= 0;
			assert(ordered == true);
			if (!ordered) {
				System.out.println("failed to sort array");
				return;
			}
		}
		System.out.println(String.format("Mergesort sorted array of %d elements in %d ms",
				sorted.length, finish - start));
	}

	/**
	 * Quicksort algorithm - worst-case performance is O(N^2) in time AND SPACE
	 * where n is the length of the unsorted array. If all elements are equal,
	 * the search will recurse to a depth of N for every node in the array. That's
	 * why we couldn't test speed performance with an array of 500,000 identical
	 * Strings.
	 * 
	 * @param unsorted
	 * @return
	 */
	private String[] qsort(String[] unsorted, int n) {
		if (n <= 1)
			return unsorted;
		
		int pindex = (int) Math.floor(Math.random()* n);
		String pivot = unsorted[pindex];
		// we don't know how many elements will be in each of the above and below arrays
		// so we have to allocate n elements for each.
		String[] above = new String[n];
		String[] below = new String[n];
		// when we are done aIndex+bIndex == n-1 (less one for the pivot)
		int aIndex = 0;
		int bIndex = 0;
		
		for (int i=0; i < n; i++) {
			if (i == pindex)
				continue;
			if (unsorted[i].compareTo(pivot) > 0) {
				above[aIndex++] = unsorted[i];
			}
			else
				below[bIndex++] = unsorted[i];
		}
		
		below = qsort(below, bIndex);
		above = qsort(above, aIndex);
		String[] sorted = new String[n];
		
		for (int i=0; i < bIndex; i++) {
			sorted[i] = below[i];
		}
		sorted[bIndex] = pivot;
		for (int i=0; i < aIndex; i++) {
			sorted[bIndex+i+1] = above[i];
		}
		return sorted;
	}
	
	/**
	 * Mergesort algorithm
	 * 
	 * @param unsorted an array list to sort
	 */
	public String[] mergesort(String[] unsorted) {
		if (unsorted.length <= 1)
			return unsorted;	// sorted
		
		// if unsorted.length is even, then left and right will both have n/2 elements
		// if unsorted.length is odd, then left will have floor(n/2) elements
		// and right will have ceiling(n/2) elements
		int maxLeft = unsorted.length / 2;
		int maxRight = unsorted.length - maxLeft;
		String[] left = new String[maxLeft];
		for (int i=0; i < maxLeft; i++) {
				left[i] = unsorted[i];
		}
		String[] right = new String[maxRight];
		for (int i=maxLeft; i < unsorted.length; i++) {
			right[i-maxLeft] = unsorted[i];
		}
		
		left = mergesort(left);
		right = mergesort(right);
		return merge(left, right);
	}
	
	private String[] merge(String[] lower, String[] upper) {
		int i = 0;
		int j = 0;
		int k = 0;
		String[] result = new String[lower.length + upper.length];
		
		while ((i < lower.length) || (j < upper.length)) {
			if ((i < lower.length ) && (j < upper.length)) {
				if (lower[i].compareTo(upper[j]) < 0) {
					result[k++] = lower[i++];
				}
				else
					result[k++] = upper[j++];
			}
			else if (i < lower.length) {
				result[k++] = lower[i++];
			}
			else if (j < upper.length) {
				result[k++] = upper[j++];
			}
		}
		
		return result;
	}
	
	/**
	 * Heapsort algorithm
	 * 
	 * @param array of strings to sort
	 */
	public void heapsort(String[] unsorted) {	
		heapify(unsorted);
		int end = unsorted.length - 1;
		while (end > 0) {
			// swap the root (maximum value) with the last element of the heap
			//swap(unsorted[end], unsorted[0]);
			String t1 = unsorted[end];
			unsorted[end] = unsorted[0];
			unsorted[0] = t1;
			// put the heap back in max-heap order
			siftDown(unsorted, 0, end-1);
			// decrease the size of the heap by one so the previous max value
			// will maintain its proper placement
			end--;
		}
	}
	
	private void heapify(String[] array) {
		// start is assigned the index of the last parent node
		int start = array.length / 2 - 1;
		while (start >= 0) {
			// sift down the node at index start so that all the nodes
			// are in the correct heap order
			siftDown(array, start, array.length - 1);
			start--;
		}
	}
	
	/**
	 * 
	 * @param array
	 * @param start
	 * @param end a non-negative integer limit to how far down the heap to sift
	 */
	private void siftDown(String[] array, int start, int end) {
		int root = start;
		while (root * 2 + 1 <= end) {	// while root has at least one child
			int child = root * 2 + 1;	// left child
			int swap = root;			// child to swap with
			// check if root is smaller than left child
			if (array[swap].compareTo(array[child]) < 0)
				swap = child;
			// check if right child exists, and if it's bigger than
			// what we are currently swapping with
			if ((child < end) &&
					(array[swap].compareTo(array[child+1]) < 0))
				swap = child + 1;
			// check if we need to swap at all
			if (swap != root) {
				//swap(array[root], array[swap]);
				String t1 = array[root];
				array[root] = array[swap];
				array[swap] = t1;
				root = swap;	// repeat to continue shifting down the child
			}
			else
				return;
		}
	}

	/**
	 * @param args are currently ignored. the input file name is hard-coded
	 */
	public static void main(String[] args) {
		DictionaryTests tester = new DictionaryTests();
		tester.testOperations();
		tester.testSimpleSort();
		try {
			tester.loadWords("/500k_wordlist_coca_orig.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		tester.testLoadFactor();
		tester.testLookup();
		tester.testQuicksort();
		tester.testHeapsort();
		tester.testMergesort();
		tester.testQuicksort500Identical();
		tester.testHeapsort500kIdentical();
		tester.testMergesort500kIdentical();
	}
}

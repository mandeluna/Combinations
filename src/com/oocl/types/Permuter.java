package com.oocl.types;

public class Permuter {
	
	/**
	 * Factorial function - does not support long ints or bignums
	 * 
	 * @param n must be greater than zero and less than or equal to 12
	 * @return n!
	 */
	public static int factorial(int n) {
		if ((n <= 0) || (n > 12))
			throw new IndexOutOfBoundsException();
		int result = 1;
		for (int i=2; i <= n; i++)
			result *= i;
		return result;
	}
	
	String[] in;		// the tokens to permute
	int length; 		// length of the token string to permute
	boolean[] used;		// the tokens currently being permuted
	String[] out;		// collection of tokens to hold intermediate results
	int outIndex;		// index to track the current position in the output array
	String[][] results;	// collection of rearrangements
	int resultCount;	// total number of results tallied
	
	/**
	 * This method will return a collection of all the possible rearrangements
	 * of the list of tokens provided in the input argument.
	 * 
	 * If there are n tokens provided, there will be n! possible rearrangements.
	 * 
	 * For example, with the tokens {"a", "b", "c", "d"} the possible rearrangements are:
	 * 
	 * {{"a", "b", "c", "d"}, {"b", "a", "c", "d"}, {"c", "a", "b", "d"}, {"d", "a", "b", "c"},
	 *  {"a", "b", "d", "c"}, {"b", "a", "d", "c"}, {"c", "a", "d", "b"}, {"d", "a", "c", "b"},
	 *  {"a", "c", "b", "d"}, {"b", "c", "a", "d"}, {"c", "b", "a", "d"}, {"d", "b", "a", "c"},
	 *  {"a", "c", "d", "b"}, {"b", "c", "d", "a"}, {"c", "b", "d", "a"}, {"d", "b", "c", "a"},
	 *  {"a", "d", "b", "c"}, {"b", "d", "a", "c"}, {"c", "d", "a", "b"}, {"d", "c", "a", "b"},
	 *  {"a", "d", "c", "b"}, {"b", "d", "c", "a"}, {"c", "d", "b", "a"}, {"d", "c", "b", "a"}}
	 * 
	 * @param tokens an array of string tokens to be rearranged
	 * @return an array of rearranged token arrays
	 */
	public String[][] permute(String[] tokens) {
		in = tokens;
		length = tokens.length;
		used = new boolean[length];
		out = new String[length];
		outIndex = 0;
		results = new String[factorial(tokens.length)][];
		
		doPermute(0);
		
		return results;
	}
	
	/**
	 * Recursively generate the rearrangements
	 * 
	 * @param in the current list of tokens to permute
	 * @param level the recursion level (this may not need to be done recursively)
	 */
	void doPermute(int level) {
		if (level == length) {
			results[resultCount++] =  out.clone();	// make a copy so we can overwrite the contents
			return;
		}
		
		for (int i=0; i < length; i++) {
			if (used[i])
				continue;
			out[outIndex++] = in[i];
			used[i] = true;
			doPermute(level+1);
			used[i] = false;
			out[--outIndex] = null;
		}
	}

}
 
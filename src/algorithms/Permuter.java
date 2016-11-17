package algorithms;

import java.util.ArrayList;
import java.util.List;

public class Permuter {
	
	/**
	 * Rotate the provided vector left by k places
	 * The equivalent right rotation is by n-k places
	 * 
	 * @param vector
	 * @param k
	 * @return
	 */
	public static void rotate(String[] vector, int k) {
		if (vector == null)
			return;
		
		int n = vector.length;
		
		// check if shortest equivalent rotation is a no-op
		if ((n == 0) || ((k = k % n) == 0))
			return;
		
		String tmp = vector[0];
		int prev = 0; int next = 0;
		while ((next = (next + k) % n) != 0) {
			String value = vector[next];
			vector[prev] = value;
			prev = next;
		}
		vector[prev] = tmp;
	}
	
	/**
	 * Factorial function - does not support long ints or bignums
	 * 
	 * @param n must be greater than zero and less than or equal to 12
	 * @return n!
	 */
	public static int factorial(int n) {
		if ((n < 0) || (n > 12))
			throw new IndexOutOfBoundsException();
		int result = 1;
		for (int i=2; i <= n; i++)
			result *= i;
		return result;
	}
	
	/**
	 * Choose function - return the number of combinations taken k at a time from n items
	 * Uses the multiplicative formula
	 * 
	 * @param n
	 * @param k
	 * @return n choose k
	 */
	public static int choose(int n, int k) {
		if (k > n - k)	// take advantage of symmetry
			k = n - k;
		int c = 1;
		for (int i=1; i <= k; i++) {
			c *= (n - k + i);
			c /= i;
		}
		return c;
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
	 * Recursively generate the rearrangements. Instead of a bit vector, Knuth uses a variable j
	 * to keep track of which tokens are currently being permuted. His algorithm doesn't recurse
	 * but includes a step to "find j" - which basically seems to be what our "used" array is doing.
	 * By being careful with i and outIndex we can avoid the recursion step completely and essentially
	 * this should be the same algorithm.
	 * 
	 * @param level the recursion level
	 */
	void doPermute(int level) {
		if (level == length) {
			results[resultCount++] = out.clone();	// make a copy so we can overwrite the contents
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
	
	/***
	 * Return a list of all the combinations of the token list taken k items
	 * at a time
	 * 
	 * @param tokens
	 * @param n
	 * @param k
	 */
	public ArrayList<ArrayList<String>> combine(ArrayList<String> tokens, int k) {
		int n = tokens.size();
		ArrayList<ArrayList<String>> choices = new ArrayList();
		if ((n < 0) || (k <= 0))
			return choices;
		
		if ((n == 0) || (n == k)) {
			choices.add(tokens);
			return choices;
		}
		
		ArrayList<String> subchoices = new ArrayList(tokens.subList(1, n));
		choices = combine(subchoices, k-1);
		if (choices.size() == 0) {
			ArrayList<String> singleton = new ArrayList();
			singleton.add(tokens.get(0));
			choices.add(singleton);
		}
		else {
			ArrayList<ArrayList<String>> morechoices = new ArrayList();
			for (ArrayList<String> choice : choices) {
				ArrayList<String> tuple = new ArrayList();
				tuple.add(tokens.get(0));
				tuple.addAll(choice);
				morechoices.add(tuple);
			}
			morechoices.addAll(combine(subchoices, k));
			choices = morechoices;
		}
		return choices;
	}

}
 
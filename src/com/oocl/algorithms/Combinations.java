package com.oocl.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Combinations {

	int n, t, j, x;
	int[] c;
	List<int[]> results;
	/*
	 * Algorithm 7.2.1.3T TAOCP by Donald Knuth: Lexicographic Combinations
	 * This algorithm is like Algorithm L, but faster. It assumes, for convenience, t < n
	 * Return a list of digits from 1 to n taken t at a time, in lexicographic order"
	 */
	public Combinations(int n, int t) {
		this.n = n;
		this.t = t;
		c = new int[t+2];
		results = new ArrayList<int[]>();

		// T1. Initialize
		for (int j = 0; j < t; j++) {
			c[j] = j;
		}
		c[t] = n; c[t+1] = 0;
		j = t;
	}

	public static void main(String[] args) {
		int n = 10, t = 4;
		Combinations combs = new Combinations(n, t);
		for (int[] result : combs.combinations()) {
			System.out.println(Arrays.toString(result));
		}
		long now = System.currentTimeMillis();
		combs = new Combinations(100, 4);
		List<int[]> results = combs.combinations();
		long millis = System.currentTimeMillis() - now;
		System.out.println(String.format("Took %3.3f seconds to generate %d results", (double)millis / 1000, results.size()));
	}
	
	private void visit() {
		int[] result = new int[t];
		for (int j = t-1; j >= 0; --j) {
			result[j] = c[j];
		}
		results.add(result);
	}

	private void findj() {
		while (true) {
			c[j-2] = j - 2;
			x = c[j-1] + 1;
			if (x != c[j]) {
				return;
			}
			++j;
		}
	}

	private List<int[]> combinations() {
		while (j <= t) {
			visit();
			if (j > 0) {
				x = j;
				// T4. Increase c[j]
				c[j-1] = x;
				--j;
				continue;
			}
			// T3. Easy case?
			else if (c[0] + 1 < c[1]) {
				c[0]++;
				continue;
			}
			else {
				j = 2;
				findj();
			}

			// T5. Terminate the algorithm if j > t
			if (j <= t) {
				// T6. Increase c[j]
				c[j-1] = x;
				--j;
			}
		}
		return results;
	}
}

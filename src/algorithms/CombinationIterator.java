package algorithms;

import java.util.Arrays;
import java.util.Iterator;

public class CombinationIterator implements Iterable<int[]> {

	int n, t, j, x;
	int[] c;
	/*
	 * Algorithm 7.2.1.3T TAOCP by Donald Knuth: Lexicographic Combinations
	 * This algorithm is like Algorithm L, but faster. It assumes, for convenience, t < n
	 * Return a list of digits from 1 to n taken t at a time, in lexicographic order"
	 */
	public CombinationIterator(int n, int t) {
		this.n = n;
		this.t = t;
		c = new int[t+2];

		// T1. Initialize
		for (int j = 0; j < t; j++) {
			c[j] = j;
		}
		c[t] = n; c[t+1] = 0;
		j = t;
	}

	@SuppressWarnings("unused")
    public static void main(String[] args) {
		int n = 6, t = 3;
		CombinationIterator combs = new CombinationIterator(n, t);
		for (int[] result : combs) {
			System.out.println(Arrays.toString(result));
		}
		long now = System.currentTimeMillis();
		combs = new CombinationIterator(100, 5);
		int count = 0;
		for (int[] result : combs) {
			count++;
		}
		long millis = System.currentTimeMillis() - now;
		System.out.println(String.format("Took %3.3f seconds to generate %d results", (double)millis / 1000, count));
	}

	@Override
    public Iterator<int[]> iterator() {
    	return new Iterator<int[]>() {

    		private int[] visit() {
    			int[] result = new int[t];
    			for (int j = t-1; j >= 0; --j) {
    				result[j] = c[j];
    			}
    			return result;
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

			@Override
            public boolean hasNext() {
			    return j <= t;
            }

			@Override
            public int[] next() {
				int[] result = visit();
				if (j > 0) {
					x = j;
					// T4. Increase c[j]
					c[j-1] = x;
					--j;
				}
				// T3. Easy case?
				else if (c[0] + 1 < c[1]) {
					c[0]++;
				}
				else {
					j = 2;
					findj();
					// T5. Terminate the algorithm if j > t
					if (j <= t) {
						// T6. Increase c[j]
						c[j-1] = x;
						--j;
					}
				}
	    		return result;
            }
    	};
    }
}

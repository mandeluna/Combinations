package com.oocl.algorithms;

/**
 * Simulate a 7-segment LED output based on an input integer between 0 and 9
 * @author WARTST
 *
 */
public class SevenSegment {

	// string offsets for boolean segment values
	int[] offsets = new int[] { 1, 4, 7, 3, 5, 6, 8 };
	
	int[][] digits = new int[][] {
			{0, 2, 3, 4, 5, 6},		// 0 has all but the center segment lit	
			{4, 6},					// 1 has the two rightmost segments lit	
			{0, 1, 2, 4, 5},		// 2 has all but the top left and bottom right segments lit	
			{0, 1, 2, 4, 6},		// 3 has all but the two left segments lit	
			{1, 3, 4, 6},			// 4 has all but the bottom, top, and bottom left segments lit	
			{0, 1, 2, 3, 6},		// 5 has all but the top right and bottom left segments lit		
			{0, 1, 2, 3, 5, 6},		// 6 has all but the top right segment lit	
			{0, 4, 6},				// 7 has the top and right segments lit	
			{0, 1, 2, 3, 4, 5, 6},	// 8 has all segments lit	
			{0, 1, 2, 3, 4, 6},		// 9 has all but the bottom left segment lit	
	};
	
	String[] enabled = new String[] {
			" ", "_", " ",
			"|", "_", "|",
			"|", "_", "|"
	};
	
	String[] disabled = new String[] {
			" ", " ", " ",
			" ", " ", " ",
			" ", " ", " "
	};
	
	private void printSegments(String[] segments) {
		for (int i=0; i < segments.length; i++) {
			if ((i % 3) == 0)
				System.out.println();
			System.out.print(segments[i]);
		}
	}
	
	// set the value of the segment at the given index
	private void setSegment(String[] segments, int index) {
		segments[offsets[index]] = enabled[offsets[index]];
	}
	
	// clear the value of the segment at the given index
	private void clearSegment(String[] segments, int index) {
		segments[offsets[index]] = disabled[offsets[index]];
	}
	
	private String[] digit(int index) {
		int[] indices = digits[index];
		String[] segments = disabled.clone();
		for (int i : indices) {
			setSegment(segments, i);
		}
		return segments;
	}
	
	private String[][] digits(int number) {
		char[] chars = new Integer(number).toString().toCharArray();
		String[][] results = new String[chars.length][];
		for (int i=0; i < chars.length; i++)
			results[i] = digit(Integer.parseInt(new Character(chars[i]).toString()));
		
		return results;
	}
	
	private void testSegments() {
		for (int i=0; i < 10; i++)
			printSegments(digit(i));
		System.out.println();

		int testInt = (int) (Math.random() * Integer.MAX_VALUE);
		String[][] digits = digits(testInt);
		System.out.println(testInt);
		/*
		for (int i=0; i < digits.length; i++)
			printSegments(digits[i]);
		*/
		for (int row=0; row < 3; row++) {
			for (int i=0; i < digits.length; i++) {
				String[] segments = digits[i];
				for (int j=0; j < 3; j++)
					System.out.print(segments[3 * row + j]);
				System.out.print(" ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		new SevenSegment().testSegments();
	}
}

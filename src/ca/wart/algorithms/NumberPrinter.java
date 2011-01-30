package ca.wart.algorithms;

import static java.lang.Math.*;

public class NumberPrinter {
	
	/**
	 * Print the provided number as an English word
	 * @param number an integer greater than Long.MIN_VALUE and less than Long.MAX_VALUE
	 * @return
	 */
	public String printWords(long number) {
		String[] digits = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
		String[] teens = {"ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
		String[] tens = {"twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};
		String[] powers = {"thousand", "million", "billion", "trillion", "quadrillion", "quintillion"};
		
		StringBuffer buffer = new StringBuffer();

		if (number < 0) {
			buffer.append("negative");
			buffer.append(' ');
			buffer.append(printWords(abs(number)));
		}
		else if (number < 10) {
			buffer.append(digits[(int) number]);
		}
		else if (number < 20) {
			buffer.append(teens[(int) (number-10)]);
		}
		else if (number < 100) {
			buffer.append(tens[(int) ((number-20) / 10)]);
			if (number % 10 > 0) {
				buffer.append(' ');
				buffer.append(printWords(number % 10));
			}
		}
		else if (number < 1000) {
			buffer.append(printWords(number / 100));
			buffer.append(' ');
			buffer.append("hundred");
			if (number % 100 > 0) {
				buffer.append(" and ");
				buffer.append(printWords(number % 100));
			}
		}
		else {
			int factor = (int) log10(number) / 3;		// at least 3 at this point
			long power = (long) pow(1000, factor);
			buffer.append(printWords(number / power));
			buffer.append(' ');
			buffer.append(powers[factor-1]);
			if (number % power > 0) {
				buffer.append(' ');
				buffer.append(printWords(number % power));
			}
		}
		
		return buffer.toString();
	}
	
	public static void main(String[] args) {
		NumberPrinter printer = new NumberPrinter();
		for (int i=0; i < 135; i++)
			System.out.println(printer.printWords(i));
		
		System.out.println("\n==== long maxvalue ====");
		System.out.print(Long.MAX_VALUE + " = ");
		System.out.println(printer.printWords(Long.MAX_VALUE));
		
		long factor = 1000;
		for (int j=0; j < 10; j++) {
			System.out.println("==== randoms up to " + factor + " ==== ");
			for (int i=0; i < 10; i++) {
				long test = (long) (random() * factor);
				System.out.print(test + " = ");
				System.out.println(printer.printWords(test));
			}
			factor *= 10;
		}

		for (long j=1; j < 1000000000000000000L; j *= 10) {
			System.out.print(j + " = ");
			System.out.println(printer.printWords(j));
		}
}
	
	/**
	 * Print the provided number in Roman numerals
	 * 
	 * @param number
	 * @return
	 */
	public String printRoman(int number) {
		return null;
	}

}

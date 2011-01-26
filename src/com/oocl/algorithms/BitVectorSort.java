package com.oocl.algorithms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 *  Programming Pearls - integer sorting algorithm
 *  
 * 	Sorts a file containing N unique integers with only a few thousand words of memory
 * 	Generate a test file if it doesn't already exist
 *
 * @author WARTST
 *
 */
public class BitVectorSort {
	
	static final String filename = "testIntegers.txt";
	static final String sortedname = "sortedIntegers.txt";
	static final int intcount = 27000;
	
	public static void main(String[] args) {
		try {
			createTestDataFile();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		try {
			sortTestDataFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Allocate only one bit per integer, then write the sorted bits to
	 * the output file. There is only one occurrence of each integer.
	 * @throws IOException 
	 */
	private static void sortTestDataFile() throws IOException {
		int bitcount = (int) Math.ceil(intcount / 32.0);	// sizeof(int) == 32
		int[] bitvector = new int[bitcount];
		
		// read the input
		FileInputStream stream = new FileInputStream(filename);
		InputStreamReader reader = new InputStreamReader(stream);
		BufferedReader in = new BufferedReader(reader);
		int count = 0;
		while (in.ready())
		{
			int i = Integer.parseInt(in.readLine());
			if (isSet(i, bitvector)) {
				System.out.println("bit " + i + " is already set");
			}
			setBit(i, bitvector);
			if (!isSet(i, bitvector)) {
				System.out.println("bit " + i + " did not get set");
			}
			count++;
		}
		System.out.println(count + " bits are being set");
		
		// write the output
		File output = new File(sortedname);
		if (!output.exists())
			output.createNewFile();
		
		count = 0;
		for (int i=0; i < bitvector.length * 32; i++) {
			if (isSet(i, bitvector)) {
				count++;
			}
		}
		System.out.println(count + " bits are actually set");
		
		PrintWriter out = new PrintWriter(output);
		try {
			for (int i=0; i < bitvector.length * 32; i++) {
				if (isSet(i, bitvector)) {
					out.println(i);
				}
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			out.close();
		}
	}

	// roll out own methods instead of using Java BitSet class
	private static boolean isSet(int i, int[] bitvector) {
		int wordIndex = i / 32;
		int bitIndex = i % 32;
		int wordValue = bitvector[wordIndex];
		int bitValue = (wordValue >> bitIndex) & 0x01;
		
		return (bitValue == 1);
	}

	/**
	 * Set the ith bit in bitvector to 1
	 * @param i
	 * @param bitvector
	 */
	private static void setBit(int i, int[] bitvector) {
		int wordIndex = i / 32;
		int bitIndex = i % 32;
		int wordValue = bitvector[wordIndex];
		wordValue |= (0x01 << bitIndex);
		
		bitvector[wordIndex] = wordValue;
	}

	/**
	 * Creating the data file allocates the entire buffer of N integers.
	 * The algorithm below will select K integers between 1 and N, without repetition.
	 * 
	 * @throws IOException
	 */
	private static void createTestDataFile() throws IOException {
		int K = intcount;
		int N = intcount;
		int[] intvector = new int[N];
		for (int i=0; i < N; i++)
			intvector[i] = i;
		
		File datafile = new File(filename);
		if (!datafile.exists())
		{
			datafile.createNewFile();
		}
		PrintWriter out = new PrintWriter(datafile);
		try {
			for (int i=0; i < K; i++)
			{
				// int randomindex = RandInt(i,N);
				int randomindex = i + (int) Math.floor(Math.random() * (N-i));
				// swap(intvector[i], intvector[randomindex]);
				int tmp = intvector[i];
				intvector[i] = intvector[randomindex];
				intvector[randomindex] = tmp;
				out.println(intvector[i]+1);
			}
		}
		finally {
			out.close();
		}
	}
}

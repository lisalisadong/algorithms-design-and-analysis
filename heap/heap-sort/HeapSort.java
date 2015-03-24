/**
 * @author QingxiaoDong
 * 
 * Sorts an input array using heap.
 * 
 * Input format: 
 * The file contains all of the integers between 1 and 10,000 (inclusive, with 
 * no repeats) in unsorted order. The integer in the ith row of the file gives 
 * you the ith entry of an input array.
 * 
 * Output format:
 * Return an array in sorted order.
 * 
 * Time complexity: O(n log n)
 */

import java.io.*;
import java.util.*;

public class HeapSort {
	public static void main(String[] args) throws FileNotFoundException {
		//Scanner in = new Scanner(new File("SmallInput.txt"));
		Scanner in = new Scanner(new File("BigInput.txt"));
		Heap heap = new Heap();
		while (in.hasNextInt()) {
			heap.add(in.nextInt());
		}
		in.close();
		int[] sorted = new int[heap.size()];
		for (int i = 0; i < sorted.length; i++) {
			sorted[i] = heap.poll();
		}
		System.out.println(Arrays.toString(sorted));
	}
}
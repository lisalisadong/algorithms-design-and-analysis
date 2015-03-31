/**
 * @author QingxiaoDong
 * 
 * The input file contains an array of unsorted numbers. Given an n (n <= size 
 * of the array, return the n-th smallest number of this array.
 * 
 * Time Complexity: Average O(n) if pivot is randomly selected
 */

import java.io.*;
import java.util.*;

public class LinearTimeSelection {
	static int select (Integer[] array, int start, int end, int order) {
		int n = end - start + 1;
		if (n == 1) {
			return array[start];
		} else {
			Random generator = new Random();
			int pivot = start + generator.nextInt(n);
			swap(array, start, pivot);
			int j = start + 1;
			for (int i = start + 1; i <= end; i++) {
				if (array[i] <= array[start]) {
					swap(array, i, j);
					j++;
				}
			}
			swap(array, start, j - 1);
			if (order == j - 1) {
				return array[j - 1];
			} else if (order > j - 1) {
				return select(array, j, end, order);
			} else {
				return select(array, start, j - 2, order);
			}
		}
	}
	
	static void swap (Integer[] array, int i, int j) {
		if (i == j){
			return;
		}
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		//Scanner in = new Scanner(new File("SmallInput.txt"));
		Scanner in = new Scanner(new File("BigInput.txt"));
		ArrayList<Integer> list = new ArrayList<Integer>();
		while (in.hasNextInt()) {
			list.add(in.nextInt());
		}
		Integer[] array = list.toArray(new Integer[list.size()]);
		int order = array.length / 2; // statistic order: find median
		System.out.println(select(array, 0, array.length - 1, order - 1));
		in.close();
	}
}
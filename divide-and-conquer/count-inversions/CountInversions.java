/**
 * @author QingxiaoDong
 * 
 * Find the number of inversions in an array.
 * (two elements a[i] and a[j] form an inversion if a[i] > a[j] and i < j)
 * 
 * Input format: 
 * the file contains all of the 100,000 integers between 1 and 100,000 
 * (inclusive) in some order, with no integer repeated.
 * 
 * Output format:
 * An integer indicates the number of inversions in the input array.
 * 
 * Time complexity: O(n log n)
 */

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class CountInversions {
	
	private static long count = 0;

	private static List<Integer> sort(List<Integer> unsorted) {
		int size = unsorted.size();
		if (size == 1) {
			return unsorted;
		} else {
			List<Integer> first = unsorted.subList(0, size/2);
			List<Integer> second = unsorted.subList(size/2, size);
			return merge(sort(first), sort(second));
		}
	}
	
	private static List<Integer> merge(List<Integer> first, List<Integer> second) {
		List<Integer> merged = new ArrayList<Integer>();
		int i = 0;
		int j = 0;
		while (merged.size() < first.size() + second.size()) {
			if (i < first.size() && (j >= second.size() || first.get(i) < second.get(j))) {
				merged.add(first.get(i));
				i++;
			} else {
				merged.add(second.get(j));
				j++;
				count = count + first.size() - i;
			}
		}
		return merged;
	}
	
    public static void main(String[] args) throws FileNotFoundException {
    	Scanner testFile = new Scanner(new File("IntegerArray.txt"));
    	List<Integer> toSort = new ArrayList<Integer>();
    	while (testFile.hasNextInt()) {
    		toSort.add(testFile.nextInt());
    	}
    	sort(toSort);
    	System.out.println(count);
    }
}
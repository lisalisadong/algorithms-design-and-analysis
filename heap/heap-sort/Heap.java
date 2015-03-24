import java.util.*;

/**
 * @author QingxiaoDong
 * 
 * Self implemented min heap structure (for integers).
 * Supported operations and time complexity:
 * poll() - O(log n)
 * add() - O(log n)
 */

public class Heap {
	private ArrayList<Integer> heap;
	public Heap() {
		heap = new ArrayList<Integer>();
	}
	
	/**
	 * Adds an integer to heap.
	 * @param a integer to be added
	 */
	public void add(int a) {
		int n = heap.size() - 1;
		heap.add(a);
		n++;
		while (n > 0 && heap.get((n-1)/2) > heap.get(n)) {
			Collections.swap(heap, (n-1)/2, n);
			n = (n-1)/2;
		}
	}
	
	/**
	 * Removes and returns the first/smallest integer from heap.
	 * @return the first/smallest integer in heap
	 */
	public int poll() {
		if (heap.size() == 0) {
			return (Integer) null;
		}
		int smallest = heap.get(0);
		int n = heap.size() - 1;
		int i = 0;
		Collections.swap(heap, 0, n);
		heap.remove(n);
		n--;
		boolean balanced = false;
		while (!balanced) {
			if (i * 2 + 1 > n) {
				balanced = true;
			} else if (i * 2 + 1 == n) {
				if (heap.get(i) > heap.get(i * 2 + 1)) {
					Collections.swap(heap, i, 2 * i + 1);
				}
				balanced = true;
			} else {
				if (heap.get(i) <= heap.get(i * 2 + 1) && heap.get(i) <= heap.get(i * 2 + 2)) {
					balanced = true;
				} else {
					if (heap.get(i * 2 + 1) < heap.get(i * 2 + 2)) {
						Collections.swap(heap, i, i * 2 + 1);
						i = i * 2 + 1;
					} else {
						Collections.swap(heap, i, i * 2 + 2);
						i = i * 2 + 2;
					}
				}
			}
		}
		return smallest;
	}
	
	/**
	 * Returns size of the heap.
	 * @return size of the heap
	 */
	public int size() {
		return heap.size();
	}
}

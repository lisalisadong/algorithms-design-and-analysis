/**
 * @author QingxiaoDong
 * 
 * Prim's minimum spanning tree algorithm. 
 * 
 * Input Format: 
 * The file describes an undirected graph with integer edge costs. It has the format
 * [number_of_nodes] [number_of_edges]
 * [one_node_of_edge_1] [other_node_of_edge_1] [edge_1_cost]
 * [one_node_of_edge_2] [other_node_of_edge_2] [edge_2_cost]
 * ...
 * For example, the third line of the file is "2 3 -8874", indicating that there is an edge connecting 
 * vertex #2 and vertex #3 that has cost -8874. The edge costs may not be positive, and may not be distinct.
 * 
 * Output Format:
 * An integer indicating the overall cost of a minimum spanning tree, which may or may not be negative.
 * 
 * Time Complexity: O(m log n) using heap that supports updatings (stores unprocessed vertices)
 */

import java.io.*;
import java.util.*;

public class PrimMST {
	
	public static void main(String[] args) throws FileNotFoundException {
		Graph graph = new Graph("BigInput.txt");
		// Graph graph = new Graph("SmallInput.txt");
		System.out.println(graph.computeMST());
	}
}

class Graph {
	
	ArrayList<ArrayList<int[]>> vertices;
	boolean[] explored;
	Heap unexplored;
	int numberOfVertices;
	int numberOfEdges;
	
	public Graph(String inputFileName) throws FileNotFoundException {
		Scanner in = new Scanner(new File(inputFileName));
		numberOfVertices = in.nextInt();
		numberOfEdges = in.nextInt();
		vertices = new ArrayList<ArrayList<int[]>>();
		for (int i = 0; i < numberOfVertices; i++) {
			vertices.add(new ArrayList<int[]>());
		}
		for (int i = 0; i < numberOfEdges; i++) {
			int u = in.nextInt();
			int v = in.nextInt();
			int cost = in.nextInt();
			vertices.get(u - 1).add(new int[]{v, cost});
			vertices.get(v - 1).add(new int[]{u, cost});
		}
		in.close();
	}
	
	/**
	 * Computes the minimum spanning tree using Prim's algorithm.
	 * @return an integer indicating the overall cost of a minimum spanning tree
	 */
	public int computeMST() {
		explored = new boolean[numberOfVertices];
		explored[0] = true;
		unexplored = new Heap();
		int sumCost = 0;
		for (int i = 1; i < numberOfVertices; i++) {
			int minCost = 1000000000;
			for (int[] edge : vertices.get(i)) {
				if (edge[0] == 1 && edge[1] < minCost) {
					minCost = edge[1];
				}
			}
			// System.out.println(i + 1);
			// System.out.println(minCost);
			unexplored.add(new int[]{i + 1, minCost});
		}
		for (int i = 1; i < numberOfVertices; i++) {
			int[] u = unexplored.poll();
			for (int[] edge : vertices.get(u[0] - 1)) {
				int v = edge[0];
				if (!explored[v - 1]) {
					int cost = edge[1];
					if (cost < unexplored.getMinCost(v)) {
						unexplored.update(new int[]{v, cost});
					}
				}
			}
			explored[u[0] - 1] = true;
			sumCost = sumCost + u[1];
		}
		return sumCost;
	}
}

/**
 * Self implemented heap structure with stores vertices and its current min cost as an array
 */
class Heap {
	private ArrayList<int[]> heap;
	HashMap<Integer, Integer> indexMap;
	
	/*
	public int check() {
		int code = 0;
		for (int i = 0; i < heap.size(); i++) {
			if (2 * i + 1 < heap.size() && 2 * i + 2 < heap.size()) {
				if (heap.get(i)[1] > heap.get(2 * i + 1)[1] || heap.get(i)[1] > heap.get(2 * i + 2)[1]) {
					code = 1;
				}
			}
		}
		return code;
	}
	*/
	
	public Heap() {
		heap = new ArrayList<int[]>();
		indexMap = new HashMap<Integer, Integer>();
	}
	
	/**
	 * Adds an array of two ([vertexID, cost]) to heap.
	 * @param a vertex to be added
	 */
	public void add(int[] a) {
		int n = heap.size() - 1;
		heap.add(a);
		n++;
		while (n > 0 && heap.get((n-1)/2)[1] > heap.get(n)[1]) {
			Collections.swap(heap, (n-1)/2, n);
			int[] b = heap.get(n);
			indexMap.remove(b[0]);
			indexMap.put(b[0], n);
			n = (n-1)/2;
		}
		// System.out.println(a[0]);
		// System.out.println(n);
		indexMap.put(a[0], n);
	}
	
	/**
	 * Removes and returns the vertex with first/smallest cost from heap.
	 * @return the vertex with first/smallest cost in heap.
	 */
	public int[] poll() {
		if (heap.size() == 0) {
			return (int[]) null;
		}
		int[] smallest = heap.get(0);
		int n = heap.size() - 1;
		int i = 0;
		Collections.swap(heap, 0, n);
		heap.remove(n);
		indexMap.remove(smallest[0]);
		if (heap.size() > 0) {
			indexMap.remove(heap.get(0)[0]);
			indexMap.put(heap.get(0)[0], 0);
		}
		n--;
		boolean balanced = false;
		while (!balanced) {
			if (i * 2 + 1 > n) {
				balanced = true;
			} else if (i * 2 + 1 == n) {
				if (heap.get(i)[1] > heap.get(i * 2 + 1)[1]) {
					Collections.swap(heap, i, 2 * i + 1);
					indexMap.remove(heap.get(i)[0]);
					indexMap.remove(heap.get(i * 2 + 1)[0]);
					indexMap.put(heap.get(i)[0], i);
					indexMap.put(heap.get(i * 2 + 1)[0], i * 2 + 1);
				}
				balanced = true;
			} else {
				if (heap.get(i)[1] <= heap.get(i * 2 + 1)[1] && heap.get(i)[1] <= heap.get(i * 2 + 2)[1]) {
					balanced = true;
				} else {
					if (heap.get(i * 2 + 1)[1] < heap.get(i * 2 + 2)[1]) {
						Collections.swap(heap, i, i * 2 + 1);
						indexMap.remove(heap.get(i)[0]);
						indexMap.remove(heap.get(i * 2 + 1)[0]);
						indexMap.put(heap.get(i)[0], i);
						indexMap.put(heap.get(i * 2 + 1)[0], i * 2 + 1);
						i = i * 2 + 1;
					} else {
						Collections.swap(heap, i, i * 2 + 2);
						indexMap.remove(heap.get(i)[0]);
						indexMap.remove(heap.get(i * 2 + 2)[0]);
						indexMap.put(heap.get(i)[0], i);
						indexMap.put(heap.get(i * 2 + 2)[0], i * 2 + 2);
						i = i * 2 + 2;
					}
				}
			}
		}
		return smallest;
	}
	
	/**
	 * Replace the cost of a certain vertex with a smaller one.
	 * @param vertexWithCost array of length two. 0 is the vertex id, 1 is the updated cost.
	 * @return true if vertex has been updated, false if there is no such vertex in heap
	 */
	public boolean update(int[] vertexWithCost) {
		boolean updated = false;
		if (indexMap.containsKey(vertexWithCost[0])) {
			int index = indexMap.get(vertexWithCost[0]);
			heap.set(index, vertexWithCost);
			while (index > 0 && heap.get((index-1)/2)[1] > heap.get(index)[1]) {
				Collections.swap(heap, (index-1)/2, index);
				int[] b = heap.get(index);
				indexMap.remove(b[0]);
				indexMap.put(b[0], index);
				index = (index-1)/2;
			}
			indexMap.remove(vertexWithCost[0]);
			indexMap.put(vertexWithCost[0], index);
			updated = true;
		}
		return updated;
	}
	
	/**
	 * Returns the current min cost of a certain vertex
	 * @param vertex id of the vertex
	 * @return current min cost
	 */
	public int getMinCost(int vertex) {
		int index = indexMap.get(vertex);
		return heap.get(index)[1];
	}
	
	/**
	 * Returns size of the heap.
	 * @return size of the heap
	 */
	public int size() {
		return heap.size();
	}
}

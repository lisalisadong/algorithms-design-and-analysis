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
 * Time Complexity: O(m n) ***This is naive algorithm used for test***.
 */

import java.io.*;
import java.util.*;

public class TestMST {
	
	public static void main(String[] args) throws FileNotFoundException {
		TestGraph graph = new TestGraph("BigInput.txt");
		// TestGraph graph = new TestGraph("SmallInput.txt");
		System.out.println(graph.computeMST());
	}
}

class TestGraph {
	
	ArrayList<ArrayList<int[]>> vertices;
	HashSet<Integer> explored;
	Heap unexplored;
	int numberOfVertices;
	int numberOfEdges;
	
	public TestGraph(String inputFileName) throws FileNotFoundException {
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
		explored = new HashSet<Integer>();
		explored.add(1);
		int sumCost = 0;
		for (int i = 1; i < numberOfVertices; i++) {
			int minCost = 1000000000;
			int minV = -1;
			for (int v : explored) {
				for (int[] edge : vertices.get(v - 1)) {
					if (!explored.contains(edge[0]) && edge[1] < minCost) {
						minCost = edge[1];
						minV = edge[0];
					}
				}
			}
			explored.add(minV);
			sumCost = sumCost + minCost;
		}
		return sumCost;
	}
}
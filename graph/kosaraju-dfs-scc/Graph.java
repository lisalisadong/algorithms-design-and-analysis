/**
 * @author QingxiaoDong
 * 
 * Input format:
 * The input file contains the edges of a directed graph. Vertices are labeled 
 * as positive integers from 1 to 875714. Every row indicates an edge, the 
 * vertex label in first column is the tail and the vertex label in second 
 * column is the head (recall the graph is directed, and the edges are directed
 * from the first column vertex to the second column vertex). So for example, 
 * the 11th row looks like : "2 47646". This just means that the vertex with 
 * label 2 has an outgoing edge to the vertex with label 47646.
 * 
 * Task:
 * The task is to code up the Kosaraju's Two-Pass algorithm for computing 
 * strongly connected components (SCCs), and to run this algorithm on the given
 * graph. 
 * 
 * Output Format: 
 * You should output the sizes of the 5 largest SCCs in the given graph, in 
 * decreasing order of sizes, separated by commas (avoid any spaces). So if 
 * your algorithm computes the sizes of the five largest SCCs to be 500, 400, 
 * 300, 200 and 100, then your answer should be "500,400,300,200,100". If your 
 * algorithm finds less than 5 SCCs, then write 0 for the remaining terms. Thus, 
 * if your algorithm computes only 3 SCCs whose sizes are 400, 300, and 100, 
 * then your answer should be "400,300,100,0,0".
 * 
 * Time complexity: O(m + n)
 * 
 * Set VM arguments in eclipse: -Xmx1024M -Xss10m
 */

import java.io.*;
import java.util.*;

public class Graph {
	
	private ArrayList<ArrayList<Integer>> vertices; // graph
	private ArrayList<ArrayList<Integer>> reverse; // graph with all arcs reversed
	private int[] labels; // ordering
	private int[] leader; // count how many nodes are of the same leader
	private int time; // DFS finishing time
	private int source; // leader
	private boolean[] explored; // track which nodes have been explored in DFS
	
	/**
	 * Read graph from input file.
	 * @param inputFileName
	 * @throws FileNotFoundException
	 */
	
	public Graph(String inputFileName) throws FileNotFoundException{
		vertices = new ArrayList<ArrayList<Integer>>();
		reverse = new ArrayList<ArrayList<Integer>>();
		Scanner in = new Scanner(new File(inputFileName));
		//add all vertices
		while (in.hasNextInt()){
			 int tail = in.nextInt();
			 int head = in.nextInt();
			 int max = Math.max(tail, head);
			 while (vertices.size() < max){
				 vertices.add(new ArrayList<Integer>());
				 reverse.add(new ArrayList<Integer>());
			 }
			 vertices.get(tail-1).add(head-1);
			 reverse.get(head-1).add(tail-1);
			 //System.out.println("Added " + tail + "->" +  head);
		}
	}
	
	/**
	 * Computes SCCs. 
	 * @return top5 an integer array of size 5, containing the sizes of the 5 
	 * largest SCCs in the given graph, in decreasing order of sizes.
	 */
	
	public int[] computeSCC(){
		int[] top5 = new int[5];
		DFSLoop1();
		DFSLoop2();
		Arrays.sort(leader);
		for (int i = 0; i < 5; i++){
			top5[i] = leader[leader.length - i - 1];
		}
		return top5;
	}
	
	/**
	 * The first DFS loop will DFS the reversed graph and labeling each nodes
	 * by the finishing time.
	 */
	
	public void DFSLoop1(){
		time = 0;
		explored = new boolean[reverse.size()];
		labels = new int[vertices.size()];
		for (int i = reverse.size()-1; i >= 0; i--){
			if (explored[i] == false){
				DFS1(i);
			}
		}
	}
	
	/**
	 * The second DFS loop will DFS the original graph. At the beginning of
	 * each loop, it will choose the largest label to begin and mark all the
	 * explored nodes' leader with this label.
	 */
	
	public void DFSLoop2(){
		explored = new boolean[vertices.size()];
		leader = new int[vertices.size()];
		for (int i = labels.length - 1; i >= 0; i--){
			int node = labels[i];
			if (explored[node] == false){
				source = node;
				DFS2(node);
			}
		}
	}
	
	/**
	 * Part of the first DFS loop.
	 * @param node
	 */
	
	public void DFS1(int node){
		explored[node] = true;
		for (int head : reverse.get(node)){
			if (explored[head] == false){
				DFS1(head);
			}
		}
		labels[time] = node;
		time++;
	}
	
	/**
	 * Part of the second DFS loop.
	 * @param node
	 */
	
	public void DFS2(int node){
		explored[node] = true;
		leader[source] ++;
		for (int head : vertices.get(node)){
			if (explored[head] == false){
				DFS2(head);
			}
		}
	}
	
	/*
	public void printGraph(){
		for (int i = 0; i < vertices.size(); i++){
			System.out.println(i + ": ");
			System.out.print("outgoings: ");
			for (int j = 0; j < vertices.get(i).size(); j++){
				System.out.print(i + "->" + vertices.get(i).get(j) + " ");
			}
			System.out.println();
			System.out.print("incommings: ");
			for (int j = 0; j < reverse.get(i).size(); j++){
				System.out.print(i + "<-" + reverse.get(i).get(j) + " ");
			}		
			System.out.println();
		}
		System.out.println();
	}
	*/
	
	public static void main(String[] args) throws FileNotFoundException{
		//Graph g = new Graph("SimpleInput.txt");
		Graph g = new Graph("SCCInput.txt");
		int[] topSCCs = g.computeSCC();
		for (int n : topSCCs){
			System.out.print(n + " ");
		}
	}
}
/**
 * @author QingxiaoDong 
 * 
 * Input format:
 * The file contains an adjacency list representation of an undirected weighted 
 * graph with 200 vertices labeled 1 to 200. Each row consists of the node 
 * tuples that are adjacent to that particular vertex along with the length of 
 * that edge. For example, the 6th row has 6 as the first entry indicating that 
 * this row corresponds to the vertex labeled 6. The next entry of this row 
 * "141,8200" indicates that there is an edge between vertex 6 and vertex 141 
 * that has length 8200. The rest of the pairs of this row indicate the other 
 * vertices adjacent to vertex 6 and the lengths of the corresponding edges.
 * 
 * Task:
 * Run Dijkstra's shortest-path algorithm on this graph, using 1 (the first 
 * vertex) as the source vertex, and to compute the shortest-path distances 
 * between 1 and every other vertex of the graph. If there is no path between a 
 * vertex v and vertex 1, we'll define the shortest-path distance between 1 and 
 * v to be 1000000.
 * 
 * Output format:
 * Report the shortest-path distances to the following ten vertices, in order: 
 * 7,37,59,82,99,115,133,165,188,197. You should encode the distances as a 
 * comma-separated string of integers. So if you find that all ten of these 
 * vertices except 115 are at distance 1000 away from vertex 1 and 115 is 2000 
 * distance away, then your answer should be 1000,1000,1000,1000,1000,2000,1000,
 * 1000,1000,1000. 
 *
 * Time complexity: O(n m) - In the "heap" directory, there is an optimized 
 * O(m log(n)) algorithm.
 */

import java.io.*;
import java.util.*;

public class Graph {
	
	private ArrayList<ArrayList<int[]>> vertices; // graph
	private HashSet<Integer> explored; // nodes that have been explored
	
	/**
	 * Reads graph from input file.
	 * @param inputFileName
	 * @throws FileNotFoundException
	 */
	
	public Graph(String inputFileName) throws FileNotFoundException{
		vertices = new ArrayList<ArrayList<int[]>>();
		Scanner in = new Scanner(new File(inputFileName));
		//add all vertices
		while (in.hasNextLine()){
			 vertices.add(new ArrayList<int[]>());
			 String[] line = in.nextLine().split("\t");
			 int node = Integer.parseInt(line[0]);
			 for (int i = 1; i < line.length; i++){
				 String[] edgeStr = line[i].split(",");
				 int[] edge = new int[2];
				 edge[0] = Integer.parseInt(edgeStr[0]);
				 edge[1] = Integer.parseInt(edgeStr[1]);
				 vertices.get(node - 1).add(edge);
			 }
		}		
		/*//Print Graph
		int k = 1;
		for (ArrayList<int[]> a : vertices){
			System.out.print(k + ": {");
			for (int[] i : a){
				System.out.print(Arrays.toString(i));
			}
			System.out.println("}");
			k++;
		}
		*/
		
	}
	
	/**
	 * Computes the shortest-path distances between 1 and every other vertex of
	 * the graph.
	 * @return paths an array of integer indicates the path distances from 1 to 
	 * every node, e.g. paths[i] is the path distance from 1 to i+1.
	 */
	
	public int[] shortestPath(){
		int n = vertices.size();
		explored = new HashSet<Integer>();
		int[] paths = new int[n];
		explored.add(1);
		paths[0] = 0;
		while (explored.size() < n){
			int w = -1;
			int l = 1000000;
			for (int node : explored){				
				for (int[] edge : vertices.get(node - 1)){
					if (!explored.contains(edge[0])){
						if (paths[node-1] + edge[1] < l){
							w = edge[0];
							l = paths[node-1] + edge[1];
						}
					}
				}
			}
			if (w != -1){
				explored.add(w);
				paths[w-1] = l;
				//System.out.println(w + " " + l);
			} else {
				for (int i = 0; i < n; i++){
					if (!explored.contains(i+1)){
						paths[i] = 1000000;
					}
				}
				break;
			}
		}		
		return paths;
	}
	
	public static void main(String[] args) throws FileNotFoundException{
		//Graph g = new Graph("SimpleInput.txt");
		Graph g = new Graph("DijkstraData.txt");
		int[] paths = g.shortestPath();	
		System.out.println(paths[6] + "," + paths[36] + "," + paths[58] + "," + 
						   paths[81] + "," + paths[98] + "," + paths[114] + "," + 
						   paths[132] + "," + paths[164] + "," + paths[187] + 
						   "," + paths[196]); //7,37,59,82,99,115,133,165,188,197		
		//System.out.println(Arrays.toString(paths));
	}
}
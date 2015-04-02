/**
 * @author QingxiaoDong
 * 
 * Minimizes the weighted sum of completion times (the completion time of job j = sum of job lengths up
 * to and including j) using greedy algorithm that schedules jobs in decreasing 
 * order of the ratio (weight/length). => This is an greedy algorithm with gets OPTIMAL result.
 * 
 * Input format:
 * This file describes a set of jobs with positive and integral weights and lengths. It has the format
 * [number_of_jobs]
 * [job_1_weight] [job_1_length]
 * [job_2_weight] [job_2_length]
 * ...
 * For example, the third line of the file is "74 59", indicating that the second job has weight 74 and 
 * length 59. Edge weights or lengths may not be distinct.
 * 
 * Break ties: 
 * If two jobs have equal ratio (weight/length), the job with higher weight should be scheduled first. 
 * 
 * Output format:
 * A positive integer which indicating the sum of weighted completion times of the resulting schedule.
 * 
 * Time complexity: O(n log n)
 */

import java.io.*;
import java.util.*;

public class RatioSchedule {
	
	public static void main(String[] args) throws FileNotFoundException {
		// Scanner in = new Scanner(new File("SmallInput.txt"));
		Scanner in = new Scanner(new File("BigInput.txt"));
		int n = in.nextInt();
		Comparator<int[]> comparator = new JobComparator();
		PriorityQueue<int[]> jobs = new PriorityQueue<int[]>(n,comparator);
		for (int i = 0; i < n; i++) {
			int[] job = new int[2];
			job[0] = in.nextInt();
			job[1] = in.nextInt();
			jobs.add(job);
		}
		in.close();
		
		long sum = 0L;
		int currentLength = 0;
		for (int i = 0; i < n; i++) {
			int[] currentJob = jobs.poll();
			currentLength = currentLength + currentJob[1];
			sum = sum + currentJob[0] * currentLength;
		}
		System.out.println(sum);
	}
	
	public static class JobComparator implements Comparator<int[]>
	{
	    @Override
	    public int compare(int[] x, int[] y)
	    {
	    	double xRatio = (double) x[0] / (double) x[1];
	    	double yRatio = (double) y[0] / (double) y[1];
	        if (xRatio == yRatio) {
	        	return y[0] - x[0];
	        } else if (yRatio > xRatio){
	        	return 1;
	        } else {
	        	return -1;
	        }
	    }
	}
}
/**
 * Find the closest pair of points (minimum distance) in an array of points.
 * 
 * @author QingxiaoDong
 */

import java.awt.Point;
import java.io.*;
import java.util.*;

public class TheClosestPair {
	
	static Pair closestPair(List<Point> points) {
		int size = points.size();
		if (size == 1) {
			return null;
		} else if (size == 2) {
			return new Pair(points.get(0), points.get(1));
		} else {
			List<Point> sorted = sortX(points);
			return closestMerge(sorted.subList(0, size/2), sorted.subList(size/2, size));
		}
	}
	
	static Pair closestMerge(List<Point> p1, List<Point> p2) {
		Pair currentPair = null;
		double currentMin = 0;
		Pair pair1 = closestPair(p1);
		Pair pair2 = closestPair(p2);
		if (pair1 == null || pair1.distance() > pair2.distance()) {
			currentPair = pair2;
			currentMin = pair2.distance();
		} else if (pair2 == null || pair1.distance() <= pair2.distance()){
			currentPair = pair1;
			currentMin = pair1.distance();
		}
		double middleX = p1.get(p1.size() - 1).getX();
		List<Point> dy = new ArrayList<Point>();
		for (Point p : p1) {
			if (middleX - p.getX() < currentMin) {
				dy.add(p);
			}
		}
		for (Point p : p2) {
			if (middleX - p.getX() <= currentMin) {
				dy.add(p);
			}
		}
		List<Point> sorted = sortY(dy);
		for (int i = 0; i < sorted.size(); i++) {
			for (int j = i + 1; j < sorted.size() && j - i < 8; j++) {
				double distance = sorted.get(i).distance(sorted.get(j));
				if (distance < currentMin) {
					currentMin = distance;
					currentPair.setPair(sorted.get(i), sorted.get(j));
				}
			}
		}
		return currentPair;
	}

	static List<Point> sortX(List<Point> unsorted) {
		int size = unsorted.size();		
		if (size == 1) {
			return unsorted;
		} else {
			List<Point> first = unsorted.subList(0, size/2);
			List<Point> second = unsorted.subList(size/2, size);
			return mergeX(sortX(first), sortX(second));
		}
	}
	
	static List<Point> mergeX(List<Point> first, List<Point> second) {
		List<Point> merged = new ArrayList<Point>();
		int i = 0;
		int j = 0;
		while (merged.size() < first.size() + second.size()) {
			if (i < first.size() && (j >= second.size() || 
				first.get(i).getX() < second.get(j).getX())) {
				merged.add(first.get(i));
				i++;
			} else {
				merged.add(second.get(j));
				j++;
			}
		}
		return merged;
	}
	
	static List<Point> sortY(List<Point> unsorted) {
		int size = unsorted.size();		
		if (size == 1) {
			return unsorted;
		} else {
			List<Point> first = unsorted.subList(0, size/2);
			List<Point> second = unsorted.subList(size/2, size);
			return mergeY(sortY(first), sortY(second));
		}
	}
	
	static List<Point> mergeY(List<Point> first, List<Point> second) {
		List<Point> merged = new ArrayList<Point>();
		int i = 0;
		int j = 0;
		while (merged.size() < first.size() + second.size()) {
			if (i < first.size() && (j >= second.size() || 
				first.get(i).getY() < second.get(j).getY())) {
				merged.add(first.get(i));
				i++;
			} else {
				merged.add(second.get(j));
				j++;
			}
		}
		return merged;
	}
	
    public static void main(String[] args) throws FileNotFoundException {
    	List<Point> toSort = new ArrayList<Point>();
    	toSort.add(new Point(3, 2));
    	toSort.add(new Point(5, 2));
    	toSort.add(new Point(1, 3));
    	toSort.add(new Point(5, 8));
    	toSort.add(new Point(10, 2));
    	toSort.add(new Point(2, 4));
    	System.out.println(sortX(toSort));
    	System.out.println(sortY(toSort));
    	closestPair(toSort).print();
    }
}
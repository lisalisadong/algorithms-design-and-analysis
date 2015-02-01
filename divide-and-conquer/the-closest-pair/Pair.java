import java.awt.Point;

public class Pair {
	
	public Point point1;
	public Point point2;
	
	public Pair(Point point1, Point point2) {
		this.point1 = point1;
		this.point2 = point2;
	}
	
	public double distance() {
		return point1.distance(point2);
	}
	
	public void setPair(Point point1, Point point2) {
		this.point1 = point1;
		this.point2 = point2;
	}
	
	public void print() {
    	System.out.println(point1);
    	System.out.println(point2);
	}
}
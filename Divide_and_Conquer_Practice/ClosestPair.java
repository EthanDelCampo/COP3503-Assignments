/* Dr. Steinberg
   COP3503 Computer Science 2
   Closest Pair of Points
   ClosestPair.java
*/

import java.util.ArrayList;
import java.util.Collections;  
import java.util.Comparator;
import java.util.Random;
import java.util.HashSet; 
import java.util.Set;


public class ClosestPair
{
	/*
	 * Ethan's Notes:
	 *  - Brute force is inefficient for large sets of points due to O(n^2) time complexity.
	 *  - For divide, and conquer, used when there the # of points to check is small
	 *  - (typically 2 or 3 points). 
	 */
	public static double bruteForce(ArrayList<Point> pts, int n) 
	{
		double min = Double.MAX_VALUE;
		double currentmin = 0;
		
		for(int i = 0; i < n; ++i)
		{
			for(int j = i + 1; j < n; ++j)
			{
				currentmin = distance(pts.get(i), pts.get(j));
				
				if(currentmin < min)
				{
					min = currentmin;
				}
			}
			
		}
		
		return min;
	}

	/*
	 * Ethan's notes:
	 *  - The distance method calculates the Euclidean distance (basic distance formula).
	 *  - Calls this in brute force (divide and conquer base case, with 2 or 3 pts left)
	 *  - or along strip near dividing line.
	 */
	//Euclidean Distance...Although can speed up the running time a bit by using vectors. That way I don't need to call sqrt...
	public static double distance(Point p1, Point p2)
	{
		return (double) Math.sqrt((p1.getX() - p2.getX()) * (p1.getX() - p2.getX()) + (p1.getY() - p2.getY()) * (p1.getY() - p2.getY()));
	}
	
	/*
	 * Ethan's notes:
	 *  - Entry point to divide and conquer solution.
	 *  - Sorts the points based on x coordinates, then calls recursive method.
	 *  - In the closestPairR method, 0 is the left index and pts.size()-1 is the right index
	 */
	//Main call
	public static double closestPair(ArrayList<Point> pts)
	{
		Comparator<Point> comparebyx = (Point p1, Point p2) -> p1.getX().compareTo(p2.getX());
		Collections.sort(pts, comparebyx);
		
		return closestPairR(pts, 0, pts.size() - 1);
	}
	
	/*
	 * Ethan's notes:
	 *  - The core of the divide and conquer algorithm
	 *  - The points are split into two halves by finding the midpoint (index k)
	 *  - In case where 2 or 3 points are to be checked, switches to brute force to find the problem
	 *  - Otherwise the points are split into 2 halves
	 *  - Delta is the minimum of the smallest distance between delta left and delta right.
	 *  - It then makes a list storing points that are within delta distance to the midline.
	 *  - It sorts points by y coordinate in the strip, efficiently compares only nearby points.
	 *  - It checks for closest points within the midline, then checks for closest point.
	 *  - Closest point is returned.
	 *  - Combine step is linear, as only 7 neighbors are checked due to geometric propertry.
	 *  - 2 subproblems are created.
	 * 	- Input size being divided n/2.
	 *  - Combine step n
	 */
	//Recursion
	public static double closestPairR(ArrayList<Point> pts, int i, int j)
	{
		//3 or 2 points
		if(j - i < 3)
		{
			
			double delta = distance(pts.get(i), pts.get(i + 1));
			
			if(j - i == 1) //two points
				return delta;
				
			//three points
			if(distance(pts.get(i + 1), pts.get(i + 2)) < delta)
				delta = distance(pts.get(i + 1), pts.get(i + 2));
			
			if(distance(pts.get(i), pts.get(i + 2)) < delta)
				delta = distance(pts.get(i), pts.get(i + 2));

			
			return delta;
			
		}
		
		int k = (i + j) / 2;
		
		double l = pts.get(k).getX();
		
		double deltal = closestPairR(pts, i, k); //left subproblem
		double deltar = closestPairR(pts, k + 1, j); //right subproblem
		
		double delta = Math.min(deltal, deltar);
		
		ArrayList<Point> midline = new ArrayList<Point>();
		
		
		int t = 0;
		
		//add the points to our range
		for(int x = i; x <= j; ++x)
		{
			if(pts.get(x).getX() > l - delta && pts.get(x).getX() < l + delta)
			{
				++t;
				midline.add(new Point(pts.get(x)));
			}
			
		}
		
		double min = delta;
		
		Comparator<Point> compareby = (Point p1, Point p2) -> p1.getY().compareTo(p2.getY());
		Collections.sort(midline, compareby);
		
		for(int x = 0; x < t; ++x)
			for(int y = x + 1; y < Math.min(t, x + 7); ++y)
				delta = Math.min(delta, distance(midline.get(x), midline.get(y)));
		
		
		return delta;
		
		
		
	}
	
	//Prints the points
	public static void display(ArrayList<Point> pts)
	{
		for(int x = 0; x < pts.size(); ++x)
			System.out.println(pts.get(x).toString());
	}
	
	//Creates the list and does both brute force and closest pair of points approach.
	public static void main(String [] args) throws Exception
	{	
		ArrayList <Point> pts = new ArrayList<Point>();
		pts.add(new Point(2, 3));
		pts.add(new Point(12, 30));
		pts.add(new Point(40, 50));
		pts.add(new Point(5, 1));
		pts.add(new Point(12, 10));
		pts.add(new Point(3, 4));
		
		
		System.out.println(closestPair(pts));
		
		System.out.println(bruteForce(pts, pts.size()));
	}
}
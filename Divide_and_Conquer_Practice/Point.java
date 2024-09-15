/* Dr. Steinberg
   COP3503 Computer Science 2
   Closest Pair of Points
   Point.java
*/
import java.util.Comparator;

public class Point implements Comparable<Point>
{
	private Integer x;
	private Integer y;
	
	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Point(Point p)
	{
		this.x = p.getX();
		this.y = p.getY();
	}
	
	public Integer getX()
	{
		return x;
	}
	
	public Integer getY()
	{
		return y;
	}
	
	@Override
	public int compareTo(Point pt)
	{
		Integer ptx = this.getX(); //convert into Wrapper Object so compareTo is used properly
		return ptx.compareTo(pt.getX());
	}
	
	@Override
    public boolean equals(Object obj) 
	{
        if (obj == null) 
		{
            return false;
        }
        if (getClass() != obj.getClass()) 
		{
            return false;
        }
		
        final Point other = (Point) obj;
		
        if (this.getX() != other.getX() || this.getY() != other.getY()) 
		{
            return false;
        }

        return true;
    }
	
	public int hashCode() 
	{
		Integer diff = getX() - getY();
        return diff.hashCode();
    }
	
	@Override
	public String toString()
	{
		return "(" + this.getX() + ", " + this.getY() + ")";
	}
}

class PointXComparator implements Comparator<Point> 
{
	@Override
	public int compare(Point pt1, Point pt2) 
	{
		return Integer.compare(pt1.getX(), pt2.getX());
	}
}

class PointYComparator implements Comparator<Point> 
{
	@Override
	public int compare(Point pt1, Point pt2) 
	{
		return Integer.compare(pt1.getY(), pt2.getY());
	}
  
}
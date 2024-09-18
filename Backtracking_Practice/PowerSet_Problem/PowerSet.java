//Dr. Steinberg
//Computer Science 2
//Backtracking Lab
//PowerSet.java

/*
	When running the program on Eustis, you need to
	include the paramters on the command line.
	Example: java PowerSet 1 2 3 4
	The set of values typed after the class
	name represents the set.
*/

import java.util.ArrayList;

public class PowerSet 
{ 	


	public static void genPowerSet(ArrayList<Integer> nums, ArrayList<Integer> set, int i)
	{
		if(i < 0)
		{
			System.out.println(set);
		}
		else
		{
			set.add(nums.get(i));
			genPowerSet(nums, set, i - 1);
			set.remove(set.size() - 1); //backtrack by removing last element
			
			genPowerSet(nums, set, i - 1); //this allows to exclude the current element just diplayed
		}
	}
	
	
	
	public static void main(String [] args)
	{
		//the main method can act like the initial method before calling the backtrack method
		ArrayList<Integer> nums = new ArrayList<Integer>();
		
		ArrayList<Integer> set = new ArrayList<Integer>();
		
		for(int x = 0; x < args.length; ++x)
			nums.add(Integer.parseInt(args[x]));
		
		
		genPowerSet(nums, set, args.length - 1);
	}
}
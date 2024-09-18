//Dr. Steinberg
//Computer Science 2
//Backtracking Lab
//Combo.java

/*
	When running the program on Eustis, you need to
	include the paramters on the command line.
	Example: java Combo 1 2 3 4
	The last value typed represents the size of the
	set while the others represent the elements in the 
	set.
*/


import java.util.ArrayList;
import java.util.Collections;

public class Combo 
{ 	

	/*
	 * Ethan's notes:
	 *  - Recursive Backtracking Method for printing unique combinations
	 */
	public static void printComboR(ArrayList <Integer> nums, ArrayList <Integer> output, int k, int i)
	{
		
		//Base case that checks if current size is equal to k, desired length of combination
		if(output.size() == k)
		{
			System.out.println(output);
			return;
		}
		else
		{
	  
			
		  for(int j = i; j < nums.size(); ++j)
		  {
			  output.add(nums.get(j));
			  System.out.println("Added element"); //add to the end
			  printComboR(nums, output, k, j);
			  System.out.println("Backtrack");
			  output.remove(output.size() - 1); //backtrack by removing last element
			  
			  while (j < nums.size() - 1 && nums.get(j).equals(nums.get(j + 1))) 
			  {
                j++;
			  }
		  }
		}
    }

  public static void printCombo(String [] args)
  {
    int k = Integer.parseInt(args[args.length - 1]);
	
	ArrayList <Integer> nums = new ArrayList <Integer>();
    
	//convert Strings into Integers
    
	for(int index = 0; index < args.length - 1; ++index)
      nums.add(Integer.parseInt(args[index]));


    ArrayList <Integer> output = new ArrayList <Integer>();
	
	Collections.sort(nums); //sort to help with duplicates
	
    printComboR(nums, output, k, 0);
    
  }
  
  public static void main(String args[]) 
  { 
    printCombo(args);
  } 
}
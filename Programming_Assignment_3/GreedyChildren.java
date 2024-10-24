/* Ethan Del Campo
 * Dr. Steinberg
 * COP3503 Fall 2024
 * Programming Assignment 3
 */


import java.util.*;
import java.io.*;


public class GreedyChildren 
{


    //Attributes necessary for this class
    private int numCandies;
    private int numChildren;
    private String childrenFileName;
    private String candyFileName;
    private int [] candies;
    private int [] children;
    private int numHappyChildren = 0;



    //Constructor for GreedyChildren
    public GreedyChildren(int numCandies, int numChildren, String childrenFileName, String candyFileName)
    {
        
        this.numCandies = numCandies;
        this.numChildren = numChildren;
        this.childrenFileName = childrenFileName;
        this.candyFileName = candyFileName;

        //Filling in sizes of array
        candies = new int[numCandies];
        children = new int[numChildren];

        //Reads files; fills in respective arrays
        read();

    }

    

    //Reads files and fills in appropriate arrays
    private void read()
    {

        //Try catch statements
        try
        {

            //Initializing file names and scanner for childrenArray
            File childrenScan = new File(childrenFileName);
            Scanner childrenScanner = new Scanner(childrenScan);

            //Fills in childrenArray
            int childrenIndex = 0;
            while(childrenScanner.hasNextInt() && childrenIndex < numChildren)
            {
                children[childrenIndex] = childrenScanner.nextInt();
                childrenIndex++;
            }

            childrenScanner.close(); //Closing childrenScanner


            //Initializing file names and scanner for candyArray
            File candyScan = new File(candyFileName);
            Scanner candyScanner = new Scanner(candyScan);

            //Fills in candyArray
            int candyIndex = 0;
            while(candyScanner.hasNextInt() && candyIndex < numCandies)
            {
                candies[candyIndex] = candyScanner.nextInt();
                candyIndex++;
            }

            candyScanner.close(); //Closing candyScanner

        }
        catch (FileNotFoundException e)
        {
            System.exit(1); //Program ended unsuccessfully
        }

    }



    //Helper function: Main method for merge sort
    private void mergeSort(int [] array, int left, int right)
    {

        if(left < right)
        {

            //Finds middle point
            int mid = (left + right) / 2;

            //Recursive calls
            mergeSort(array, left, mid);
            mergeSort(array, mid+1, right);

            //Merges sorted halves
            merge(array, left, mid, right);

        }

    }



    //Helper function: Merges 2 sorted halves for merge sort
    private void merge(int [] array, int left, int mid, int right)
    {

        //Sizes of sub-arrays to be merged
        int size1 = mid - left + 1;
        int size2 = right - mid;

        //Temporary arrays
        int[] tempLeft = new int[size1];
        int[] tempRight = new int[size2];

        //Fills in temporary arrays
        for (int i = 0; i < size1; i++) 
        {
            tempLeft[i] = array[left + i];
        }
        for (int j = 0; j < size2; j++) 
        {
            tempRight[j] = array[mid + 1 + j];
        }

        //Merges temporary arrays
        int i = 0;
        int j = 0;
        int k = left;

        //Compares elements from both arrays; inserts into original
        while (i < size1 && j < size2) 
        {
            if (tempLeft[i] <= tempRight[j]) 
            {
                array[k] = tempLeft[i];
                i++;
            } 
            else 
            {
                array[k] = tempRight[j];
                j++;
            }
            k++;
        }

        //Copies remaining elements of arrays, if any exist
        while (i < size1) 
        {
            array[k] = tempLeft[i];
            i++;
            k++;
        }
        while (j < size2) 
        {
            array[k] = tempRight[j];
            j++;
            k++;
        }

    }


    
    //Greedy Algorithm: Sorts arrays, maximizes children getting proper candy
    public void greedyCandy()
    {

        //MergeSort sorting; O(nlgn) worst case time complexity
        mergeSort(children, 0, numChildren - 1); //Sorts children array
        mergeSort(candies, 0, numCandies - 1); //Sorts candies array

        //Greedy algorithm approach; traverses through candies array
        int candyIndex = 0;
        int childrenIndex = 0;
        while (candyIndex < numCandies)
        {
            if((candies[candyIndex] < children[childrenIndex]) || (childrenIndex >= numChildren))
            {
                candyIndex++;
            }
            else if((candies[candyIndex] >= children[childrenIndex]) && (childrenIndex < numChildren))
            {
                numHappyChildren++;
                candyIndex++;
                childrenIndex++;
            }
        }

    }



    //Prints number of happy and angry children
    public void display()
    {
        
        System.out.println("There are " + numHappyChildren + " happy children.");
        System.out.println("There are " + (numChildren - numHappyChildren) + " angry children.");

    }

    
}

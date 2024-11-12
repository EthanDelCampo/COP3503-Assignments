/* Ethan Del Campo
 * Dr. Andrew Steinberg
 * COP3503 Fall 2024
 * Programming Assignment 4
 */

public class HopStepGame 
{

    public int minCost(int[] squares, int index)
    {
        
        //Base case where 2 squares are left to consider
        if(index == 1)
        {
            return Math.min(squares[0] + squares[1], squares[1]);
        }
        //Base case when 3 squares left to consider
        if(index == 2)
        {
            return Math.min(squares[0] + squares[2], squares[1] + squares[2]);
        }

        //Compares the two options between hopping and skipping
        int optionOne = minCost(squares, index - 2) + squares[index];
        int optionTwo = minCost(squares, index - 1) + squares[index];

        //Stores minimum between the two options above
        int result = Math.min(optionOne, optionTwo);

        //Returns the index
        return result;
        
    }


    //Function that uses memoization to find the result
    public int minCostMemoization(int[] squares, int index, int[] results) 
    {

        //Base case where 2 squares are left to consider
        if(index == 1)
        {
            results[index] = Math.min(squares[0] + squares[1], squares[1]);
            return Math.min(squares[0] + squares[1], squares[1]);
        }
        //Base case when 3 squares left to consider
        if(index == 2)
        {
            results[index] = Math.min(squares[0] + squares[2], squares[1] + squares[2]);
            return Math.min(squares[0] + squares[2], squares[1] + squares[2]);
        }

        //Checks to see if a result is already in place
        if(results[index] != 0)
        {
            return results[index];
        }
        
        //Compares the two options between hopping and skipping
        int optionOne = minCostMemoization(squares, index - 2, results) + squares[index];
        int optionTwo = minCostMemoization(squares, index - 1, results) + squares[index];

        //Stores minimum between options in results array
        results[index] = Math.min(optionOne, optionTwo);

        //Returns the answer
        return results[index];

    }


    //Function that uses tabulation to find the result
    public int minCostTabulation(int[] squares)
    {

        //Case where the length is 0
        if(squares.length == 0)
        {
            return 0;
        }
        //Case where the length is 1
        else if(squares.length == 1)
        {
            return squares[0];
        }
        //Case where the length is 2
        else if(squares.length == 2)
        {
            return Math.min(squares[0] + squares[1], squares[1]);
        }

        //Setting up an array of results
        int[] results = new int[squares.length];

        //Filling in first and second value of results
        results[0] = squares[0];
        results[1] = Math.min(squares[0] + squares[1], squares[1]);

        //Iterating for the rest of the array
        for(int i = 2; i < squares.length; i++)
        {
            results[i] = Math.min(results[i - 2] + squares[i], results[i - 1] + squares[i]);
        }

        //Returning better final option
        return Math.min(results[squares.length - 1], results[squares.length - 2]);

    }

}
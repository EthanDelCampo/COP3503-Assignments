/* Dr. Steinberg
   COP3503 Computer Science 2
   N-Queens
   NQueen.java
*/

import java.util.*;

public class NQueen
{
	private int n;
	private int board[][];
	private boolean [] rowused;
	private boolean [] ddiagused;
	private boolean [] udiagused;
	
	/*
	 * Ethan's Notes:
	 *  - n: the size of the board
	 *  - rowused: keeps track of which rows have queens on them
	 *  - ddiagused: keeps track of downward diagonals (top left to bottom right) used
	 *  - udiagused: keeps track of upward diagonals (bottom left to top right) used
	 *  - board: the board
	 */
	public NQueen(int n)
	{
		this.n = n;
		this.rowused = new boolean [n];
		this.ddiagused = new boolean [2 * n];
		this.udiagused = new boolean [2 * n];
		this.board = new int [n][n];
	}
	
	/*
	 * Ethan's notes:
	 *  - begins backtracking function at the first column (index 0)
	 */
	public boolean solveNQueen()
	{
		//in java all values in array are set to false and 0 by default when intialized
		
		return solveNQueenR(0);
	}
	
	/*
	 * Ethan's notes:
	 * 
	 * Recursive backtracking function for solving N-Queens problem:
	 *  - k represents current column for solving the problem
	 *  - for loop, iterates all rows x, over column k, trying to place queen in valid row of the column
	 *  - if positionOk(row, column) is false: nothing happens. invalid solution to the problem
	 *  - if positionOk(row, column) is true:
	 * 		- marks row x, relevant diagonal for this position as used, rused, ddiagused, udiagused updated to true; and board piece filled in
	 *  - checks for base case: if all queens have been placed properly (k = n - 1), returns true.
	 *  - if there are more queens left to go:
	 *  		- if recursive call returns true, it propogates up recursion column.
	 *  		- if recursive call returns false, backtrack!!
	 *  - return false: there if there is failure from the column, will backtrack.
	 */
	public boolean solveNQueenR(int k)
	{
		for(int x = 0; x < n; ++x)
		{
			if (positionOk(x, k))
			{
				
				rowused[x] = true;
				ddiagused[n - k + x] = true;
				udiagused[k + x] = true;
				board[x][k] = 1;
				
				if(k == n - 1) //did we solve the problem based on the bound given
					return true;
				else
				{
					if(solveNQueenR(k + 1))
						return true;
					else
					{
						//backtrack!!!
						rowused[x] = false;
						ddiagused[n - k + x] = false;
						udiagused[k + x] = false;
						board[x][k] = 0;
					}
				}
			}
		}
		
		return false;
	}
	
	/*
	 * Ethan's notes:
	 *  - helper function that checks if position is okay for a row index x and a column index k
	 */
	public boolean positionOk(int x, int k)
	{
		return !(rowused[x] || ddiagused[n - k + x] || udiagused[k + x]); //check to see if another queen already occupies a certain spot
	}
	
	/*
	 * Ethan's notes:
	 *  - Provides a string representation of the current state of the board
	 *  - displays where 1 is a queen, 0 where there is no queen
	 *  - DON'T FORGET toString() representations of functions!
	 */
	public String toString()
	{
		StringBuilder str = new StringBuilder("Board Size: ");
		str.append(n + " X " + n + "\n");
		str.append("Board Visualization\n");
		for(int row = 0; row < n; ++row)
		{
			for(int column = 0; column < n; ++column) 
				str.append(board[row][column] + " ");
			
			str.append("\n");
		}
		return str.toString();
	}
	
	/*
	 * Ethan's notes:
	 *  - Entry point of program
	 *  - Obj1 is a new NQueen wihich is 10x10
	 *  - Prints board
	 *  - Does the same thing for a 40x40 board, which will take a long time to solve
	 */
	public static void main(String [] args)
	{
		NQueen obj1 = new NQueen(10); 
		
		obj1.solveNQueen();
		
		System.out.println(obj1.toString());
		
		NQueen obj2 = new NQueen(20); //40 will take a long time to solve
		
		obj2.solveNQueen();
		
		System.out.println(obj2.toString());
	}
}
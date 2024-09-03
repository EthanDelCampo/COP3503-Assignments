/* Ethan Del Campo
 * Dr. Steinberg
 * COP3503 Fall 2024
 * Programming Assignment 1
 */

import java.util.Random;

public class Game 
{

    //Attributes necessary for game
    private int[][] board = new int[8][8];
    private char[] moves = {'d','r','b'};
    private Random random;

    private int currentPositionR; //Stores the current row index
    private int currentPositionC; //Stores the current column index

    //Constructor, assigns random to current game
    public Game(Random currentRandom)
    {

        this.random = currentRandom;
        this.board[0][0] = 1; //Assigns "Knight" to starting position
        this.currentPositionR = 0;
        this.currentPositionC = 0;

    }


    //Helper function that sees if move is out of bounds
    public boolean isValidMove(char move, int currentR, int currentC)
    {

        //Considering downward diagonal right move
        if(move == 'd')
        {
            if(((currentR + 1) <= 7) && ((currentC + 1) <= 7))
            {
                return true;
            }
            else {
                return false;
            }

        }
        //Considering rightward move
        else if(move == 'r')
        {
            if((currentC + 1) <= 7){
                return true;
            }
            else {
                return false;
            }

        }
        //Considering downward move
        else if(move == 'b')
        {
            if((currentR + 1) <= 7){
                return true;
            }
            else {
                return false;
            }

        }
        //Catches all other cases
        else 
        {
            return false;
        }

    }


    //Helper function that returns if there is a winner
    public boolean isThereWinner(){

        if(board[7][7] == 1)
        {
            return true;
        }
        else 
        {
            return false;
        }

    }

    
    /*
        Helper function that moves piece to new position
        given that move has been already checked to be valid
    */
    public void movePiece(char direction, int currentRow, int currentColumn){
        
        //Moves piece diagonally
        if(direction == 'd')
        {

            this.board[currentRow][currentColumn] = 0;
            this.board[currentRow+1][currentColumn+1] = 1;
            this.currentPositionR = currentRow+1;
            this.currentPositionC = currentColumn+1;

        }

        //Moves piece to right
        if(direction == 'r')
        {

            this.board[currentRow][currentColumn] = 0;
            this.board[currentRow][currentColumn+1] = 1;
            this.currentPositionC = currentColumn+1;

        }

        //Moves piece downward
        if(direction == 'b')
        {

            this.board[currentRow][currentColumn] = 0;
            this.board[currentRow+1][currentColumn] = 1;
            this.currentPositionR = currentRow+1;

        }

    }


    //Assigns random move to Player 2 (the computer)
    public char selectPlayerTwoMove(){
        
        //Random number
        int randomMove = this.random.nextInt(3);
        
        //Random move is diagonal
        if(randomMove == 0)
        {
            return moves[0];
        }

        //Random move is rightward
        else if(randomMove == 1)
        {
            return moves[1];
        }

        //Random move is downward
        else {
            return moves[2];
        }

    }


    //Actually moves the piece for player 2
    public void playerTwoTurn(char assignedMove, int currentR, int currentC)
    {

        char move = selectPlayerTwoMove();
        boolean isValidMove = isValidMove(move, currentR, currentC);

        /*
        If move is invalid, keeps generating 
        moves until a valid one is found
        */
        while(isValidMove == false)
        {
            move = selectPlayerTwoMove();
            isValidMove = isValidMove(move, currentR, currentC);
        }

        //Actually moves the piece for player 2 and updates indices
        movePiece(move, currentR, currentC);

    }


    /*
        Helper function that returns what direction 
        (if any) for Player 1 leads to
        a critical point pivotal for victory

        The critical points are {3,3}, {3,5}, {3,7},
        {5,3}, {5,5}, {5,7}, {7,3}, {7,5}, and {7,7}
        due to their mathematical advantage to win the game.

        These positions allow Player 1 to control the
        board and force Player 2 into a less advantageous
        position.
    */
    public char criticalPointMove(int currentR, int currentC)
    {

        //Preliminary check to get new index
        int possibleNewR = currentR + 1;
        int possibleNewC = currentC + 1;


        //Checks for critical point in diagonal right direction
        if (((possibleNewR >= 3) && (possibleNewR % 2 == 1)) && ((possibleNewC >= 3) && (possibleNewC % 2 == 1)))
        {
            return 'd';
        }

        //Checks for critical point in rightward direction
        else if (((currentR >= 3) && (currentR % 2 == 1)) && ((possibleNewC >= 3) && (possibleNewC % 2 == 1)))
        {
            return 'r';
        }

        //Checks for critical point in downward direction
        else if (((possibleNewR >= 3) && (possibleNewR % 2 == 1)) && ((currentC >= 3) && (currentC % 2 == 1)))
        {
            return 'b';
        }
       
        //Default case
        else return 'n';
    }


    //Assigns move to Player 1 through the strategy created
    public void playerOneTurn(int currentR, int currentC){

        //Moves diagonally at start of game
        if((currentR == 0) && (currentC == 0))
        {
            movePiece('d', currentR, currentC);
        }

        /*
        Case where current position is on second row but not [1][7]:

        Piece moves to right
        */
        else if((currentR == 1) && (currentC < 7))
        {
            movePiece('r', currentR, currentC);
        }

        /*
         Case where current position is on second column but not [7][1]:

         Piece moves downward
         */
        else if((currentC == 1) && (currentR < 7))
        {
            movePiece('d', currentR, currentC);
        }

        /* 
         Case where piece is anywhere else on board:
        
         Critical piece is guaranteed to be within the bounds
         of a valid move while it's Player 1's turn.
        
         This helps ensure that Player 1 can continue on the
         path to victory.
        */
        else if((currentR >= 2) && (currentC >= 2))
        {
            movePiece(criticalPointMove(currentR, currentC), currentR, currentC);
        }
    }


    //Function that actually simulates a round of the game
    public int play()
    {

        //Ensures the game only runs while there is no winner
        while(!isThereWinner())
        {

            //Simulates Player 1's turn
            playerOneTurn(this.currentPositionR, this.currentPositionC);

            //Checks to see if player 1 won
            if(isThereWinner())
            {
                return 1;
            }

            //Simulates Player 2 (the computer)'s turn
            playerTwoTurn(selectPlayerTwoMove(), this.currentPositionR, this.currentPositionC);

            //Checks to see if player 2 won
            if(isThereWinner())
            {
                return 2;
            }

        }

        //Default case
        return 2;
    }

}
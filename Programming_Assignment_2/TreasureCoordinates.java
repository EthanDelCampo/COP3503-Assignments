/* Ethan Del Campo
 * Dr. Steinberg
 * COP3503 Fall 2024
 * Programming Assignment 2
 */

import java.util.*;

public class TreasureCoordinates 
{
    
    //Fills ArrayList output with possible valid coordinates
    public ArrayList<String> determineCoordinates(String s)
    {

        ArrayList<String> result = new ArrayList<String>(); //Initializes list of possible coordinates
       
        //For loop finds possible x and y Strings
        for(int i = 1; i < s.length()-2; i++)
        {

            //Breaks string into possible numbers
            String xString = s.substring(1,i+1);
            String yString = s.substring(i+1, s.length()-1);

            //ArrayLists for possible unique x and y coordinates
            ArrayList<String> possibleXCoordinates = new ArrayList<String>();
            ArrayList<String> possibleYCoordinates = new ArrayList<String>();

            //Backtracking function for x-coordinates
            StringBuilder newXString = new StringBuilder(); //sets up X StringBuilder
            backTrackingDecimal(possibleXCoordinates, xString, newXString, 0, false);

            //Backtracking function for y-coordinates
            StringBuilder newYString = new StringBuilder(); //sets up Y StringBuilder
            backTrackingDecimal(possibleYCoordinates, yString, newYString, 0, false);


            //Combines the solutions, then adds them to result
            for(int x = 0; x < possibleXCoordinates.size(); x++)
            {
                for(int y = 0; y < possibleYCoordinates.size(); y++)
                {
                    result.add("(" + possibleXCoordinates.get(x) + ", " + possibleYCoordinates.get(y) + ")");
                }
            }

        }

        return result; //Returns ArrayList of coordinates

    }



    // Backtracking function that generates possible valid decimal coordinates
    public void backTrackingDecimal(ArrayList<String> decimals, String originalString, StringBuilder newString, int index, boolean isThereDecimal)
    {

        //Base case where index equals original length
        if(index == originalString.length())
        {

            //If decimal is valid, add to arrayList
            if(isValidDecimal(newString))
            {

                String result = newString.toString();

                decimals.add(result);

            }

            return;

        }

        //Adds element at index by default
        newString.append(originalString.charAt(index));

        //Checks to see if there is no decimal
        if(!isThereDecimal)
        {

            newString.append('.'); //Adds decimal place

            //Recursive backtracking call, given decimal place is already inserted
            backTrackingDecimal(decimals, originalString, newString, index + 1, true);

            newString.deleteCharAt(newString.length() - 1); //Undos the decimal place

        }

        //Generalized recursive backtracking call
        backTrackingDecimal(decimals, originalString, newString, index + 1, isThereDecimal);

        //Undos the last character
        newString.deleteCharAt(newString.length() - 1);

    }
    


    //Function that ensures a decimal is valid
    public boolean isValidDecimal(StringBuilder decimal)
    {

        //Converting StringBuilder to String
        String decimalStr = decimal.toString();

        //Checks to ensure length isn't out of bounds
        if(decimalStr.length() >= 2)
        {
            //Checks for inappropriate start of decimal
            if(decimalStr.charAt(0) == '0' && decimalStr.charAt(1) != '.')
            {
                return false;
            }

            //Checks for inappropriate decimal point placing
            if(decimalStr.charAt(0) == '.' || decimalStr.charAt(decimalStr.length() - 1) == '.')
            {
                return false;
            }

            //Checks if decimal has at least one trailing zero
            if(areTrailingZeroes(decimalStr))
            {
                return false;
            }

        }

        //All other cases
        return true;

    }



    //Checks if decimal has at least one trailing zero
    public boolean areTrailingZeroes(String decimalStr)
    {

        //If not a decimal, automatically return false
        if(decimalStr.indexOf('.') == -1)
        {
            return false;
        }

        //Checks if last character place is a '0'
        if(decimalStr.charAt(decimalStr.length() - 1) == '0')
        {
            return true;
        }

        return false; //Default case

    }

}
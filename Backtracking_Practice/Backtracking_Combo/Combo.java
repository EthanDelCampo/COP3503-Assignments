import java.util.ArrayList;
import java.util.Arrays;

public class Combo {
    public static void main(String[] args) {
        ArrayList<Integer> original = new ArrayList<>(Arrays.asList(1, 2, 3));
        int k = 2;  // Length of combinations
        ArrayList<Integer> result = new ArrayList<>();
        
        
        powerSet(original, result, 3, 3, 0);
    }

    /*
     * Notes:
     *  - original: input array containing integers to make combinations from
     *  - result: list holding current combination being built
     *  - currentIndex: tracks how many elements have been added to result so far
     *  - size: length of combination we want to built
     *  - start: tells the function to start picking elements from the original array to avoid generating permutations
     */
    public static void backtrack(ArrayList<Integer> original, ArrayList<Integer> result, int currentIndex, int size, int start) {
        
        /*
         * Base case for recursion; when currentIndex = size, built a combination of required length.
         * So we print current combination (result) and return to backtrack and try other sizes
         */
        if (currentIndex == size) {
            System.out.println(result);
            return;
        }

        /*
         * Iterates over elements in the original array starting from index start
         *  - Ensures we only move forward in the array, so combinations are built in non-decreasing order
         *  - Avoid generating permutations like [2,1] and [1,2] if has already been generated
         */
        for (int i = start; i < original.size(); i++) {
            // Add the current element to the result

            /*
             * Take element at position i in the original array and add it to the current result list
             */
            result.add(original.get(i));

            System.out.println("i is " + i);

            /*
             * Recursive Call:
             *  - Continues building the combination, currentIndex + 1  moves to the next position in the combination
             *  - Passing i as the new start ensures the next recursive call will only consider elements at or after i
             */
            backtrack(original, result, currentIndex + 1, size, i);

            /*
             * Removing last element: backtracking
             */
            result.remove(result.size() - 1);

            /*
            * Used for skipping duplicates (i.e. if original contains [1,1,2])
            */
            while (i + 1 < original.size() && original.get(i).equals(original.get(i + 1))) {
                i++;
                System.out.println("Went through the process of duplicating");
            }
        }
    }



}



    


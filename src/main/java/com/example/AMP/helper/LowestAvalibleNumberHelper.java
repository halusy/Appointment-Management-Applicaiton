package com.example.AMP.helper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class contains method to help return the lowest available number from an arraylist
 *
 * @author Nicholas Ryan
 * @version 1.0
 */
public class LowestAvalibleNumberHelper {

    /**
     *
     * This method will take an arraylist of integers and return the lowest available number.
     * e.g. Passed in arraylist = [5,3,2,1] This method will return 4
     * e.g. Passed in arraylist = [5,4,3,2,1] This method will return 6
     *
     * @param numbers
     * @return
     */

    public static int lowestNumberFinder(ArrayList<Integer> numbers){

        int minValue = Integer.MAX_VALUE;

        // Iterate through the ArrayList to find the minimum value
        for (int value : numbers) {
            if (value < minValue) {
                minValue = value;
            }
        }

        // Check if the minimum value is already in the list, if not, return it
        for (int i = 1; i <= Integer.MAX_VALUE; i++) {
            if (!numbers.contains(i)) {
                return i;
            }
        } return (Collections.max(numbers) + 1);
    }
}

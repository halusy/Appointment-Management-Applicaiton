package com.example.AMP.helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * This class contains methods which return descending sequences based of a given integer
 *
 * @author Nicholas Ryan
 * @version 1.0
 */
public class DescendingSequenceHelper {
    static int numberOf = 0;

    /**
     *
     * This method takes a String tableValueNeeded and will return a descending sequence based off the SQL table of interest
     * e.g. Pass in the customer table, and if there are 5 customers, this will return [5,4,3,2,1]
     *
     * @param tableValueNeeded
     * @return
     * @throws SQLException
     */
    public static Integer[] descendingSequenceRetriever(String tableValueNeeded) throws SQLException {

        numberOf = 0;

        String sql = "SELECT * FROM " + tableValueNeeded;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);;
        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            numberOf++;
        }
        return finalHelper(numberOf);
    }

    public static Integer[] finalHelper(Integer numberOf){

        Integer[] sequence = new Integer[numberOf];
        for (int i = 0; i < numberOf; i++) {
            sequence[i] = numberOf - i;
        }

        return sequence;

    }
}

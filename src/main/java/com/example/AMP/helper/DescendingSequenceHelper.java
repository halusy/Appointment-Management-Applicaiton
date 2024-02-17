package com.example.AMP.helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DescendingSequenceHelper {
    static int numberOf = 0;
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

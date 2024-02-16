package com.example.AMP.helper;

import java.sql.*;
public class LoginVerification{

    public static String currentUser;

    public static Boolean loginVerfication(String username, String password) throws SQLException {

        String sql = "SELECT * FROM USERS WHERE User_Name = ? AND Password = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();

        String usrRetrival = null;
        String pwRetrival = null;

        while(rs.next()){

            usrRetrival = rs.getString("User_Name");
            pwRetrival = rs.getString("Password");

        }
        if (usrRetrival == null && pwRetrival == null){
            return false;
        } else {
            currentUser = usrRetrival;
            return true;
        }
    }
    public static String getCurrentUser(){

        return currentUser;

    }

}

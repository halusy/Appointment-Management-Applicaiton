package com.example.AMP.helper;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.ZonedDateTime;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * This class contains methods that manage the login verification of all login attempts to the program.
 *
 * @author Nicholas Ryan
 * @version 1.0
 */
public class LoginVerification{
    public static String currentUser;
    public static int currentUserId;

    /**
     *
     * This method takes a username and password String and returns true if the login credentials are correct.
     *
     * @param username
     * @param password
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public static Boolean loginVerfication(String username, String password) throws SQLException, IOException {

        String sql = "SELECT * FROM USERS WHERE User_Name = ? AND Password = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();

        String usrRetrival = null;
        String pwRetrival = null;
        int idRetrieval = -1;

        while(rs.next()){

            idRetrieval = rs.getInt("User_ID");
            usrRetrival = rs.getString("User_Name");
            pwRetrival = rs.getString("Password");
        }
        if (usrRetrival == null && pwRetrival == null){
            loginAttemptTracker(username, password, false);
            return false;

        } else {
            currentUser = usrRetrival;
            currentUserId = idRetrieval;
            loginAttemptTracker(username, password, true);
            return true;
        }
    }
    public static String getCurrentUser(){
        return currentUser;
    }

    public static int getCurrentUserId(){
        return currentUserId;
    }

    /**
     *
     * This method writes all login attempts to the login_activity.txt file in the Root of this project
     *
     * @param username
     * @param Password
     * @param isSuccessful
     * @throws IOException
     */
    private static void loginAttemptTracker(String username, String Password, boolean isSuccessful) throws IOException{

        ZonedDateTime loginAttemptTime = ZonedDateTime.now();
        String outcome;

        if (isSuccessful){

            outcome = "Succeeded ";

        } else { outcome = "Failed "; }

        String filePath = "login_activity.txt";
        Path outputPath = Paths.get(filePath);

        String content = "A Login attempted " + outcome + "at: " + loginAttemptTime + " with the Username: " + username + " and the Password: " + Password + "\n";

        Files.write(outputPath, content.getBytes(), java.nio.file.StandardOpenOption.APPEND);

        }
    }






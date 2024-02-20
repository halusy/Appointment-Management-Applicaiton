package com.example.AMP.helper;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class manages the SQL Database Connection. It was already in place from the example project on the VM I am utilizing
 *
 * @author WGUTeam
 * @version ?
 */
public abstract class JDBC {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "sqlUser";
    private static String password = "Passw0rd!";
    public static Connection connection;
    private static PreparedStatement preparedStatement;

    public static String connectionStatus;

    /**
     * This method connects to the SQL Database
     */

    public static void makeConnection() {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // reference Connection object
            connectionStatus = LocaleDesignation.LocalLang.getString("dbConnection");
        }
        catch(ClassNotFoundException e) {
            System.out.println("Error:" + e.getMessage());
        }
        catch(SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * This method closes the connection to the SQL database
     */
    public static void closeConnection() {
        try {
            connection.close();
            connectionStatus = "Database Connection closed!";
            System.out.println(connectionStatus);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

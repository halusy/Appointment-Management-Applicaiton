package com.example.AMP.helper;

import com.example.AMP.models.Appointment;
import com.example.AMP.models.Customer;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * This class contains methods that convert SQL Database customers to the correct Java Object
 *
 * @author Nicholas Ryan
 * @version 1.0
 */
public class SQLCustomerToObject {

    static int customerId;
    static String name;
    static String address;
    static String postalCode;
    static String phoneNumber;
    static Timestamp createDate;
    static String createdBy;
    static Timestamp lastUpdateDate;
    static String lastUpdatedBy;
    static int divisionId;

    /**
     * converts SQL Customers to Java Customer Objects
     *
     * @throws SQLException
     */
    public static void SQLCustomerToObjectMethod() throws SQLException {

        ObservableListHelper.clearCustomers();
        Customer.resetCustomerCounter();

        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            customerId = rs.getInt("Customer_ID");
            name = rs.getString("Customer_Name");
            address = rs.getString("Address");
            postalCode = rs.getString("Postal_Code");
            phoneNumber = rs.getString("Phone");
            createDate = rs.getTimestamp("Create_Date");
            createdBy = rs.getString("Created_By");
            lastUpdateDate = rs.getTimestamp("Last_Update");
            lastUpdatedBy = rs.getString("Last_Updated_By");
            divisionId = rs.getInt("Division_ID");

            Customer customer = new Customer(customerId, name, address, postalCode, phoneNumber, createDate, createdBy, lastUpdateDate, lastUpdatedBy, divisionId);

            ObservableListHelper.addCustomer(customer);

        }
    }
}

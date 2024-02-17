package com.example.AMP.helper;

import com.example.AMP.models.Appointment;
import javafx.fxml.Initializable;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class SQLAppointmentToObject{

        static int appointmentId;
        static String title;
        static String description;
        static String location;
        static String type;
        static Timestamp startDate;
        static Timestamp endDate;
        static Timestamp dateCreated;
        static String createdBy;
        static Timestamp lastUpdateDate;
        static String lastUpdatedBy;
        static int customerId;
        static int userId;
        static int contactId;

    public static void SQLAppointmentToObjectMethod() throws SQLException {

            ObservableListHelper.clearAppointments();
            Appointment.resetAppointmentCounter();

            String sql = "SELECT * FROM APPOINTMENTS";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                appointmentId = rs.getInt("Appointment_ID");
                title = rs.getString("Title");
                description = rs.getString("Description");
                location = rs.getString("Location");
                type = rs.getString("Type");
                startDate = rs.getTimestamp("Start");
                endDate = rs.getTimestamp("End");
                dateCreated = rs.getTimestamp("Create_Date");
                createdBy = rs.getString("Created_By");
                lastUpdateDate = rs.getTimestamp("Last_Update");
                lastUpdatedBy = rs.getString("Last_Updated_By");
                customerId = rs.getInt("Customer_ID");
                userId = rs.getInt("User_ID");
                contactId = rs.getInt("Contact_ID");

                Appointment appointment = new Appointment(appointmentId, title, description, location, type, startDate, endDate, dateCreated, createdBy, lastUpdateDate, lastUpdatedBy, customerId, userId, contactId);

                ObservableListHelper.addAppointment(appointment);

            }
    }
}

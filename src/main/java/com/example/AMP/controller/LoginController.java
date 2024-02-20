package com.example.AMP.controller;

import com.example.AMP.MainApplication;
import com.example.AMP.helper.*;
import com.example.AMP.models.Appointment;

import java.io.IOException;
import java.sql.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.time.ZoneId;
import java.time.zone.ZoneRulesProvider;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.io.Serializable;
import java.util.TimeZone;

/**
 *This is the LoginController Class. It manages the elements of the LoginView and gives the Login View it's Functionality.
 *
 * @author Nicholas Ryan
 * @version 1.0
 *
 */
public class LoginController implements Initializable {

    //FXML Element Declaration
    @FXML private Label ErrorLabel;
    @FXML private Label LocationDataLabel;
    @FXML private Label LocationLabel;
    @FXML private Button LoginButton;
    @FXML private Label LoginPortalLabel;
    @FXML private TextField PasswordField;
    @FXML private Label PasswordLabel;
    @FXML private TextField UsernameField;
    @FXML private Label UsernameLabel;
    @FXML private Label DatabaseStatusLabel;

    /**
     * This is the Initialize method which runs when the Login Page is viewed by the User. This method manages critical login processes.
     *
     * @param url
     * @param resourceBundle
     */

    @Override public void initialize(URL url, ResourceBundle resourceBundle) {

        //Tells the program that the previous scene was the Login Page
        PreviousSceneHelper.loginPageSetter();

        //Captures the Users timezone in a Timezone Variable for conversions
        ZoneIdHelper.setCurrentZone();

        //Sets the Programs Timezone to UTC
        String timeZoneID = "UTC";
        TimeZone.setDefault(TimeZone.getTimeZone(timeZoneID));

        //Checks the SQL Database to Initialize the Java Appointment Objects
        try {
            SQLAppointmentToObject.SQLAppointmentToObjectMethod();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //Checks the SQL Database to Initialize the Java Customer Objects
        try {
            SQLCustomerToObject.SQLCustomerToObjectMethod();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Sets Login Error Label to not be visible
        ErrorLabel.setVisible(false);

        //Establishes Connection with the SQL Database and Displays the Outcome
        DatabaseStatusLabel.setText(JDBC.connectionStatus);

        UsernameLabel.setText(LocaleDesignation.LocalLang.getString("UsernameLabel"));
        PasswordLabel.setText(LocaleDesignation.LocalLang.getString("PasswordLabel"));
        LoginPortalLabel.setText(LocaleDesignation.LocalLang.getString("LoginPortalLabel"));
        LoginButton.setText(LocaleDesignation.LocalLang.getString("LoginButton"));
        ErrorLabel.setText(LocaleDesignation.LocalLang.getString("ErrorLabel"));
        LocationLabel.setText(LocaleDesignation.LocalLang.getString("LocationLabel"));
        LocationDataLabel.setText(String.valueOf(ZoneIdHelper.currentZone));
    }

    /**
     * This method executed when the Login Button is Clicked. It calls many other methods to assist in the Login Verification of the Login Process.
     *
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML void onLoginButtonClick(ActionEvent event) throws SQLException, IOException {

        //Captures the Entered Username and Password
        String UserUsername = String.valueOf(UsernameField.getCharacters());
        String UserPassword = String.valueOf(PasswordField.getCharacters());

        //Passes username and Password into the LoginVerification class and allows the user to move to the Main Scene if Credentials are Valid
        if (LoginVerification.loginVerfication(UserUsername,UserPassword)){

            //Moves to Main Scene
            System.out.println("Login Successful");
            Parent root = FXMLLoader.load(MainApplication.class.getResource("main-schedule-view.fxml"));
            Stage stage = (Stage) PasswordField.getScene().getWindow();
            Scene scene = new Scene(root, 720, 400);
            stage.setTitle("Appointment Management Program (AMP)");
            stage.setScene(scene);
            stage.show();

        } else {

            //Shows the Error Label if Verification Fails
            ErrorLabel.setVisible(true);
        }
    }
}



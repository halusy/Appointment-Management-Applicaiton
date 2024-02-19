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

public class LoginController implements Initializable {

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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        PreviousSceneHelper.loginPageSetter();
        ZoneIdHelper.setCurrentZone();


        String timeZoneID = "UTC";
        TimeZone.setDefault(TimeZone.getTimeZone(timeZoneID));

        try {
            SQLAppointmentToObject.SQLAppointmentToObjectMethod();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            SQLCustomerToObject.SQLCustomerToObjectMethod();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ErrorLabel.setVisible(false);

        DatabaseStatusLabel.setText(JDBC.connectionStatus);

        UsernameLabel.setText(LocaleDesignation.LocalLang.getString("UsernameLabel"));
        PasswordLabel.setText(LocaleDesignation.LocalLang.getString("PasswordLabel"));
        LoginPortalLabel.setText(LocaleDesignation.LocalLang.getString("LoginPortalLabel"));
        LoginButton.setText(LocaleDesignation.LocalLang.getString("LoginButton"));
        ErrorLabel.setText(LocaleDesignation.LocalLang.getString("ErrorLabel"));
        LocationLabel.setText(LocaleDesignation.LocalLang.getString("LocationLabel"));
        LocationDataLabel.setText(String.valueOf(ZoneIdHelper.currentZone));
    }
    @FXML void onLoginButtonClick(ActionEvent event) throws SQLException, IOException {

        String UserUsername = String.valueOf(UsernameField.getCharacters());
        String UserPassword = String.valueOf(PasswordField.getCharacters());

        if (LoginVerification.loginVerfication(UserUsername,UserPassword)){

            System.out.println("Login Successful");
            Parent root = FXMLLoader.load(MainApplication.class.getResource("main-schedule-view.fxml"));
            Stage stage = (Stage) PasswordField.getScene().getWindow();
            Scene scene = new Scene(root, 720, 400);
            stage.setTitle("Appointment Management Program (AMP)");
            stage.setScene(scene);
            stage.show();

        } else {

            System.out.println("Login Failed");
            ErrorLabel.setVisible(true);

        }
    }
}



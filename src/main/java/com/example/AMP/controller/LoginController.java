package com.example.AMP.controller;

import com.example.AMP.helper.JDBC;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.time.ZoneId;
import java.time.zone.ZoneRulesProvider;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.time.ZoneId;
import java.io.Serializable;


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

        ErrorLabel.setVisible(false);

        DatabaseStatusLabel.setText(JDBC.connectionStatus);

        Locale L =  new Locale("fr","FR");

        ResourceBundle LocalLang = ResourceBundle.getBundle("com.example.AMP.Bundle.Bundle", L);
        UsernameLabel.setText(LocalLang.getString("UsernameLabel"));
        PasswordLabel.setText(LocalLang.getString("PasswordLabel"));
        LoginPortalLabel.setText(LocalLang.getString("LoginPortalLabel"));
        LoginButton.setText(LocalLang.getString("LoginButton"));
        ErrorLabel.setText(LocalLang.getString("ErrorLabel"));
        // set Location string
        // set Database status string

        ZoneId thisZone = ZoneId.systemDefault();
        System.out.println(thisZone);

    }

    @FXML void onLoginButtonClick(ActionEvent event) {

        String H = String.valueOf(Locale.getDefault());
        System.out.println(H);
        ErrorLabel.setVisible(true);


    }

}



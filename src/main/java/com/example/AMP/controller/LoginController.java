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

    @FXML private Label welcomeText;@FXML protected void onHelloButtonClick() {
        welcomeText.setText("Boner");
    }
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

        if (Objects.equals(String.valueOf(Locale.getDefault()), "en_US")) {
            UsernameLabel.setText("Username");
            PasswordLabel.setText("Password");
            LoginPortalLabel.setText("LOGIN PORTAL");
            LoginButton.setText("Login");
            ErrorLabel.setText("Username & Password Do Not Match! Try Again");
        } else if (Objects.equals(String.valueOf(Locale.getDefault()), "fr_US")) {
            UsernameLabel.setText("Nom d'utilisateur");
            PasswordLabel.setText("Mot de passe");
            LoginPortalLabel.setText("PORTAIL DE CONNEXION");
            LoginButton.setText("Se Connecter");
            ErrorLabel.setText("Le nom d'utilisateur et le mot de passe ne correspondent pas! Réessayez");
        }
    }

    @FXML void onLoginButtonClick(ActionEvent event) {

        String H = String.valueOf(Locale.getDefault());
        System.out.println(H);
        ErrorLabel.setVisible(true);


    }

}



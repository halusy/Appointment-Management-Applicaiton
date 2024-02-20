package com.example.AMP;

import com.example.AMP.helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * This application is an Appointment Management Applicaiton (AMP) which will allow users to add, modify, and delete both Appointments and Customers.
 * The program works in tandem with a SQL Database, allowing the user to do complex Database management while only interacting with a simple GUI.
 *
 * JAVADOC FOLDER LOCATION: in the src of this project
 *
 * @author Nicholas Ryan
 * @version 1.0
 */

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 360);
        stage.setTitle("Appointment Management Program (AMP)");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        JDBC.makeConnection();
        launch();
        JDBC.closeConnection();
    }
}
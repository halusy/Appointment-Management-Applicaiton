package com.example.AMP.controller;

import com.example.AMP.MainApplication;
import com.example.AMP.helper.LocaleDesignation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScheduleViewController implements Initializable {

    @FXML private Button LogoutButton;
    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button modifyButton;
    @FXML private Button reportsButton;
    @FXML private RadioButton sortAppointmentsAllRadio;
    @FXML private Label sortAppointmentsByLabel;
    @FXML private RadioButton sortAppointmentsMonthRadio;
    @FXML private RadioButton sortAppointmentsWeekRadio;
    @FXML private Label titleLabel;
    @FXML private RadioButton viewCustomersRadio;

    @FXML void onAddButtonClick(ActionEvent event) throws IOException {

        if (viewCustomersRadio.isSelected() == true){

            Parent root = FXMLLoader.load(MainApplication.class.getResource("add-customer-view.fxml"));
            Stage stage = (Stage) viewCustomersRadio.getScene().getWindow();
            Scene scene = new Scene(root, 720, 400);
            stage.setTitle("Appointment Management Program (AMP)");
            stage.setScene(scene);
            stage.show();

        }

        if (viewCustomersRadio.isSelected() == false){

            Parent root = FXMLLoader.load(MainApplication.class.getResource("add-appointment-view.fxml"));
            Stage stage = (Stage) viewCustomersRadio.getScene().getWindow();
            Scene scene = new Scene(root, 720, 400);
            stage.setTitle("Appointment Management Program (AMP)");
            stage.setScene(scene);
            stage.show();

        }
    }
    @FXML void onDeleteButtonClick(ActionEvent event) {}
    @FXML void onLogoutButtonClick(ActionEvent event) {



    }
    @FXML void onModifyButtonClick(ActionEvent event) throws IOException {

        if (viewCustomersRadio.isSelected() == true){

            Parent root = FXMLLoader.load(MainApplication.class.getResource("modify-customer-view.fxml"));
            Stage stage = (Stage) viewCustomersRadio.getScene().getWindow();
            Scene scene = new Scene(root, 720, 400);
            stage.setTitle("Appointment Management Program (AMP)");
            stage.setScene(scene);
            stage.show();
        }

        if (viewCustomersRadio.isSelected() == false){

            Parent root = FXMLLoader.load(MainApplication.class.getResource("modify-appointment-view.fxml"));
            Stage stage = (Stage) viewCustomersRadio.getScene().getWindow();
            Scene scene = new Scene(root, 720, 400);
            stage.setTitle("Appointment Management Program (AMP)");
            stage.setScene(scene);
            stage.show();

        }

    }
    @FXML void onReportsButtonClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(MainApplication.class.getResource("report-view.fxml"));
        Stage stage = (Stage) viewCustomersRadio.getScene().getWindow();
        Scene scene = new Scene(root, 720, 400);
        stage.setTitle("Appointment Management Program (AMP)");
        stage.setScene(scene);
        stage.show();

    }
    @FXML void sortAppointmentsAllRadioToggle(ActionEvent event) {}
    @FXML void sortAppointmentsMonthRadioToggle(ActionEvent event) {}
    @FXML void sortAppointmentsWeekRadioToggle (ActionEvent event) {}
    @FXML void viewCustomersRadioToggle(ActionEvent event) {

        if (viewCustomersRadio.isSelected() == true){
            addButton.setText(LocaleDesignation.LocalLang.getString("addCustomerButtonText"));
            modifyButton.setText(LocaleDesignation.LocalLang.getString("modifyCustomerButtonText"));
            deleteButton.setText(LocaleDesignation.LocalLang.getString("deleteCustomerButtonText"));
        }

        if (viewCustomersRadio.isSelected() == false){
            addButton.setText(LocaleDesignation.LocalLang.getString("addAppointmentButtonText"));
            modifyButton.setText(LocaleDesignation.LocalLang.getString("modifyAppointmentButtonText"));
            deleteButton.setText(LocaleDesignation.LocalLang.getString("deleteAppointmentButtonText"));
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        addButton.setText(LocaleDesignation.LocalLang.getString("addAppointmentButtonText"));
        modifyButton.setText(LocaleDesignation.LocalLang.getString("modifyAppointmentButtonText"));
        deleteButton.setText(LocaleDesignation.LocalLang.getString("deleteAppointmentButtonText"));

        //create and populate both a customer table view & an appointment table view

    }

}
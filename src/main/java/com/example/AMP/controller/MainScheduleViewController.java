package com.example.AMP.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

import java.net.URL;
import java.util.ResourceBundle;

public class MainScheduleViewController implements Initializable {

    @FXML private Button LogoutButton;
    @FXML private Button addAppointmentButton;
    @FXML private Button deleteAppointmentButton;
    @FXML private Button modifyAppointmentButton;
    @FXML private Button reportsButton;
    @FXML private RadioButton sortAppointmentsAllRadio;
    @FXML private Label sortAppointmentsByLabel;
    @FXML private RadioButton sortAppointmentsMonthRadio;
    @FXML private RadioButton sortAppointmentsWeekRadio;
    @FXML private Label titleLabel;
    @FXML private RadioButton viewCustomersRadio;

    @FXML void onAddAppointmentButtonClick(ActionEvent event) {}
    @FXML void onDeleteAppointmentButtonClick(ActionEvent event) {}
    @FXML void onLogoutButtonClick(ActionEvent event) {}
    @FXML void onModifyAppointmentButtonClick(ActionEvent event) {}
    @FXML void onReportsButtonClick(ActionEvent event) {}
    @FXML void sortAppointmentsAllRadioToggle(ActionEvent event) {}
    @FXML void sortAppointmentsMonthRadioToggle(ActionEvent event) {}
    @FXML void sortAppointmentsWeekRadioToggle (ActionEvent event) {}
    @FXML void viewCustomersRadioToggle(ActionEvent event) {}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){



    }

}
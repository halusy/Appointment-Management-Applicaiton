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

    @FXML void onAddButtonClick(ActionEvent event) {}
    @FXML void onDeleteButtonClick(ActionEvent event) {}
    @FXML void onLogoutButtonClick(ActionEvent event) {}
    @FXML void onModifyButtonClick(ActionEvent event) {}
    @FXML void onReportsButtonClick(ActionEvent event) {}
    @FXML void sortAppointmentsAllRadioToggle(ActionEvent event) {}
    @FXML void sortAppointmentsMonthRadioToggle(ActionEvent event) {}
    @FXML void sortAppointmentsWeekRadioToggle (ActionEvent event) {}
    @FXML void viewCustomersRadioToggle(ActionEvent event) {
        if (viewCustomersRadio.isSelected() == true){
            addButton.setText("cheese");
        }

        if (viewCustomersRadio.isSelected() == false){
            System.out.println("ayo");
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){


        //create and populate both a customer table view & an appointment table view


    }

}
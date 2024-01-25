package com.example.AMP.controller;

import com.example.AMP.MainApplication;
import com.example.AMP.helper.PreviousSceneHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyAppointmentViewController implements Initializable {

    //FXML Label Declarations
    @FXML private Label modifyAppointmentFormTitleLabel;
    @FXML private Label appointmentTitleLabel;
    @FXML private Label appointmentContactIdLabel;
    @FXML private Label appointmentCustomerIdLabel;
    @FXML private Label appointmentDescriptionLabel;
    @FXML private Label appointmentEndDateLabel;
    @FXML private Label appointmentEndTimeLabel;
    @FXML private Label appointmentIdLabel;
    @FXML private Label appointmentLocationLabel;
    @FXML private Label appointmentStartDateLabel;
    @FXML private Label appointmentStartTimeLabel;
    @FXML private Label appointmentTypeLabel;
    @FXML private Label appointmentUserIdLabel;

    //FXML TextField Declarations

    @FXML private TextField appointmentDescriptionTextField;
    @FXML private TextField appointmentIdTextField;
    @FXML private TextField appointmentLocationTextField;
    @FXML private TextField appointmentTitleTextField;
    @FXML private TextField appointmentTypeTextField;

    //FXML Button Declarations

    @FXML private Button cancelButton;
    @FXML private Button appointmentModifyFormSaveButton;

    //FXML ChoiceBox Declarations

    @FXML private ChoiceBox<?> contactIdChoiceBox;
    @FXML private ChoiceBox<?> customerIdChoiceBox;
    @FXML private ChoiceBox<?> userIdChoiceBox;

    //FXML DatePicker Declarations

    @FXML private DatePicker endDatePicker;
    @FXML private DatePicker startDatePicker;

    //FXML Spinner Declarations

    @FXML private Spinner<?> endTimeHourSpinner;
    @FXML private Spinner<?> endTimeMinuteSpinner;
    @FXML private Spinner<?> startTimeHourSpinner;
    @FXML private Spinner<?> startTimeMinuteSpinner;

    //FXML ActionEvent Declarations

    @FXML void onAppointmentModifyFormSaveButtonClick(ActionEvent event) {}

    @FXML void onCancelButtonClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(MainApplication.class.getResource("main-schedule-view.fxml"));
        Stage stage = (Stage) modifyAppointmentFormTitleLabel.getScene().getWindow();
        Scene scene = new Scene(root, 720, 400);
        stage.setTitle("Appointment Management Program (AMP)");
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        PreviousSceneHelper.PsSetterFalse();

    }

} //Main Class End Bracket

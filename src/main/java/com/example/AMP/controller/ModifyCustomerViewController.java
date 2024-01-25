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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyCustomerViewController implements Initializable {

    //FXML Label Declarations

    @FXML private Label modifyCustomerFormTitleLabel;
    @FXML private Label customerAddressLabel;
    @FXML private Label customerCountryLabel;
    @FXML private Label customerPostalCodeLabel;
    @FXML private Label customerPhoneNumberLabel;
    @FXML private Label customerNameLabel;
    @FXML private Label customerDivisionLabel;
    @FXML private Label customerIdLabel;

    //FXML ChoiceBox Declarations

    @FXML private ChoiceBox<?> customerCountryChoiceBox;
    @FXML private ChoiceBox<?> customerDivisionChoiceBox;

    //FXML Button Declarations

    @FXML private Button cancelButton;
    @FXML private Button customerModifyFormSaveButton;

    //FXML TextField Declarations

    @FXML private TextField customerAddressTextField;
    @FXML private TextField customerNameTextField;
    @FXML private TextField customerPhoneNumberTextField;
    @FXML private TextField customerPostalCodeTextField;
    @FXML private TextField customerIdTextField;

    //FXML ActionEvent Declarations

    @FXML void onCancelButtonClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(MainApplication.class.getResource("main-schedule-view.fxml"));
        Stage stage = (Stage) modifyCustomerFormTitleLabel.getScene().getWindow();
        Scene scene = new Scene(root, 720, 400);
        stage.setTitle("Appointment Management Program (AMP)");
        stage.setScene(scene);
        stage.show();

    }
    @FXML void onCustomerModifyFormSaveButtonClick(ActionEvent event) {}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        PreviousSceneHelper.PsSetterTrue();

    }

} //Main Class Closing Bracket

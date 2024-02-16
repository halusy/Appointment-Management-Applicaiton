package com.example.AMP.controller;

import com.example.AMP.MainApplication;
import com.example.AMP.helper.LoginVerification;
import com.example.AMP.helper.PreviousSceneHelper;
import com.example.AMP.models.Customer;
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
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class AddCustomerViewController implements Initializable {

    //FXML Label Declarations

    @FXML private Label addCustomerFormTitleLabel;
    @FXML private Label customerAddressLabel;
    @FXML private Label customerCountryLabel;
    @FXML private Label customerPostalCodeLabel;
    @FXML private Label customerPhoneNumberLabel;
    @FXML private Label customerNameLabel;
    @FXML private Label customerDivisionLabel;
    @FXML private Label customerIdLabel;

    //FXML Button Declarations

    @FXML private Button cancelButton;
    @FXML private Button customerAddFormSaveButton;

    //FXML ChoiceBox Declarations

    @FXML private ChoiceBox<String> customerCountryChoiceBox;
    @FXML private ChoiceBox<String> customerDivisionChoiceBox;

    private String[] UsDivisionSelection = {"US"};
    private String[] CaDivisionSelection = {"CANADA"};
    private String[] UkDivisionSelection = {"UK"};
    private String[] CountrySelection = {"United States", "Canada", "United Kingdom" };

    public void getCountrySelection(ActionEvent event){

        customerDivisionChoiceBox.setDisable(false);
        customerDivisionChoiceBox.getItems().removeAll(UkDivisionSelection);
        customerDivisionChoiceBox.getItems().removeAll(UsDivisionSelection);
        customerDivisionChoiceBox.getItems().removeAll(CaDivisionSelection);

        if (customerCountryChoiceBox.getValue() == "United States"){

            customerDivisionChoiceBox.getItems().addAll(UsDivisionSelection);

        } else if (customerCountryChoiceBox.getValue() == "Canada"){

            customerDivisionChoiceBox.getItems().addAll(CaDivisionSelection);

        } else if (customerCountryChoiceBox.getValue() == "United Kingdom"){

            customerDivisionChoiceBox.getItems().addAll(UkDivisionSelection);

        } else {

            customerDivisionChoiceBox.setDisable(true);

        }

    }

    //FXML TextField Declarations

    @FXML private TextField customerAddressTextField;
    @FXML private TextField customerIdTextField;
    @FXML private TextField customerNameTextField;
    @FXML private TextField customerPhoneNumberTextField;
    @FXML private TextField customerPostalCodeTextField;

    //FXML ActionEvent Declarations

    @FXML void onCancelButtonClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(MainApplication.class.getResource("main-schedule-view.fxml"));
        Stage stage = (Stage) addCustomerFormTitleLabel.getScene().getWindow();
        Scene scene = new Scene(root, 720, 400);
        stage.setTitle("Appointment Management Program (AMP)");
        stage.setScene(scene);
        stage.show();

    }
    @FXML void onCustomerAddFormSaveButtonClick(ActionEvent event) {



        try {

            int customerId = Customer.customerIdGenerator();
            String name = String.valueOf(customerNameTextField);
            String address = String.valueOf(customerAddressTextField);
            String postalCode = String.valueOf(customerPostalCodeTextField);
            String phoneNumber = String.valueOf(customerPhoneNumberTextField);
            Timestamp createDate;
            String createdBy = LoginVerification.getCurrentUser();
            Timestamp lastUpdateDate = null;
            String lastUpdatedBy = null;
            //int divisionId = customerDivisionChoiceBox.getValue();



        } catch (Exception e){



        }





    }

    //End of FXML Declarations

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        PreviousSceneHelper.PsSetterTrue();
        customerCountryChoiceBox.getItems().addAll(CountrySelection);
        customerCountryChoiceBox.setOnAction(this::getCountrySelection);
        customerDivisionChoiceBox.setDisable(true);

        customerIdTextField.setDisable(true);
        customerIdTextField.setText(String.valueOf(Customer.customerIdGenerator()));

    }

} //Main Class Closing Bracket




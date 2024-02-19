package com.example.AMP.controller;

import com.example.AMP.MainApplication;
import com.example.AMP.helper.*;
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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ResourceBundle;
import java.sql.*;

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

    private String[] UsDivisionSelection = {
            "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado",
            "Connecticut", "District of Columbia", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho",
            "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana",
            "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi",
            "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey",
            "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma",
            "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee",
            "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"
    };
    private String[] CaDivisionSelection = {
            "Northwest Territories", "Alberta", "British Columbia", "Manitoba",
            "New Brunswick", "Nova Scotia", "Prince Edward Island", "Ontario", "Québec",
            "Saskatchewan", "Nunavut", "Yukon", "Newfoundland and Labrador"};
    private String[] UkDivisionSelection = {"England", "Wales", "Northern Ireland", "Scotland"};
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
    @FXML void onCustomerAddFormSaveButtonClick(ActionEvent event) throws SQLException, IOException {

        int customerId = Customer.customerIdGenerator();
        String name = customerNameTextField.getText();
        String address = customerAddressTextField.getText();
        String postalCode = customerPostalCodeTextField.getText();
        String phoneNumber = customerPhoneNumberTextField.getText();
        Timestamp createDate = Timestamp.from(Instant.now());
        String createdBy = LoginVerification.getCurrentUser();
        Timestamp lastUpdateDate = null;
        String lastUpdatedBy = null;
        int divisionId = DivisionIdHelper.divisionIdRetriever(customerDivisionChoiceBox.getValue());

        String sql = "INSERT INTO customers(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        ps.setString(2, name);
        ps.setString(3, address);
        ps.setString(4, postalCode);
        ps.setString(5, phoneNumber);
        ps.setTimestamp(6, createDate);
        ps.setString(7, createdBy);
        ps.setTimestamp(8, lastUpdateDate);
        ps.setString(9, lastUpdatedBy);
        ps.setInt(10, divisionId);
        int Results = ps.executeUpdate();
        System.out.println(Results);

        SQLCustomerToObject.SQLCustomerToObjectMethod();

        Parent root = FXMLLoader.load(MainApplication.class.getResource("main-schedule-view.fxml"));
        Stage stage = (Stage) addCustomerFormTitleLabel.getScene().getWindow();
        Scene scene = new Scene(root, 720, 400);
        stage.setTitle("Appointment Management Program (AMP)");
        stage.setScene(scene);
        stage.show();

    }

    //End of FXML Declarations

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Bundle Text Declarations
        addCustomerFormTitleLabel.setText(LocaleDesignation.LocalLang.getString("addCusTitle"));
        customerIdLabel.setText(LocaleDesignation.LocalLang.getString("addCusCustomerIdText"));
        customerNameLabel.setText(LocaleDesignation.LocalLang.getString("addCusNameText"));
        customerAddressLabel.setText(LocaleDesignation.LocalLang.getString("addCusAddressText"));
        customerPostalCodeLabel.setText(LocaleDesignation.LocalLang.getString("addCusPostalText"));
        customerPhoneNumberLabel.setText(LocaleDesignation.LocalLang.getString("addCusPhoneText"));
        customerCountryLabel.setText(LocaleDesignation.LocalLang.getString("addCusCountryText"));
        customerDivisionLabel.setText(LocaleDesignation.LocalLang.getString("addCusDivisionText"));
        customerAddFormSaveButton.setText(LocaleDesignation.LocalLang.getString("addCusSaveButtonText"));
        cancelButton.setText(LocaleDesignation.LocalLang.getString("addCusCancelButtonText"));

        customerCountryChoiceBox.getItems().addAll(CountrySelection);
        customerCountryChoiceBox.setOnAction(this::getCountrySelection);
        customerDivisionChoiceBox.setDisable(true);

        customerIdTextField.setDisable(true);
        customerIdTextField.setText(String.valueOf(Customer.customerIdGenerator()));

        PreviousSceneHelper.PsSetterTrue();

    }

} //Main Class Closing Bracket




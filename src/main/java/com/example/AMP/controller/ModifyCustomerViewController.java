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
import java.sql.Timestamp;
import java.time.Instant;
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

        if (AlertHelper.confirmation("Are You Sure?", "If you exit now, none of your changes will be saved!")){

            Parent root = FXMLLoader.load(MainApplication.class.getResource("main-schedule-view.fxml"));
            Stage stage = (Stage) modifyCustomerFormTitleLabel.getScene().getWindow();
            Scene scene = new Scene(root, 720, 400);
            stage.setTitle("Appointment Management Program (AMP)");
            stage.setScene(scene);
            stage.show();

        } else{
            return;
        }
    }
    @FXML void onCustomerModifyFormSaveButtonClick(ActionEvent event) throws SQLException, IOException {

        Customer currentCustomer = CustomerTransferHelper.getTransferCustomer();

        int customerId = currentCustomer.getCustomerId();
        String name = customerNameTextField.getText();
        String address = customerAddressTextField.getText();
        String postalCode = customerPostalCodeTextField.getText();
        String phoneNumber = customerPhoneNumberTextField.getText();
        Timestamp lastUpdateDate = Timestamp.from(Instant.now());
        String lastUpdatedBy = LoginVerification.getCurrentUser();
        int divisionId = DivisionIdHelper.divisionIdRetriever(customerDivisionChoiceBox.getValue());

        // UPDATE Customers
        //SET ContactName = 'Alfred Schmidt', City= 'Frankfurt'
        //WHERE CustomerID = 1;

        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNumber);
        ps.setTimestamp(5, lastUpdateDate);
        ps.setString(6, lastUpdatedBy);
        ps.setInt(7, divisionId);
        ps.setInt(8, customerId);
        int Results = ps.executeUpdate();
        System.out.println(Results);

        SQLCustomerToObject.SQLCustomerToObjectMethod();

        Parent root = FXMLLoader.load(MainApplication.class.getResource("main-schedule-view.fxml"));
        Stage stage = (Stage) modifyCustomerFormTitleLabel.getScene().getWindow();
        Scene scene = new Scene(root, 720, 400);
        stage.setTitle("Appointment Management Program (AMP)");
        stage.setScene(scene);
        stage.show();



    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        PreviousSceneHelper.PsSetterTrue();
        Customer currentCustomer = CustomerTransferHelper.getTransferCustomer();

        int UsLowerBound = 1;
        int UsUpperBound = 54;
        int CaLowerBound = 60;
        int CaUpperBound = 72;
        int UkLowerBound = 101;
        int UkUpperBound = 104;

        if (currentCustomer.getDivisionId() >= UsLowerBound && currentCustomer.getDivisionId() <= UsUpperBound){
            customerCountryChoiceBox.setValue("United States");
        } else if (currentCustomer.getDivisionId() >= CaLowerBound && currentCustomer.getDivisionId() <= CaUpperBound){
            customerCountryChoiceBox.setValue("Canada");
        } else if (currentCustomer.getDivisionId() >= UkLowerBound && currentCustomer.getDivisionId() <= UkUpperBound) {
            customerCountryChoiceBox.setValue("United Kingdom");
        }

        customerIdTextField.setText(String.valueOf(currentCustomer.getCustomerId()));
        customerDivisionChoiceBox.setValue(DivisionIdHelper.divisionStringRetriever(currentCustomer.getDivisionId()));
        customerNameTextField.setText(currentCustomer.getName());
        customerAddressTextField.setText(currentCustomer.getAddress());
        customerPostalCodeTextField.setText(currentCustomer.getPostalCode());
        customerPhoneNumberTextField.setText(currentCustomer.getPhoneNumber());

        customerCountryChoiceBox.getItems().addAll(CountrySelection);
        customerCountryChoiceBox.setOnAction(this::getCountrySelection);

        customerIdTextField.setDisable(true);

    }

} //Main Class Closing Bracket

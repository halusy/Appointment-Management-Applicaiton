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
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This is the AddCustomerViewController Class. It contains various methods to facilitate the adding of new Customers to the SQL Database.
 *
 * @author Nicholas Ryan
 * @version 1.0
 */
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

    //ChoiceBox Content Initialization
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

    /**
     * This method determines which Divisions will populate the DivisionChoiceBox based on the selected Country.
     *
     * @param event
     */

    public void getCountrySelection(ActionEvent event){

        //Allowing a Division to be selected once User selects a Country
        customerDivisionChoiceBox.setDisable(false);
        //Resetting Division ChoiceBox
        customerDivisionChoiceBox.getItems().removeAll(UkDivisionSelection);
        customerDivisionChoiceBox.getItems().removeAll(UsDivisionSelection);
        customerDivisionChoiceBox.getItems().removeAll(CaDivisionSelection);
        //Populating Division ChoiceBox based on selected Country
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

    /**
     * This method moves the user back to the Main Scene when the Cancel Button is Clicked
     *
     * @param event
     * @throws IOException
     */
    @FXML void onCancelButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(MainApplication.class.getResource("main-schedule-view.fxml"));
        Stage stage = (Stage) addCustomerFormTitleLabel.getScene().getWindow();
        Scene scene = new Scene(root, 770, 400);
        stage.setTitle("Appointment Management Program (AMP)");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method executes when the User is ready to Add a New Customer into the Database, and clicks the Save Button.
     *
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML void onCustomerAddFormSaveButtonClick(ActionEvent event) throws SQLException, IOException {


        //Prepping variables to be pushed into the SQL Database
        int customerId = Integer.valueOf(customerIdTextField.getText());
        String name = customerNameTextField.getText();
        String address = customerAddressTextField.getText();
        String postalCode = customerPostalCodeTextField.getText();
        String phoneNumber = customerPhoneNumberTextField.getText();
        Timestamp createDate = Timestamp.from(Instant.now());
        String createdBy = LoginVerification.getCurrentUser();
        Timestamp lastUpdateDate = null;
        String lastUpdatedBy = null;
        int divisionId = DivisionIdHelper.divisionIdRetriever(customerDivisionChoiceBox.getValue());

        //Executing the Update on the SQL Database
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

        //Turning the Updated SQL Database Customers back into Objects
        SQLCustomerToObject.SQLCustomerToObjectMethod();

        //Moving the User back to the Main Scene
        Parent root = FXMLLoader.load(MainApplication.class.getResource("main-schedule-view.fxml"));
        Stage stage = (Stage) addCustomerFormTitleLabel.getScene().getWindow();
        Scene scene = new Scene(root, 770, 400);
        stage.setTitle("Appointment Management Program (AMP)");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This is the Initialize method, which will execute when the Add Customer Scene is opened. It will initialize many important pieces of the Add Customer Scene.
     *
     * LAMBDA EXPRESSION 2: Here is where I also use my second Lambda expression. Much like the Appointment ID Generator, I had a bad Customer ID generator built into the Appointment Class which caused errors.
     * Using a Lambda in this scenario was perfect since the logic needed for the Customer ID Generator method was simple enough to not warrant an entire class, which is why I chose this
     * use case for implementation.
     *
     *
     * @param url
     * @param resourceBundle
     */

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

        //Initializing ChoiceBoxes
        customerCountryChoiceBox.getItems().addAll(CountrySelection);
        customerCountryChoiceBox.setOnAction(this::getCountrySelection);
        customerDivisionChoiceBox.setDisable(true);

        //Setting CustomerID text and Disabling the Field
        //Implementing a Lambda Expression for the Customer ID

        CustomerIDGenerator customerIDGenerator = (o) -> {
            ArrayList<Integer> customerIds = new ArrayList<>();
            for (Customer currentCustomer : o) {
                customerIds.add(currentCustomer.getCustomerId());
            }
            return LowestAvalibleNumberHelper.lowestNumberFinder(customerIds);
        };

        int newID = customerIDGenerator.idGeneratorMethod(ObservableListHelper.getCustomers());

        customerIdTextField.setDisable(true);
        customerIdTextField.setText(String.valueOf(newID));

        //Telling the program that the previous scene was a Customer scene
        PreviousSceneHelper.PsSetterTrue();

    }
} //Main Class Closing Bracket




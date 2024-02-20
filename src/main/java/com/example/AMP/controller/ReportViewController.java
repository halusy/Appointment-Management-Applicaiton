package com.example.AMP.controller;

import com.example.AMP.MainApplication;
import com.example.AMP.helper.DescendingSequenceHelper;
import com.example.AMP.helper.LocaleDesignation;
import com.example.AMP.helper.ObservableListHelper;
import com.example.AMP.models.Appointment;
import com.example.AMP.models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * This class contains the various methods for, and manages the function of the Reports page of the application.
 *
 * @author Nicholas Ryan
 * @version 1.0
 */
public class ReportViewController implements Initializable {

    @FXML private TableColumn<Appointment, Integer> appointmentContactIdCol;
    @FXML private TableColumn<Appointment, Integer> appointmentCustomerIdCol;
    @FXML private TableColumn<Appointment, String> appointmentDescriptionCol;
    @FXML private TableColumn<Appointment, Timestamp> appointmentEndCol;
    @FXML private TableColumn<Appointment, Integer> appointmentIdCol;
    @FXML private TableColumn<Appointment, String> appointmentLocationCol;
    @FXML private TableColumn<Appointment, Timestamp> appointmentStartCol;
    @FXML private TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, String> appointmentTitleCol;
    @FXML private TableColumn<Appointment, String> appointmentTypeCol;
    @FXML private TableColumn<Appointment, Integer> appointmentUserIdCol;

    @FXML private TableColumn<Customer, String> customerAddressCol;
    @FXML private TableColumn<Customer, Timestamp> customerCreateDateCol;
    @FXML private TableColumn<Customer, String> customerCreatedByCol;
    @FXML private TableColumn<Customer, Integer> customerDivisionIdCol;
    @FXML private TableColumn<Customer, Integer> customerIdCol;
    @FXML private TableColumn<Customer, String> customerLastUpdatedByCol;
    @FXML private TableColumn<Customer, Timestamp> customerLastUpdatedCol;
    @FXML private TableColumn<Customer, String> customerNameCol;
    @FXML private TableColumn<Customer, String> customerPhoneCol;
    @FXML private TableColumn<Customer, String> customerPostalCodeCol;
    @FXML private TableView<Customer> customerTable;

    @FXML private RadioButton contactScheduleRadio;
    @FXML private RadioButton customersLocationRadio;
    @FXML private RadioButton customersMonthRadio;
    @FXML private RadioButton customersTypeRadio;
    ToggleGroup customerRadio = new ToggleGroup();

    @FXML private Label currentSelectionLabel;
    @FXML private Label numberOfSelectionLabel;
    @FXML private Label totalOfLabel;
    @FXML private Label reportsTitle;

    @FXML private Button searchButton;
    @FXML private Button backButton;

    @FXML private ChoiceBox<Integer> contactChoiceBox = new ChoiceBox<>();
    @FXML private ChoiceBox<String> customerChoiceBox = new ChoiceBox<>();


    private Integer[] contactIdChoiceBoxValues = DescendingSequenceHelper.descendingSequenceRetriever("contacts");
    private String[] customerType;
    private String[] customerMonth = {
            "January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"
    };
    private String[] customerLocation;

    public ReportViewController() throws SQLException {
    }

    /**
     * This method chances the appearance of the Reports page to reflect the relevant info for Contact Schedule
     *
     * @param event
     */
    @FXML void contactScheduleToggle(ActionEvent event) {
        contactChoiceBox.setVisible(true);
        customerChoiceBox.setVisible(false);
        contactChoiceBox.getItems().clear();
        contactChoiceBox.getItems().addAll(contactIdChoiceBoxValues);
        currentSelectionLabel.setText(LocaleDesignation.LocalLang.getString("contactID"));
        numberOfSelectionLabel.setText("0");
        totalOfLabel.setText(LocaleDesignation.LocalLang.getString("totalApts"));
    }
    /**
     * This method chances the appearance of the Reports page to reflect the relevant info for Location Schedule.
     * @param event
     */
    @FXML void customersLocationToggle(ActionEvent event) {
        contactChoiceBox.setVisible(false);
        customerChoiceBox.setVisible(true);
        customerChoiceBox.getItems().clear();
        customerChoiceBox.getItems().addAll(customerLocation);
        currentSelectionLabel.setText(LocaleDesignation.LocalLang.getString("aptLocation"));
        numberOfSelectionLabel.setText("0");
        totalOfLabel.setText(LocaleDesignation.LocalLang.getString("totalApts"));
    }
    /**
     * This method chances the appearance of the Reports page to reflect the relevant info for Appointment Month Schedule
     *
     * @param event
     */
    @FXML void customersMonthToggle(ActionEvent event) {
        contactChoiceBox.setVisible(false);
        customerChoiceBox.setVisible(true);
        customerChoiceBox.getItems().clear();
        customerChoiceBox.getItems().addAll(customerMonth);
        currentSelectionLabel.setText(LocaleDesignation.LocalLang.getString("aptMonth"));
        numberOfSelectionLabel.setText("0");
        totalOfLabel.setText(LocaleDesignation.LocalLang.getString("totalApts"));

    }
    /**
     * This method chances the appearance of the Reports page to reflect the relevant info for Appointment Week Schedule
     *
     * @param event
     */
    @FXML void customersTypeToggle(ActionEvent event) {
        contactChoiceBox.setVisible(false);
        customerChoiceBox.setVisible(true);
        customerChoiceBox.getItems().clear();
        customerChoiceBox.getItems().addAll(customerType);
        currentSelectionLabel.setText(LocaleDesignation.LocalLang.getString("aptType"));
        numberOfSelectionLabel.setText("0");
        totalOfLabel.setText(LocaleDesignation.LocalLang.getString("totalApts"));
    }

    /**
     * This method shows the correct information when the search button is clicked based on the selected radio button
     *
     * @param event
     */
    @FXML void searchButtonClick(ActionEvent event) {

        if(contactScheduleRadio.isSelected()){
            Integer desiredContact = contactChoiceBox.getValue();
            ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
            ObservableList<Appointment> contactAppointments =FXCollections.observableArrayList();
            allAppointments.addAll(ObservableListHelper.getAppointments());
            for (Appointment currentApt : allAppointments){
                if(desiredContact == currentApt.getContactId()){
                    contactAppointments.add(currentApt);
                }
            }
            numberOfSelectionLabel.setText(String.valueOf(contactAppointments.size()));
            appointmentTable.setItems(contactAppointments);
            appointmentTable.setVisible(true);
        }

        if(customersMonthRadio.isSelected()){

            String selectedMonth = customerChoiceBox.getValue();
            ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
            ObservableList<Appointment> inMonthAppointments =FXCollections.observableArrayList();
            allAppointments.addAll(ObservableListHelper.getAppointments());

            for (Appointment currentApt : allAppointments){

                LocalDateTime aptDate = currentApt.getStartDate().toLocalDateTime();
                int aptYear = aptDate.getYear();
                int thisYear = LocalDateTime.now().getYear();
                String aptMonth = aptDate.getMonth().toString();

                if(aptYear == thisYear && selectedMonth.equalsIgnoreCase(aptMonth)){
                    inMonthAppointments.add(currentApt);
                }
            }
            numberOfSelectionLabel.setText(String.valueOf(inMonthAppointments.size()));
            appointmentTable.setItems(inMonthAppointments);
            appointmentTable.setVisible(true);

        }
        if (customersTypeRadio.isSelected()){

            String type = customerChoiceBox.getValue();

            ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
            ObservableList<Appointment> typeAppointments =FXCollections.observableArrayList();
            allAppointments.addAll(ObservableListHelper.getAppointments());

            for(Appointment currentApt : allAppointments){

                if (type == currentApt.getType()){
                    typeAppointments.add(currentApt);
                }
            }
            numberOfSelectionLabel.setText(String.valueOf(typeAppointments.size()));
            appointmentTable.setItems(typeAppointments);
            appointmentTable.setVisible(true);
        }

        if (customersLocationRadio.isSelected()){

            String location = customerChoiceBox.getValue();

            ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
            ObservableList<Appointment> locationAppointments =FXCollections.observableArrayList();
            allAppointments.addAll(ObservableListHelper.getAppointments());

            for(Appointment currentApt : allAppointments){

                if (location == currentApt.getLocation()){
                    locationAppointments.add(currentApt);
                }
            }
            numberOfSelectionLabel.setText(String.valueOf(locationAppointments.size()));
            appointmentTable.setItems(locationAppointments);
            appointmentTable.setVisible(true);

        }
    }

    /**
     * Moves the user back to the main view
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void backButtonClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(MainApplication.class.getResource("main-schedule-view.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(root, 790, 400);
        stage.setTitle("Appointment Management Program (AMP)");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * This method is called when the Reports page is visited. It initializes many vital parts of the reports page.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        allAppointments.setAll(ObservableListHelper.getAppointments());
        Set<String> uniqueTypes = new HashSet<>();

        for (Appointment currentApt : allAppointments){
            uniqueTypes.add(currentApt.getType());
        }

        customerType = uniqueTypes.toArray(new String[0]);

        ObservableList<Appointment> allAppointments2 = FXCollections.observableArrayList();
        allAppointments2.setAll(ObservableListHelper.getAppointments());
        Set<String> uniqueTypes2 = new HashSet<>();

        for (Appointment currentApt : allAppointments2){
            uniqueTypes2.add(currentApt.getLocation());
        }

        customerLocation = uniqueTypes2.toArray(new String[0]);

        contactScheduleRadio.setToggleGroup(customerRadio);
        customersLocationRadio.setToggleGroup(customerRadio);
        customersTypeRadio.setToggleGroup(customerRadio);
        customersMonthRadio.setToggleGroup(customerRadio);

        appointmentContactIdCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("contactId"));
        appointmentContactIdCol.setText(LocaleDesignation.LocalLang.getString("aContactText"));
        appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerId"));
        appointmentCustomerIdCol.setText(LocaleDesignation.LocalLang.getString("aCustomerText"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        appointmentTitleCol.setText(LocaleDesignation.LocalLang.getString("aTitleText"));
        appointmentUserIdCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("userId"));
        appointmentUserIdCol.setText(LocaleDesignation.LocalLang.getString("aUserText"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        appointmentTypeCol.setText(LocaleDesignation.LocalLang.getString("aTypeText"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        appointmentLocationCol.setText(LocaleDesignation.LocalLang.getString("aLocationText"));
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentId"));
        appointmentIdCol.setText(LocaleDesignation.LocalLang.getString("aIdText"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        appointmentDescriptionCol.setText(LocaleDesignation.LocalLang.getString("aDescriptionText"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("startDate"));
        appointmentStartCol.setText(LocaleDesignation.LocalLang.getString("aStartText"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("endDate"));
        appointmentEndCol.setText(LocaleDesignation.LocalLang.getString("aEndText"));


        customerIdCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerId"));
        customerIdCol.setText(LocaleDesignation.LocalLang.getString("cIdText"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        customerNameCol.setText(LocaleDesignation.LocalLang.getString("cNameText"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        customerAddressCol.setText(LocaleDesignation.LocalLang.getString("cAddressText"));
        customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("postalCode"));
        customerPostalCodeCol.setText(LocaleDesignation.LocalLang.getString("cPostalText"));
        customerDivisionIdCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("divisionId"));
        customerDivisionIdCol.setText(LocaleDesignation.LocalLang.getString("cDivisionText"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNumber"));
        customerPhoneCol.setText(LocaleDesignation.LocalLang.getString("cPhoneText"));
        customerCreateDateCol.setCellValueFactory(new PropertyValueFactory<Customer, Timestamp>("createDate"));
        customerCreateDateCol.setText(LocaleDesignation.LocalLang.getString("cDateAddText"));
        customerCreatedByCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("createdBy"));
        customerCreatedByCol.setText(LocaleDesignation.LocalLang.getString("cCreatorText"));
        customerLastUpdatedCol.setCellValueFactory(new PropertyValueFactory<Customer, Timestamp>("lastUpdateDate"));
        customerLastUpdatedCol.setText(LocaleDesignation.LocalLang.getString("cLastUpdateDateText"));
        customerLastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("lastUpdatedBy"));
        customerLastUpdatedByCol.setText(LocaleDesignation.LocalLang.getString("cUpdatedByText"));

        contactScheduleRadio.setText(LocaleDesignation.LocalLang.getString("contactRadio"));
        customersTypeRadio.setText(LocaleDesignation.LocalLang.getString("typeRadio"));
        customersLocationRadio.setText(LocaleDesignation.LocalLang.getString("locationRadio"));
        customersMonthRadio.setText(LocaleDesignation.LocalLang.getString("monthRadio"));
        searchButton.setText(LocaleDesignation.LocalLang.getString("searchBtn"));
        reportsTitle.setText(LocaleDesignation.LocalLang.getString("reportsTitle"));
        backButton.setText(LocaleDesignation.LocalLang.getString("backBtn"));

        contactScheduleRadio.fire();

        customerTable.setVisible(false);
        appointmentTable.setVisible(false);


    }
}

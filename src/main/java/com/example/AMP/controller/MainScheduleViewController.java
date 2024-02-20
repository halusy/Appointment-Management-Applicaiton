package com.example.AMP.controller;

import com.example.AMP.MainApplication;
import com.example.AMP.helper.*;
import com.example.AMP.models.Appointment;
import com.example.AMP.models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 *
 * This is the MainScheduleViewController Class. This class encapsulates all the functionality of the Homepage of the Application, and is where users will interact with the program the most.
 *
 * @author Nicholas Ryan
 * @version 1.0
 */

public class MainScheduleViewController implements Initializable {

    //Appointment Table Declaration
    @FXML private TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, Integer> appointmentContactIdCol;
    @FXML private TableColumn<Appointment, Integer> appointmentCustomerIdCol;
    @FXML private TableColumn<Appointment, String> appointmentDescriptionCol;
    @FXML private TableColumn<Appointment, Timestamp> appointmentEndCol;
    @FXML private TableColumn<Appointment, Integer> appointmentIdCol;
    @FXML private TableColumn<Appointment, String> appointmentLocationCol;
    @FXML private TableColumn<Appointment, Timestamp> appointmentStartCol;
    @FXML private TableColumn<Appointment, String> appointmentTitleCol;
    @FXML private TableColumn<Appointment, String> appointmentTypeCol;
    @FXML private TableColumn<Appointment, Integer> appointmentUserIdCol;

    //Customer Table Declaration
    @FXML private TableView<Customer> customerTable;
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

    //FXML Buttons
    @FXML private Button LogoutButton;
    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button modifyButton;
    @FXML private Button reportsButton;

    //FXML Radio Buttons
    @FXML private RadioButton sortAppointmentsAllRadio = new RadioButton();
    @FXML private RadioButton sortAppointmentsMonthRadio = new RadioButton();
    @FXML private RadioButton sortAppointmentsWeekRadio = new RadioButton();
    @FXML private RadioButton viewCustomersRadio;
    ToggleGroup appointmentSort = new ToggleGroup();

    //FXML Labels
    @FXML private Label titleLabel;
    @FXML private Label sortAppointmentsByLabel;

    /**
     * This method determines what happens when the Add button is clicked, based on the current state of the program.
     *
     * @param event
     * @throws IOException
     */
    @FXML void onAddButtonClick(ActionEvent event) throws IOException {

        if (viewCustomersRadio.isSelected() == true){

            //Brings the User to the Add Customer page is the viewCustomersRadio button is selected
            Parent root = FXMLLoader.load(MainApplication.class.getResource("add-customer-view.fxml"));
            Stage stage = (Stage) viewCustomersRadio.getScene().getWindow();
            Scene scene = new Scene(root, 316, 419);
            stage.setTitle("Appointment Management Program (AMP)");
            stage.setScene(scene);
            stage.show();
        }

        if (viewCustomersRadio.isSelected() == false){

            //Brings the User to the Add Appointments page is the viewAppointmentsRadio button is selected
            Parent root = FXMLLoader.load(MainApplication.class.getResource("add-appointment-view.fxml"));
            Stage stage = (Stage) viewCustomersRadio.getScene().getWindow();
            Scene scene = new Scene(root, 565, 483);
            stage.setTitle("Appointment Management Program (AMP)");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     *
     * This method removes the selected item from the program and SQL Database when the Delete Button is Pressed.
     *
     * @param event
     * @throws SQLException
     */
    @FXML void onDeleteButtonClick(ActionEvent event) throws SQLException {

        //Checks if User wants to delete a customer or appointment
        if (viewCustomersRadio.isSelected() == true){

            //This variable will hold the number of appointments exist for the customer the User is trying to delete
            int numOfAppointments = 0;

            //Captures selected customer and customer ID
            Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
            int toDeleteId = selectedCustomer.getCustomerId();

            //Searches the SQL Database for appointments associated with the customer to be deleted.
            String verifySql = "SELECT * FROM appointments WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(verifySql);
            ps.setInt(1, toDeleteId);
            ResultSet rs = ps.executeQuery();

            //Adds 1 to the numOfAppointments variable for each appointment that exists with the same customer_ID as the selected Customer
            while(rs.next()){
                numOfAppointments++;
            }

            //Allows the customer to be deleted if no associated appointments exist
            if (numOfAppointments == 0){

                //Gets user confrimation
                if (AlertHelper.confirmation(LocaleDesignation.LocalLang.getString("areYouSureTitle"), LocaleDesignation.LocalLang.getString("deleteCustomer"))){

                    //Deletes the Customer from the Database
                    String deleteSql = "DELETE FROM customers WHERE Customer_ID = ?";
                    PreparedStatement ps2 = JDBC.connection.prepareStatement(deleteSql);
                    ps2.setInt(1, toDeleteId);
                    ps2.executeUpdate();

                    //Refreshes the Customer Objects form the updated Database
                    SQLCustomerToObject.SQLCustomerToObjectMethod();

                } else {
                    //User canceled
                    return;
                }
            //Does not allow customer to be deleted since associated appointments Exist
            } else {
                AlertHelper.warning(LocaleDesignation.LocalLang.getString("warningTitle"), LocaleDesignation.LocalLang.getString("removeCustomerAppointments"));
            }
        //Checks if user wants to delete a Customer or Appointment
        } if (viewCustomersRadio.isSelected() == false){

            //Gets selected appointment and ID
            Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
            int toDeleteId = selectedAppointment.getAppointmentId();
            //Stores appointment type for display
            String appointmentType = selectedAppointment.getType();

            //User confirmation to delete appointment
            if (AlertHelper.confirmation(LocaleDesignation.LocalLang.getString("areYouSureTitle"), LocaleDesignation.LocalLang.getString("confirmAptDelete"))){

                //Executes SQL Update
                String deleteSql = "DELETE FROM appointments WHERE Appointment_ID = ?";
                PreparedStatement ps2 = JDBC.connection.prepareStatement(deleteSql);
                ps2.setInt(1, toDeleteId);
                ps2.executeUpdate();

                //Tells user which appointment was deleted
                AlertHelper.warning(LocaleDesignation.LocalLang.getString("youDeletedAnAppointment"), LocaleDesignation.LocalLang.getString("deleteAppointmentFirst") + toDeleteId +
                        " " + LocaleDesignation.LocalLang.getString("ofType") + appointmentType + " " + LocaleDesignation.LocalLang.getString("hadBeenDeleted"));

                //Refreshes Appointment Objects from updated Database
                SQLAppointmentToObject.SQLAppointmentToObjectMethod();

            } else {
                //User canceled
                return;
            }
        }
    }

    /**
     * This method will log the user out of the program.
     *
     * @param event
     */
    @FXML void onLogoutButtonClick(ActionEvent event) {
        System.exit(1);
    }

    /**
     *
     * This method will send the User to the proper modification form.
     *
     * @param event
     * @throws IOException
     */
    @FXML void onModifyButtonClick(ActionEvent event) throws IOException {

        if (viewCustomersRadio.isSelected() == true){

            //Holds selected user to be modified
            ObjectTransferHelper.customerHolder(customerTable.getSelectionModel().getSelectedItem());

            //Sends user to customer modify form
            Parent root = FXMLLoader.load(MainApplication.class.getResource("modify-customer-view.fxml"));
            Stage stage = (Stage) viewCustomersRadio.getScene().getWindow();
            Scene scene = new Scene(root, 316, 419);
            stage.setTitle("Appointment Management Program (AMP)");
            stage.setScene(scene);
            stage.show();
        }

        if (viewCustomersRadio.isSelected() == false){

            //Holds selected appointment to be modified
            ObjectTransferHelper.appointmentHolder(appointmentTable.getSelectionModel().getSelectedItem());

            //Sends user to appointment modify form
            Parent root = FXMLLoader.load(MainApplication.class.getResource("modify-appointment-view.fxml"));
            Stage stage = (Stage) viewCustomersRadio.getScene().getWindow();
            Scene scene = new Scene(root, 565, 483);
            stage.setTitle("Appointment Management Program (AMP)");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     *
     * This method takes the user to the reports page of the program.
     *
     * @param event
     * @throws IOException
     */
    @FXML void onReportsButtonClick(ActionEvent event) throws IOException {

        //Sends user to Reports page
        Parent root = FXMLLoader.load(MainApplication.class.getResource("report-view.fxml"));
        Stage stage = (Stage) viewCustomersRadio.getScene().getWindow();
        Scene scene = new Scene(root, 790, 400);
        stage.setTitle("Appointment Management Program (AMP)");
        stage.setScene(scene);
        stage.show();
    }

    @FXML void sortAppointmentsAllRadioToggle(ActionEvent event) {
        appointmentTable.setItems(ObservableListHelper.getAppointments());
    }
    @FXML void sortAppointmentsMonthRadioToggle(ActionEvent event) {
        appointmentTable.setItems(ObservableListHelper.getAppointmentsByMonth());
    }
    @FXML void sortAppointmentsWeekRadioToggle (ActionEvent event) {
        appointmentTable.setItems(ObservableListHelper.getAppointmentsByWeek());
    }

    /**
     * This method switches mulitple elements of the maincontroller based on the users preference to view either appointments or customers.
     *
     *
     * @param event
     */
    @FXML void viewCustomersRadioToggle(ActionEvent event) {

        if (viewCustomersRadio.isSelected() == true){
            addButton.setText(LocaleDesignation.LocalLang.getString("addCustomerButtonText"));
            modifyButton.setText(LocaleDesignation.LocalLang.getString("modifyCustomerButtonText"));
            deleteButton.setText(LocaleDesignation.LocalLang.getString("deleteCustomerButtonText"));
            appointmentTable.setVisible(false);
            customerTable.setVisible(true);
            sortAppointmentsAllRadio.setSelected(false);
            sortAppointmentsMonthRadio.setSelected(false);
            sortAppointmentsWeekRadio.setSelected(false);
            sortAppointmentsAllRadio.setVisible(false);
            sortAppointmentsMonthRadio.setVisible(false);
            sortAppointmentsWeekRadio.setVisible(false);
            sortAppointmentsByLabel.setVisible(false);
        }

        if (viewCustomersRadio.isSelected() == false){
            addButton.setText(LocaleDesignation.LocalLang.getString("addAppointmentButtonText"));
            modifyButton.setText(LocaleDesignation.LocalLang.getString("modifyAppointmentButtonText"));
            deleteButton.setText(LocaleDesignation.LocalLang.getString("deleteAppointmentButtonText"));
            appointmentTable.setVisible(true);
            customerTable.setVisible(false);
            sortAppointmentsAllRadio.fire();
            sortAppointmentsAllRadio.setVisible(true);
            sortAppointmentsMonthRadio.setVisible(true);
            sortAppointmentsWeekRadio.setVisible(true);
            sortAppointmentsByLabel.setVisible(true);
        }
    }

    /**
     * This method executes when the MainView is accessed by the user, and contains many vital processes.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        //Calls method to notify user if an appointment is scheduled within 15mins of current time
        if (PreviousSceneHelper.loginGetter()){
            OverlapChecker.fifteen();
        }

        //Init labels and button text
        titleLabel.setText(LocaleDesignation.LocalLang.getString("AmpTitleText"));
        LogoutButton.setText(LocaleDesignation.LocalLang.getString("logoutButtonText"));
        reportsButton.setText(LocaleDesignation.LocalLang.getString("reportsButtonText"));
        viewCustomersRadio.setText(LocaleDesignation.LocalLang.getString("viewCustomersRadioButtonText"));

        //Init radio buttons
        sortAppointmentsByLabel.setText(LocaleDesignation.LocalLang.getString("SortAppointmentsByLabelText"));
        sortAppointmentsAllRadio.setText(LocaleDesignation.LocalLang.getString("appointmentRadioButtonAllText"));
        sortAppointmentsWeekRadio.setText(LocaleDesignation.LocalLang.getString("appointmentRadioButtonWeekText"));
        sortAppointmentsMonthRadio.setText(LocaleDesignation.LocalLang.getString("appointmentRadioButtonMonthText"));

        //Sets radio button toggle group
        sortAppointmentsAllRadio.setToggleGroup(appointmentSort);
        sortAppointmentsMonthRadio.setToggleGroup(appointmentSort);
        sortAppointmentsWeekRadio.setToggleGroup(appointmentSort);

        //Init Application TableView
        appointmentTable.setItems(ObservableListHelper.getAppointments());
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

        //Init customer TableView
        customerTable.setItems(ObservableListHelper.getCustomers());
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

        //Init Button Text
        addButton.setText(LocaleDesignation.LocalLang.getString("addAppointmentButtonText"));
        modifyButton.setText(LocaleDesignation.LocalLang.getString("modifyAppointmentButtonText"));
        deleteButton.setText(LocaleDesignation.LocalLang.getString("deleteAppointmentButtonText"));

        //Init some view controls
        customerTable.setVisible(false);
        appointmentTable.setVisible(true);
        sortAppointmentsAllRadio.fire();

        //Adjusts parts of main scene based on the scene the user is coming from to create continuity
        if(PreviousSceneHelper.PreviousScene() == true){

            viewCustomersRadio.fire();
            appointmentTable.setVisible(false);
            customerTable.setVisible(true);
        }

        //Login page can no longer be accessed without logging out
        PreviousSceneHelper.loginPageSetterFalse();
    }
}
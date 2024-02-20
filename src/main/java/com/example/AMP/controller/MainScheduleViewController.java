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

public class MainScheduleViewController implements Initializable {

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

    @FXML private Button LogoutButton;
    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button modifyButton;
    @FXML private Button reportsButton;

    @FXML private RadioButton sortAppointmentsAllRadio = new RadioButton();
    @FXML private RadioButton sortAppointmentsMonthRadio = new RadioButton();
    @FXML private RadioButton sortAppointmentsWeekRadio = new RadioButton();
    @FXML private RadioButton viewCustomersRadio;
    ToggleGroup appointmentSort = new ToggleGroup();

    @FXML private Label titleLabel;
    @FXML private Label sortAppointmentsByLabel;

    @FXML void onAddButtonClick(ActionEvent event) throws IOException {

        if (viewCustomersRadio.isSelected() == true){

            Parent root = FXMLLoader.load(MainApplication.class.getResource("add-customer-view.fxml"));
            Stage stage = (Stage) viewCustomersRadio.getScene().getWindow();
            Scene scene = new Scene(root, 316, 419);
            stage.setTitle("Appointment Management Program (AMP)");
            stage.setScene(scene);
            stage.show();

        }

        if (viewCustomersRadio.isSelected() == false){

            Parent root = FXMLLoader.load(MainApplication.class.getResource("add-appointment-view.fxml"));
            Stage stage = (Stage) viewCustomersRadio.getScene().getWindow();
            Scene scene = new Scene(root, 565, 483);
            stage.setTitle("Appointment Management Program (AMP)");
            stage.setScene(scene);
            stage.show();

        }
    }
    @FXML void onDeleteButtonClick(ActionEvent event) throws SQLException {

        if (viewCustomersRadio.isSelected() == true){

            int numOfAppointments = 0;

            Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
            int toDeleteId = selectedCustomer.getCustomerId();

            String verifySql = "SELECT * FROM appointments WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(verifySql);
            ps.setInt(1, toDeleteId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                numOfAppointments++;
            }

            if (numOfAppointments == 0){

                if (AlertHelper.confirmation("Are you sure?", "Are you sure you'd like to remove this customer from the database?")){

                    String deleteSql = "DELETE FROM customers WHERE Customer_ID = ?";
                    PreparedStatement ps2 = JDBC.connection.prepareStatement(deleteSql);
                    ps2.setInt(1, toDeleteId);
                    ps2.executeUpdate();

                    SQLCustomerToObject.SQLCustomerToObjectMethod();

                } else {
                    return;
                }

            } else {

                AlertHelper.warning("Warning!", "You must remove all appointments associated with this customer before you remove the customer");

            }
        } if (viewCustomersRadio.isSelected() == false){

            Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
            int toDeleteId = selectedAppointment.getAppointmentId();
            String appointmentType = selectedAppointment.getType();

            if (AlertHelper.confirmation("Are you sure?", "Are you sure you'd like to remove this appointment from the database?")){

                String deleteSql = "DELETE FROM appointments WHERE Appointment_ID = ?";
                PreparedStatement ps2 = JDBC.connection.prepareStatement(deleteSql);
                ps2.setInt(1, toDeleteId);
                ps2.executeUpdate();

                AlertHelper.warning(LocaleDesignation.LocalLang.getString("youDeletedAnAppointment"), LocaleDesignation.LocalLang.getString("deleteAppointmentFirst") + toDeleteId +
                        " " + LocaleDesignation.LocalLang.getString("ofType") + appointmentType + " " + LocaleDesignation.LocalLang.getString("hadBeenDeleted"));

                SQLAppointmentToObject.SQLAppointmentToObjectMethod();

            } else {
                return;
            }
        }
    }
    @FXML void onLogoutButtonClick(ActionEvent event) {

        System.exit(1);

    }
    @FXML void onModifyButtonClick(ActionEvent event) throws IOException {

        if (viewCustomersRadio.isSelected() == true){

            ObjectTransferHelper.customerHolder(customerTable.getSelectionModel().getSelectedItem());

            Parent root = FXMLLoader.load(MainApplication.class.getResource("modify-customer-view.fxml"));
            Stage stage = (Stage) viewCustomersRadio.getScene().getWindow();
            Scene scene = new Scene(root, 316, 419);
            stage.setTitle("Appointment Management Program (AMP)");
            stage.setScene(scene);
            stage.show();
        }

        if (viewCustomersRadio.isSelected() == false){

            ObjectTransferHelper.appointmentHolder(appointmentTable.getSelectionModel().getSelectedItem());

            Parent root = FXMLLoader.load(MainApplication.class.getResource("modify-appointment-view.fxml"));
            Stage stage = (Stage) viewCustomersRadio.getScene().getWindow();
            Scene scene = new Scene(root, 565, 483);
            stage.setTitle("Appointment Management Program (AMP)");
            stage.setScene(scene);
            stage.show();

        }

    }
    @FXML void onReportsButtonClick(ActionEvent event) throws IOException {

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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        if (PreviousSceneHelper.loginGetter()){
            OverlapChecker.fifteen();
        }

        titleLabel.setText(LocaleDesignation.LocalLang.getString("AmpTitleText"));
        LogoutButton.setText(LocaleDesignation.LocalLang.getString("logoutButtonText"));
        reportsButton.setText(LocaleDesignation.LocalLang.getString("reportsButtonText"));
        viewCustomersRadio.setText(LocaleDesignation.LocalLang.getString("viewCustomersRadioButtonText"));

        sortAppointmentsByLabel.setText(LocaleDesignation.LocalLang.getString("SortAppointmentsByLabelText"));
        sortAppointmentsAllRadio.setText(LocaleDesignation.LocalLang.getString("appointmentRadioButtonAllText"));
        sortAppointmentsWeekRadio.setText(LocaleDesignation.LocalLang.getString("appointmentRadioButtonWeekText"));
        sortAppointmentsMonthRadio.setText(LocaleDesignation.LocalLang.getString("appointmentRadioButtonMonthText"));

        sortAppointmentsAllRadio.setToggleGroup(appointmentSort);
        sortAppointmentsMonthRadio.setToggleGroup(appointmentSort);
        sortAppointmentsWeekRadio.setToggleGroup(appointmentSort);

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

        addButton.setText(LocaleDesignation.LocalLang.getString("addAppointmentButtonText"));
        modifyButton.setText(LocaleDesignation.LocalLang.getString("modifyAppointmentButtonText"));
        deleteButton.setText(LocaleDesignation.LocalLang.getString("deleteAppointmentButtonText"));

        customerTable.setVisible(false);
        appointmentTable.setVisible(true);
        sortAppointmentsAllRadio.fire();



        if(PreviousSceneHelper.PreviousScene() == true){

            viewCustomersRadio.fire();
            appointmentTable.setVisible(false);
            customerTable.setVisible(true);

        }

        PreviousSceneHelper.loginPageSetterFalse();

    }

}
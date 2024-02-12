package com.example.AMP.controller;

import com.example.AMP.MainApplication;
import com.example.AMP.helper.ObservableListHelper;
import com.example.AMP.models.Appointment;
import com.example.AMP.models.Customer;
import com.example.AMP.helper.LocaleDesignation;
import com.example.AMP.helper.PreviousSceneHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
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

    @FXML private RadioButton sortAppointmentsAllRadio;
    @FXML private RadioButton sortAppointmentsMonthRadio;
    @FXML private RadioButton sortAppointmentsWeekRadio;
    @FXML private RadioButton viewCustomersRadio;

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
    @FXML void onDeleteButtonClick(ActionEvent event) {}
    @FXML void onLogoutButtonClick(ActionEvent event) {

        System.exit(1);

    }
    @FXML void onModifyButtonClick(ActionEvent event) throws IOException {

        if (viewCustomersRadio.isSelected() == true){

            Parent root = FXMLLoader.load(MainApplication.class.getResource("modify-customer-view.fxml"));
            Stage stage = (Stage) viewCustomersRadio.getScene().getWindow();
            Scene scene = new Scene(root, 316, 419);
            stage.setTitle("Appointment Management Program (AMP)");
            stage.setScene(scene);
            stage.show();
        }

        if (viewCustomersRadio.isSelected() == false){

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
        Scene scene = new Scene(root, 720, 400);
        stage.setTitle("Appointment Management Program (AMP)");
        stage.setScene(scene);
        stage.show();

    }
    @FXML void sortAppointmentsAllRadioToggle(ActionEvent event) {}
    @FXML void sortAppointmentsMonthRadioToggle(ActionEvent event) {}
    @FXML void sortAppointmentsWeekRadioToggle (ActionEvent event) {}
    @FXML void viewCustomersRadioToggle(ActionEvent event) {

        if (viewCustomersRadio.isSelected() == true){
            addButton.setText(LocaleDesignation.LocalLang.getString("addCustomerButtonText"));
            modifyButton.setText(LocaleDesignation.LocalLang.getString("modifyCustomerButtonText"));
            deleteButton.setText(LocaleDesignation.LocalLang.getString("deleteCustomerButtonText"));
            appointmentTable.setVisible(false);
            customerTable.setVisible(true);
        }

        if (viewCustomersRadio.isSelected() == false){
            addButton.setText(LocaleDesignation.LocalLang.getString("addAppointmentButtonText"));
            modifyButton.setText(LocaleDesignation.LocalLang.getString("modifyAppointmentButtonText"));
            deleteButton.setText(LocaleDesignation.LocalLang.getString("deleteAppointmentButtonText"));
            appointmentTable.setVisible(true);
            customerTable.setVisible(false);
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        appointmentTable.setItems(ObservableListHelper.getAppointments());
        appointmentContactIdCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("contactId"));
        appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerId"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        appointmentUserIdCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("userId"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentId"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("startDate"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("endDate"));

        customerTable.setItems(ObservableListHelper.getCustomers());
        customerIdCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("postalCode"));
        customerDivisionIdCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("divisionId"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNumber"));
        customerCreateDateCol.setCellValueFactory(new PropertyValueFactory<Customer, Timestamp>("createDate"));
        customerCreatedByCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("createdBy"));
        customerLastUpdatedCol.setCellValueFactory(new PropertyValueFactory<Customer, Timestamp>("lastUpdateDate"));
        customerLastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("lastUpdatedBy"));

        addButton.setText(LocaleDesignation.LocalLang.getString("addAppointmentButtonText"));
        modifyButton.setText(LocaleDesignation.LocalLang.getString("modifyAppointmentButtonText"));
        deleteButton.setText(LocaleDesignation.LocalLang.getString("deleteAppointmentButtonText"));
        customerTable.setVisible(false);
        appointmentTable.setVisible(true);

        if(PreviousSceneHelper.PreviousScene() == true){

            viewCustomersRadio.fire();
            appointmentTable.setVisible(false);
            customerTable.setVisible(true);

        }

        //create and populate both a customer table view & an appointment table view

    }

}
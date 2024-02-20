package com.example.AMP.controller;

import com.example.AMP.MainApplication;
import com.example.AMP.helper.*;
import com.example.AMP.models.Appointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;

/**
 * This is the AddAppointmentViewController Class. It contains various methods which allow the Program to Add Appointment Objects into the SQL Database.
 *
 * @author Nicholas Ryan
 * @version 1.0
 *
 */

public class AddAppointmentViewController implements Initializable {

    //FXML Label Declarations
    @FXML private Label addAppointmentFormTitleLabel;
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
    @FXML private Button appointmentAddFormSaveButton;

    //FXML ChoiceBox Declarations
    @FXML private ChoiceBox<Integer> contactIdChoiceBox;
    @FXML private ChoiceBox<Integer> customerIdChoiceBox;
    @FXML private ChoiceBox<Integer> userIdChoiceBox;

    //ChoiceBox Data Declaration
    private Integer[] contactIdChoiceBoxValues = DescendingSequenceHelper.descendingSequenceRetriever("contacts");
    private Integer[] customerIdChoiceBoxValues = DescendingSequenceHelper.descendingSequenceRetriever("customers");
    private Integer[] userIdChoiceBoxValues = DescendingSequenceHelper.descendingSequenceRetriever("users");

    //FXML DatePicker Declarations
    @FXML private DatePicker endDatePicker;
    @FXML private DatePicker startDatePicker;

    //FXML Spinner Declarations
    @FXML private Spinner<Integer> endTimeHourSpinner = new Spinner<>();
    @FXML private Spinner<Integer> endTimeMinuteSpinner = new Spinner<>();
    @FXML private Spinner<Integer> startTimeHourSpinner = new Spinner<>();
    @FXML private Spinner<Integer> startTimeMinuteSpinner = new Spinner<>();

    //Spinner Value Factory Definitions
    SpinnerValueFactory<Integer> endHourFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 23, 1);
    SpinnerValueFactory<Integer> endMinuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 59, 00);
    SpinnerValueFactory<Integer> startHourFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 23, 1);
    SpinnerValueFactory<Integer> startMinuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 59, 00);

    public AddAppointmentViewController() throws SQLException {
    }

    /**
     * This method executes when the Cancel Button is Clicked. It returns the user to the Main Scene.
     *
     * @param event
     * @throws IOException
     */

    @FXML void onCancelButtonClick(ActionEvent event) throws IOException {
        //Moves to Main Scene
        Parent root = FXMLLoader.load(MainApplication.class.getResource("main-schedule-view.fxml"));
        Stage stage = (Stage) addAppointmentFormTitleLabel.getScene().getWindow();
        Scene scene = new Scene(root, 770, 400);
        stage.setTitle("Appointment Management Program (AMP)");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method executes when the User is ready to Add a New Appointment into the Database, and clicks the Save Button.
     *
     *
     * @param event
     * @throws SQLException
     * @throws IOException
     */

    @FXML void onAppointmentAddFormSaveButtonClick(ActionEvent event) throws SQLException, IOException {

        //Appointment Time Conversion and Checking
        LocalDate newStartDate = startDatePicker.getValue();
        LocalDate newEndDate = endDatePicker.getValue();
        LocalDateTime newStart = LocalDateTime.of(newStartDate.getYear(), newStartDate.getMonthValue(), newStartDate.getDayOfMonth(), startTimeHourSpinner.getValue(), startTimeMinuteSpinner.getValue(), 0);
        LocalDateTime newEnd = LocalDateTime.of(newEndDate.getYear(), newEndDate.getMonthValue(), newEndDate.getDayOfMonth(), endTimeHourSpinner.getValue(), endTimeMinuteSpinner.getValue(), 0);
        Timestamp utcStart = ZoneIdHelper.timeConverterUtc(Timestamp.valueOf(newStart));
        Timestamp utcEnd = ZoneIdHelper.timeConverterUtc(Timestamp.valueOf(newEnd));

        //SQL Data Preparation
        int appointmentId = Appointment.appointmentIdGenerator();
        String title = appointmentTitleTextField.getText();
        String description = appointmentDescriptionTextField.getText();
        String location = appointmentLocationTextField.getText();
        String type = appointmentTypeTextField.getText();
        int contactId = contactIdChoiceBox.getValue();
        int customerId = customerIdChoiceBox.getValue();
        int userId = userIdChoiceBox.getValue();
        Timestamp start = utcStart;
        Timestamp end = utcEnd;
        String createdBy = LoginVerification.getCurrentUser();
        Timestamp createDate = Timestamp.from(Instant.now());
        Timestamp lastUpdateDate = null;
        String lastUpdatedBy = null;

        //SQL Database Commit w/ Contingency Checkers
        if(OverlapChecker.isWithinBusinessHours(start, end)){

            if(OverlapChecker.customerOverlapChecker(customerId, start, end)){

                //SQL Commit Statements
                String sql = "INSERT INTO appointments(Appointment_ID, Title, Description, Location, Type, Start, End, " +
                        "Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement ps = JDBC.connection.prepareStatement(sql);
                ps.setInt(1, appointmentId);
                ps.setString(2, title);
                ps.setString(3, description);
                ps.setString(4, location);
                ps.setString(5, type);
                ps.setTimestamp(6, start);
                ps.setTimestamp(7, end);
                ps.setTimestamp(8, createDate);
                ps.setString(9, createdBy);
                ps.setTimestamp(10, lastUpdateDate);
                ps.setString(11, lastUpdatedBy);
                ps.setInt(12, customerId);
                ps.setInt(13, userId);
                ps.setInt(14, contactId);
                int Results = ps.executeUpdate();

                //Converting Updated SQL Database to Appointment Objects
                SQLAppointmentToObject.SQLAppointmentToObjectMethod();

                //Moving back to Main Scene
                Parent root = FXMLLoader.load(MainApplication.class.getResource("main-schedule-view.fxml"));
                Stage stage = (Stage) addAppointmentFormTitleLabel.getScene().getWindow();
                Scene scene = new Scene(root, 720, 400);
                stage.setTitle("Appointment Management Program (AMP)");
                stage.setScene(scene);
                stage.show();

            } else {
                //Alert that This Appointment overlaps with another Customer Appointment
                AlertHelper.warning(LocaleDesignation.LocalLang.getString("overlappingTitle"), LocaleDesignation.LocalLang.getString("overlappingContent"));
            }
        } else {
            //Alert that This Appointment is outside of EST Business Hours
            AlertHelper.warning(LocaleDesignation.LocalLang.getString("outsideHoursTitle"),LocaleDesignation.LocalLang.getString("outsideHoursContent"));
        }
    }

    /**
     * This is the Initialize method, which will execute when the Add Appointment Scene is opened. It will initialize many important pieces of the Add Appointment Scene.
     *
     *
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Creating the TableView
        addAppointmentFormTitleLabel.setText(LocaleDesignation.LocalLang.getString("addAptMainTitleText"));
        appointmentTitleLabel.setText(LocaleDesignation.LocalLang.getString("addAptTitleText"));
        appointmentContactIdLabel.setText(LocaleDesignation.LocalLang.getString("addAptContactIdText"));
        appointmentCustomerIdLabel.setText(LocaleDesignation.LocalLang.getString("addAptCustomerIdText"));
        appointmentDescriptionLabel.setText(LocaleDesignation.LocalLang.getString("addAptDescriptionText"));
        appointmentEndDateLabel.setText(LocaleDesignation.LocalLang.getString("addAptEndDateText"));
        appointmentEndTimeLabel.setText(LocaleDesignation.LocalLang.getString("addAptEndTimeText"));
        appointmentIdLabel.setText(LocaleDesignation.LocalLang.getString("addAptIdText"));
        appointmentLocationLabel.setText(LocaleDesignation.LocalLang.getString("addAptLocationText"));
        appointmentStartDateLabel.setText(LocaleDesignation.LocalLang.getString("addAptStartDateText"));
        appointmentStartTimeLabel.setText(LocaleDesignation.LocalLang.getString("addAptStartTimeText"));
        appointmentTypeLabel.setText(LocaleDesignation.LocalLang.getString("addAptTypeText"));
        appointmentUserIdLabel.setText(LocaleDesignation.LocalLang.getString("addAptUserIdText"));
        appointmentAddFormSaveButton.setText(LocaleDesignation.LocalLang.getString("addAptSaveButtonText"));
        cancelButton.setText(LocaleDesignation.LocalLang.getString("addAptCancelButtonText"));

        //Prepping the form
        appointmentIdTextField.setDisable(true);
        appointmentIdTextField.setText(String.valueOf(Appointment.appointmentIdGenerator()));

        //Setting Choice Box Values
        userIdChoiceBox.getItems().addAll(userIdChoiceBoxValues);
        customerIdChoiceBox.getItems().addAll(customerIdChoiceBoxValues);
        contactIdChoiceBox.getItems().addAll(contactIdChoiceBoxValues);

        //Initializing Spinner Values
        endTimeHourSpinner.setValueFactory(endHourFactory);
        endTimeMinuteSpinner.setValueFactory(endMinuteFactory);
        startTimeHourSpinner.setValueFactory(startHourFactory);
        startTimeMinuteSpinner.setValueFactory(startMinuteFactory);

        //Setting the Current UserID to the UserID in the Form
        userIdChoiceBox.setValue(LoginVerification.getCurrentUserId());

        //Telling the Program that the Previous Scene was not a Customer Scene
        PreviousSceneHelper.PsSetterFalse();

    }
} //Main Class End Bracket

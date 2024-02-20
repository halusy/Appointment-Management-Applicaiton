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
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * This is the ModifyAppointmentViewController Class. It contains various methods which allow the Program to Modify Appointments from the SQL Database.
 *
 * @author Nicholas Ryan
 * @version 1.0
 */
public class ModifyAppointmentViewController implements Initializable {

    //FXML Label Declarations
    @FXML private Label modifyAppointmentFormTitleLabel;
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
    @FXML private Button appointmentModifyFormSaveButton;

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

    //Spinner Value Factory Define
    SpinnerValueFactory<Integer> endHourFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 23, 1);
    SpinnerValueFactory<Integer> endMinuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 59, 00);
    SpinnerValueFactory<Integer> startHourFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 23, 1);
    SpinnerValueFactory<Integer> startMinuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 59, 00);

    public ModifyAppointmentViewController() throws SQLException {
    }

    /**
     *
     * This method executes when the User is ready to Update an Existing Appointment into the Database, and clicks the Save Button.
     *
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML void onAppointmentModifyFormSaveButtonClick(ActionEvent event) throws SQLException, IOException {

        //Gets selected appointment held from the main view
        Appointment currentAppointment = ObjectTransferHelper.getTransferAppointment();

        //Time conversion for consistency
        LocalDateTime originalStart = currentAppointment.getStartDate().toLocalDateTime();
        LocalDateTime originalEnd = currentAppointment.getEndDate().toLocalDateTime();
        LocalDate newStartDate = startDatePicker.getValue();
        LocalDate newEndDate = endDatePicker.getValue();
        LocalDateTime updatedStart = originalStart.withYear(newStartDate.getYear()).withMonth(newStartDate.getMonthValue()).withDayOfMonth(newStartDate.getDayOfMonth()).withHour(startTimeHourSpinner.getValue()).withMinute(startTimeMinuteSpinner.getValue());
        LocalDateTime updatedEnd = originalEnd.withYear(newEndDate.getYear()).withMonth(newEndDate.getMonthValue()).withDayOfMonth(newEndDate.getDayOfMonth()).withHour(endTimeHourSpinner.getValue()).withMinute(endTimeMinuteSpinner.getValue());
        Timestamp utcStart = ZoneIdHelper.timeConverterUtc(Timestamp.valueOf(updatedStart));
        Timestamp utcEnd = ZoneIdHelper.timeConverterUtc(Timestamp.valueOf(updatedEnd));

        try {

            //Prepping variable for the SQL Update
            String title = appointmentTitleTextField.getText();
            String description = appointmentDescriptionTextField.getText();
            String location = appointmentLocationTextField.getText();
            String type = appointmentTypeTextField.getText();
            Timestamp start = utcStart;
            Timestamp end = utcEnd;
            Timestamp lastUpdated = Timestamp.from(Instant.now());
            String updatedBy = LoginVerification.getCurrentUser();
            Integer customerId = customerIdChoiceBox.getValue();
            Integer userId = LoginVerification.getCurrentUserId();
            Integer contactId = contactIdChoiceBox.getValue();

            //SQL Database Commit w/ Contingency Checkers
            if (OverlapChecker.isWithinBusinessHours(start, end)) {

                if (OverlapChecker.customerOverlapChecker(customerId, start, end)) {

                    //SQL Commit Statements
                    String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, " +
                            "Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
                    PreparedStatement ps = JDBC.connection.prepareStatement(sql);
                    ps.setString(1, title);
                    ps.setString(2, description);
                    ps.setString(3, location);
                    ps.setString(4, type);
                    ps.setTimestamp(5, utcStart);
                    ps.setTimestamp(6, utcEnd);
                    ps.setTimestamp(7, lastUpdated);
                    ps.setString(8, updatedBy);
                    ps.setInt(9, customerId);
                    ps.setInt(10, userId);
                    ps.setInt(11, contactId);
                    ps.setInt(12, currentAppointment.getAppointmentId());
                    int Results = ps.executeUpdate();

                    //Refreshes appointment objects from updated Database
                    SQLAppointmentToObject.SQLAppointmentToObjectMethod();

                    //Moves user back to main scene
                    Parent root = FXMLLoader.load(MainApplication.class.getResource("main-schedule-view.fxml"));
                    Stage stage = (Stage) modifyAppointmentFormTitleLabel.getScene().getWindow();
                    Scene scene = new Scene(root, 770, 400);
                    stage.setTitle("Appointment Management Program (AMP)");
                    stage.setScene(scene);
                    stage.show();
                } else {
                    AlertHelper.warning(LocaleDesignation.LocalLang.getString("overlappingTitle"), LocaleDesignation.LocalLang.getString("overlappingContent"));
                }
            } else {
                AlertHelper.warning(LocaleDesignation.LocalLang.getString("outsideHoursTitle"), LocaleDesignation.LocalLang.getString("outsideHoursContent"));
            }
        } catch (IOException e){

            AlertHelper.warning(LocaleDesignation.LocalLang.getString("appointmentCommitTitle"), LocaleDesignation.LocalLang.getString("appointmentCommitContent"));

        }
    }

    /**
     *
     * This method moves the user back to the main view when the Cancel Button is clicked
     *
     * @param event
     * @throws IOException
     */
    @FXML void onCancelButtonClick(ActionEvent event) throws IOException {

        //Moves user to main view
        Parent root = FXMLLoader.load(MainApplication.class.getResource("main-schedule-view.fxml"));
        Stage stage = (Stage) modifyAppointmentFormTitleLabel.getScene().getWindow();
        Scene scene = new Scene(root, 770, 400);
        stage.setTitle("Appointment Management Program (AMP)");
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * This method runs when the ModifyAppointmentView is accessed by the user.
     *
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Resource Language Designation
        modifyAppointmentFormTitleLabel.setText(LocaleDesignation.LocalLang.getString("modifyAptMainTitleText"));
        appointmentTitleLabel.setText(LocaleDesignation.LocalLang.getString("modifyAptTitleText"));
        appointmentContactIdLabel.setText(LocaleDesignation.LocalLang.getString("modifyAptContactIdText"));
        appointmentCustomerIdLabel.setText(LocaleDesignation.LocalLang.getString("modifyAptCustomerIdText"));
        appointmentDescriptionLabel.setText(LocaleDesignation.LocalLang.getString("modifyAptDescriptionText"));
        appointmentEndDateLabel.setText(LocaleDesignation.LocalLang.getString("modifyAptEndDateText"));
        appointmentEndTimeLabel.setText(LocaleDesignation.LocalLang.getString("modifyAptEndTimeText"));
        appointmentIdLabel.setText(LocaleDesignation.LocalLang.getString("modifyAptIdText"));
        appointmentLocationLabel.setText(LocaleDesignation.LocalLang.getString("modifyAptLocationText"));
        appointmentStartDateLabel.setText(LocaleDesignation.LocalLang.getString("modifyAptStartDateText"));
        appointmentStartTimeLabel.setText(LocaleDesignation.LocalLang.getString("modifyAptStartTimeText"));
        appointmentTypeLabel.setText(LocaleDesignation.LocalLang.getString("modifyAptTypeText"));
        appointmentUserIdLabel.setText(LocaleDesignation.LocalLang.getString("modifyAptUserIdText"));
        appointmentModifyFormSaveButton.setText(LocaleDesignation.LocalLang.getString("modifyAptSaveButtonText"));
        cancelButton.setText(LocaleDesignation.LocalLang.getString("modifyAptCancelButtonText"));

        //Initializing ChoiceBoxes with their Value Ranges
        customerIdChoiceBox.getItems().addAll(customerIdChoiceBoxValues);
        userIdChoiceBox.getItems().addAll(userIdChoiceBoxValues);
        contactIdChoiceBox.getItems().addAll(contactIdChoiceBoxValues);

        //Initialization of TextFields with Selected Appointment Information
        appointmentIdTextField.setDisable(true);
        Appointment currentAppointment = ObjectTransferHelper.getTransferAppointment();
        appointmentIdTextField.setText(String.valueOf(currentAppointment.getAppointmentId()));
        appointmentTitleTextField.setText(currentAppointment.getTitle());
        appointmentDescriptionTextField.setText(currentAppointment.getDescription());
        appointmentLocationTextField.setText(currentAppointment.getLocation());
        appointmentTypeTextField.setText(currentAppointment.getType());
        contactIdChoiceBox.setValue(currentAppointment.getContactId());
        customerIdChoiceBox.setValue(currentAppointment.getCustomerId());
        userIdChoiceBox.setValue(currentAppointment.getUserId());

        //Setting DatePicker Values
        LocalDateTime currentStartTimestamp = currentAppointment.getStartDate().toLocalDateTime();
        LocalDateTime currentEndTimestamp = currentAppointment.getEndDate().toLocalDateTime();
        startDatePicker.setValue(currentStartTimestamp.toLocalDate());
        endDatePicker.setValue(currentEndTimestamp.toLocalDate());

        //Setting Spinner Value Model
        startTimeHourSpinner.setValueFactory(startHourFactory);
        startTimeMinuteSpinner.setValueFactory(startMinuteFactory);
        endTimeHourSpinner.setValueFactory(endHourFactory);
        endTimeMinuteSpinner.setValueFactory(endMinuteFactory);

        //Setting Spinner Values
        startTimeHourSpinner.getValueFactory().setValue(currentStartTimestamp.getHour());
        startTimeMinuteSpinner.getValueFactory().setValue(currentStartTimestamp.getMinute());
        endTimeHourSpinner.getValueFactory().setValue(currentEndTimestamp.getHour());
        endTimeMinuteSpinner.getValueFactory().setValue(currentEndTimestamp.getMinute());

        //Tells PreviousSceneHelper that the last scene was an Appointment
        PreviousSceneHelper.PsSetterFalse();
    }
} //Main Class End Bracket

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

    //FXML ActionEvent Declarations
    @FXML void onAppointmentModifyFormSaveButtonClick(ActionEvent event) throws SQLException, IOException {

        Appointment currentAppointment = ObjectTransferHelper.getTransferAppointment();
        LocalDateTime originalStart = currentAppointment.getStartDate().toLocalDateTime();
        LocalDateTime originalEnd = currentAppointment.getEndDate().toLocalDateTime();

        LocalDate newStartDate = startDatePicker.getValue();
        LocalDate newEndDate = endDatePicker.getValue();

        LocalDateTime updatedStart = originalStart.withYear(newStartDate.getYear()).withMonth(newStartDate.getMonthValue()).withDayOfMonth(newStartDate.getDayOfMonth()).withHour(startTimeHourSpinner.getValue()).withMinute(startTimeMinuteSpinner.getValue());
        LocalDateTime updatedEnd = originalEnd.withYear(newEndDate.getYear()).withMonth(newEndDate.getMonthValue()).withDayOfMonth(newEndDate.getDayOfMonth()).withHour(endTimeHourSpinner.getValue()).withMinute(endTimeMinuteSpinner.getValue());

        String title = appointmentTitleTextField.getText();
        String description = appointmentDescriptionTextField.getText();
        String location = appointmentLocationTextField.getText();
        String type = appointmentTypeTextField.getText();
        Timestamp start = Timestamp.valueOf(updatedStart);
        Timestamp end = Timestamp.valueOf(updatedEnd);
        Timestamp lastUpdated = Timestamp.from(Instant.now());
        String updatedBy = LoginVerification.getCurrentUser();
        Integer customerId = customerIdChoiceBox.getValue();
        Integer userId = LoginVerification.getCurrentUserId();
        Integer contactId = contactIdChoiceBox.getValue();

        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, " +
                "Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, start);
        ps.setTimestamp(6, end);
        ps.setTimestamp(7, lastUpdated);
        ps.setString(8, updatedBy);
        ps.setInt(9, customerId);
        ps.setInt(10, userId);
        ps.setInt(11, contactId);
        ps.setInt(12, currentAppointment.getAppointmentId());

        int Results = ps.executeUpdate();

        System.out.println(Results);

        SQLAppointmentToObject.SQLAppointmentToObjectMethod();

        Parent root = FXMLLoader.load(MainApplication.class.getResource("main-schedule-view.fxml"));
        Stage stage = (Stage) modifyAppointmentFormTitleLabel.getScene().getWindow();
        Scene scene = new Scene(root, 720, 400);
        stage.setTitle("Appointment Management Program (AMP)");
        stage.setScene(scene);
        stage.show();

    }

    @FXML void onCancelButtonClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(MainApplication.class.getResource("main-schedule-view.fxml"));
        Stage stage = (Stage) modifyAppointmentFormTitleLabel.getScene().getWindow();
        Scene scene = new Scene(root, 720, 400);
        stage.setTitle("Appointment Management Program (AMP)");
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

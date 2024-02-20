package com.example.AMP.helper;

import com.example.AMP.models.Appointment;
import com.example.AMP.models.Customer;
import javafx.collections.ObservableList;

/**
 * This interface defines the method for retrieving a new Appointment ID
 *
 * @author Nicholas Ryan
 * @version 1.0
 *
 */

@FunctionalInterface
public interface AppointmentIDGenerator {

    int idGeneratorMethod(ObservableList<Appointment> appointmentList);


}

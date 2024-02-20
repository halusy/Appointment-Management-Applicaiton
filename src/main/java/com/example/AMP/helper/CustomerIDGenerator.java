package com.example.AMP.helper;

import com.example.AMP.models.Appointment;
import com.example.AMP.models.Customer;
import javafx.collections.ObservableList;
/**
 * This interface defines the method for retrieving a new Customer ID
 *
 * @author Nicholas Ryan
 * @version 1.0
 *
 */
@FunctionalInterface
public interface CustomerIDGenerator {

    int idGeneratorMethod(ObservableList<Customer> customerList);

}

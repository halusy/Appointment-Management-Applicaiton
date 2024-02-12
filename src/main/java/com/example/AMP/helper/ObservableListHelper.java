package com.example.AMP.helper;

import com.example.AMP.models.Appointment;
import com.example.AMP.models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ObservableListHelper {

    private static ObservableList<Appointment>appointments = FXCollections.observableArrayList();

    public static ObservableList<Appointment> getAppointments() {
        return appointments;
    }

    public static void addAppointment(Appointment newAppointment) {
        appointments.add(newAppointment);
    }

    private static ObservableList<Customer>customers = FXCollections.observableArrayList();

    public static ObservableList<Customer> getCustomers() {
        return customers;
    }

    public static void addCustomer(Customer newCustomer){
        customers.add(newCustomer);
    }
}

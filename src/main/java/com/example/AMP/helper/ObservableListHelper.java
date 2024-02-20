package com.example.AMP.helper;

import com.example.AMP.models.Appointment;
import com.example.AMP.models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * This class hold the current list of appointment and customer objects for access across the program.
 *
 * @author Nicholas Ryan
 * @version 1.0
 */
public class ObservableListHelper {

    private static ObservableList<Appointment>appointments = FXCollections.observableArrayList();
    private static ObservableList<Appointment>appointmentsByMonth = FXCollections.observableArrayList();
    private static ObservableList<Appointment>appointmentsByWeek = FXCollections.observableArrayList();

    /**
     * Returns the list of appointment objects
     *
     * @return
     */
    public static ObservableList<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * Adds appointments to the universal appointment list
     *
     * @param newAppointment
     */
    public static void addAppointment(Appointment newAppointment) {
        appointments.add(newAppointment);
    }

    private static ObservableList<Customer>customers = FXCollections.observableArrayList();

    /**
     * Returns the list of customer objects
     *
     * @return
     */
    public static ObservableList<Customer> getCustomers() {
        return customers;
    }

    /**
     * Adds customers to the universal customer list
     *
     * @param newCustomer
     */
    public static void addCustomer(Customer newCustomer){
        customers.add(newCustomer);
    }

    /**
     *
     * Resets the customer list
     */
    public static void clearCustomers(){

        customers.clear();

    }

    /**
     *
     * Resets all appointment lists
     */
    public static void clearAppointments(){

        appointments.clear();
        appointmentsByMonth.clear();
        appointmentsByWeek.clear();

    }

    /**
     * Adds appointments to a list intended to hold only appointments within the current month
     *
     * @param appointment
     */
    public static void getAppointmentsByMonthSorter (Appointment appointment) {

        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        LocalDate checker = appointment.getStartDate().toLocalDateTime().toLocalDate();

        if (checker.isAfter(startOfMonth.minusDays(1)) && checker.isBefore(endOfMonth.plusDays(1))){

            appointmentsByMonth.add(appointment);

        }
    }

    /**
     * Returns the list of appointments for the current month
     *
     * @return
     */
    public static ObservableList<Appointment> getAppointmentsByMonth (){

        return appointmentsByMonth;

    }

    /**
     * Adds appointments to a list intended to hold only appointments within the current week
     *
     * @param appointment
     */
    public static void getAppointmentsByWeekSorter (Appointment appointment) {

        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        LocalDate checker = appointment.getStartDate().toLocalDateTime().toLocalDate();

        if (checker.isAfter(startOfWeek.minusDays(1)) && checker.isBefore(endOfWeek.plusDays(1))){

            appointmentsByWeek.add(appointment);

        }
    }

    /**
     * Returns the list of appointments for the current week
     *
     * @return
     */
    public static ObservableList<Appointment> getAppointmentsByWeek (){

        return appointmentsByWeek;

    }
}

package com.example.AMP.helper;

import com.example.AMP.models.Appointment;
import com.example.AMP.models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class ObservableListHelper {

    private static ObservableList<Appointment>appointments = FXCollections.observableArrayList();
    private static ObservableList<Appointment>appointmentsByMonth = FXCollections.observableArrayList();
    private static ObservableList<Appointment>appointmentsByWeek = FXCollections.observableArrayList();

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

    public static void clearCustomers(){

        customers.clear();

    }

    public static void clearAppointments(){

        appointments.clear();
        appointmentsByMonth.clear();
        appointmentsByWeek.clear();

    }

    public static void getAppointmentsByMonthSorter (Appointment appointment) {

        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        LocalDate checker = appointment.getStartDate().toLocalDateTime().toLocalDate();

        if (checker.isAfter(startOfMonth.minusDays(1)) && checker.isBefore(endOfMonth.plusDays(1))){

            appointmentsByMonth.add(appointment);

        }
    }
    public static ObservableList<Appointment> getAppointmentsByMonth (){

        return appointmentsByMonth;

    }
    public static void getAppointmentsByWeekSorter (Appointment appointment) {

        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        LocalDate checker = appointment.getStartDate().toLocalDateTime().toLocalDate();

        if (checker.isAfter(startOfWeek.minusDays(1)) && checker.isBefore(endOfWeek.plusDays(1))){

            appointmentsByWeek.add(appointment);

        }
    }
    public static ObservableList<Appointment> getAppointmentsByWeek (){

        return appointmentsByWeek;

    }
}

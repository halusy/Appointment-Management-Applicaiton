package com.example.AMP.helper;

import com.example.AMP.models.Appointment;
import com.example.AMP.models.Customer;

public class ObjectTransferHelper {

    static Customer transferCustomer;
    public static void customerHolder(Customer customer){

        transferCustomer = customer;

    }

    public static Customer getTransferCustomer() {
        return transferCustomer;
    }

    static Appointment transferAppointment;

    public static void appointmentHolder(Appointment appointment){

        transferAppointment = appointment;

    }

    public static Appointment getTransferAppointment(){

        return transferAppointment;

    }

}

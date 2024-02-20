package com.example.AMP.helper;

import com.example.AMP.models.Appointment;
import com.example.AMP.models.Customer;

/**
 * This class will hold objects for the transfer of objects between different scenes within the program.
 *
 * @author Nicholas Ryan
 * @version 1.0
 */
public class ObjectTransferHelper {

    static Customer transferCustomer;

    /**
     * This method takes a customer object and holds it for retrieval
     *
     * @param customer
     */
    public static void customerHolder(Customer customer){

        transferCustomer = customer;

    }

    /**
     *
     * This method returns the held customer object
     *
     * @return
     */
    public static Customer getTransferCustomer() {
        return transferCustomer;
    }

    static Appointment transferAppointment;

    /**
     * This method takes and stores a desired appointment
     *
     * @param appointment
     */
    public static void appointmentHolder(Appointment appointment){

        transferAppointment = appointment;
    }

    /**
     * This method returns the stored appointment
     *
     * @return
     */
    public static Appointment getTransferAppointment(){

        return transferAppointment;
    }

}

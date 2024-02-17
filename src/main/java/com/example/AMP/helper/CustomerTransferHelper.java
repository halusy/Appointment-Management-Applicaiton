package com.example.AMP.helper;

import com.example.AMP.models.Customer;

public class CustomerTransferHelper {

    static Customer transferCustomer;
    public static void customerHolder(Customer customer){

        transferCustomer = customer;

    }

    public static Customer getTransferCustomer() {
        return transferCustomer;
    }
}

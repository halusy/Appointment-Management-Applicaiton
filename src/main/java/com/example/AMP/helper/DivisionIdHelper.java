package com.example.AMP.helper;

public class DivisionIdHelper {

    public static int divisionIdRetriever(String division){

        if(division == "Alabama"){
            return 1;
        } else if(division == "Arizona"){
            return 2;
        } else if (division == "Arkansas") {
            return 3;
        } else {
            return 0;
        }
    }
}

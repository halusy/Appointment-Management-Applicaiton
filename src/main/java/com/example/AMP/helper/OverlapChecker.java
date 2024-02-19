package com.example.AMP.helper;

import com.example.AMP.models.Appointment;
import javafx.collections.ObservableList;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class OverlapChecker {

    static public ArrayList<Integer> appointmentsWithin15 = new ArrayList<>();



    public static void fifteen() {

        ObservableList<Appointment> appointments = ObservableListHelper.getAppointments();

        appointmentsWithin15.clear();
        LocalDateTime nowe = LocalDateTime.now(ZoneIdHelper.getCurrentTimezone());

        LocalDate nowDate = nowe.toLocalDate();

        for (Appointment appointment : appointments) {

            Timestamp aptTime = appointment.getStartDate();
            LocalDateTime convertedAptTime = aptTime.toLocalDateTime();
            LocalDate convertedAptDate = convertedAptTime.toLocalDate();

            int results = convertedAptDate.compareTo(nowDate);

            if (results == 0 && isWithin15Minutes(nowe, convertedAptTime)) {

                appointmentsWithin15.add(appointment.getAppointmentId());

            }
        } if (!(appointmentsWithin15.isEmpty())){

            AlertHelper.warning("15", "The following appointment ID's " + appointmentsWithin15 + " are within 15 mins");

        } else {

            AlertHelper.warning("all good", "all good");

        }
    }

    private static boolean isWithin15Minutes(LocalDateTime referenceDateTime, LocalDateTime appointmentTime) {

        System.out.println(referenceDateTime);
        System.out.println(appointmentTime);

        long minutesDifference = ChronoUnit.MINUTES.between(referenceDateTime, appointmentTime);

        if (minutesDifference <= 15){

            return true;

        } else {

            return false;

        }

    }
}

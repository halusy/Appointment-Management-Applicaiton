package com.example.AMP.helper;

import com.example.AMP.models.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
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

        if (referenceDateTime.toLocalTime().isBefore(appointmentTime.toLocalTime())) {

            long minutesDifference = ChronoUnit.MINUTES.between(referenceDateTime, appointmentTime);
            long hoursDifference = ChronoUnit.HOURS.between(referenceDateTime, appointmentTime);

            if (hoursDifference <= 1 && minutesDifference <= 15){

                return true;

            } else return false;
        } else { return false; }

    }

    public static boolean isWithinBusinessHours(Timestamp appointmentStartTime, Timestamp appointmentEndTime) {
        // Assuming business hours from 9 AM to 5 PM in EST

        LocalDateTime aptStart = appointmentStartTime.toLocalDateTime();
        ZonedDateTime utcAptStart = aptStart.atZone(ZoneId.of("UTC"));
        ZonedDateTime estAptStart = utcAptStart.withZoneSameInstant(ZoneId.of("America/New_York"));

        LocalDateTime aptEnd = appointmentEndTime.toLocalDateTime();
        ZonedDateTime utcAptEnd = aptEnd.atZone(ZoneId.of("UTC"));
        ZonedDateTime estAptEnd = utcAptEnd.withZoneSameInstant(ZoneId.of("America/New_York"));

        ZonedDateTime dateTimeStart = estAptStart;
        ZonedDateTime dateTimeEnd = estAptEnd;

        ZonedDateTime businessStart = dateTimeStart.withHour(8).withMinute(0).withSecond(0);
        ZonedDateTime businessEnd = dateTimeEnd.withHour(22).withMinute(0).withSecond(0);

        if (estAptStart.isBefore(businessEnd) && estAptStart.isAfter(businessStart) && estAptEnd.isBefore(businessEnd) && estAptEnd.isBefore(businessEnd)){
            if (estAptStart.getDayOfWeek().equals(DayOfWeek.SATURDAY) || estAptStart.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public static boolean customerOverlapChecker(int customerId, Timestamp startTime, Timestamp endTime){

        ObservableList<Appointment> appointments = ObservableListHelper.getAppointments();
        ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();

        for (Appointment checkingAppointment : appointments) {

            if (checkingAppointment.getAppointmentId() == customerId) {

                customerAppointments.add(checkingAppointment);

            }
        }

        if (customerAppointments.isEmpty()){

            return true;

        }

        else {

            for (Appointment appointmentDateCheck : customerAppointments) {



                Timestamp endAptTime = ZoneIdHelper.timeConverterUtc(appointmentDateCheck.getEndDate());
                Timestamp startAptTime = ZoneIdHelper.timeConverterUtc(appointmentDateCheck.getStartDate());

                if (endTime.toLocalDateTime().toLocalDate() != endAptTime.toLocalDateTime().toLocalDate() && startTime.toLocalDateTime().toLocalDate() != startAptTime.toLocalDateTime().toLocalDate()){

                    return true;

                }

                if (startTime.before(endAptTime) && endTime.after(startAptTime)) {

                    return false;

                } else return true;
            }
        }
        return false;
    }
}

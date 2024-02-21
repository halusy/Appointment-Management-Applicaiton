package com.example.AMP.helper;

import com.example.AMP.models.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Locale;

/**
 * This class handles all the methods associated with appointment overlap, and scheduling appointments outside the normal business hours
 *
 * @author Nicholas Ryan
 * @version 1.0
 */
public class OverlapChecker {

    static public ArrayList<Integer> appointmentsWithin15 = new ArrayList<>();


    /**
     *
     * This method, when called, will let the user know if there are any existing appointment occurring within 15 minutes of the current time
     */
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

            AlertHelper.warning(LocaleDesignation.LocalLang.getString("fifteen"), LocaleDesignation.LocalLang.getString("theFollowing") + " " + appointmentsWithin15 + " " + LocaleDesignation.LocalLang.getString("within"));
        } else {

            AlertHelper.warning(LocaleDesignation.LocalLang.getString("allGood"), LocaleDesignation.LocalLang.getString("allGood2"));
        }
    }

    /**
     *
     * This method checks if the appointments are within 15 minutes of the current time
     *
     * @param referenceDateTime
     * @param appointmentTime
     * @return
     */
    private static boolean isWithin15Minutes(LocalDateTime referenceDateTime, LocalDateTime appointmentTime) {

        if (referenceDateTime.toLocalTime().isBefore(appointmentTime.toLocalTime())) {

            long minutesDifference = ChronoUnit.MINUTES.between(referenceDateTime, appointmentTime);
            long hoursDifference = ChronoUnit.HOURS.between(referenceDateTime, appointmentTime);

            if (hoursDifference <= 1 && minutesDifference <= 15){

                return true;

            } else return false;
        } else { return false; }

    }

    /**
     * This method checks if a given appointment is outside of normal business hours
     *
     * @param appointmentStartTime
     * @param appointmentEndTime
     * @return
     */
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

    /**
     *
     * This method checks is two customer appointments overlap
     *
     * @param customerId
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean customerOverlapChecker(int customerId, Timestamp startTime, Timestamp endTime){

        ObservableList<Appointment> appointments = ObservableListHelper.getAppointments();
        ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();

        for (Appointment checkingAppointment : appointments) {

            if (checkingAppointment.getCustomerId() == customerId) {

                customerAppointments.add(checkingAppointment);

            }
        }

        if (customerAppointments.isEmpty()){
            return true;
        }

        else {

            int noOverlap = 0;
            int overlap =0;

            for (Appointment appointmentDateCheck : customerAppointments) {

                Timestamp endAptTime = ZoneIdHelper.timeConverterUtc(appointmentDateCheck.getEndDate());
                Timestamp startAptTime = ZoneIdHelper.timeConverterUtc(appointmentDateCheck.getStartDate());

                LocalDate endDate = endTime.toLocalDateTime().toLocalDate();
                LocalDate startDate = startTime.toLocalDateTime().toLocalDate();
                LocalDate endAptDate = endAptTime.toLocalDateTime().toLocalDate();
                LocalDate startAptDate = startAptTime.toLocalDateTime().toLocalDate();

                if (!(endDate.equals(endAptDate)) && (!startDate.equals(startAptDate))){

                } else {
                    if (startTime.before(endAptTime) && endTime.after(startAptTime) || startTime.equals(startAptTime)) {
                    overlap++;
                    }
                }
            }

            if (overlap == 0){

                return true;

            } else {

                return false;
            }

        }

    }
}

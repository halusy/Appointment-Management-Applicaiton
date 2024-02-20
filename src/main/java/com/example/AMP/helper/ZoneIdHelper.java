package com.example.AMP.helper;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;

/**
 *This class contains multiple methods to help with time conversions within the project.
 *
 * @author Nicholas Ryan
 * @version 1.0
 */
public class ZoneIdHelper {
    public static ZoneId currentZone;

    /**
     * Returns the current user timezone (not the JVM default)
     *
     * @return
     */
    public static ZoneId getCurrentTimezone(){
        return currentZone;
    }

    /**
     *
     * Sets the variable currentZone to the users timezone
     */
    public static void setCurrentZone (){
        currentZone = ZoneId.systemDefault();
    }

    /**
     * This method takes a UTC time and converts it to the users current timezone
     *
     * @param originalTime
     * @return
     */
    public static Timestamp timeConverter(Timestamp originalTime){

        if(originalTime == null) {

            return null;

        } else {
            ZonedDateTime originalStartTime = ZonedDateTime.ofInstant(originalTime.toInstant(), ZoneId.of("UTC"));
            ZonedDateTime convertedToLocal = ZoneIdHelper.utcToCurrentZoneConverter(originalStartTime);
            Timestamp localStartTimestamp = Timestamp.valueOf(convertedToLocal.toLocalDateTime());
            return localStartTimestamp;
        }
    }

    /**
     * This method converts times in the users timezone to UTC
     *
     * @param zonedTime
     * @return
     */
    public static Timestamp timeConverterUtc(Timestamp zonedTime){

        LocalDateTime originalStartTime = zonedTime.toLocalDateTime();
        ZonedDateTime oStart = originalStartTime.atZone(ZoneIdHelper.getCurrentTimezone());
        ZonedDateTime convertedTime = oStart.withZoneSameInstant(ZoneId.of("UTC"));
        Timestamp utcStartTimestamp = Timestamp.valueOf(convertedTime.toLocalDateTime());
        return utcStartTimestamp;

    }
    public static ZonedDateTime utcToCurrentZoneConverter(ZonedDateTime originalTime){

        ZonedDateTime convertedTime = originalTime.withZoneSameInstant(currentZone);

        return convertedTime;

    }

}

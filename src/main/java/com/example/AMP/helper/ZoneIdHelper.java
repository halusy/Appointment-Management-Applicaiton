package com.example.AMP.helper;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;

public class ZoneIdHelper {
    public static ZoneId currentZone;
    public static ZoneId getCurrentTimezone(){
        return currentZone;
    }
    public static void setCurrentZone (){
        currentZone = ZoneId.systemDefault();
    }
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

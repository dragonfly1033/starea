package org.openjfx.starea;


import java.time.Instant;
import java.util.Calendar;

public final class MoonPhaseHelper {
    private final static double LUNAR_MONTH = 29.530588853;

    private static double getJulianDate(long time) {
        Calendar c = Calendar.getInstance();
        int timezoneOffset = -(c.get(Calendar.ZONE_OFFSET) + c.get(Calendar.DST_OFFSET)) / (60 * 1000);

        return (time / 86400000.0) - (timezoneOffset / 1440.0) + 2440587.5;
    }
    private static double getLunarAgePercent(long time) {
        return normalise((getJulianDate(time) - 2451550.1) / LUNAR_MONTH);
    }

    private static double normalise(double value) {
        value = value - Math.floor(value);
        if (value < 0) {
            value += 1;
        }

        return value;
    }

    // Use Instant.now().toEpochMilli() for current time
    // To get 2 days from now, Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()
    public static double getLunarAge(long time) {
        double percent = getLunarAgePercent(time);
        double age = percent * LUNAR_MONTH;

        return age;
    }

    public static String getLunarPhase(long time) {
        double age = getLunarAge(time);
        if (age < 1.84566)
            return "New";
        else if (age < 5.53699)
            return "Waxing Crescent";
        else if (age < 9.22831)
            return "First Quarter";
        else if (age < 12.91963)
            return "Waxing Gibbous";
        else if (age < 16.61096)
            return "Full";
        else if (age < 20.30228)
            return "Waning Gibbous";
        else if (age < 23.99361)
            return "Last Quarter";
        else if (age < 27.68493)
            return "Waning Crescent";
        return "New";
    }
}

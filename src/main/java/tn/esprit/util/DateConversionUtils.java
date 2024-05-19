package tn.esprit.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateConversionUtils {

    // Method to convert Date to Symfony's DateInterval format
    public static String convertDateToInterval(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.now();
        Duration duration = Duration.between(now, localDate);
        long days = duration.toDays();
        long months = days / 30; // Assuming 30 days per month
        return "P" + months + "M"; // Symfony's DateInterval format: P{months}M
    }

    // Method to convert Symfony's DateInterval format to Date
    public static Date convertIntervalToDate(String interval) throws ParseException {
        if (!interval.startsWith("P") || !interval.endsWith("M")) {
            throw new IllegalArgumentException("Invalid DateInterval format");
        }
        String monthsStr = interval.substring(1, interval.length() - 1);
        long months = Long.parseLong(monthsStr);
        LocalDate now = LocalDate.now();
        LocalDate convertedDate = now.plusMonths(months);
        return Date.from(convertedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    // Example usage:
    public static void main(String[] args) throws ParseException {
        // Convert Date to DateInterval format
        Date date = new Date(); // Assuming you have a Date object
        String interval = convertDateToInterval(date);
        System.out.println("DateInterval format: " + interval);

        // Convert DateInterval format to Date
        String symfonyInterval = "P3M"; // Example Symfony's DateInterval format
        Date convertedDate = convertIntervalToDate(symfonyInterval);
        System.out.println("Converted Date: " + convertedDate);
    }
}

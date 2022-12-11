package Helpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class DateHelper {

    private static String datesFormat = "d MMMM yyyy";

    private static LocalDate dateNow = LocalDate.now();

    public static int currentDay(){
        return dateNow.getDayOfMonth();
    }


    // Check date
    public static String checkInDate(){
        return dateNow.format(DateTimeFormatter.ofPattern(datesFormat, Locale.ENGLISH));
    }

    public static String checkInDateNDaysFromToday(int n){
        return dateNow.plusDays(n).format(DateTimeFormatter.ofPattern(datesFormat, Locale.ENGLISH));
    }

    public static String checkOutDateAfter_X_Nights(int x){
        return dateNow.plusDays(x).format(DateTimeFormatter.ofPattern(datesFormat, Locale.ENGLISH));
    }

    public static int currentYear(){
        return dateNow.getYear();
    }

    public static List<String> DAYS_BetweenCheckInAndCheckOut(String checkIn, String checkOut){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy").withLocale(Locale.ENGLISH);

        LocalDate date1 = LocalDate.parse(checkIn, formatter);
        LocalDate date2 = LocalDate.parse(checkOut, formatter);

        return  date1.datesUntil(date2.plusDays(1)).map(el -> el.getDayOfMonth()).map(el->String.valueOf(el))
                .collect(Collectors.toList());
    }


}

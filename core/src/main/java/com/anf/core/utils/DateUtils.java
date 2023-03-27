// ***Begin Code - Tejaswini Nallamothu ***
package com.anf.core.utils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    /**
     * To get the current date
     * @param format date format
     * @return formatted date
     */
    public static  String getCurrentDate(String format){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        LocalDate localDate = LocalDate.now();
        return dtf.format(localDate);
    }
}
// ***END Code*****

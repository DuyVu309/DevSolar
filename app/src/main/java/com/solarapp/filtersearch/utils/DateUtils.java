package com.solarapp.filtersearch.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateUtils {
    public static String formatDate(String raw){    //2019-10-12T12:30:00+00:00
//        DateTimeFormatter parser2 = ISODateTimeFormat.dateTime();
//        DateTime date = parser2.parseDateTime(raw);
        DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ"); //Default format
        DateTime dateTime = new DateTime(DATE_FORMAT.parseDateTime(raw));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Calendar cal=dateTime.toCalendar(Locale.getDefault());

        return simpleDateFormat.format(cal.getTime()) + " GMT";
    }
}

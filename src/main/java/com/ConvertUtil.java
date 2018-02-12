package com;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertUtil {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat DATE_FORMAT_SHORT = new SimpleDateFormat("yyyy-MM-dd");

    public static String toDate(Date date) {
        return DATE_FORMAT.format(date);
    }

    public static Date toDate(String date)
            throws ParseException {
        return DATE_FORMAT.parse(date);
    }

    public static Date toDateShort(String date)
            throws ParseException {
        return DATE_FORMAT_SHORT.parse(date);
    }
}

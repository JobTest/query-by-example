package com;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.Period;

import java.text.DecimalFormat;
import java.text.ParseException;

public final class JodaUtils {
    private static Logger log = Logger.getLogger(JodaUtils.class);

    private JodaUtils() {
    }

    /**
     * Период по строке
     * @param value строка
     * @return период
     */
    public static Period periodInst(String value) {
        if (value == null) {
            throw new IllegalArgumentException("period value is null");
        }
        Period result;

        int intValue = 0;
        try {
            intValue = new DecimalFormat().parse(value).intValue();
        } catch (ParseException e) {
            log.error("can't parse period value ", e);
        }

        if (value.endsWith("S")) {
            result = Period.millis(intValue);
        } else if (value.endsWith("s")) {
            result = Period.seconds(intValue);
        } else if (value.endsWith("m")) {
            result = Period.minutes(intValue);
        } else if (value.endsWith("h")) {
            result = Period.hours(intValue);
        } else if (value.endsWith("d")) {
            result = Period.days(intValue);
        } else if (value.endsWith("w")) {
            result = Period.weeks(intValue);
        } else if (value.endsWith("M")) {
            result = Period.months(intValue);
        } else if (value.endsWith("y")) {
            result = Period.years(intValue);
        } else {
            throw new IllegalArgumentException("invalid period " + value);
        }

        return result;
    }

    /**
     * Создать дату и время из даты и времени
     * @param dateTime дата
     * @param localTime время
     * @return дата и время
     */
    public static DateTime createDate(final DateTime dateTime, final LocalTime localTime) {
        DateTime result = new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(),
                localTime.getHourOfDay(), localTime.getMinuteOfHour());
        return result;
    }
}

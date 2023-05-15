package com.example.application.JdbcTemplateExample.ValueConverters;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateToLocalDateUtil {
    private Date date;

    public DateToLocalDateUtil(Date date) {
        this.date=date;
    }

    public static LocalDateTime convertDateToLocalDate(Date date){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        Instant instant = calendar.toInstant();
        LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        return localDateTime;
    }
}

package com.example.application.JdbcTemplateExample.ValueConverters;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateToLocalDateUtil {
    private Date date;

    public DateToLocalDateUtil(Date date) {
        this.date=date;
    }

    public static LocalDateTime convertDateToLocalDate(Date date){
        return date.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime();
    }
}

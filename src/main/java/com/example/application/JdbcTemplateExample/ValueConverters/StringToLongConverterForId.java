package com.example.application.JdbcTemplateExample.ValueConverters;

import java.text.NumberFormat;
import java.util.Locale;


import com.vaadin.flow.data.converter.StringToLongConverter;

public class StringToLongConverterForId {
    private Long Id;

    public StringToLongConverterForId(Long id) {
        Id = id;
    }

    public static StringToLongConverter CustomConverter() {
        StringToLongConverter idConverter = new StringToLongConverter("Lütfen sayısal bir değer giriniz.") {
            protected java.text.NumberFormat getFormat(Locale Id) {
                NumberFormat format = super.getFormat(Id);
                format.setGroupingUsed(false);
                return format;
            }
        };
        return idConverter;
    }   
}

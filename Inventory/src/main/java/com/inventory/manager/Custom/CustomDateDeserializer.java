package com.inventory.manager.Custom;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateDeserializer extends JsonDeserializer<Date> {
    private static final DateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
    private static final DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");


    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        String dateStr = p.getText();
        try {
            return dateFormat2.parse(dateStr);
        } catch (ParseException e) {
            try {
                return dateFormat1.parse(dateStr);
            } catch (ParseException ex) {
                throw new RuntimeException("Invalid date format: " + dateStr);
            }
        }
    }
}

package com.system.creditOnline.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class CustomLocalDateDeserializer extends JsonDeserializer<LocalDate> {

    private static final List<DateTimeFormatter> SUPPORTED_FORMATS = List.of(
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy")
    );

    @Override
    public LocalDate deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String dateStr = parser.getText();
        for (DateTimeFormatter formatter : SUPPORTED_FORMATS) {
            try {
                return LocalDate.parse(dateStr, formatter);
            } catch (DateTimeParseException ignored) {
                // Intentar con el siguiente formato
            }
        }
        throw new IOException("Formato de fecha no válido: " + dateStr);
    }
}

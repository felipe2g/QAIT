package com.stackti.server.Util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public class DifferenceBetweenDates {
    public static String differenceBetweenDates(Timestamp time) {
        LocalDateTime fim = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime inicio = time.toLocalDateTime();

        if (inicio.until(fim, ChronoUnit.MONTHS) > 12) {
            return inicio.until(fim, ChronoUnit.YEARS) + " ano(s)";
        } else if (inicio.until(fim, ChronoUnit.DAYS) > 30) {
            return inicio.until(fim, ChronoUnit.MONTHS) + " mes(es)";
        } else if (inicio.until(fim, ChronoUnit.HOURS) > 24) {
            return inicio.until(fim, ChronoUnit.DAYS) + " dia(s)";
        } else if (inicio.until(fim, ChronoUnit.MINUTES) > 60) {
            return inicio.until(fim, ChronoUnit.HOURS) + " hora(s)";
        } else if (inicio.until(fim, ChronoUnit.SECONDS) > 60) {
            return inicio.until(fim, ChronoUnit.MINUTES) + " minuto(s)";
        } else {
            return inicio.until(fim, ChronoUnit.SECONDS) + " segundo(s)";
        }
    }
}

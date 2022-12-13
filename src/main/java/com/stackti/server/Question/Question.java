package com.stackti.server.Question;

import com.stackti.server.User.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Data
@AllArgsConstructor
public class Question {
    private long question_id;
    private String title;
    private String body;
    private int viewCount;
    private int score;
    private User author;
    private int answearCount;
    private Long correct_answear_id;
    private Timestamp questionCreatedAt;
    private Timestamp questionUpdatedAt;

    public Question() {
        author = new User();
    }

    public String getDate() {
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date(questionCreatedAt.getTime()));
    }

    public String differenceBetweenDates() {
        // tenho que arrumar, tem alguns bugs, mas nao atrapalha o serviço
        LocalDateTime fim = LocalDateTime.now(ZoneId.of("America/Sao_Paulo")); // pega data e hora atual
        LocalDateTime inicio;
        if (questionUpdatedAt == null) {
            inicio = questionCreatedAt.toLocalDateTime();
        } else {
            inicio = questionUpdatedAt.toLocalDateTime();
        }
        if (inicio.getYear() != fim.getYear()) {
            if (inicio.until(fim, ChronoUnit.YEARS) == 0) {
                return inicio.until(fim, ChronoUnit.MONTHS) + " mes(es)";
            } else {
                return inicio.until(fim, ChronoUnit.YEARS) + " ano(s)";
            }
        } else if (inicio.getMonthValue() != fim.getMonthValue()) {
            if (inicio.until(fim, ChronoUnit.MONTHS) == 0) {
                return inicio.until(fim, ChronoUnit.DAYS) + " dia(s)";
            } else {
                return inicio.until(fim, ChronoUnit.MONTHS) + " mes(es)";
            }
        } else if (inicio.getDayOfMonth() != fim.getDayOfMonth()) {
            if (inicio.until(fim, ChronoUnit.DAYS) == 0) {
                System.out.println(inicio.until(fim, ChronoUnit.DAYS));
                return inicio.until(fim, ChronoUnit.HOURS) + " hora(s)";
            } else {
                return inicio.until(fim, ChronoUnit.DAYS) + " dia(s)";
            }
        } else if (inicio.getHour() != fim.getHour()) {
            if (inicio.until(fim, ChronoUnit.HOURS) == 0) {
                return inicio.until(fim, ChronoUnit.MINUTES) + " minuto(s)";
            } else {
                return "" + inicio.until(fim, ChronoUnit.HOURS) + " hora(s)";
            }
        } else {
            if (inicio.until(fim, ChronoUnit.MINUTES) == 0) {
                return inicio.until(fim, ChronoUnit.SECONDS) + " segundo(s)";
            } else {
                return inicio.until(fim, ChronoUnit.MINUTES) + " minuto(s)";
            }
        }
    }
}

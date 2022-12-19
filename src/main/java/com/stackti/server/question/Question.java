package com.stackti.server.question;

import com.stackti.server.User.User;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;



@AllArgsConstructor
@NoArgsConstructor
@Data
public class Question {
    private int question_id;
    private String title;
    private String body;
    private int view_count;
    private int score;
    private Timestamp created_at;
    private Timestamp updated_at;
    private User author;
    private int correct_answer_id;

    public String getDate() {
        return new String(new SimpleDateFormat("dd-MM-yyyy").format(new Date(created_at.getTime())));
    }

    public String differenceBetweenDates() {
        LocalDateTime fim = LocalDateTime.now(ZoneId.of("America/Sao_Paulo")); // pega data e hora atual
        LocalDateTime inicio;
        if (updated_at==null) {
            inicio = created_at.toLocalDateTime(); 
        } else {
            inicio = updated_at.toLocalDateTime(); 
        }
        if (inicio.until(fim, ChronoUnit.MONTHS)>12) {
            return inicio.until(fim, ChronoUnit.YEARS)+" ano(s)";
        } else if (inicio.until(fim, ChronoUnit.DAYS)>30) {
            return inicio.until(fim, ChronoUnit.MONTHS)+" mes(es)";
        } else if (inicio.until(fim, ChronoUnit.HOURS)>24) {
            return inicio.until(fim, ChronoUnit.DAYS)+" dia(s)";
        } else if (inicio.until(fim, ChronoUnit.MINUTES)>60) {
            return inicio.until(fim, ChronoUnit.HOURS)+" hora(s)";
        } else if (inicio.until(fim, ChronoUnit.SECONDS)>60) {
            return inicio.until(fim, ChronoUnit.MINUTES)+" minuto(s)";
        } else {
            return inicio.until(fim, ChronoUnit.SECONDS)+" segundo(s)";
        }
        
    }
}

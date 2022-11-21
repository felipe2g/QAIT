package com.stackti.server.question;

import com.stackti.server.User.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Question {
    private int question_id;
    private String title;
    private String question_description;
    private int visits;
    private LocalDateTime data;
    private int rate;
    private Timestamp created_at;
    private Timestamp updated_at;
    private User author;
    private int correct_answer_id;

    public String dataFormat() {
        return new String(data.format(DateTimeFormatter.ofPattern("dd-MM-yyy")));
    }
}





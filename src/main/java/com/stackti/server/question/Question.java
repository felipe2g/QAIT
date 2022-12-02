package com.stackti.server.question;

import com.stackti.server.User.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
}





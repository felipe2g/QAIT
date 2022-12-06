package com.stackti.server.Question;

import com.stackti.server.User.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    public String getDate() {
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date(questionCreatedAt.getTime()));
    }
}

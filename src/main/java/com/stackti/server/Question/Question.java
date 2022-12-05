package com.stackti.server.Question;

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
    private long author_id;
    private long correct_answear_id;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public String getDate() {
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date(createdAt.getTime()));
    }
}

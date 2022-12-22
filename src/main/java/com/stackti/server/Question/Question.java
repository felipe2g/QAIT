package com.stackti.server.Question;

import com.stackti.server.User.User;
import com.stackti.server.Util.DifferenceBetweenDates;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

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

    public String getQuestionUpdatedAt() {
        return DifferenceBetweenDates.differenceBetweenDates(questionUpdatedAt);
    }
}

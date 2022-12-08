package com.stackti.server.Answear;

import com.stackti.server.User.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class Answear {
    private Long answear_id;
    private long question_id;
    private User author;
    private String body;
    private int score;
    private int viewerVote;
    private Timestamp answearCreatedAt;
    private Timestamp answearUpdatedAt;

    public Answear() {
        author = new User();
    }
}

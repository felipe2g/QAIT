package com.stackti.server.Question.Vote;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionVote {
    private long user_id;
    private long question_id;
    private int vote;
}

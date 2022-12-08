package com.stackti.server.question.voto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VotoRepository {
    @Autowired
    JdbcTemplate db;

    public void setVote(int cod, String op) {
        db.update("UPDATE question_vote SET vote = vote"+op+"1 WHERE question_id = ?;", cod);
    }

    public void insertVote(int codQuestion) {
        db.update("insert into question_vote (question_id,user_id,vote) values (?, ?,0);", codQuestion,1);
    }
}

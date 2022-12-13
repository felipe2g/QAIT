package com.stackti.server.answer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class AnswerRepository {
    @Autowired
    JdbcTemplate db;

    public int getCountByQuestion(int cod) {
        return db.queryForObject(
                "select count(*) from answer a join question q on a.question_id = q.question_id where a.question_id = ? ",
                Integer.class, new Object[] { cod });

    }
}

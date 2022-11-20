package com.stackti.server.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionRepository {
    @Autowired
    JdbcTemplate db;

    public void questionInsert(Question question) {
        db.update("insert into question (title, question_description, visits, question_data, rate, created_at, updated_at, author_id,correct_answer_id) values (?,?,?,?,?,?,?,?,?);",
                question.getTitle(),question.getQuestion_description(),question.getVisits(),new java.sql.Date(question.getData().getTime()),question.getRate(),question.getCreated_at(),question.getUpdated_at(),1,null);
    }
}

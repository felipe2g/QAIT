package com.stackti.server.Question;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionRepository {
    final JdbcTemplate jdbc;

    public QuestionRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Question> findAllSortedByDate() {
        return jdbc.query("select * from question order by created_at desc;", new BeanPropertyRowMapper<>(Question.class));
    }

    public Question find(long id) {
        return jdbc.queryForObject("select * from question where question_id = ? ", new BeanPropertyRowMapper<>(Question.class), id);
    }

    public void save(Question question) {
        jdbc.update("insert into question (title, body, author_id) values (?, ?, ?);", question.getTitle(), question.getBody(), question.getAuthor_id());
    }

    public void update(Question question) {
        jdbc.update("update question set title = ?, body = ?, correct_answer_id = ?, updated_at = now() where question_id = ?", question.getTitle(), question.getBody(), question.getCorrect_answear_id(), question.getQuestion_id());
    }

    public void delete(long id) {
        jdbc.update("delete from question where question_id = ?;", id);
    }
}

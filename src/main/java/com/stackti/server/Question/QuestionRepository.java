package com.stackti.server.Question;

import com.stackti.server.User.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class QuestionRepository {
    final JdbcTemplate jdbc;

    public QuestionRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Question rowMapper(ResultSet rs, int rowNum) throws SQLException {
        Question question = BeanPropertyRowMapper.newInstance(Question.class).mapRow(rs, rowNum);
        if (question != null) question.setAuthor(BeanPropertyRowMapper.newInstance(User.class).mapRow(rs, rowNum));
        return question;
    }

    public List<Question> findAllSortedByDate() {
        return jdbc.query("select * from question join \"user\" u on u.user_id = question.author_id order by question_updated_at desc;", this::rowMapper);
    }

    public Question findById(long id) {
        return jdbc.queryForObject("select * from question join \"user\" u on u.user_id = question.author_id where question_id = ? ", this::rowMapper, id);
    }

    public Long findQuestionIdByAnswearId(long id) {
        return jdbc.queryForObject("select question_id from answear where answear_id = ?", Long.class, id);
    }

    public void updateVisitCount(long id) {
        jdbc.update("UPDATE question SET view_count = view_count+1 WHERE question_id = ?;", id);
    }

    public void updateAnswearCount(long id) {
        jdbc.update("UPDATE question SET answear_count = answear_count+1 WHERE question_id = ?;", id);
    }

    public void save(Question question) {
        jdbc.update("insert into question (title, body, author_id) values (?, ?, ?);", question.getTitle(), question.getBody(), question.getAuthor().getUser_id());
    }

    public void update(Question question) {
        jdbc.update("update question set title = ?, body = ?, correct_answear_id = ?, question_created_at = now() where question_id = ?", question.getTitle(), question.getBody(), question.getCorrect_answear_id(), question.getQuestion_id());
    }

    public void delete(long id) {
        jdbc.update("delete from question where question_id = ?;", id);
    }
}

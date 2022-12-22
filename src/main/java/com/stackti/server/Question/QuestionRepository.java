package com.stackti.server.Question;

import com.stackti.server.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class QuestionRepository {
    final String selectBase = "select * from question join user u on u.user_id = question.author_id ";
    final String orderBase = " order by question_created_at desc;";
    @Autowired
    JdbcTemplate jdbc;

    public Question rowMapper(ResultSet rs, int rowNum) throws SQLException {
        Question question = BeanPropertyRowMapper.newInstance(Question.class).mapRow(rs, rowNum);
        if (question != null) question.setAuthor(BeanPropertyRowMapper.newInstance(User.class).mapRow(rs, rowNum));
        return question;
    }

    public List<Question> findAll(String search) {
        if (search == null) {
            return jdbc.query(selectBase + orderBase, this::rowMapper);
        }

        String query = "%" + search + "%";
        return jdbc.query(selectBase + " where body ilike ? or title ilike ?" + orderBase, this::rowMapper, query, query);
    }

    public Question findById(long id) {
        return jdbc.queryForObject(selectBase + " where question_id = ?;", this::rowMapper, id);
    }

    public Long findQuestionIdByAnswearId(long id) {
        return jdbc.queryForObject("select question_id from answear where answear_id = ?", Long.class, id);
    }

    public List<Question> findAllByAuthorId(long id) {
        return jdbc.query(selectBase + " where author_id = ?" + orderBase, this::rowMapper, id);
    }

    public void updateVisitCount(long id) {
        jdbc.update("UPDATE question SET view_count = view_count+1 WHERE question_id = ?;", id);
    }

    public void updateAnswearCount(long id) {
        jdbc.update("UPDATE question SET answear_count = answear_count+1 WHERE question_id = ?;", id);
    }

    public void updateCorrectAnswear(long QuestionId, long AnswearId) {
        jdbc.update("UPDATE question SET correct_answear_id = ? WHERE question_id = ?;", AnswearId, QuestionId);
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

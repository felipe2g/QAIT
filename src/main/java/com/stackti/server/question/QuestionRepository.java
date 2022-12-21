package com.stackti.server.question;

import com.stackti.server.User.User;
import com.stackti.server.question.tag.TagRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class QuestionRepository {
    @Autowired
    JdbcTemplate db;

    @Autowired
    TagRepository tag;

    public int questionInsert(Question question) {
        return db.queryForObject(
                "insert into question (title, body, view_count, score, updated_at, author_id,correct_answer_id) values (?,?,?,?,?,?,?) returning question_id;",
                Integer.class,
                new Object[] { question.getTitle(), question.getBody(), question.getView_count(),
                        question.getScore(), question.getUpdated_at(), 1, null });

    }

    public Question all(ResultSet res, int nowNum) throws SQLException {
        Question qt = new Question(
                res.getInt("question_id"),
                res.getString("title"),
                res.getString("body"),
                res.getInt("view_count"),
                res.getInt("score"),
                res.getTimestamp("created_at"),
                res.getTimestamp("updated_at"),
                new User(
                        res.getLong("user_id"),
                        res.getString("first_name"),
                        res.getString("last_name"),
                        res.getString("email"),
                        res.getString("password"),
                        res.getInt("role"),
                        res.getString("job_title"),
                        res.getInt("rate"),
                        res.getTimestamp("created_us"),
                        res.getTimestamp("updated_us")),
                res.getInt("correct_answer_id"));
        return qt;
    }

    public Question questionById(int cod) {
        return db.queryForObject("select * from question q join \"user\" u on author_id=user_id where question_id = ? ",
                this::all, cod);
    }

    public void updateVisitInQuestion(int cod) {
        db.update("UPDATE question SET view_count = view_count+1 WHERE question_id = ?;", cod);
    }

    public List<Question> allQuestionsByDate() {
        return db.query("select * from question q join \"user\" u on author_id=user_id order by q.created_at desc;",
                this::all);
    }
}

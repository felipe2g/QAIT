package com.stackti.server.question;

import com.stackti.server.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public class QuestionRepository {
    @Autowired
    JdbcTemplate db;

    public void questionInsert(Question question) {
        db.update("insert into question (title, question_description, visits, question_data, rate, created_at, updated_at, author_id,correct_answer_id) values (?,?,?,?,?,?,?,?,?);",
                question.getTitle(),question.getQuestion_description(),question.getVisits(), Timestamp.valueOf(question.getData()),question.getRate(),question.getCreated_at(),question.getUpdated_at(),1,null);
    }

    public Question all(ResultSet res, int nowNum) throws SQLException {
        Question qt = new Question(
                res.getInt("question_id"),
                res.getString("title"),
                res.getString("question_description"),
                res.getInt("visits"),
                res.getTimestamp("question_data").toLocalDateTime(),
                res.getInt("rate"),
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
                1);
        return qt;
    }
    public  Question questionById(int cod) {
        return db.queryForObject("select * from question q join \"user\" u on author_id=user_id where question_id = ? ", this::all, cod);
    }
    public List<Question> allQuestionsByDate() {
        return db.query("select * from question q join \"user\" u on author_id=user_id order by q.question_data desc;", this::all);
    }
}

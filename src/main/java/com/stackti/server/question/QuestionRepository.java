package com.stackti.server.question;

import com.stackti.server.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class QuestionRepository {
    @Autowired
    JdbcTemplate db;

    public void questionInsert(Question question) {
        db.update("insert into question (title, question_description, visits, question_data, rate, created_at, updated_at, author_id,correct_answer_id) values (?,?,?,?,?,?,?,?,?);",
                question.getTitle(),question.getQuestion_description(),question.getVisits(),new java.sql.Date(question.getData().getTime()),question.getRate(),question.getCreated_at(),question.getUpdated_at(),1,null);
    }

    public List<Question> allQuestionsByDate() {
        return db.query("select * from question q join \"user\" u on author_id=user_id order by q.question_data desc ;",
            (res, nowNum) -> {
                Question qt = new Question(
                        res.getInt("question_id"),
                        res.getString("title"),
                        res.getString("question_description"),
                        res.getInt("visits"),
                        res.getDate("question_data"),
                        res.getInt("rate"),
                        new Date(),
                        new Date(),
                        new User(
                                res.getLong("user_id"),
                                res.getString("first_name"),
                                res.getString("last_name"),
                                res.getString("email"),
                                res.getString("password"),
                                res.getInt("role"),
                                res.getString("job_title"),
                                res.getInt("rate"),
                                res.getDate("created_at"),
                                res.getDate("updated_at")),
                       1);
                return qt;
            }
        );
    }
}

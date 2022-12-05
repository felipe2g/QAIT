package com.stackti.server.Answear;

import com.stackti.server.User.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AnswearRepository {
    private final JdbcTemplate jdbc;

    public AnswearRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Answear rowMapper(ResultSet rs, int rowNum) throws SQLException {
        Answear answear = BeanPropertyRowMapper.newInstance(Answear.class).mapRow(rs, rowNum);
        if (answear != null) answear.setAuthor(BeanPropertyRowMapper.newInstance(User.class).mapRow(rs, rowNum));
        return answear;
    }

    public List<Answear> findAllByQuestionId(int question_id) {
        return jdbc.query("SELECT * FROM answear join \"user\" u on u.user_id = author_id WHERE question_id = ?", this::rowMapper, question_id);
    }

    public void vote(int vote, long id) {
        jdbc.update("UPDATE answear SET score = score + ? WHERE answear_id = ?", vote, id);
    }

    public void save(Answear answear) {
        jdbc.update("INSERT INTO answear (question_id, author_id, body) VALUES (?, ?, ?)", answear.getQuestion_id(), answear.getAuthor().getUser_id(), answear.getBody());
    }

    public void update(Answear answear) {
        jdbc.update("UPDATE answear SET body = ?, answear_updated_at = NOW() WHERE answear_id = ?", answear.getBody(), answear.getAuthor().getUser_id());
    }

    public void delete(long id) {
        jdbc.update("DELETE FROM answear WHERE answear_id = ?", id);
    }
}

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
        if (answear != null) {
            answear.setAuthor(BeanPropertyRowMapper.newInstance(User.class).mapRow(rs, rowNum));
            answear.setViewerVote(rs.getInt("vote"));
        }
        return answear;
    }

    public List<Answear> findAllByQuestionIdAndViewerIdOrderByScore(long question_id, long viewer_id) {
        String sql = "SELECT a.answear_id as answear_id, a.question_id, a.author_id as user_id, u.name, a.body, a.score, a.answear_created_at, a.answear_updated_at, av.vote FROM answear a join \"user\" u on u.user_id = a.author_id left join answear_vote av on a.answear_id = av.answear_id and av.user_id = ? WHERE question_id = ? order by a.score desc;";
        return jdbc.query(sql, this::rowMapper, viewer_id, question_id);
    }

    public void vote(long answear_id, long user_id, int vote) {
        Integer oldVote = null;
        try {
            oldVote = jdbc.queryForObject("SELECT vote FROM answear_vote WHERE user_id = ? and answear_id = ?",
                    Integer.class, user_id, answear_id);
        } catch (Exception ignored) {
        }

        if (oldVote == null) {
            jdbc.update("INSERT INTO answear_vote (answear_id, user_id, vote) VALUES (?, ?, ?)", answear_id, user_id,
                    vote);
        } else {
            if (oldVote == vote) {
                jdbc.update("DELETE FROM answear_vote WHERE user_id = ? and answear_id = ?", user_id, answear_id);
                vote *= -1;
            } else {
                jdbc.update("UPDATE answear_vote SET vote = ? WHERE user_id = ? and answear_id = ?", vote, user_id,
                        answear_id);
                vote *= 2;
            }
        }

        jdbc.update("UPDATE answear SET score = score + ? WHERE answear_id = ?", vote, answear_id);
    }

    public void save(Answear answear) {
        jdbc.update("INSERT INTO answear (question_id, author_id, body) VALUES (?, ?, ?)", answear.getQuestion_id(),
                answear.getAuthor().getUser_id(), answear.getBody());
    }

    public void update(Answear answear) {
        jdbc.update("UPDATE answear SET body = ?, answear_updated_at = NOW() WHERE answear_id = ?", answear.getBody(),
                answear.getAuthor().getUser_id());
    }

    public void delete(long id) {
        jdbc.update("DELETE FROM answear WHERE answear_id = ?", id);
    }
}

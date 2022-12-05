package com.stackti.server.Answear.Vote;

import com.stackti.server.Answear.Answear;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class AnswearVoteRepository {
    private final JdbcTemplate jdbc;

    public AnswearVoteRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public AnswearVote rowMapper(ResultSet rs, int rownNum) throws SQLException {
        AnswearVote answearVote = BeanPropertyRowMapper.newInstance(AnswearVote.class).mapRow(rs, rownNum);
        if (answearVote != null)
            answearVote.setAnswear(BeanPropertyRowMapper.newInstance(Answear.class).mapRow(rs, rownNum));
        return answearVote;
    }

    public AnswearVote find(long user_id, long answear_id) {
        return jdbc.queryForObject("SELECT * FROM answear_vote join \"user\" on \"user\".user_id = answear_vote.user_id WHERE answear_vote.user_id = ? and answear_id = ?", this::rowMapper, user_id, answear_id);
    }

    public void save(AnswearVote answearVote) {
        jdbc.update("INSERT INTO answear_vote (user_id, answear_id, vote) VALUES (?, ?, ?)", answearVote.getUser().getUser_id(), answearVote.getAnswear().getAnswear_id(), answearVote.getVote());
    }

    public void update(AnswearVote answearVote) {
        jdbc.update("UPDATE answear_vote SET vote = ? WHERE user_id = ? and answear_id = ?", answearVote.getVote(), answearVote.getUser().getUser_id(), answearVote.getAnswear().getAnswear_id());
    }

    public void delete(long user_id, long answear_id) {
        jdbc.update("DELETE FROM answear_vote WHERE user_id = ? and answear_id = ?", user_id, answear_id);
    }
}

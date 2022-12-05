package com.stackti.server.Question.Vote;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionVoteRepository {
    private final JdbcTemplate jdbc;

    public QuestionVoteRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<QuestionVote> findAllByUserId(int user_id) {
        return jdbc.query("SELECT * FROM question_vote WHERE user_id = ?", new BeanPropertyRowMapper<>(QuestionVote.class), user_id);
    }

    public void save(QuestionVote questionVote) {
        jdbc.update("INSERT INTO question_vote (user_id, question_id, vote) VALUES (?, ?, ?)", questionVote.getUser_id(), questionVote.getQuestion_id(), questionVote.getVote());
    }

    public void update(QuestionVote questionVote) {
        jdbc.update("UPDATE question_vote SET vote = ? WHERE user_id = ? and question_id = ?", questionVote.getVote(), questionVote.getUser_id(), questionVote.getQuestion_id());
    }

    public void delete(QuestionVote questionVote) {
        jdbc.update("DELETE FROM question_vote WHERE user_id = ? and question_id = ?", questionVote.getUser_id(), questionVote.getQuestion_id());
    }
}

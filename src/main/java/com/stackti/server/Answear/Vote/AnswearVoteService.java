package com.stackti.server.Answear.Vote;

import com.stackti.server.Answear.AnswearRepository;
import org.springframework.stereotype.Service;

@Service
public class AnswearVoteService {
    final AnswearVoteRepository answearVoteRepository;
    final AnswearRepository answearRepository;

    public AnswearVoteService(AnswearVoteRepository answearVoteRepository, AnswearRepository answearRepository) {
        this.answearVoteRepository = answearVoteRepository;
        this.answearRepository = answearRepository;
    }

    public AnswearVote find(long user_id, long answear_id) {
        return answearVoteRepository.find(user_id, answear_id);
    }

    public void vote(AnswearVote answearVote) {
        try {
            answearVoteRepository.save(answearVote);
        } catch (Exception e) {
            answearVoteRepository.update(answearVote);
        } finally {
            answearRepository.vote(answearVote.getVote(), answearVote.getAnswear().getAnswear_id());
        }
    }

    public void delete(AnswearVote answearVote) {
        answearRepository.vote(answearVote.getVote() * -1, answearVote.getAnswear().getAnswear_id());
        answearVoteRepository.delete(answearVote.getUser().user_id, answearVote.getAnswear().getAnswear_id());
    }
}

package com.stackti.server.Question.Vote;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionVoteService {
    private final QuestionVoteRepository questionVoteRepository;

    public QuestionVoteService(QuestionVoteRepository questionVoteRepository) {
        this.questionVoteRepository = questionVoteRepository;
    }

    public List<QuestionVote> findAllByUserId(int user_id) {
        return questionVoteRepository.findAllByUserId(user_id);
    }

    public void vote(QuestionVote questionVote) {
        try {
            questionVoteRepository.save(questionVote);
        } catch (Exception e) {
            questionVoteRepository.update(questionVote);
        }
    }

    public void delete(QuestionVote questionVote) {
        questionVoteRepository.delete(questionVote);
        // todo - update question vote count
    }
}

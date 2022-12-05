package com.stackti.server.Question;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> findAllSortedByDate() {
        return questionRepository.findAllSortedByDate();
    }

    public Question find(long id) {
        return questionRepository.find(id);
    }

    public void save(Question question) {
        questionRepository.save(question);
    }

    public void delete(long id) {
        questionRepository.delete(id);
    }
}

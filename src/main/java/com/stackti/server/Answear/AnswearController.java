package com.stackti.server.Answear;

import com.stackti.server.Question.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/answear")
public class AnswearController {
    @Autowired
    AnswearRepository repository;
    @Autowired
    QuestionRepository questionRepository;

    @PostMapping("/new")
    public String newAnswear(Answear answear) {
        repository.save(answear);
        questionRepository.updateAnswearCount(answear.getQuestion_id(), 1);
        return "redirect:/question/" + answear.getQuestion_id();
    }

    @GetMapping("/vote")
    public String upvote(@RequestParam Long answear_id, @RequestParam Long user_id, @RequestParam int vote) {
        repository.vote(answear_id, user_id, vote);
        return "redirect:/question/" + questionRepository.findQuestionIdByAnswearId(answear_id);
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long answear_id) {
        Long question_id = questionRepository.findQuestionIdByAnswearId(answear_id);
        questionRepository.updateAnswearCount(question_id, -1);
        questionRepository.verifyAndDeleteCorrectAnswear(question_id, answear_id);
        repository.delete(answear_id);
        return "redirect:/question/" + question_id;
    }
}

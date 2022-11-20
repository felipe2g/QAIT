package com.stackti.server.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    QuestionRepository repository;

    @GetMapping("questionNew")
    public String question(Model model) {
        model.addAttribute("question", new Question());
        return "question";
    }
    @GetMapping("home")
    public String allQuestion(Model model) {
        List<Question> list = repository.allQuestionsByDate();
        model.addAttribute("lista", list);
        return "index";
    }
    @PostMapping("questioNew")
    public String questioNew(Question question) {
        question.setData(new Date());
        repository.questionInsert(question);
        return "redirect:/questionNew";
    }
}

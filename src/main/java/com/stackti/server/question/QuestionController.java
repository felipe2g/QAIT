package com.stackti.server.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.stackti.server.answer.AnswerRepository;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    QuestionRepository repository;
    @Autowired
    AnswerRepository answer;
    
    @GetMapping("question-new")
    public String question(Model model) {
        model.addAttribute("question", new Question());
        return "question";
    }
    @GetMapping("/question")
    public String questionView(@RequestParam(value = "id", required = true)Integer cod, Model model) {
        Question qt = repository.questionById(cod);
        repository.updateVisitInQuestion(cod);
        model.addAttribute("q",qt);
        return "viewquestion";
    }
    @GetMapping("/")
    public String allQuestion(Model model) {
        List<Question> list = repository.allQuestionsByDate();
        model.addAttribute("lista", list);
        model.addAttribute("answer", answer);
        return "index";
    }
    @PostMapping("question-new")
    public String questioNew(Question question) {
        repository.questionInsert(question);
        return "redirect:/question-new";
    }
}

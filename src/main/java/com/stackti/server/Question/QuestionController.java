package com.stackti.server.Question;

import com.stackti.server.Answear.Answear;
import com.stackti.server.Answear.AnswearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionRepository repository;
    @Autowired
    AnswearRepository answer;

    @GetMapping("/{id}")
    public String questionView(@PathVariable Long id, Model model) {
        Answear newAnswear = new Answear();
        newAnswear.setQuestion_id(id);
        newAnswear.getAuthor().setUser_id(1L);

        model.addAttribute("question", repository.findById(id));
        model.addAttribute("answears", answer.findAllByQuestionIdAndViewerIdOrderByScore(id, 1L));
        model.addAttribute("newAnswear", newAnswear);
        repository.updateVisitCount(id);
        return "viewquestion";
    }

    @GetMapping("/new")
    public String question(Model model, HttpSession session) {
        Question question = new Question();
        question.getAuthor().setUser_id(1L);

        model.addAttribute("question", question);
        return "question";
    }

    @PostMapping("/new")
    public String questioNew(Question question) {
        repository.save(question);
        return "redirect:/";
    }
}

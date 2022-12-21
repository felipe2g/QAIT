package com.stackti.server.Question;

import com.stackti.server.Answear.Answear;
import com.stackti.server.Answear.AnswearRepository;
import com.stackti.server.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionRepository repository;
    @Autowired
    AnswearRepository answer;

    @GetMapping("/{id}")
    public String questionView(@PathVariable Long id, Model model, HttpSession session) {
        Answear newAnswear = new Answear();
        long user_id = ((User) session.getAttribute("user")).user_id;

        newAnswear.setQuestion_id(id);
        newAnswear.getAuthor().setUser_id(user_id);

        model.addAttribute("question", repository.findById(id));
        model.addAttribute("answears", answer.findAllByQuestionIdAndViewerIdOrderByScore(id, user_id));
        model.addAttribute("newAnswear", newAnswear);
        repository.updateVisitCount(id);
        return "viewquestion";
    }

    @GetMapping("/correct")
    public String correctAnswear(@RequestParam Long answear_id, @RequestParam Long question_id) {
        repository.updateCorrectAnswear(question_id, answear_id);
        return "redirect:/question/" + question_id;
    }

    @GetMapping("/new")
    public String question(Model model, HttpSession session) {
        Question question = new Question();
        User user = (User) session.getAttribute("user");

        question.getAuthor().setUser_id(user.getUser_id());
        model.addAttribute("question", question);
        return "question";
    }

    @PostMapping("/new")
    public String questioNew(Question question) {
        repository.save(question);
        return "redirect:/";
    }
}

package com.stackti.server;

import com.stackti.server.Question.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Index {
    @Autowired
    QuestionRepository repository;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("lista", repository.findAllSortedByDate());
        return "index";
    }
}

package com.stackti.server;

import com.stackti.server.Answear.AnswearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Post {
    @Autowired
    AnswearRepository answearRepository;

    @GetMapping("/post")
    public String index(Model model) {
        model.addAttribute("answears", answearRepository.findAllByQuestionIdAndViewerId(1, 2));
        return "index";
    }
}

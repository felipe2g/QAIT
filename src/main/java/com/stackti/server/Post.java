package com.stackti.server;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Post {
    @GetMapping("/post")
    public String index(Model model) {
        model.addAttribute("body", "lorem");
        model.addAttribute("user", "joao");
        model.addAttribute("rate", 13);
        model.addAttribute("vote", -1);
        return "index";
    }
}

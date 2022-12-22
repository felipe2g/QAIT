package com.stackti.server;

import com.stackti.server.Question.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@Controller
public class ServerApplication {
    @Autowired
    JdbcTemplate jdbc;
    @Autowired
    QuestionRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @GetMapping()
    public String index(@RequestParam(required = false) String q, Model model) {
        model.addAttribute("questions", repository.findAll(q));
        return "index";
    }
}

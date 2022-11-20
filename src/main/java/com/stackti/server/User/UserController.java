package com.stackti.server.User;

import com.stackti.server.Login.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/sign-up")
    public String getSignUp(Model model) {
        model.addAttribute("user", new User());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String postSignUp(User user) {
        userRepository.create(user);
        return "redirect:/login";
    }
}

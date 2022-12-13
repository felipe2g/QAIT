package com.stackti.server.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        // TODO: Criar regra para não criar usuários repetidos
        userRepository.create(user);
        return "redirect:/login";
    }

    @GetMapping("/user/{id}")
    public String getProfile(@PathVariable String id, Model model) {
        User user = userRepository.findById(Integer.parseInt(id));

        model.addAttribute(user);
        return "profile";
    }

    @GetMapping("/user/edit/{id}")
    public String getEditProfile(@PathVariable String id, Model model) {
        User user = userRepository.findById(Integer.parseInt(id));

        model.addAttribute(user);

        return "editProfile";
    }
}

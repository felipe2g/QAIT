package com.stackti.server.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/sign-up")
    public String getSignUp(Model model) {
        model.addAttribute("user", new User());
        return "sign-up";
    }

    @GetMapping("/sign-in")
    public String getSignIn(Model model) {
        model.addAttribute("user", new User());
        return "sign-in";
    }

    @PostMapping("/sign-up")
    public String postSignUp(User user, Model model) {
        if (!userRepository.create(user)) {
            model.addAttribute("error", true);
            return "sign-up";
        }

        return "redirect:/sign-in";
    }

    @PostMapping("/sign-in")
    public String postSignIn(User user, Model model, HttpSession session) {
        User userFound = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if (userFound == null) {
            model.addAttribute("error", true);
            return "sign-in";
        }

        session.setAttribute("user", userFound);
        return "redirect:/";
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

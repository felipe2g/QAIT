package com.stackti.server.Login;

import com.stackti.server.Authentication.Authentication;
import com.stackti.server.Authentication.AuthenticationRepository;
import com.stackti.server.User.User;
import com.stackti.server.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationRepository authenticationRepository;

    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute("login", new Login());
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(Login login) {
        //TODO: Caso o login seja antigo
        String authenticationId = loginRepository.authenticate(login);

        if(authenticationId.equals("")) {
            return "login";
        }

        return "redirect:/authentication/" + authenticationId;
    }

    @GetMapping("/authentication/{id}")
    public String getAuthentication(@PathVariable String id, Model model) {
        //TODO: Fazer caso que o authentication está errado
        Authentication authentication = authenticationRepository.findByHash(id);

        User user = userRepository.findById(authentication.user_id);

        model.addAttribute("user", user);
        model.addAttribute("authentication", authentication);

        return "authenticate";
    }
}

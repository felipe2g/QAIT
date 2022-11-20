package com.stackti.server.Login;

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

    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute("login", new Login());
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(Login login) {
        String authenticationId = loginRepository.authenticate(login);

        if(authenticationId.equals("")) {
            return "login";
        }

        return "redirect:/authentication/" + authenticationId;
    }

    // TODO: Criar rota de autenticação
    // Essa rota irá receber os dados vindos do post login
    // E irá salvar no localStorage do navegador
    @GetMapping("/authentication/{id}")
    public String getAuthentication(@PathVariable String id, Model model) {
        //TODO: Buscar as informações do login e salvar no localStorage
        return "index";
    }
}

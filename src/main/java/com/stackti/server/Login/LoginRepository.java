package com.stackti.server.Login;

import com.stackti.server.User.User;
import com.stackti.server.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.UUID;

@Repository
public class LoginRepository {
    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    UserRepository userRepository;

    public String authenticate(Login login) {
        User user = userRepository.findByEmail(login.email);

        if(Objects.equals(user.password, login.password)) {
            String authenticationId = this.saveAuthentication(user.user_id);

            return authenticationId;
        } else {
            //TODO: Melhorar este retorno
            return "";
        }
    }

    public String saveAuthentication(Long userId) {
        UUID authenticationId = UUID.randomUUID();
        String authenticationString = authenticationId.toString();

        jdbc.update("INSERT INTO authentication(user_id, hash) VALUES (?, ?)",
                userId, authenticationString);

        return authenticationString;
    }
}

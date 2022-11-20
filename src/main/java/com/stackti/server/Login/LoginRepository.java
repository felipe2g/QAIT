package com.stackti.server.Login;

import com.stackti.server.Authentication.Authentication;
import com.stackti.server.Authentication.AuthenticationRepository;
import com.stackti.server.User.User;
import com.stackti.server.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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

    @Autowired
    AuthenticationRepository authenticationRepository;

    public String authenticate(Login login) {
        User user = userRepository.findByEmail(login.email);

        if(Objects.equals(user.password, login.password)) {
            String authenticationId = authenticationRepository.saveAuthentication(user.user_id);

            return authenticationId;
        } else {
            //TODO: Melhorar este retorno
            return "";
        }
    }
}

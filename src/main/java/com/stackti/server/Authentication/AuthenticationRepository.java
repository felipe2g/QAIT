package com.stackti.server.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class AuthenticationRepository {

    @Autowired
    JdbcTemplate jdbc;

    public String saveAuthentication(Long userId) {
        UUID authenticationId = UUID.randomUUID();
        String authenticationString = authenticationId.toString();

        jdbc.update("INSERT INTO authentication(user_id, hash) VALUES (?, ?)",
                userId, authenticationString);

        return authenticationString;
    }

    public Authentication findByUserId(int user_id) {
        Authentication authentication = jdbc.queryForObject("SELECT * FROM authentication WHERE user_id = (?) ORDER BY created_at DESC",
                new BeanPropertyRowMapper<Authentication>(Authentication.class), user_id);

        return authentication;
    }

    public Authentication findByHash(String hash) {
        Authentication authentication = jdbc.queryForObject("SELECT * FROM authentication WHERE hash = (?)",
                new BeanPropertyRowMapper<Authentication>(Authentication.class), hash);

        return authentication;
    }
}

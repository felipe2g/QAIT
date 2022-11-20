package com.stackti.server.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    @Autowired
    JdbcTemplate jdbc;

    public void create(User user) {
        jdbc.update("INSERT INTO \"user\"(first_name, last_name, email, password) VALUES (?,?,?,?)",
                user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM \"user\" WHERE email = ?";

        User user = jdbc.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), email);

        return user;
    }
}

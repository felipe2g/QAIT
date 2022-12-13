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
        // TODO: Criar regra para não criar usuários repetidos
        jdbc.update("INSERT INTO \"user\"(first_name, last_name, email, password) VALUES (?,?,?,?)",
                user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM \"user\" WHERE email = ?";

        User user = jdbc.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), email);

        return user;
    }

    public User findById(int id) {
        String sql = "SELECT * FROM \"user\" WHERE user_id = ?";

        User user = jdbc.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);

        return user;
    }
}

package com.stackti.server.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository {
    @Autowired
    JdbcTemplate jdbc;

    public User rowMapper(ResultSet rs, int rowNum) throws SQLException {
        return BeanPropertyRowMapper.newInstance(User.class).mapRow(rs, rowNum);
    }

    public User findById(long id) {
        return jdbc.queryForObject("SELECT * FROM \"user\" WHERE user_id = ?", this::rowMapper, id);
    }

    public User findByEmailAndPassword(String email, String password) {
        try {
            return jdbc.queryForObject("SELECT * FROM \"user\" WHERE email = ? AND password = ?", this::rowMapper, email, password);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean create(User user) {
        try {
            jdbc.update("INSERT INTO \"user\" (name, email, password) VALUES (?, ?, ?);", user.getName(), user.getEmail(), user.getPassword());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

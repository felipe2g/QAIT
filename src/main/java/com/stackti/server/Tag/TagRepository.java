package com.stackti.server.Tag;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagRepository {
    private final JdbcTemplate jdbc;

    public TagRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Tag> findAllByQuestionId(int question_id) {
        return jdbc.query("SELECT tag.tag_id, tag.name FROM tag INNER JOIN question_tag ON question_tag.tag_id = tag.tag_id WHERE question_tag.question_id = ?", new BeanPropertyRowMapper<>(Tag.class), question_id);
    }

    public List<Tag> findAll() {
        return jdbc.query("SELECT * FROM tag", BeanPropertyRowMapper.newInstance(Tag.class));
    }

    public void save(Tag tag) {
        jdbc.update("INSERT INTO tag (name) VALUES (?)", tag.getName());
    }

    public void delete(int id) {
        jdbc.update("DELETE FROM tag WHERE tag_id = ?", id);
    }
}

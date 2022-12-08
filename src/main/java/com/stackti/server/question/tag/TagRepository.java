package com.stackti.server.question.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TagRepository {
    @Autowired
    JdbcTemplate db;

    public void insertTags(int codQuestion, int tag[]) {
        for (int i=0; i<tag.length; i++) {
            db.update("insert into question_tag (question_id,tag_id) values (?, ?);", codQuestion, tag[i]);
        }
    }
}

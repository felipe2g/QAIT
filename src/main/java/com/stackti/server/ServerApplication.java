package com.stackti.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ServerApplication {
	final JdbcTemplate jdbc;

	public ServerApplication(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@GetMapping("/resetdb")
	public String resetdb() {
		try {
			jdbc.execute("""
					 DO $$ DECLARE
					   r RECORD;
					 BEGIN
					   FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = current_schema()) LOOP
						 EXECUTE 'DROP TABLE ' || quote_ident(r.tablename) || ' CASCADE';
					   END LOOP;
					 END $$;
					     
					DROP TABLE IF EXISTS "user" CASCADE;
					CREATE TABLE "user"
					(
					    user_id    SERIAL,
					    first_name varchar(20),
					    last_name  varchar(20),
					    email      varchar(50) UNIQUE,
					    password   varchar(30),
					    role       int       DEFAULT 0,
					    job_title  varchar(30),
					    rate       int       DEFAULT 5,
					    created_us TIMESTAMP DEFAULT NOW(),
					    updated_us TIMESTAMP DEFAULT NOW(),
					    PRIMARY KEY (user_id)
					);

					DROP TABLE IF EXISTS tag CASCADE;
					CREATE TABLE tag
					(
					    tag_id SERIAL,
					    name   varchar(20),
					    PRIMARY KEY (tag_id)
					);

					DROP TABLE IF EXISTS question CASCADE;
					CREATE TABLE question
					(
					    question_id                 SERIAL,
					    author_id                   int,
					    correct_answear_id          int,
					    title                       varchar(120),
					    body                        text,
					    view_count                  int       DEFAULT 0,
					    score                       int       DEFAULT 0,
					    answear_count               int       DEFAULT 0,
					    question_created_at         TIMESTAMP DEFAULT NOW(),
					    question_updated_at          TIMESTAMP DEFAULT NOW(),
					    PRIMARY KEY (question_id),
					    CONSTRAINT fk_user FOREIGN KEY (author_id) REFERENCES "user" (user_id)
					);

					DROP TABLE IF EXISTS question_tag CASCADE;
					CREATE TABLE question_tag
					(
					    question_id int,
					    tag_id      int,
					    PRIMARY KEY (question_id, tag_id),
					    CONSTRAINT fk_question FOREIGN KEY (question_id) REFERENCES question (question_id),
					    CONSTRAINT fk_tag FOREIGN KEY (tag_id) REFERENCES tag (tag_id)
					);

					DROP TABLE IF EXISTS question_vote CASCADE;
					CREATE TABLE question_vote
					(
					    question_id int,
					    user_id     int,
					    vote        int,
					    PRIMARY KEY (question_id, user_id),
					    CONSTRAINT fk_question_vote_question FOREIGN KEY (question_id) REFERENCES question (question_id),
					    CONSTRAINT fk_question_vote_user FOREIGN KEY (user_id) REFERENCES "user" (user_id)
					);

					DROP TABLE IF EXISTS answear CASCADE;
					CREATE TABLE answear
					(
					    answear_id          SERIAL,
					    question_id         int,
					    author_id           int,
					    body                text,
					    score               int       DEFAULT 0,
					    answear_created_at  TIMESTAMP DEFAULT NOW(),
					    answear_updated_at  TIMESTAMP DEFAULT NOW(),
					    PRIMARY KEY (answear_id),
					    CONSTRAINT fk_user FOREIGN KEY (author_id) REFERENCES "user" (user_id),
					    CONSTRAINT fk_question FOREIGN KEY (question_id) REFERENCES question (question_id)
					);

					ALTER TABLE question
					    ADD CONSTRAINT fk_correct_answear
					        FOREIGN KEY (correct_answear_id)
					            REFERENCES answear (answear_id);

					DROP TABLE IF EXISTS answear_vote CASCADE;
					CREATE TABLE answear_vote
					(
					    answear_id int,
					    user_id    int,
					    vote       int,
					    PRIMARY KEY (answear_id, user_id),
					    CONSTRAINT fk_answear_vote_answear FOREIGN KEY (answear_id) REFERENCES answear (answear_id),
					    CONSTRAINT fk_answear_vote_user FOREIGN KEY (user_id) REFERENCES "user" (user_id)
					);
										
					insert into "user" (first_name, last_name, email, password, job_title)
					values ('John', 'Doe', 'jhon@gmail.com', '123', 'Developer'),
						   ('Jane', 'Doe', 'jane@gmail.com', '123', 'Developer'),
						   ('Jack', 'Doe', 'jack@gmail.com', '123', 'Developer');
										
					insert into tag (name)
					values ('postgres');
										
					insert into question (author_id, title, body)
					values (1, 'How to create a table in postgresql?', 'I want to create a table in postgresql, but I don''t know how to do it.');
										
					insert into question_tag (question_id, tag_id)
					values (1, 1);
										
					insert into answear (question_id, author_id, body)
					values (1, 2, 'You can create a table like this: CREATE TABLE table_name (column_name data_type);'),
						   (1, 3, 'idk');
										
					insert into question_vote (question_id, user_id, vote)
					values (1, 2, 1);
										
					insert into answear_vote (answear_id, user_id, vote)
					values (1, 3, 1);""");
			return "Success";
		} catch (Exception e) {
			return "Failed: " + e.getMessage();
		}
	}
}

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
					    question_id       SERIAL,
					    title             varchar(120),
					    body              text,
					    view_count        int       DEFAULT 0,
					    score             int       DEFAULT 0,
					    created_at        TIMESTAMP DEFAULT NOW(),
					    updated_at        TIMESTAMP DEFAULT NOW(),
					    author_id         int,
					    correct_answer_id int,
					    PRIMARY KEY (question_id),
					    CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES "user" (user_id)
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

					DROP TABLE IF EXISTS answer CASCADE;
					CREATE TABLE answer
					(
					    answer_id   SERIAL,
					    question_id int,
					    body        text,
					    score       int,
					    created_at  TIMESTAMP DEFAULT NOW(),
					    updated_at  TIMESTAMP DEFAULT NOW(),
					    author_id   int,
					    PRIMARY KEY (answer_id),
					    CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES "user" (user_id),
					    CONSTRAINT fk_question FOREIGN KEY (question_id) REFERENCES question (question_id)
					);

					ALTER TABLE question
					    DROP CONSTRAINT IF EXISTS fk_correct_answer;
					ALTER TABLE question
					    ADD CONSTRAINT fk_correct_answer
					        FOREIGN KEY (correct_answer_id)
					            REFERENCES answer (answer_id);

					DROP TABLE IF EXISTS answer_vote CASCADE;
					CREATE TABLE answer_vote
					(
					    answer_id int,
					    user_id   int,
					    vote      int,
					    PRIMARY KEY (answer_id, user_id),
					    CONSTRAINT fk_answer_vote_answer FOREIGN KEY (answer_id) REFERENCES answer (answer_id),
					    CONSTRAINT fk_answer_vote_user FOREIGN KEY (user_id) REFERENCES "user" (user_id)
					);""");
			return "Success";
		} catch (Exception e) {
			return "Failed: " + e.getMessage();
		}
	}
}

package com.stackti.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class ServerApplication implements CommandLineRunner {

	@Autowired
	JdbcTemplate jdbc;

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Cria banco de dados Usuário

		jdbc.update("alter table question drop constraint fk_correct_answer;");

		jdbc.execute("DROP TABLE IF EXISTS tags_at_question");
		jdbc.execute("DROP TABLE IF EXISTS answer");
		jdbc.execute("DROP TABLE IF EXISTS question");
		jdbc.execute("DROP TABLE IF EXISTS tag");
		jdbc.execute("drop table \"user\";");


		jdbc.execute("""
            CREATE TABLE "user" (
            user_id serial,
            firstName varchar(20),
            last_name varchar(20),
            email varchar(50),
            password varchar(30),
            role int,
            job_title varchar(30),
            rate int,
            created_at Date,
            updated_at Date,
            PRIMARY KEY(user_id));
        """);

		// Cria banco de dados de tag
		jdbc.execute("""
            CREATE TABLE tag(
            tag_id serial,
            name varchar(20),
            PRIMARY KEY(tag_id));
        """);

		// Cria banco de dados questão
		jdbc.execute("""
            CREATE TABLE question(
            question_id serial,
            title varchar(120),
            question_description varchar(255),
            visits int,
            question_data Date,
            rate int,
            created_at Date,
            updated_at Date,
            author_id int,
            correct_answer_id int,
            PRIMARY KEY(question_id),
            CONSTRAINT fk_author FOREIGN KEY(author_id) REFERENCES "user" (user_id)
            );
        """);

		// Cria banco de dados resposta
		jdbc.execute("""
  			CREATE TABLE answer(
  				answer_id serial,
  				question_id int,
  				answer_description varchar (255),
  				data Date,
  				author_id int,
  				rate int,
  				PRIMARY KEY(answer_id),
  				CONSTRAINT fk_author FOREIGN KEY(author_id) REFERENCES "user" (user_id),
  				CONSTRAINT fk_question FOREIGN KEY(question_id) REFERENCES question(question_id)
  			);
		""");

		jdbc.execute("""
  			CREATE TABLE tags_at_question(
  				question_id serial,
  				tag_id int,
  				PRIMARY KEY(question_id, tag_id),
  				CONSTRAINT fk_question FOREIGN KEY(question_id) REFERENCES question(question_id),
  				CONSTRAINT fk_tag FOREIGN KEY(tag_id) REFERENCES tag(tag_id)
  			);
		""");

		jdbc.execute("""
			ALTER TABLE question
				ADD CONSTRAINT fk_correct_answer
				FOREIGN KEY(correct_answer_id)
				REFERENCES answer(answer_id);
		""");

		jdbc.update(""" 
			insert into "user" ( firstname, last_name, email, password, role, job_title, rate, created_at, updated_at)
   			values ('SAMUEL','LUCAS', '2003SAMUELLUCAS','1234',0,'DEV',0,'2022-11-19',null);
		""");
	}
}

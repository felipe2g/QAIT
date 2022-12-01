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
		jdbc.execute("DROP TABLE IF EXISTS \"user\" CASCADE");
		System.out.println("SUCCESS: Table user DELETED!");
		jdbc.execute("""
            CREATE TABLE "user" (
            user_id SERIAL,
            first_name varchar(20),
            last_name varchar(20),
            email varchar(50) UNIQUE,
            password varchar(30),
            role int DEFAULT 0,
            job_title varchar(30),
            rate int DEFAULT 5,
            created_at TIMESTAMP DEFAULT NOW(),
            updated_at TIMESTAMP DEFAULT NOW(),
            PRIMARY KEY(user_id));
        """);
		System.out.println("SUCCESS: Table user CREATED!");

		// Cria banco de dados de tag
		jdbc.execute("DROP TABLE IF EXISTS tag CASCADE");
		System.out.println("SUCCESS: Table tag DELETED!");
		jdbc.execute("""
            CREATE TABLE tag(
            tag_id SERIAL,
            name varchar(20),
            PRIMARY KEY(tag_id));
        """);
		System.out.println("SUCCESS: Table tag CREATED!");

		// Cria banco de dados questão
		jdbc.execute("DROP TABLE IF EXISTS question CASCADE");
		System.out.println("SUCCESS: Table question DELETED!");
		jdbc.execute("""
            CREATE TABLE question(
            question_id SERIAL,
            title varchar(120),
            question_description varchar,
            visits int,
            question_date Date,
            rate int,
            created_at TIMESTAMP DEFAULT NOW(),
            updated_at TIMESTAMP DEFAULT NOW(),
            author_id int,
            correct_answer_id int,
            PRIMARY KEY(question_id),
            CONSTRAINT fk_author FOREIGN KEY(author_id) REFERENCES "user" (user_id)
            );
        """);
		System.out.println("SUCCESS: Table question CREATED!");

		// Cria banco de dados resposta
		jdbc.execute("DROP TABLE IF EXISTS answer CASCADE");
		System.out.println("SUCCESS: Table answer DELETED!");
		jdbc.execute("""
  			CREATE TABLE answer(
  				answer_id SERIAL,
  				question_id int,
  				answer_description varchar (255),
  				created_at TIMESTAMP DEFAULT NOW(),
  				updated_at TIMESTAMP DEFAULT NOW(),
  				author_id int,
  				rate int,
  				PRIMARY KEY(answer_id),
  				CONSTRAINT fk_author FOREIGN KEY(author_id) REFERENCES "user" (user_id),
  				CONSTRAINT fk_question FOREIGN KEY(question_id) REFERENCES question(question_id)
  			);
		""");
		System.out.println("SUCCESS: Table answer CREATED!");

		jdbc.execute("DROP TABLE IF EXISTS question_tag CASCADE");
		System.out.println("SUCCESS: Table question_tag DELETED!");
		jdbc.execute("""
  			CREATE TABLE question_tag(
  				question_id int,
  				tag_id int,
  				PRIMARY KEY(question_id, tag_id),
  				CONSTRAINT fk_question FOREIGN KEY(question_id) REFERENCES question(question_id),
  				CONSTRAINT fk_tag FOREIGN KEY(tag_id) REFERENCES tag(tag_id)
  			);
		""");
		System.out.println("SUCCESS: Table question_tag CREATED!");

		jdbc.execute("""
			ALTER TABLE question
			DROP CONSTRAINT IF EXISTS fk_correct_answer;
		""");
		System.out.println("SUCCESS: Table Constraint question(fk_correct_answer) DELETED!");

		jdbc.execute("""
			ALTER TABLE question
				ADD CONSTRAINT fk_correct_answer
				FOREIGN KEY(correct_answer_id)
				REFERENCES answer(answer_id);
		""");
		System.out.println("SUCCESS: Constraint question(fk_correct_answer) CREATED!");

		// Default Data
		jdbc.update(""" 
			insert into "user" (first_name, last_name, email, password, role, job_title)
   			values ('Admin', 'Foo', 'admin@stackti.com','admin', 1, 'Admin');
		""");
		System.out.println("SUCCESS: User admin CREATED!");
	}
}

CREATE TABLE user
(
    user_id    int primary key auto_increment,
    name       varchar(50),
    email      varchar(50) UNIQUE,
    password   varchar(30),
    job_title  varchar(30),
    rate       int       DEFAULT 5,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE question
(
    question_id         int primary key auto_increment,
    author_id           int,
    correct_answear_id  int,
    title               varchar(120),
    body                text,
    view_count          int       DEFAULT 0,
    score               int       DEFAULT 0,
    answear_count       int       DEFAULT 0,
    question_created_at TIMESTAMP DEFAULT NOW(),
    question_updated_at TIMESTAMP DEFAULT NOW(),
    CONSTRAINT fk_user FOREIGN KEY (author_id) REFERENCES user (user_id)
);

CREATE TABLE question_vote
(
    question_id int,
    user_id     int,
    vote        int,
    PRIMARY KEY (question_id, user_id),
    CONSTRAINT fk_question_vote_question FOREIGN KEY (question_id) REFERENCES question (question_id),
    CONSTRAINT fk_question_vote_user FOREIGN KEY (user_id) REFERENCES user (user_id)
);

CREATE TABLE answear
(
    answear_id         int primary key auto_increment,
    question_id        int,
    author_id          int,
    body               text,
    score              int       DEFAULT 0,
    answear_created_at TIMESTAMP DEFAULT NOW(),
    answear_updated_at TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY (author_id) REFERENCES user (user_id),
    FOREIGN KEY (question_id) REFERENCES question (question_id)
);

ALTER TABLE question
    ADD CONSTRAINT fk_correct_answear
        FOREIGN KEY (correct_answear_id)
            REFERENCES answear (answear_id);

CREATE TABLE answear_vote
(
    answear_id int,
    user_id    int,
    vote       int,
    PRIMARY KEY (answear_id, user_id),
    CONSTRAINT fk_answear_vote_answear FOREIGN KEY (answear_id) REFERENCES answear (answear_id),
    CONSTRAINT fk_answear_vote_user FOREIGN KEY (user_id) REFERENCES user (user_id)
);

insert into user (name, email, password)
values ('João Vitor', 'joao@gmail.com', '123');

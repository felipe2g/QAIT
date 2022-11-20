package com.stackti.server.question;

import com.stackti.server.User.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Question {
    private int question_id;
    private String title;
    private String question_description;
    private int visits;
    private Date data;
    private int rate;
    private Date created_at;
    private Date updated_at;
    private User author;
    private int correct_answer_id;

}



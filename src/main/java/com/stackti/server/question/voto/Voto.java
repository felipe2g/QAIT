package com.stackti.server.question.voto;

import com.stackti.server.User.User;
import com.stackti.server.question.Question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Voto {
    private Question question;
    private User author;
    private int vote;
}

package com.stackti.server.Answear.Vote;

import com.stackti.server.Answear.Answear;
import com.stackti.server.User.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswearVote {
    private User user;
    private Answear answear;
    private int vote;
}

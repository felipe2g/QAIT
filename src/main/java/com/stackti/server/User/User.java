package com.stackti.server.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    public Long user_id;
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public int role;
    public String jobTitle;
    public int points;
    public int reputation;
}

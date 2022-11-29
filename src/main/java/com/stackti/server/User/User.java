package com.stackti.server.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

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
    public int rate;
    public Timestamp createAt;
    public Timestamp updateAt;
}

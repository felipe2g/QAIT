package com.stackti.server.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    public Long user_id;
    public String name;
    public String email;
    public String password;
    public int rate;
    public Timestamp createdAt;
    public Timestamp updatedAt;
}

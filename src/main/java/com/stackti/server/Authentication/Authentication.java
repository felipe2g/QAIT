package com.stackti.server.Authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authentication {
    public Long authentication_id;
    public int user_id;
    public String hash;
    public Timestamp created_at;
}

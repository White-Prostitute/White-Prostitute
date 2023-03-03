package com.example.stocksystem.entity;

import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

@Data
public class User {

    @Id
    Integer userId;
    String userName;
    String userPassword;
    String userPhoneNum;

}

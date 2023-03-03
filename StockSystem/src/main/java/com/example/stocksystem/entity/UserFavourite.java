package com.example.stocksystem.entity;

import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

@Data
public class UserFavourite {

    @Id
    private Integer stockId;
    private Integer userId;

    public UserFavourite(Integer stockId, Integer userId) {
        this.stockId = stockId;
        this.userId = userId;
    }
}

package com.example.stocksystem.entity;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.Data;

@Data
public class UserFavourite {

    private Integer stockId;
    private Integer userId;

    public UserFavourite(Integer stockId, Integer userId) {
        this.stockId = stockId;
        this.userId = userId;
    }
}

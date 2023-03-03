package com.example.stocksystem.entity;

import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

@Data
public class Stock {

    @Id
    int stockId;
    String stockName;
    String stockType;
}

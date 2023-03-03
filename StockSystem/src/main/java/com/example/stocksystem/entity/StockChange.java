package com.example.stocksystem.entity;

import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

import java.util.Date;

@Data
public class StockChange {

    @Id
    int stockId;
    Date date;
    float open;
    float close;
    float high;
    float low;
    int volume;
    float percent;

}

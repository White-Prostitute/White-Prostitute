package com.example.stocksystem.entity;

import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

import java.util.Date;

@Data
public class StockChange {

    @Id
    int stockId;
    Date date;
    float priceOpen;
    float priceClose;
    float priceHigh;
    float priceLow;
    int volume;

}

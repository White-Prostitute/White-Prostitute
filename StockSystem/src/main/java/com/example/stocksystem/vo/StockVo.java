package com.example.stocksystem.vo;

import lombok.Data;

import java.util.Date;

@Data
public class StockVo {

    private Integer stockId;

    private Date date;

    private String stockName;

    private String stockType;

    private Float priceHigh;

    private Float priceLow;

    private Float priceOpen;

    private Float priceClose;

    private Integer volume;

}

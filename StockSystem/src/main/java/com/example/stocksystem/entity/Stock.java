package com.example.stocksystem.entity;

public class Stock {

    int stockId;
    String stockName;
    String stockType;

    public Stock(int stockId, String stockName, String stockType){
        this.stockId = stockId;
        this.stockName = stockName;
        this.stockType = stockType;
    }

    public Stock(){

    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }
}

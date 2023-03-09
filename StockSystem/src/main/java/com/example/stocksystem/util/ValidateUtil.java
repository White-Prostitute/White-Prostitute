package com.example.stocksystem.util;

import com.example.stocksystem.entity.StockChange;

public class ValidateUtil{

    //检验股票交易数据是否合理
    public static boolean validateStockChange(StockChange change){
        return (change.getStockId() > 0)&&
                (change.getPriceClose() > 0)&&
                (change.getPriceHigh() > 0)&&
                (change.getPriceLow() > 0)&&
                (change.getPriceOpen() > 0);
    }

}

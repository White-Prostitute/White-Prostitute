package com.example.stocksystem.dao;


import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface StockDao {

    @Select("select stock.*,stock_change.* from stock inner join stock_change on stock.stock_id=stock_change.stock_id")
    List<Map<String, String>> getStockInfo();

    @MapKey("list")
    List<Map<String, Object>> getStockInfoByCondition(@Param("stock_id") int stock_id, @Param("stock_name") String stock_name);


}

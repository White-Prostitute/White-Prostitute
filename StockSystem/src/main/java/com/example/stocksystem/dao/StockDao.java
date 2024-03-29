package com.example.stocksystem.dao;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.stocksystem.entity.StockChange;
import com.example.stocksystem.entity.UserFavourite;
import com.example.stocksystem.vo.StockVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StockDao extends BaseMapper<StockChange> {

    @MapKey("list")
    IPage<StockVo> getStockInfoByCondition(IPage<StockVo> page/*@Param("stock_id") int stock_id,
                                                      @Param("stock_name") String stock_name,
                                                      @Param("page_index") int pageIndex,
                                                      @Param("page_size") int pageSize*/);


    @Select("select * from stock join stock_change_update on stock.stock_id = stock_change_update.stock_id "+
        "${ew.customSqlSegment}"
    )
    IPage<StockVo> findStockInfo(IPage<StockVo> page, @Param("ew") Wrapper wrapper);

    @Select("select price_high from stock_change where stock_id = #{stock_id}")
    List<Float> getHighList(int stock_id);

    @Select("select * from stock join stock_change on stock.stock_id = stock_change.stock_id " +
    "${ew.customSqlSegment}")
    List<StockVo> getOneStockInfo(@Param("ew") Wrapper<StockVo> wrapper);

    @Insert("insert into stock_change values(#{stockId}, #{date}, #{priceHigh}, #{priceLow}, #{priceOpen}, " +
            "#{priceClose}, #{volume})")
    int addOneStockInfo(StockChange change);

    @Update("update stock_change_update set date = #{date}, price_high=#{priceHigh}, price_low=#{priceLow},"+
    "price_open=#{priceOpen}, price_close=#{priceClose}, volume=#{volume} where stock_id = #{stockId}")
    void updateStockInfo(StockChange change);

    @Select("select * from stock_change where stock_id = #{stock_id}")
    IPage<StockChange> getHistoryRecord(IPage<StockChange> page, Integer stock_id);

    @Select("select * from stock_change_update where stock_id = #{stock_id}")
    StockChange getUpdateRecord(Integer stock_id);

    @Select("select * from stock join stock_change_update on stock.stock_id = stock_change_update.stock_id order by volume DESC")
    IPage<StockVo> getTop(IPage<StockVo> page);

    @Select("select * from stock join stock_change_update on stock.stock_id = stock_change_update.stock_id where stock.stock_id=#{stock_id}")
    StockVo getOneInfoById(Integer stock_id);

    @Select("select stock_name from stock where stock_name like '%${name}%'")
    List<String> getNameList(String name);

    @Select("select * from user_favourite where user_id = #{user_id} and stock_id=#{stock_id}")
    UserFavourite checkFavourite(@Param("user_id") int user_id, @Param("stock_id") int stock_id);
}

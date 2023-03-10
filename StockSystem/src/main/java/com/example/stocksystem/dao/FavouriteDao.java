package com.example.stocksystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.stocksystem.entity.UserFavourite;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FavouriteDao extends BaseMapper<UserFavourite> {

    @Delete("delete from user_favourite where stock_id = #{stock_id} and user_id = #{user_id}")
    void cancelFavourite(@Param("stock_id") Integer stock_id, @Param("user_id") Integer user_id);

}

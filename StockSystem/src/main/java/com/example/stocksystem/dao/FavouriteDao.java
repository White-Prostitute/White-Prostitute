package com.example.stocksystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.stocksystem.entity.UserFavourite;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FavouriteDao extends BaseMapper<UserFavourite> {
}

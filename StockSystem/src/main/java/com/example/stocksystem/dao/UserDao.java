package com.example.stocksystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.stocksystem.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {
}

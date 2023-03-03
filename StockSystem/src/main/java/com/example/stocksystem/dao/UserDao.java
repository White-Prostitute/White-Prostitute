package com.example.stocksystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.stocksystem.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao extends BaseMapper<User> {

    @Select("select * from user where user_id = #{user_id}")
    User getOne(Integer user_id);

}

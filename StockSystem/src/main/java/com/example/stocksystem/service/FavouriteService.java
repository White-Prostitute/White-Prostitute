package com.example.stocksystem.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.stocksystem.dao.FavouriteDao;
import com.example.stocksystem.entity.UserFavourite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FavouriteService {

    @Autowired
    FavouriteDao dao;

    public void favourite(Integer user_id, Integer stock_id, Integer type){
        if(type == 1){
            dao.insert(new UserFavourite(stock_id, user_id));
        }else if(type == 0){
            System.out.println("删除操作"+ user_id + "  " + stock_id);
            dao.cancelFavourite(stock_id, user_id);
        }
    }

    public List<Integer> list(Integer user_id){
        QueryWrapper<UserFavourite> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", user_id);
        wrapper.select("stock_id");
        List<Object> listObj = dao.selectObjs(wrapper);
        Stream<Object> stream = listObj.stream();
        List<Integer> list = new ArrayList<>();
        stream.forEach(o -> {
            list.add((Integer)o );
        });
        return list;
    }
}

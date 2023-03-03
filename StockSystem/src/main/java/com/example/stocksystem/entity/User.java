package com.example.stocksystem.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class User {

    @TableId
    Integer userId;
    String userName;
    String userPassword;
    String userPhoneNum;

}

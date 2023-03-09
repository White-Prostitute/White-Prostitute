package com.example.stocksystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.stocksystem.dao.UserDao;
import com.example.stocksystem.entity.User;
import com.example.stocksystem.service.UserService;
import com.example.stocksystem.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@Slf4j
@RequestMapping("/login")
@CrossOrigin
public class login {

    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    /**
     * 登录
     * @param request
     * @param user
     * @return
     */
    @PostMapping
    public Response login(HttpServletRequest request, @RequestBody User user, HttpServletResponse rp){

        String password = user.getUserPassword();
        // 创建查询条件
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserPassword, user.getUserPassword());

        User one = userService.getOne(queryWrapper);

        log.info(String.valueOf(user.getUserPassword()));
        log.info(String.valueOf(user.getUserId()));

        System.out.println(user);

        Response<String> response = new Response<>();

        // 如果用户 id 不存在
        if(one == null){
            response.setCode(Response.SERVER_EXCEPTION);
            response.setMsg("用户 id 不存在！");
            response.setData("failure!");
            return response;
        }

        // 如果密码错误
        if(!one.getUserPassword().equals(user.getUserPassword())){
            response.setCode(Response.PARA_MISTAKE);
            response.setMsg("密码错误！");
            response.setData("failure!");
            return  response;
        }

        // 存入 session
        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute("flag", 1);
        System.out.println("登录中 " + user);

        // 正确
        response.setCode(Response.OK);
        response.setMsg("登录成功！");
        response.setData("success!");
        return response;
    }
}
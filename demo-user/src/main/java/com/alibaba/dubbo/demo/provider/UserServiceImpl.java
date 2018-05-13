package com.alibaba.dubbo.demo.provider;

import com.alibaba.dubbo.demo.User;
import com.alibaba.dubbo.demo.UserService;

public class UserServiceImpl implements UserService{

    @Override
    public User find(int userId) {
        User user=new User();
        user.setAge(20);
        user.setName("zhangSan");
        user.setUserId(userId);
        System.out.println("UserServiceImpl");
        return user;
    }
}

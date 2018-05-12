package com.alibaba.dubbo.demo.provider;

import com.alibaba.dubbo.demo.Order;
import com.alibaba.dubbo.demo.OrderService;
import com.alibaba.dubbo.demo.User;
import com.alibaba.dubbo.demo.UserService;

public class OrderServiceImpl implements OrderService {
    private UserService userService;
    @Override
    public Order find(int orderId, int userId) {
        User user=userService.find(userId);
        Order order=new Order();
        order.setMemony(100);
        order.setOrderId(orderId);
        order.setUser(user);
        return order;
    }
    public void setUserService(UserService userService){
        this.userService=userService;
    }

}

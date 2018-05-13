package com.alibaba.dubbo.demo.provider;

import com.alibaba.dubbo.demo.*;

public class OrderServiceImpl implements OrderService {
    private UserService userService;
    private AddressService addressService;
    @Override
    public Order find(int orderId, int userId) {
        System.out.println("OrderServiceImpl");
        User user=userService.find(userId);
        Address adress=addressService.getAddress();
        Order order=new Order();
        order.setMemony(100);
        order.setOrderId(orderId);
        order.setUser(user);
        order.setAddress(adress);
        return order;
    }
    public void setUserService(UserService userService){
        this.userService=userService;
    }
    public void setAddressService(AddressService addressService){
        this.addressService=addressService;
    }
}

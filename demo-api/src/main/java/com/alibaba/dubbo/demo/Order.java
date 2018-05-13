package com.alibaba.dubbo.demo;

import java.io.Serializable;

public class Order implements Serializable {
    private int orderId;
    private double memony;
    private User user;
    private Address address;
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getMemony() {
        return memony;
    }

    public void setMemony(double memony) {
        this.memony = memony;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", memony=" + memony +
                ", user=" + user +
                ", address=" + address +
                '}';
    }
}

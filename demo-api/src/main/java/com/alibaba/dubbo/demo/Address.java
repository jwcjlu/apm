package com.alibaba.dubbo.demo;

public class Address {
    private String  name;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Address{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}

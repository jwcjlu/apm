package com.alibaba.dubbo.demo.provider;

import com.alibaba.dubbo.demo.Address;
import com.alibaba.dubbo.demo.AddressService;

public class AddressServiceImpl implements AddressService {
    @Override
    public Address getAddress() {
        Address address=new Address();
        address.setId(100);
        address.setName("guangdong-shenzhou-baoanr");
        return address;
    }
}

package com.jwcjlu.apm.common.buffer;

import java.util.concurrent.atomic.AtomicInteger;

public class Buffer <T>{
   private final Object[]data;
    private  AtomicInteger index;
    public Buffer(int bufferSize){
        data=new Object[bufferSize];
    }

}


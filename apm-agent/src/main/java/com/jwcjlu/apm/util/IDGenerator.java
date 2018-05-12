package com.jwcjlu.apm.util;



public enum IDGenerator {
    INSTSNCE;
    public long generateId(){
    return  nextSeq()+currentThreadId();
    }
    private long currentThreadId(){
        return  Thread.currentThread().getId();
    }
    private long nextSeq() {
        return timestamp() * 10000 ;
    }
    private long timestamp() {
        return  System.currentTimeMillis();


    }

}

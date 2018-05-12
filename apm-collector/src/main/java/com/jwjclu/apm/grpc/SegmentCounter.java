package com.jwjclu.apm.grpc;

import java.util.concurrent.atomic.AtomicLong;

public enum SegmentCounter {
    INSTANCE;
    static AtomicLong  count=new AtomicLong();
    public long incrementAndGet(){
        return count.incrementAndGet();
    }
}

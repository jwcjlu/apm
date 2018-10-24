package com.jwcjlu.apm.common.thread;

import com.jwcjlu.apm.common.channel.Channels;

public class ThreadPool {
    private final ConsumerThread[]pool;
    private Channels channens;
    public ThreadPool(int threadNum,int channelNum){
        pool=new ConsumerThread[threadNum];
        channens=new Channels();
    }

}

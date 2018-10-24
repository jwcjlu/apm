package com.jwcjlu.apm.common.channel;

import com.jwcjlu.apm.common.buffer.Buffer;

public class Channels {
    private Buffer[] buffers;
    public Channels(int num){
        buffers=new Buffer[num];
    }
}

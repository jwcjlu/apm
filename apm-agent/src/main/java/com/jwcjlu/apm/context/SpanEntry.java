package com.jwcjlu.apm.context;

import com.jwcjlu.apm.protocol.SpanObject;

public class SpanEntry {
    private  int spanId;
    private int parentSpanId;
    private String operationName;
    private String peer;
    private long startTime;
    private long endTime;
    public SpanEntry(int spanId,int parentSpanId,String operationName,String peer){
        this.spanId=spanId;
        this.parentSpanId=parentSpanId;
        this.operationName=operationName;
        this.peer=peer;
    }
    public void start(){
        startTime=System.currentTimeMillis();
    }
    public void stop(){
        endTime=System.currentTimeMillis();
    }
    public int getSpanId() {
        return spanId;
    }

    public int getParentSpanId() {
        return parentSpanId;
    }

    public String getOperationName() {
        return operationName;
    }

    public String getPeer() {
        return peer;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }
    public SpanObject transform(){
        SpanObject.Builder builder=  SpanObject.newBuilder();
        return builder.setEndTime(endTime).setOperationName(operationName).setStartTime(startTime)
                .setPeer(peer).setParentSpanId(parentSpanId).setSpanId(spanId).build();
    }

}

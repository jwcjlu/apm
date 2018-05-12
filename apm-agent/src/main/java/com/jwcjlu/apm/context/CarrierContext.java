package com.jwcjlu.apm.context;

public class CarrierContext {
    private String traceId;
    private int spanId=-1;
    public  CarrierContext(){

    }
    public CarrierContext(String traceId,int spanId){
        this.traceId=traceId;
        this.spanId=spanId;
    }
    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public int getSpanId() {
        return spanId;
    }

    public void setSpanId(int spanId) {
        this.spanId = spanId;
    }
    public void inject(TracingContext context){
        this.spanId=context.getSpanId().get();
        this.traceId=context.getTraceId();
    }



}

package com.jwcjlu.apm.context;

public class CarrierContext {
    private String traceId;
    private String spanId;
    public  CarrierContext(){

    }
    public CarrierContext(String traceId,String spanId){
        this.traceId=traceId;
        this.spanId=spanId;
    }
    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getSpanId() {
        return spanId;
    }

    public void setSpanId(String spanId) {
        this.spanId = spanId;
    }
    public void inject(TracingContext context){
       /* this.spanId=context.getSpanId().get();*/
        this.traceId=context.getTraceId();
    }



}

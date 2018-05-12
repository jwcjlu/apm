package com.jwcjlu.apm.context;


public class ContextManager {
    private static ThreadLocal<TracingContext> CONTEXT = new ThreadLocal<TracingContext>();
    public static TracingContext createOrGetTracingContext(String operationName){
        TracingContext context=CONTEXT.get();
        if(context==null){
            context=new TracingContext();
        }
        CONTEXT.set(context);
        return context;
    }
    public static SpanEntry createSpan(String operationName,  CarrierContext carrier,String remotePeer) {
        TracingContext context = createOrGetTracingContext(operationName);
        SpanEntry span = context.createSpan(operationName, carrier,remotePeer);
        carrier.setTraceId(context.getTraceId());
        carrier.setSpanId(span.getSpanId());
        return span;
    }
    public static void stopSpan() {
        TracingContext context= CONTEXT.get();
        context.stopSpan();

    }

}

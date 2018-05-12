package com.jwcjlu.apm.context;

import com.jwcjlu.apm.grpc.TraceServiceClient;
import com.jwcjlu.apm.remote.RemoteConfig;
import io.netty.util.internal.StringUtil;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class TracingContext {
    private String traceId;
    private AtomicInteger  spanId=new AtomicInteger();
    private TraceSegment  traceSegment;
    private int parentSpanId=-1;
    public SpanEntry createSpan(String operationName, CarrierContext carrier,String remotePeer){
        if(carrier.getSpanId()!=-1){
            parentSpanId=carrier.getSpanId();
            spanId=new AtomicInteger(carrier.getSpanId());
        }
        if(StringUtil.isNullOrEmpty(carrier.getTraceId())&&StringUtil.isNullOrEmpty(traceId)){
            traceId= UUID.randomUUID().toString();
            traceSegment=new TraceSegment(traceId, RemoteConfig.APPLICATIONNAME);
        }
        if(!StringUtil.isNullOrEmpty(carrier.getTraceId())&&StringUtil.isNullOrEmpty(traceId)){
            traceId=carrier.getTraceId();
            traceSegment=new TraceSegment(traceId, RemoteConfig.APPLICATIONNAME);
        }
          SpanEntry span= new SpanEntry(spanId.incrementAndGet(),parentSpanId,operationName,remotePeer);
          traceSegment.addSpan(span);

           return span;
    }
    public String getTraceId() {
        return traceId;
    }

    public AtomicInteger getSpanId() {
        return spanId;
    }

    public void stopSpan(){
        traceSegment.stopSpan();
        TraceServiceClient.getInstance().collector(traceSegment);
    }
}

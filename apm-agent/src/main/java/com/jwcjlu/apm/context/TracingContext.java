package com.jwcjlu.apm.context;

import com.jwcjlu.apm.SpanEnum;
import com.jwcjlu.apm.grpc.TraceServiceClient;
import com.jwcjlu.apm.remote.RemoteConfig;
import io.netty.util.internal.StringUtil;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class TracingContext {
    private String traceId;
    private AtomicInteger  spanId=new AtomicInteger();
    private TraceSegment  traceSegment;
    private String parentSpanId=0+"";
    public SpanEntry createSpan(String operationName, CarrierContext carrier,String remotePeer){
        SpanEnum  type=SpanEnum.ENTRY;
        if(!StringUtil.isNullOrEmpty(carrier.getSpanId())){
            type=SpanEnum.EXIT;
            parentSpanId=carrier.getSpanId();
        }
        if(StringUtil.isNullOrEmpty(carrier.getTraceId())&&StringUtil.isNullOrEmpty(traceId)){
            traceId= UUID.randomUUID().toString();
            traceSegment=new TraceSegment(traceId, RemoteConfig.APPLICATIONNAME);
        }
        if(!StringUtil.isNullOrEmpty(carrier.getTraceId())&&StringUtil.isNullOrEmpty(traceId)){
            traceId=carrier.getTraceId();
            traceSegment=new TraceSegment(traceId, RemoteConfig.APPLICATIONNAME);
        }
          SpanEntry span= new SpanEntry(buildSpanId(),parentSpanId,operationName,remotePeer,type);
          traceSegment.addSpan(span);

           return span;
    }
    public String getTraceId() {
        return traceId;
    }
    public void stopSpan(){
        if(traceSegment.stopSpan()) {
            TraceServiceClient.getInstance().collector(traceSegment);
        }
    }
    private String buildSpanId(){
    StringBuilder  builder=new StringBuilder();
    builder.append(parentSpanId).append(".").append(spanId.incrementAndGet());
    return builder.toString();
    }
}

package com.jwcjlu.apm.context;

import com.jwcjlu.apm.protocol.TraceObject;
import com.jwcjlu.apm.protocol.UpstreamSegment;

import java.util.LinkedList;
public class TraceSegment {
    private String traceId;
    private LinkedList<SpanEntry> spans;
    private LinkedList<SpanEntry> activeSpans;
    private String applicationName;
    public TraceSegment(String traceId,String applicationName){
        this.traceId=traceId;
        this.applicationName=applicationName;
        spans=new LinkedList<>();
        activeSpans=new LinkedList<>();
    }
    public String getTraceId() {
        return traceId;
    }
    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
    public String getApplicationName() {
        return applicationName;
    }
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
    public void addSpan(SpanEntry spanEntry){
        activeSpans.add(spanEntry);
    }
    public boolean stopSpan(){
        if(activeSpans.isEmpty()){
            return false;
        }
        SpanEntry spanEntry=  activeSpans.removeLast();
        spanEntry.stop();
        spans.add(spanEntry);
        return activeSpans.isEmpty();

    }
    public UpstreamSegment  transform(){
        UpstreamSegment.Builder upstreamBuilder = UpstreamSegment.newBuilder();
        TraceObject.Builder traceSegmentBuilder = TraceObject.newBuilder();
        /**
         * Trace Segment
         */
       // traceSegmentBuilder.setTraceId(this.traceId);
        // Don't serialize TraceSegmentReference
        // SpanObject
        for (SpanEntry span : this.spans) {
            traceSegmentBuilder.addSpans(span.transform());
        }
        traceSegmentBuilder.setTraceId(traceId);
        traceSegmentBuilder.setApplicationName(applicationName);
        upstreamBuilder.setSegment(traceSegmentBuilder.build().toByteString());
        return upstreamBuilder.build();
    }
}

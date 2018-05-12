package com.jwjclu.apm.grpc;

import com.google.protobuf.InvalidProtocolBufferException;
import com.jwcjlu.apm.protocol.TraceObject;
import com.jwcjlu.apm.protocol.UpstreamSegment;


public class SegmentParse {

    public static TraceObject parseBinarySegment(UpstreamSegment segment) throws InvalidProtocolBufferException {
        return TraceObject.parseFrom(segment.getSegment());
    }
}

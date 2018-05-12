package com.jwcjlu.apm.grpc;

import com.jwcjlu.apm.context.TraceSegment;
import com.jwcjlu.apm.protocol.Downstream;
import com.jwcjlu.apm.protocol.TraceServiceGrpc;
import com.jwcjlu.apm.protocol.UpstreamSegment;
import com.jwcjlu.apm.remote.RemoteConfig;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TraceServiceClient {
    public static  TraceServiceClient client;
    public static TraceServiceClient getInstance(){
       if(client==null){
           client=new TraceServiceClient(RemoteConfig.GRPC_HOST,RemoteConfig.GRPC_PORT);
       }
       return client;
    }

    private  ManagedChannel channel;
    private  TraceServiceGrpc.TraceServiceStub traceServiceStub;
    private TraceServiceClient(String host, int port){
        channel = ManagedChannelBuilder.forAddress(host,port)
                .usePlaintext()
                .build();

        traceServiceStub = TraceServiceGrpc.newStub(channel);
    }


    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
    public void collector(TraceSegment data) {
        StreamObserver<UpstreamSegment> upstreamSegmentStreamObserver = traceServiceStub.collect(new StreamObserver<Downstream>() {
            @Override
            public void onNext(Downstream downstream) {
            }
            @Override
            public void onError(Throwable throwable) {
            }
            @Override
            public void onCompleted() {
            }
            });
            try {
                UpstreamSegment upstreamSegment = data.transform();
                upstreamSegmentStreamObserver.onNext(upstreamSegment);
            } catch (Throwable t) {
            }

         upstreamSegmentStreamObserver.onCompleted();
    }
}

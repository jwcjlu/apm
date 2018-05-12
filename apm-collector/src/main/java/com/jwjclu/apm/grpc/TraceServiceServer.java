package com.jwjclu.apm.grpc;

import com.google.protobuf.InvalidProtocolBufferException;
import com.jwcjlu.apm.protocol.Downstream;
import com.jwcjlu.apm.protocol.TraceObject;
import com.jwcjlu.apm.protocol.TraceServiceGrpc;
import com.jwcjlu.apm.protocol.UpstreamSegment;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

public class TraceServiceServer {
    private int port = 50051;
    private Server server;
    public void start() throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(new TraceServiceImpl())
                .build()
                .start();

        System.out.println("service start...");

        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {

                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                TraceServiceServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }
    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    // block 一直到退出程序
    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
    // 实现 定义一个实现服务接口的类
    private class TraceServiceImpl extends TraceServiceGrpc.TraceServiceImplBase {


        public StreamObserver<UpstreamSegment> collect(StreamObserver<Downstream> responseObserver) {
            return new StreamObserver<UpstreamSegment>() {
                @Override public void onNext(UpstreamSegment segment) {
                    System.out.println("received message");
                    TraceObject trace=null;
                    try {
                        trace= SegmentParse.parseBinarySegment(segment);
                        System.out.println(trace);
                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                    }
                    if(trace==null){
                        return;
                    }
                    long count = SegmentCounter.INSTANCE.incrementAndGet();
                        if (count % 100 == 0) {
                           System.out.println(String.format("received segment count: {}", count));
                        }

                }

                @Override public void onError(Throwable throwable) {
                    responseObserver.onCompleted();
                }

                @Override public void onCompleted() {
                    responseObserver.onNext(Downstream.newBuilder().build());
                    responseObserver.onCompleted();
                }
            };
        }
    }
}

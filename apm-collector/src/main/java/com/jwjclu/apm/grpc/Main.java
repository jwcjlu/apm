package com.jwjclu.apm.grpc;


public class Main {
    public static void main(String[]agrs) throws Exception {
        TraceServiceServer  server=new TraceServiceServer();
        server.start();
        server.blockUntilShutdown();
    }
}

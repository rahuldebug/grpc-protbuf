package com.rahul.jproto;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GreetServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        //need to add service here
        //server is created and started
        Server server= ServerBuilder.forPort(5000).addService(new GreetServiceImpl()).build();
        server.start();
        //so server runs continuously
        server.awaitTermination();
    }
}

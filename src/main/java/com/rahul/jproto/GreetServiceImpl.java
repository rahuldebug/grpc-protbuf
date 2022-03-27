package com.rahul.jproto;

import com.rahul.greeting.*;
import com.rahul.greeting.GreetingServiceGrpc;
import io.grpc.stub.StreamObserver;

public class GreetServiceImpl extends GreetingServiceGrpc.GreetingServiceImplBase {

    @Override
    public StreamObserver<LongGreetRequest> longreet(StreamObserver<LongGreetResponse> responseObserver) {
        StreamObserver<LongGreetRequest> request= new StreamObserver<LongGreetRequest>() {
            int i=0;
            @Override
            public void onNext(LongGreetRequest longGreetRequest) {
                System.out.println(longGreetRequest.getGreeting().getFirstName()+ "hello :"+ ++i);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                System.out.println("completed ..............");
            }
        };
       return request;

    }

    @Override
    public void continousGreet(ContinousGreetRequest request, StreamObserver<ContinousGreetingResponse> responseObserver) {
        try {
        for (int i = 0; i < 10; i++) {
            // ContinousGreetingResponse.newBuilder().setResponse(request.getGreeting().getFirstName() + " hello"+ "this is "+i).build();

                responseObserver.onNext(ContinousGreetingResponse.newBuilder().setResponse(request.getGreeting().getFirstName() + " hello" + "this is " + i).build());
                Thread.sleep(100);
            } }
        catch (Exception e) {
            e.printStackTrace();
        }

        finally{
        responseObserver.onCompleted();
    }}
    @Override
    public void greet(GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {
        //everything is done using builder pattern
        //fetching value from request
        String firstName=request.getGreeting().getFirstName();
        String secondName=request.getGreeting().getSecondName();
        //creating response
        GreetingResponse response= GreetingResponse.newBuilder().setResponse("hi"+firstName+secondName).build();
        // since response server is asynchronous so we don't send the response directly
        //sending response
        responseObserver.onNext(response);
        //super.greet(request, responseObserver);
        //completed call
        responseObserver.onCompleted();
        //error handling can also be taken care
        //responseObserver.onError();
    }
}

import com.rahul.greeting.*;
import com.rahul.greeting.GreetingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Iterator;
import java.util.List;

public class ClientCall {
    public static void main(String[] args) {

//creating channel to connect to host
        ManagedChannel clientchannel = ManagedChannelBuilder.forAddress("localhost", 5000).usePlaintext().build();
        //creating stub
        GreetingServiceGrpc.GreetingServiceBlockingStub greetclient = GreetingServiceGrpc.newBlockingStub(clientchannel);
        //creating stub for client side streaming
        GreetingServiceGrpc.GreetingServiceStub asyncClient= GreetingServiceGrpc.newStub(clientchannel);

       StreamObserver<LongGreetRequest> requestObserver= asyncClient.longreet(new StreamObserver<LongGreetResponse>() {
            @Override
            public void onNext(LongGreetResponse longGreetResponse) {
                System.out.println("######## response is  #######" +longGreetResponse.getResponse());
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                System.out.println("response completed");
            }
        });
        //Creating greeting
        Greeting greeting=Greeting.newBuilder().setFirstName("rahul").setSecondName("choudhary").build();
        //creating Greetrequest
        GreetingRequest request= GreetingRequest.newBuilder().setGreeting(greeting).build();
        //looks like we are calling method directly but in reality we are creating a channel and calling it thier
       GreetingResponse response= greetclient.greet(request);
        System.out.println(" response is ::"+ response);
        //creating continous request
        ContinousGreetRequest continousGreetRequest=  ContinousGreetRequest.newBuilder().setGreeting(greeting).build();
//need to check why this is not working
        greetclient.continousGreet(continousGreetRequest).forEachRemaining(ContinousGreetingResponse ->{
            System.out.println(ContinousGreetingResponse.getResponse());
        });
        clientchannel.shutdown();

    }
}
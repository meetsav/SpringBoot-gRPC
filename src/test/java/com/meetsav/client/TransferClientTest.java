package com.meetsav.client;
import com.meetsav.model.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransferClientTest {

    private TransferServiceGrpc.TransferServiceStub stub;
    @BeforeAll
    public void setup(){
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost",9090)
                .usePlaintext().build();

        this.stub = TransferServiceGrpc.newStub(managedChannel);

    }

    @Test
    public void transfer() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        TransferStreamingRequest response = new TransferStreamingRequest(latch);
        StreamObserver<TransferRequest> requestStreamObserver = this.stub.transfer(response);

        for(int i = 0; i< 100; i++){
            TransferRequest request = TransferRequest.newBuilder()
                    .setFromAmount(ThreadLocalRandom.current().nextInt(1,11))
                    .setToAmount(ThreadLocalRandom.current().nextInt(1,11))
                    .setAmount(ThreadLocalRandom.current().nextInt(1,21))
                    .build();
            requestStreamObserver.onNext(request);

        }
        requestStreamObserver.onCompleted();
        latch.await();


    }
}

package com.meetsav.client;

import com.meetsav.model.Money;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;

public class MoneyStreamingResponseObserver implements StreamObserver<Money> {

    private CountDownLatch latch;

    public MoneyStreamingResponseObserver(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void onNext(Money money) {
        System.out.println(
                "Received Async : "+ money.getValue()
        );
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println(
                "ERROR: "+ throwable.getMessage()
        );
        latch.countDown();

    }

    @Override
    public void onCompleted() {
        System.out.println(
                "Server is done serving!!"
        );
        latch.countDown();

    }
}

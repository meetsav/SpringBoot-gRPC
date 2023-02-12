package com.meetsav.client;

import com.meetsav.model.Balance;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;

public class BalanceStreammingObserver implements StreamObserver<Balance> {
    private CountDownLatch latch;
    public BalanceStreammingObserver(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void onNext(Balance balance) {
        System.out.println(
                "Final amount : " + balance.getAmount()
        );

    }

    @Override
    public void onError(Throwable throwable) {
        this.latch.countDown();
    }

    @Override
    public void onCompleted() {
        System.out.println(
                "server is done serving!!"
        );
        this.latch.countDown();

    }
}

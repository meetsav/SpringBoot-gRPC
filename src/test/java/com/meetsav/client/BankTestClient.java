package com.meetsav.client;

import com.google.common.util.concurrent.Uninterruptibles;
import com.meetsav.model.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankTestClient {

    private BankServiceGrpc.BankServiceBlockingStub blockingStub;
    private BankServiceGrpc.BankServiceStub bankServiceStub;
    @BeforeAll
    public void setup(){
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost",9090)
                .usePlaintext().build();

        this.blockingStub = BankServiceGrpc.newBlockingStub(managedChannel);
        this.bankServiceStub = BankServiceGrpc.newStub(managedChannel);

    }

    @Test
    public void balanceTest(){
        BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder()
                .setAccountNumber(5)
                .build();
        Balance balance = this.blockingStub.getBalance(balanceCheckRequest);
        System.out.println(balance.getAmount());
    }

    @Test
    public void serverStreamingWithdrawBalanceTest(){
        WithdrawRequest withdrawMoney = WithdrawRequest.newBuilder()
                .setAccountNumber(10)
                .setAmount(50).build();
        this.blockingStub.withdraw(withdrawMoney).forEachRemaining(money -> System.out.println("Received : " + money.getValue()));

    }

    @Test
    public void withdrawAsyncTest() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        WithdrawRequest withdrawMoney = WithdrawRequest.newBuilder()
                .setAccountNumber(9)
                .setAmount(40).build();
        this.bankServiceStub.withdraw(withdrawMoney,new MoneyStreamingResponseObserver(latch));
        latch.await();
    }

    @Test
    public void cashDepositTest() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<DepositRequest> streamObserver = this.bankServiceStub.cashDeposit(new BalanceStreammingObserver(latch));

        for(int i=0;i<10;i++){
            DepositRequest depositRequest =  DepositRequest.newBuilder().setAccountNumber(3)
                    .setAmount(10).build();
            streamObserver.onNext(depositRequest);
        }
        streamObserver.onCompleted();
        latch.await();
    }
}

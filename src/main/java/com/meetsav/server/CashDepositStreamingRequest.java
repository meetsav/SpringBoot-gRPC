package com.meetsav.server;

import com.meetsav.model.Balance;
import com.meetsav.model.DepositRequest;
import io.grpc.stub.StreamObserver;

public class CashDepositStreamingRequest implements StreamObserver<DepositRequest> {

    private StreamObserver<Balance> balanceStreamObserver;
    private int accountBalance;

    public CashDepositStreamingRequest(StreamObserver<Balance> balance){
        this.balanceStreamObserver = balance;
    }
    @Override
    public void onNext(DepositRequest depositRequest) {

        int accountNumber = depositRequest.getAccountNumber();
        int amount = depositRequest.getAmount();
        this.accountBalance = AccountDB.addBalance(accountNumber,amount);

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
        Balance bal =  Balance.newBuilder().setAmount(this.accountBalance).build();
        this.balanceStreamObserver.onNext(bal);
        this.balanceStreamObserver.onCompleted();

    }
}

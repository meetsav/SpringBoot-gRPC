package com.meetsav.server;

import com.meetsav.model.Account;
import com.meetsav.model.TransferRequest;
import com.meetsav.model.TransferResponse;
import com.meetsav.model.TransferStatus;
import io.grpc.stub.StreamObserver;

public class TransferResponseObserver implements StreamObserver<TransferRequest> {

    private StreamObserver<TransferResponse> streamObserverResponse;

    public TransferResponseObserver(StreamObserver<TransferResponse> streamObserverResponse) {
        this.streamObserverResponse = streamObserverResponse;
    }

    @Override
    public void onNext(TransferRequest transferRequest) {
        int fromAccount = transferRequest.getFromAmount();
        int toAccount = transferRequest.getToAmount();
        int amount = transferRequest.getAmount();
        int balance = AccountDB.getBalance(fromAccount);
        TransferStatus status = TransferStatus.FAILED;

        if(balance >= amount && fromAccount != toAccount){
            AccountDB.deductBalance(fromAccount,amount);
            AccountDB.addBalance(toAccount,amount);
            status = TransferStatus.SUCCESS;
        }
        TransferResponse transferResponse = TransferResponse.newBuilder().setStatus(status)
                .addAccounts(Account.newBuilder().setAccountNumber(fromAccount).setAmount(AccountDB.getBalance(fromAccount)).build())
                .addAccounts(Account.newBuilder().setAccountNumber(toAccount).setAmount(AccountDB.getBalance(toAccount)).build())
                .build();
        this.streamObserverResponse.onNext(transferResponse);

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
        System.out.println("Transfer is done\n-------------------------\n");
        AccountDB.printAccountDetails();
        this.streamObserverResponse.onCompleted();

    }
}

package com.meetsav.server;

import com.meetsav.model.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

public class BankService extends BankServiceGrpc.BankServiceImplBase {

    @Override
    public void getBalance(BalanceCheckRequest request, StreamObserver<Balance> responseObserver) {

        int account = request.getAccountNumber();
        Balance balance = Balance.newBuilder()
                .setAmount(AccountDB.getBalance(account))
                .build();
        responseObserver.onNext(balance);
        responseObserver.onCompleted();

    }

    @Override
    public void withdraw(WithdrawRequest request, StreamObserver<Money> responseObserver){
       int accountNumber = request.getAccountNumber();
       int amount = request.getAmount();
       int balance_in_account = AccountDB.getBalance(accountNumber);
       if(balance_in_account < amount){
           Status status = Status.FAILED_PRECONDITION.withDescription("No enough money to be withdraw ");
           responseObserver.onError(status.asRuntimeException());
           return;
       }

       for(int i=0;i<(amount/10);i++){
           Money money = Money.newBuilder().setValue(10).build();
           responseObserver.onNext(money);
           try{
               Thread.sleep(1000);
           }
           catch (Exception e){
               System.out.println(e.getMessage());
               return;
           }

           AccountDB.deductBalance(accountNumber,10);
       }
       responseObserver.onCompleted();


    }

    @Override
    public StreamObserver<DepositRequest> cashDeposit(StreamObserver<Balance> responseObserver) {
        return new CashDepositStreamingRequest(responseObserver);
    }


}

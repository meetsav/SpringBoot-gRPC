package com.meetsav.server;

import com.meetsav.model.TransferRequest;
import com.meetsav.model.TransferResponse;
import com.meetsav.model.TransferServiceGrpc;
import io.grpc.stub.StreamObserver;

public class TransferService extends TransferServiceGrpc.TransferServiceImplBase {
    @Override
    public StreamObserver<TransferRequest> transfer(StreamObserver<TransferResponse> responseObserver) {
        return new TransferResponseObserver(responseObserver);
    }
}

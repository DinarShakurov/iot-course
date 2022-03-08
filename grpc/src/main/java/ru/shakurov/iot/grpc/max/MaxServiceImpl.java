package ru.shakurov.iot.grpc.max;

import io.grpc.stub.StreamObserver;
import ru.shakurov.iot.grpc.proto.max.MaxRequest;
import ru.shakurov.iot.grpc.proto.max.MaxResponse;
import ru.shakurov.iot.grpc.proto.max.MaxServiceGrpc;

public class MaxServiceImpl extends MaxServiceGrpc.MaxServiceImplBase {
    @Override
    public StreamObserver<MaxRequest> getMax(StreamObserver<MaxResponse> responseObserver) {
        return new StreamObserver<>() {
            private int currentMax = Integer.MIN_VALUE;

            @Override
            public void onNext(MaxRequest value) {
                currentMax = value.getNumber() > currentMax ? value.getNumber() : currentMax;
                responseObserver.onNext(MaxResponse.newBuilder().setMax(currentMax).build());
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}

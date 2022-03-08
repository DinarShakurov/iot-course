package ru.shakurov.iot.grpc.prime_factor;

import io.grpc.stub.StreamObserver;
import ru.shakurov.iot.grpc.proto.prime_factors.PFRequest;
import ru.shakurov.iot.grpc.proto.prime_factors.PFResponse;
import ru.shakurov.iot.grpc.proto.prime_factors.PFServiceGrpc;

public class PFServiceImpl extends PFServiceGrpc.PFServiceImplBase {
    @Override
    public void primeFactor(PFRequest request, StreamObserver<PFResponse> responseObserver) {
        int number = request.getNumber();
        for (int i = 2; i <= number; i++) {
            while(number % i == 0) {
                responseObserver.onNext(PFResponse.newBuilder().setPrimeFactor(i).build());
                number /= i;
            }
        }
        responseObserver.onCompleted();
    }
}

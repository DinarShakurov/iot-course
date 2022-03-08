package ru.shakurov.iot.grpc.sqrt;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shakurov.iot.grpc.proto.sqrt.SqrtRequest;
import ru.shakurov.iot.grpc.proto.sqrt.SqrtResponse;
import ru.shakurov.iot.grpc.proto.sqrt.SqrtServiceGrpc;

public class SqrtServiceImpl extends SqrtServiceGrpc.SqrtServiceImplBase {

    private final Logger logger = LoggerFactory.getLogger(SqrtServiceImpl.class);

    @Override
    public void sqrt(SqrtRequest request, StreamObserver<SqrtResponse> responseObserver) {
        logger.debug("sqrt request = {}", request.getOf());
        SqrtResponse response = SqrtResponse.newBuilder().setSqrt(Math.sqrt(request.getOf())).build();
        responseObserver.onNext(response);
        logger.debug("sqrt response = {}", response.getSqrt());
        responseObserver.onCompleted();
    }
}

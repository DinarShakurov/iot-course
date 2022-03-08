package ru.shakurov.iot.grpc.std;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shakurov.iot.grpc.proto.std.StdRequest;
import ru.shakurov.iot.grpc.proto.std.StdResponse;
import ru.shakurov.iot.grpc.proto.std.StdServiceGrpc;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static ru.shakurov.iot.grpc.Properties.SERVER_IP_ADDRESS;
import static ru.shakurov.iot.grpc.Properties.SERVER_PORT;

public class StdClient {

    public static final Logger logger = LoggerFactory.getLogger(StdClient.class);

    public static void main(String[] args) throws InterruptedException {
        logger.debug("Starting gRPC-Client Std");

        ManagedChannel channel = ManagedChannelBuilder.forAddress(SERVER_IP_ADDRESS, SERVER_PORT + 1)
                .usePlaintext()
                .build();

        StdServiceGrpc.StdServiceStub stdClient = StdServiceGrpc.newStub(channel);
        StreamObserver<StdRequest> requestObserver = stdClient.calculateStd(new StreamObserver<>() {
            @Override
            public void onNext(StdResponse value) {
                logger.info("STD = {}", value.getStd());
            }

            @Override
            public void onError(Throwable t) {
                logger.error("Something goes wrong...", t);
            }

            @Override
            public void onCompleted() {
            }
        });

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            StdRequest request = StdRequest.newBuilder().setNumber(random.nextDouble(1000.00)).build();
            // logger.debug("sending request - {}", request);
            requestObserver.onNext(request);
        }
        requestObserver.onCompleted();

        Thread.sleep(1000);
    }
}

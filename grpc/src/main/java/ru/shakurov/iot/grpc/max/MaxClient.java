package ru.shakurov.iot.grpc.max;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shakurov.iot.grpc.proto.max.MaxRequest;
import ru.shakurov.iot.grpc.proto.max.MaxResponse;
import ru.shakurov.iot.grpc.proto.max.MaxServiceGrpc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static ru.shakurov.iot.grpc.Properties.SERVER_IP_ADDRESS;
import static ru.shakurov.iot.grpc.Properties.SERVER_PORT;

public class MaxClient {

    public static final Logger logger = LoggerFactory.getLogger(MaxClient.class);

    public static void main(String[] args) throws InterruptedException {
        logger.debug("Starting gRPC-Client Max");

        ManagedChannel channel = ManagedChannelBuilder.forAddress(SERVER_IP_ADDRESS, SERVER_PORT + 3)
                .usePlaintext()
                .build();

        MaxServiceGrpc.MaxServiceStub client = MaxServiceGrpc.newStub(channel);
        StreamObserver<MaxRequest> requestObserver = client.getMax(new StreamObserver<>() {
            @Override
            public void onNext(MaxResponse value) {
                logger.debug("Current MAX = {}", value.getMax());
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onCompleted() {
                logger.debug("completed");
            }
        });
        List<Integer> list = new ArrayList<>(10);
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            list.add(random.nextInt(1_000));
        }
        list.forEach(integer -> requestObserver.onNext(MaxRequest.newBuilder().setNumber(integer).build()));
        requestObserver.onCompleted();
        Thread.sleep(1000);
    }
}

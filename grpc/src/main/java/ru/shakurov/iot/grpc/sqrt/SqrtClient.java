package ru.shakurov.iot.grpc.sqrt;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shakurov.iot.grpc.proto.sqrt.SqrtRequest;
import ru.shakurov.iot.grpc.proto.sqrt.SqrtResponse;
import ru.shakurov.iot.grpc.proto.sqrt.SqrtServiceGrpc;

import static ru.shakurov.iot.grpc.Properties.*;

public class SqrtClient {

    private static final Logger logger = LoggerFactory.getLogger(SqrtClient.class);

    public static void main(String[] args) {
        logger.debug("Starting gRPC-Client Sqrt");

        ManagedChannel channel = ManagedChannelBuilder.forAddress(SERVER_IP_ADDRESS, SERVER_PORT)
                .usePlaintext()
                .build();

        logger.debug("Channel is created on {} with port {}", SERVER_IP_ADDRESS, SERVER_PORT);

        SqrtServiceGrpc.SqrtServiceBlockingStub sqrtClient = SqrtServiceGrpc.newBlockingStub(channel);

        SqrtRequest request = SqrtRequest.newBuilder().setOf(144).build();
        logger.debug("Request - {}", request);

        SqrtResponse response = sqrtClient.sqrt(request);

        logger.debug("Response - {}", response);
    }
}

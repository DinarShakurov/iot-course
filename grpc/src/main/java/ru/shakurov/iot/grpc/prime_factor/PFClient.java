package ru.shakurov.iot.grpc.prime_factor;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shakurov.iot.grpc.proto.prime_factors.PFRequest;
import ru.shakurov.iot.grpc.proto.prime_factors.PFServiceGrpc;
import ru.shakurov.iot.grpc.proto.sqrt.SqrtRequest;
import ru.shakurov.iot.grpc.proto.sqrt.SqrtResponse;
import ru.shakurov.iot.grpc.proto.sqrt.SqrtServiceGrpc;
import ru.shakurov.iot.grpc.sqrt.SqrtClient;

import static ru.shakurov.iot.grpc.Properties.SERVER_IP_ADDRESS;
import static ru.shakurov.iot.grpc.Properties.SERVER_PORT;

public class PFClient {

    private static final Logger logger = LoggerFactory.getLogger(PFClient.class);

    public static void main(String[] args) throws InterruptedException {
        logger.debug("Starting gRPC-Client PF");

        ManagedChannel channel = ManagedChannelBuilder.forAddress(SERVER_IP_ADDRESS, SERVER_PORT + 2)
                .usePlaintext()
                .build();

        PFServiceGrpc.PFServiceBlockingStub client = PFServiceGrpc.newBlockingStub(channel);
        client.primeFactor(PFRequest.newBuilder().setNumber(21).build()).forEachRemaining(pfResponse -> {
            logger.debug("Response = {}", pfResponse.getPrimeFactor());
        });
        Thread.sleep(1000);
    }
}

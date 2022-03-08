package ru.shakurov.iot.grpc.max;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shakurov.iot.grpc.Properties;

import java.io.IOException;

public class MaxServer {
    public static final Logger logger = LoggerFactory.getLogger(MaxServer.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        logger.debug("Starting gRPC-Server Max");

        Server server = ServerBuilder.forPort(Properties.SERVER_PORT + 3)
                .addService(new MaxServiceImpl())
                .build();
        server.start();

        logger.debug("Server Max is started");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Received Shutdown Request");
            server.shutdown();
            System.out.println("Successfully stopped the server");
        }));

        server.awaitTermination();
    }
}

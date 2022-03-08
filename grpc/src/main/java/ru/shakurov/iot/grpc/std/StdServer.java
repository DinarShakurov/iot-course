package ru.shakurov.iot.grpc.std;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shakurov.iot.grpc.Properties;

import java.io.IOException;

public class StdServer {

    private static final Logger logger = LoggerFactory.getLogger(StdServer.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        logger.debug("Starting gRPC-Server Std");

        Server server = ServerBuilder.forPort(Properties.SERVER_PORT + 1)
                .addService(new StdServiceImpl())
                .build();
        server.start();

        logger.debug("Server Std is started");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Received Shutdown Request");
            server.shutdown();
            System.out.println("Successfully stopped the server");
        }));

        server.awaitTermination();
    }
}

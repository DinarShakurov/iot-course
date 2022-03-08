package ru.shakurov.iot.grpc.prime_factor;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shakurov.iot.grpc.Properties;

import java.io.IOException;

public class PFServer {

    public static final Logger logger = LoggerFactory.getLogger(PFServer.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        logger.debug("Starting gRPC-Server PF   ");

        Server server = ServerBuilder.forPort(Properties.SERVER_PORT + 2)
                .addService(new PFServiceImpl())
                .build();
        server.start();

        logger.debug("Server PF is started");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Received Shutdown Request");
            server.shutdown();
            System.out.println("Successfully stopped the server");
        }));

        server.awaitTermination();
    }
}

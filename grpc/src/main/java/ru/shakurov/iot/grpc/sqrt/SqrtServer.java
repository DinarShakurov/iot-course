package ru.shakurov.iot.grpc.sqrt;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shakurov.iot.grpc.Properties;

import java.io.IOException;

public class SqrtServer {

    private final static Logger logger = LoggerFactory.getLogger(SqrtServer.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        logger.debug("Starting gRPC-Server Sqrt");

        Server server = ServerBuilder.forPort(Properties.SERVER_PORT)
                .addService(new SqrtServiceImpl())
                .build();
        server.start();

        logger.debug("Server Sqrt is started");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Received Shutdown Request");
            server.shutdown();
            System.out.println("Successfully stopped the server");
        }));

        server.awaitTermination();
    }
}

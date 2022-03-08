package ru.shakurov.iot.grpc.std;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shakurov.iot.grpc.proto.std.StdRequest;
import ru.shakurov.iot.grpc.proto.std.StdResponse;
import ru.shakurov.iot.grpc.proto.std.StdServiceGrpc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;


public class StdServiceImpl extends StdServiceGrpc.StdServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(StdServiceImpl.class);

    @Override
    public StreamObserver<StdRequest> calculateStd(StreamObserver<StdResponse> responseObserver) {
        return new StreamObserver<>() {

            private final List<Double> list = new ArrayList<>();

            @Override
            public void onNext(StdRequest value) {
                logger.debug("request from client - {}", value);
                list.add(value.getNumber());
            }

            @Override
            public void onError(Throwable t) {
                logger.error("Something goes wrong...", t);
            }

            @Override
            public void onCompleted() {
                double sum = list.stream().reduce(0.00, Double::sum);
                double mean = sum / list.size();
                double result = list.stream().reduce(0.00,
                        (aDouble, aDouble2) -> aDouble + Math.pow(aDouble2 - mean, 2));
                result = Math.sqrt(result / list.size());

                StdResponse response = StdResponse.newBuilder()
                        .setStd(result).build();
                logger.debug("response for client - {}", response);
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        };
    }
}

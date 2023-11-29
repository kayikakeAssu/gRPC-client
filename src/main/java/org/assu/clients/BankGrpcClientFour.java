package org.assu.clients;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.assu.stubs.Bank;
import org.assu.stubs.BankServiceGrpc;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class BankGrpcClientFour {
    public static void main(String[] args) throws IOException {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 5555)
                .usePlaintext()
                .build();
        BankServiceGrpc.BankServiceStub newStub = BankServiceGrpc.newStub(managedChannel);

        Bank.ConvertCurrencyRequest request = Bank.ConvertCurrencyRequest
                .newBuilder()
                .setCurrencyFrom("CDF")
                .setCurrencyTo("USD")
                .setAmount(1214)
                .build();

        StreamObserver<Bank.ConvertCurrencyRequest> performStream = newStub.performStream(new StreamObserver<Bank.ConvertCurrencyResponse>() {
            @Override
            public void onNext(Bank.ConvertCurrencyResponse convertCurrencyResponse) {
                System.out.println("++++++++++++++++++++++");
                System.out.println(convertCurrencyResponse);
                System.out.println("++++++++++++++++++++++");
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                System.out.println("---END---");
            }


        });
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int counter = 0;
            @Override
            public void run() {
                Bank.ConvertCurrencyRequest convertCurrencyRequest = Bank.ConvertCurrencyRequest
                        .newBuilder()
                        .setAmount((float) Math.random()*10)
                        .build();
                performStream.onNext(convertCurrencyRequest);
                ++counter;
                System.out.println("Counter ======>counter = "+counter);
                if (counter ==20) {
                    performStream.onCompleted();
                    timer.cancel();
                }

            }
        }, 1000, 1000);

        System.out.println("......?");
        System.in.read();

    }
}

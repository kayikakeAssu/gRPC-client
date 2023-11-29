package org.assu.clients;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.assu.stubs.Bank;
import org.assu.stubs.BankServiceGrpc;

import java.io.IOException;

public class BankGrpcClientTwo {
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

        newStub.convert(request, new StreamObserver<Bank.ConvertCurrencyResponse>() {
            @Override
            public void onNext(Bank.ConvertCurrencyResponse convertCurrencyResponse) {
                System.out.println("*****************");
                System.out.println(convertCurrencyResponse);
                System.out.println("*****************");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("...END...");
            }
        });

        System.out.println("No blocking response from java");
        System.in.read();
    }
}

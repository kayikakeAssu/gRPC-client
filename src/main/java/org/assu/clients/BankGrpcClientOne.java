package org.assu.clients;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.assu.stubs.Bank;
import org.assu.stubs.BankServiceGrpc;

public class BankGrpcClientOne {
    public static void main(String[] args) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 5555)
                .usePlaintext()
                .build();
        BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub = BankServiceGrpc.newBlockingStub(managedChannel);

        Bank.ConvertCurrencyRequest request = Bank.ConvertCurrencyRequest
                .newBuilder()
                .setCurrencyFrom("CDF")
                .setCurrencyTo("USD")
                .setAmount(1214)
                .build();

        Bank.ConvertCurrencyResponse response = bankServiceBlockingStub.convert(request);

        System.out.println(response);
    }
}

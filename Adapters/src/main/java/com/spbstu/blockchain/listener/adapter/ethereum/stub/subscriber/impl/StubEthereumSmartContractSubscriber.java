package com.spbstu.blockchain.listener.adapter.ethereum.stub.subscriber.impl;

import com.spbstu.blockchain.listener.adapter.ethereum.stub.subscriber.StubEthereumBlockchainSubscriber;
import com.spbstu.blockchain.listener.adapter.ethereum.stub.util.DomainModelBuilder;
import lombok.SneakyThrows;
import org.web3j.protocol.websocket.events.Log;
import org.web3j.protocol.websocket.events.LogNotification;

import java.net.ConnectException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class StubEthereumSmartContractSubscriber extends StubEthereumBlockchainSubscriber<Log> {

    public StubEthereumSmartContractSubscriber() throws ConnectException {
    }

    @SneakyThrows
    @Override
    public void subscribe(Consumer<Log> consumer) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(() ->
        {
            Thread.sleep(10000L);
            while (true) {
                consumer.accept(DomainModelBuilder.buildLog());
                Thread.sleep(1000L);
            }
        });
    }
}

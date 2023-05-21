package com.spbstu.blockchain.listener.adapter.ethereum.stub.subscriber.impl;

import com.spbstu.blockchain.listener.adapter.ethereum.stub.subscriber.StubEthereumBlockchainSubscriber;
import com.spbstu.blockchain.listener.adapter.ethereum.stub.util.DomainModelBuilder;
import lombok.SneakyThrows;
import org.web3j.protocol.core.methods.response.EthBlock;

import java.net.ConnectException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class StubEthereumBlockSubscriber extends StubEthereumBlockchainSubscriber<EthBlock> {

    public StubEthereumBlockSubscriber() throws ConnectException {
    }

    @SneakyThrows
    @Override
    public void subscribe(Consumer<EthBlock> consumer) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(() ->
        {
            while (true) {
                consumer.accept(DomainModelBuilder.buildEthBlock(10));
                Thread.sleep(1000L);
            }
        });
    }
}

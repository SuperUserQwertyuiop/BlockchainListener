package com.spbstu.blockchain.listener.adapter.ethereum.stub.subscriber.impl;

import com.spbstu.blockchain.listener.adapter.ethereum.stub.subscriber.StubEthereumBlockchainSubscriber;
import lombok.SneakyThrows;
import org.web3j.protocol.websocket.events.LogNotification;

import java.net.ConnectException;
import java.util.function.Consumer;

public class StubEthereumSmartContractSubscriber extends StubEthereumBlockchainSubscriber<LogNotification> {

    public StubEthereumSmartContractSubscriber() throws ConnectException {
    }

    @SneakyThrows
    @Override
    public void subscribe(Consumer<LogNotification> consumer) {
    }
}

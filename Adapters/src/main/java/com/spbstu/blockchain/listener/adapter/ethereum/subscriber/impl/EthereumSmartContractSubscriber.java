package com.spbstu.blockchain.listener.adapter.ethereum.subscriber.impl;

import com.spbstu.blockchain.listener.adapter.ethereum.subscriber.WebsocketEthereumBlockchainSubscriber;
import lombok.SneakyThrows;
import org.web3j.protocol.websocket.events.LogNotification;

import java.net.ConnectException;
import java.util.Collections;
import java.util.function.Consumer;

public class EthereumSmartContractSubscriber extends WebsocketEthereumBlockchainSubscriber<LogNotification> {

    private int counter;

    public EthereumSmartContractSubscriber() throws ConnectException {
        counter = 10;
    }

    @SneakyThrows
    @Override
    public void subscribe(Consumer<LogNotification> consumer) {
        this.subscription = web3
                .logsNotifications(Collections.emptyList(), Collections.emptyList())
                .subscribe(consumer::accept, throwable -> {
                    throw new Exception(throwable);
                });
    }
}

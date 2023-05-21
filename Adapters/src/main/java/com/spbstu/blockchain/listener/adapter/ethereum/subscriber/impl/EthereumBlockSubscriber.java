package com.spbstu.blockchain.listener.adapter.ethereum.subscriber.impl;

import com.spbstu.blockchain.listener.adapter.ethereum.subscriber.WebsocketEthereumBlockchainSubscriber;
import lombok.SneakyThrows;
import org.web3j.protocol.core.methods.response.EthBlock;

import java.net.ConnectException;
import java.util.function.Consumer;

public class EthereumBlockSubscriber extends WebsocketEthereumBlockchainSubscriber<EthBlock> {

    private int counter;

    public EthereumBlockSubscriber() throws ConnectException {
        counter = 10;
    }

    @SneakyThrows
    @Override
    public void subscribe(Consumer<EthBlock> consumer) {
        this.subscription = web3
                .blockFlowable(true)
                .subscribe(consumer::accept, throwable -> {throw new Exception(throwable);});
    }
}

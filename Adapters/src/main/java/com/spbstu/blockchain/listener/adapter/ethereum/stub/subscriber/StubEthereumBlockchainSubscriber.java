package com.spbstu.blockchain.listener.adapter.ethereum.stub.subscriber;

import com.spbstu.blockchain.listener.adapter.BlockchainSubscriber;

import java.net.ConnectException;

public abstract class StubEthereumBlockchainSubscriber<T> implements BlockchainSubscriber<T> {

    public StubEthereumBlockchainSubscriber() throws ConnectException {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void testAccessibility() {
    }
}

package com.spbstu.blockchain.listener.adapter;

import org.web3j.protocol.core.methods.response.EthBlock;

public interface BlockchainAdapter {

    void initSubscribers();

    void onStop();

    Object getTransactionByHash(String hash);

    Object getByNumber(Long number);

    Object getLogByTransactionHash(String hash);
}

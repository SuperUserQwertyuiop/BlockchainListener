package com.sbstu.client.service;

import com.sbstu.client.model.BlockchainType;

public interface ClientToListenerIntegrationService {

    String getBlock(BlockchainType blockchainType, String hash);

    String getBlock(BlockchainType blockchainType, Long number);

    String getLatestBlock(BlockchainType blockchainType);

    String getTransaction(BlockchainType blockchainType, String hash);

    String getLog(BlockchainType blockchainType, String transactionHash);

}

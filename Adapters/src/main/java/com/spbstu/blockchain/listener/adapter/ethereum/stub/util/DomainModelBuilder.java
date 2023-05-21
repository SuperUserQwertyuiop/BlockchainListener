package com.spbstu.blockchain.listener.adapter.ethereum.stub.util;

import com.spbstu.blockchain.listener.adapter.model.Block;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class DomainModelBuilder {

    private static Long number = 0l;

    public static EthBlock buildEthBlock(int transactionsCount) {
        Random random = new Random();
        EthBlock ethBlock = new EthBlock();
        EthBlock.Block block = new EthBlock.Block();
        String blockHash = UUID.randomUUID().toString();
        block.setHash(blockHash);
        block.setNumber(Long.toString(Math.abs(random.nextLong())));
        block.setTransactions(buildTransactionList(transactionsCount, blockHash));
        ethBlock.setResult(block);
        return ethBlock;
    }

    public static List<EthBlock.TransactionResult> buildTransactionList(int transactionsCount, String blockHash) {
        List<EthBlock.TransactionResult> transactionObjectList = new ArrayList<>();
        for (int i = 0; i < transactionsCount; i++) {
            EthBlock.TransactionObject transactionObject = new EthBlock.TransactionObject();
            transactionObject.setHash(UUID.randomUUID().toString());
            transactionObject.setTransactionIndex(Integer.toString(i));
            transactionObject.setBlockHash(blockHash);
            transactionObjectList.add(transactionObject);
        }
        return transactionObjectList;
    }

}

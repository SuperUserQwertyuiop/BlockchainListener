package com.spbstu.blockchain.listener.adapter.ethereum.stub.util;

import com.spbstu.blockchain.listener.adapter.model.Block;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.websocket.events.Log;
import org.web3j.protocol.websocket.events.LogNotification;
import org.web3j.protocol.websocket.events.Notification;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class DomainModelBuilder {

    private static Long number = 0l;

    private static List<String> blocksHash = new ArrayList<>();
    private static List<String> transactionsHash = new ArrayList<>();

    public static EthBlock buildEthBlock(int transactionsCount) {
        Random random = new Random();
        EthBlock ethBlock = new EthBlock();
        EthBlock.Block block = new EthBlock.Block();
        String blockHash = UUID.randomUUID().toString();
        block.setHash(blockHash);
        block.setNumber(Long.toString(Math.abs(random.nextLong())));
        block.setTransactions(buildTransactionList(transactionsCount, blockHash));
        ethBlock.setResult(block);

        blocksHash.add(blockHash);
        return ethBlock;
    }

    public static List<EthBlock.TransactionResult> buildTransactionList(int transactionsCount, String blockHash) {
        List<EthBlock.TransactionResult> transactionObjectList = new ArrayList<>();
        for (int i = 0; i < transactionsCount; i++) {
            EthBlock.TransactionObject transactionObject = new EthBlock.TransactionObject();
            String hash = UUID.randomUUID().toString();
            transactionObject.setHash(hash);
            transactionObject.setTransactionIndex(Integer.toString(i));
            transactionObject.setBlockHash(blockHash);
            transactionObjectList.add(transactionObject);

            transactionsHash.add(hash);
        }
        return transactionObjectList;
    }

    public static Log buildLog() {
        Random random = new Random();
        Log log = new Log();
        try {
            Field address = log.getClass().getDeclaredField("address");
            address.setAccessible(true);
            address.set(log, "addr");
            Field blockHash = log.getClass().getDeclaredField("blockHash");
            blockHash.setAccessible(true);
            blockHash.set(log, blocksHash.get(random.nextInt(blocksHash.size() - 1)));
            Field blockNumber = log.getClass().getDeclaredField("blockNumber");
            blockNumber.setAccessible(true);
            blockNumber.set(log, "1");
            Field data = log.getClass().getDeclaredField("data");
            data.setAccessible(true);
            data.set(log, "data");
            Field logIndex = log.getClass().getDeclaredField("logIndex");
            logIndex.setAccessible(true);
            logIndex.set(log, "1");
            Field topics = log.getClass().getDeclaredField("topics");
            topics.setAccessible(true);
            topics.set(log, List.of("topic1", "topic2"));
            Field transactionHash = log.getClass().getDeclaredField("transactionHash");
            transactionHash.setAccessible(true);
            transactionHash.set(log, transactionsHash.get(random.nextInt(transactionsHash.size() - 1)));
            Field transactionIndex = log.getClass().getDeclaredField("transactionIndex");
            transactionIndex.setAccessible(true);
            transactionIndex.set(log, "1");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return log;
    }

}

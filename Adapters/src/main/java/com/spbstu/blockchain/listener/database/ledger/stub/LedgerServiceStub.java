package com.spbstu.blockchain.listener.database.ledger.stub;

import com.spbstu.blockchain.listener.adapter.model.Block;
import com.spbstu.blockchain.listener.adapter.model.Log;
import com.spbstu.blockchain.listener.adapter.model.Transaction;
import com.spbstu.blockchain.listener.database.ledger.LedgerService;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class LedgerServiceStub implements LedgerService {

    private List<Block> blockList = new LinkedList<>();
    private List<Log> logList = new LinkedList<>();

    @Override
    public void callSaving(Block block) {
        blockList.add(block);
    }

    @Override
    public void callSaving(Log log) {
        logList.add(log);
    }

    public Block getBlockByHash(String hash) {
        return blockList.stream()
                .filter(x->x.getHash().equals(hash))
                .collect(Collectors.toList())
                .get(0);
    }

    public Block getBlockByNumber(String number) {
        return blockList.stream()
                .filter(x -> x.getNumber().equals(BigInteger.valueOf(Long.parseLong(number))))
                .collect(Collectors.toList())
                .get(0);
    }

    public Block getLatestBlock() {
        return blockList.get(blockList.size() - 1);
    }

    public Transaction getTransactionByHash(String hash) {
        return blockList.stream()
                .flatMap(x -> x.getTransactions().stream())
                .filter(t -> t.getHash().equals(hash))
                .collect(Collectors.toList()).get(0);
    }
}

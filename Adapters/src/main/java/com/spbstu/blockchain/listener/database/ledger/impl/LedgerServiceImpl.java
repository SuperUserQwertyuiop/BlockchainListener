package com.spbstu.blockchain.listener.database.ledger.impl;

import com.spbstu.blockchain.listener.adapter.model.Block;
import com.spbstu.blockchain.listener.adapter.model.Log;
import com.spbstu.blockchain.listener.adapter.model.Transaction;
import com.spbstu.blockchain.listener.database.ledger.LedgerService;
import com.spbstu.blockchain.listener.database.ledger.util.HyperledgerConnector;
import jakarta.annotation.PostConstruct;
import org.hyperledger.fabric.client.CommitException;
import org.hyperledger.fabric.client.CommitStatusException;
import org.hyperledger.fabric.client.EndorseException;
import org.hyperledger.fabric.client.SubmitException;
import org.springframework.stereotype.Service;

@Service
public class LedgerServiceImpl implements LedgerService {

    private HyperledgerConnector hyperledgerConnector;

    @PostConstruct
    private void initLedgerConnector() throws Exception {
        hyperledgerConnector = new HyperledgerConnector();
    }

    @Override
    public void callSaving(Block block) throws EndorseException, CommitException, SubmitException, CommitStatusException {
        hyperledgerConnector.createBlock("ETHEREUM", block.getHash(), block.getNumber().toString(),
                block.getCreationDate().toString(), block.getAdditionalInfo());
        for (Transaction transaction : block.getTransactions()) {
            hyperledgerConnector.createTransaction("ETHEREUM", transaction.getHash(), transaction.getBlockHash(),
                    transaction.getTransactionIndex().toString(), transaction.getCreationDate().toString(), transaction.getAdditionalInfo());
        }
    }

    @Override
    public void callSaving(Log log) {
        hyperledgerConnector.createLog("ETHEREUM", log.getHash(), log.getCreationDate().toString(), log.getInfo());
    }
}

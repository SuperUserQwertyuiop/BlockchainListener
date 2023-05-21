package com.spbstu.blockchain.listener.database.ledger;

import com.spbstu.blockchain.listener.adapter.model.Block;
import com.spbstu.blockchain.listener.adapter.model.Log;
import org.hyperledger.fabric.client.CommitException;
import org.hyperledger.fabric.client.CommitStatusException;
import org.hyperledger.fabric.client.EndorseException;
import org.hyperledger.fabric.client.SubmitException;

import java.io.IOException;

public interface LedgerService {

    void callSaving(Block block) throws EndorseException, CommitException, SubmitException, CommitStatusException, IOException;

    void callSaving(Log log);

}

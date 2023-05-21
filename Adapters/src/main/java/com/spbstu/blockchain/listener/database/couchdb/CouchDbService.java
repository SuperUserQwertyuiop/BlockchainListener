package com.spbstu.blockchain.listener.database.couchdb;

import com.spbstu.blockchain.listener.adapter.model.Block;
import com.spbstu.blockchain.listener.adapter.model.Transaction;

public interface CouchDbService {

    Block getBlockByNumber(Long number);

    Transaction getTransactionByBlockHash(String hash);

}

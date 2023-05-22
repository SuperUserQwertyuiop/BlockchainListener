package com.spbstu.blockchain.listener.database.couchdb.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.spbstu.blockchain.listener.adapter.model.Block;
import com.spbstu.blockchain.listener.adapter.model.Log;
import com.spbstu.blockchain.listener.adapter.model.Transaction;
import com.spbstu.blockchain.listener.database.couchdb.CouchDbService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.ektorp.ViewResult.Row;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.NameConventions;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.DesignDocument;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.net.MalformedURLException;

@Service
public class CouchDbServiceImpl implements CouchDbService {

    private CouchDbConnector db;

    @PostConstruct
    private void init() throws MalformedURLException {
        HttpClient httpClient = new StdHttpClient.Builder()
                .url("http://localhost:5984")
                .username("admin")
                .password("adminpw")
                .build();
        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
        db = new StdCouchDbConnector("mychannel_basic", dbInstance);
    }

    public Block getBlockByNumber(Long number) {
        return db.get(Block.class, String.format("ETHEREUM-BLOCK-%d", number));
    }

    public Transaction getTransactionByBlockHash(String hash) {
        return db.get(Transaction.class, String.format("ETHEREUM-TRANSACTION-%s", hash));
    }

    @Override
    public Log getLogByTransactionHash(String hash) {
        return db.get(Log.class, String.format("ETHEREUM-LOG-%s", hash));
    }
}

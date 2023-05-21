package com.spbstu.blockchain.listener.adapter.ethereum;

import com.spbstu.blockchain.listener.adapter.BlockchainAdapter;
import com.spbstu.blockchain.listener.adapter.BlockchainSubscriber;
import com.spbstu.blockchain.listener.adapter.ethereum.stub.subscriber.impl.StubEthereumBlockSubscriber;
import com.spbstu.blockchain.listener.adapter.model.Block;
import com.spbstu.blockchain.listener.adapter.model.Transaction;
import com.spbstu.blockchain.listener.database.couchdb.CouchDbService;
import com.spbstu.blockchain.listener.database.ledger.LedgerService;
import com.spbstu.blockchain.listener.database.ledger.stub.LedgerServiceStub;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.EthBlock;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EthereumAdapter implements BlockchainAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(EthereumAdapter.class);

    private final LedgerService ledgerService;
    private final CouchDbService couchDbService;

    private List<BlockchainSubscriber> subscribers;

    @Override
    @PostConstruct
    public void initSubscribers() {
        subscribers = new ArrayList<>();
        try {
            BlockchainSubscriber ethereumBlockSubscriber = new StubEthereumBlockSubscriber();
            ethereumBlockSubscriber.subscribe(this::handleBlockSubscriberData);
            subscribers.add(ethereumBlockSubscriber);
            LOG.info("EthereumBlockSubscriber successfully subscribed");
        } catch (ConnectException e) {
            LOG.warn("EthereumBlockSubscriber wasn't subscribed. Cause: ", e);
        }

        try {
            BlockchainSubscriber ethereumSmartContractSubscriber = new StubEthereumBlockSubscriber();
            ethereumSmartContractSubscriber.subscribe(this::handleLogNotificationSubscriberData);
            subscribers.add(ethereumSmartContractSubscriber);
            LOG.info("EthereumSmartContractSubscriber successfully subscribed");
        } catch (ConnectException e) {
            LOG.warn("EthereumSmartContractSubscriber wasn't subscribed. Cause: ", e);
        }
    }

    @Override
    @PreDestroy
    public void onStop() {
        subscribers.forEach(BlockchainSubscriber::onStop);
    }

    @Override
    public EthBlock.TransactionObject getTransactionByHash(String hash) {
        return Transaction.map(couchDbService.getTransactionByBlockHash(hash));
    }

    @Override
    public EthBlock getByNumber(Long number) {
        return Block.map(couchDbService.getBlockByNumber(number));
    }

    private void handleBlockSubscriberData(Object data) {
        Block block = Block.map((EthBlock) data);
        System.out.println(block.getHash());
        try {
            ledgerService.callSaving(block);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleLogNotificationSubscriberData(Object data) {

    }
}

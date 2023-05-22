package com.spbstu.blockchain.listener.adapter.ethereum.controller;

import com.spbstu.blockchain.listener.adapter.ethereum.EthereumAdapter;
import com.spbstu.blockchain.listener.adapter.model.Log;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.core.methods.response.EthBlock;

@RestController
@RequiredArgsConstructor
@RequestMapping("ethereum")
public class EthereumAdapterController {

    private final static Logger LOGGER = LoggerFactory.getLogger(EthereumAdapterController.class);
    private final EthereumAdapter ethereumAdapter;

    @GetMapping("block/getByNumber")
    public ResponseEntity<EthBlock.Block> getBlockByNumber(@RequestParam(name = "number") Long number) {
        LOGGER.info(String.format("New request for getting block by number %d", number));
        return ResponseEntity.ok(ethereumAdapter.getByNumber(number).getBlock());
    }

    @GetMapping("transaction/getByHash")
    public ResponseEntity<EthBlock.TransactionObject> getTransactionByHash(@RequestParam(name = "hash") String hash) {
        LOGGER.info(String.format("New request for getting transaction by hash %s", hash));
        return ResponseEntity.ok(ethereumAdapter.getTransactionByHash(hash));
    }

    @GetMapping("log/getByTransactionHash")
    public ResponseEntity<Log> getLogByTransactionHash(@RequestParam(name = "hash") String hash) {
        LOGGER.info(String.format("New request for getting log by hash %s", hash));
        return ResponseEntity.ok(ethereumAdapter.getLogByTransactionHash(hash));
    }

}

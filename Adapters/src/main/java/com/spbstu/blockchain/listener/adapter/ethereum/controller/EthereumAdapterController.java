package com.spbstu.blockchain.listener.adapter.ethereum.controller;

import com.spbstu.blockchain.listener.adapter.ethereum.EthereumAdapter;
import lombok.RequiredArgsConstructor;
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

    private final EthereumAdapter ethereumAdapter;

    @GetMapping("block/getByNumber")
    public ResponseEntity<EthBlock.Block> getBlockByNumber(@RequestParam(name = "number") Long number) {
        return ResponseEntity.ok(ethereumAdapter.getByNumber(number).getBlock());
    }

    @GetMapping("transaction/getByHash")
    public ResponseEntity<EthBlock.TransactionObject> getTransactionByHash(@RequestParam(name = "hash") String hash) {
        return ResponseEntity.ok(ethereumAdapter.getTransactionByHash(hash));
    }

}

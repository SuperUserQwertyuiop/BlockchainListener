package com.sbstu.client.controller;

import com.sbstu.client.model.BlockchainType;
import com.sbstu.client.service.ClientToListenerIntegrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientToListenerIntegrationService clientToListenerIntegrationService;

    @GetMapping("/block/getByHash")
    public ResponseEntity<String> getBlockByHash(@RequestParam(name = "hash") String hash, @RequestParam String blockchainType) {
        BlockchainType type = BlockchainType.valueOf(blockchainType);
        return ResponseEntity.ok(clientToListenerIntegrationService.getBlock(type, hash));
    }

    @GetMapping("/block/getByNumber")
    public ResponseEntity<String> getBlockByNumber(@RequestParam(name = "number") String number, @RequestParam String blockchainType) {
        BlockchainType type = BlockchainType.valueOf(blockchainType);
        return ResponseEntity.ok(clientToListenerIntegrationService.getBlock(type, Long.valueOf(number)));
    }

    @GetMapping("/transaction/getByHash")
    public ResponseEntity<String> getTransactionByHash(@RequestParam(name = "hash") String hash, @RequestParam String blockchainType) {
        BlockchainType type = BlockchainType.valueOf(blockchainType);
        return ResponseEntity.ok(clientToListenerIntegrationService.getTransaction(type, hash));
    }

}

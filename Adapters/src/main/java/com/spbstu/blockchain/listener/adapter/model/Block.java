package com.spbstu.blockchain.listener.adapter.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.web3j.protocol.core.methods.response.EthBlock;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Block {

    String hash;

    BigInteger number;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Berlin")
    Timestamp creationDate;

    String additionalInfo;

    List<Transaction> transactions;

    public static Block map(EthBlock ethBlock) {
        return Block.builder()
                .hash(ethBlock.getBlock().getHash())
                .number(ethBlock.getBlock().getNumber())
                .additionalInfo(constructAdditionalInfo(ethBlock))
                .creationDate(new Timestamp(System.currentTimeMillis()))
                .transactions(
                        ethBlock.getBlock().getTransactions().stream()
                                .map(Transaction::map)
                                .collect(Collectors.toList())
                )
                .build();
    }

    public static EthBlock map(Block commonBlock) {
        EthBlock ethBlock = new EthBlock();
        EthBlock.Block block = new EthBlock.Block();
        block.setHash(commonBlock.getHash());
        block.setNumber(commonBlock.getNumber().toString());
        if(!isNull(commonBlock.transactions)) {
            block.setTransactions(commonBlock.transactions.stream().map(Transaction::map).collect(Collectors.toList()));
        }
        //remove to ok variant
        block.setNonce("1234");
        block.setDifficulty("1234");
        block.setTotalDifficulty("1234");
        block.setSize("1234");
        block.setGasLimit("1234");
        block.setGasUsed("1234");
        block.setTimestamp("1234");
        //remove to here
        ethBlock.setResult(block);
        //todo() additional info
        return ethBlock;
    }

    private static String constructAdditionalInfo(EthBlock ethBlock) {
        StringBuilder additionalInfo = new StringBuilder();
        EthBlock.Block block = ethBlock.getBlock();
        additionalInfo.append("\"parentHash\":").append(getStringValueForJSON(block.getParentHash()));
        additionalInfo.append("\"nonce\":").append(getStringValueForJSON(block.getNonceRaw()));
        additionalInfo.append("\"sha3Uncles\":").append(getStringValueForJSON(block.getSha3Uncles()));
        additionalInfo.append("\"logsBloom\":").append(getStringValueForJSON(block.getLogsBloom()));
        additionalInfo.append("\"transactionsRoot\":").append(getStringValueForJSON(block.getTransactionsRoot()));
        additionalInfo.append("\"stateRoot\":").append(getStringValueForJSON(block.getStateRoot()));
        additionalInfo.append("\"receiptsRoot\":").append(getStringValueForJSON(block.getReceiptsRoot()));
        additionalInfo.append("\"author\":").append(getStringValueForJSON(block.getAuthor()));
        additionalInfo.append("\"miner\":").append(getStringValueForJSON(block.getMiner()));
        additionalInfo.append("\"mixHash\":").append(getStringValueForJSON(block.getMixHash()));
        additionalInfo.append("\"difficulty\":").append(getStringValueForJSON(block.getDifficultyRaw()));
        additionalInfo.append("\"totalDifficulty\":").append(getStringValueForJSON(block.getTotalDifficultyRaw()));
        additionalInfo.append("\"extraData\":").append(getStringValueForJSON(block.getExtraData()));
        additionalInfo.append("\"size\":").append(getStringValueForJSON(block.getSize().toString()));
        additionalInfo.append("\"gasLimit\":").append(getStringValueForJSON(block.getGasLimitRaw()));
        additionalInfo.append("\"gasUsed\":").append(getStringValueForJSON(block.getGasUsedRaw()));
        additionalInfo.append("\"timestamp\":").append(getStringValueForJSON(block.getTimestampRaw()));
        return additionalInfo.toString();
    }

    private static String getStringValueForJSON(String value) {
        return String.format(" \"%s\",", value);
    }
}

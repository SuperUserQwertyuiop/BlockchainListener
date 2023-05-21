package com.spbstu.blockchain.listener.adapter.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.web3j.protocol.core.methods.response.EthBlock;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {

    String hash;

    String blockHash;

    BigInteger transactionIndex;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Berlin")
    Timestamp creationDate;

    String additionalInfo;

    Boolean hasLogs;

    List<Log> logList;

    public static Transaction map(EthBlock.TransactionResult transactionResult) {
        org.web3j.protocol.core.methods.response.Transaction transaction = (org.web3j.protocol.core.methods.response.Transaction) transactionResult.get();
        return Transaction.builder()
                .hash(transaction.getHash())
                .blockHash(transaction.getBlockHash())
                .transactionIndex(transaction.getTransactionIndex())
                .creationDate(new Timestamp(System.currentTimeMillis()))
                .additionalInfo(constructAdditionalInfo(transaction))
                .hasLogs(false)
                .build();
    }

    public static EthBlock.TransactionObject map(Transaction commonTransaction) {
        EthBlock.TransactionObject transaction = new EthBlock.TransactionObject();
        transaction.setTransactionIndex(commonTransaction.getTransactionIndex().toString());
        transaction.setBlockHash(commonTransaction.getBlockHash());
        transaction.setHash(commonTransaction.getHash());
        //remove this
        transaction.setNonce("1234");
        transaction.setBlockNumber("1234");
        transaction.setValue("1234");
        transaction.setGasPrice("1234");
        transaction.setGas("1234");
        // remove to here
        //todo() map normally
        return transaction;
    }

    private static String constructAdditionalInfo(org.web3j.protocol.core.methods.response.Transaction transaction) {
        StringBuilder additionalInfo = new StringBuilder();
        additionalInfo.append("\"nonce\":").append(getStringValueForJSON(transaction.getNonceRaw()));
        additionalInfo.append("\"blockNumber\":").append(getStringValueForJSON(transaction.getBlockNumberRaw()));
        additionalInfo.append("\"from\":").append(getStringValueForJSON(transaction.getFrom()));
        additionalInfo.append("\"to\":").append(getStringValueForJSON(transaction.getTo()));
        additionalInfo.append("\"value\":").append(getStringValueForJSON(transaction.getValueRaw()));
        additionalInfo.append("\"gasPrice\":").append(getStringValueForJSON(transaction.getGasPriceRaw()));
        additionalInfo.append("\"gas\":").append(getStringValueForJSON(transaction.getGasRaw()));
        additionalInfo.append("\"input\":").append(getStringValueForJSON(transaction.getInput()));
        additionalInfo.append("\"creates\":").append(getStringValueForJSON(transaction.getCreates()));
        additionalInfo.append("\"publicKey\":").append(getStringValueForJSON(transaction.getPublicKey()));
        additionalInfo.append("\"raw\":").append(getStringValueForJSON(transaction.getRaw()));
        additionalInfo.append("\"r\":").append(getStringValueForJSON(transaction.getR()));
        additionalInfo.append("\"s\":").append(getStringValueForJSON(transaction.getS()));
        additionalInfo.append("\"v\":").append(getStringValueForJSON(Long.toString(transaction.getV())));
        return additionalInfo.toString();
    }

    private static String getStringValueForJSON(String value) {
        return String.format(" \"%s\",", value);
    }
}

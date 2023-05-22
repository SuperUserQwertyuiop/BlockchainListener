package com.spbstu.blockchain.listener.adapter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.web3j.protocol.core.methods.response.EthBlock;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Log {

    String hash;

    String info;

    public static Log map(org.web3j.protocol.websocket.events.Log ethLog) {
        Log log = Log.builder()
                .hash(ethLog.getTransactionHash())
                .info(constructAdditionalInfo(ethLog))
                .build();
        return log;
    }

    private static String constructAdditionalInfo(org.web3j.protocol.websocket.events.Log log) {
        StringBuilder additionalInfo = new StringBuilder();
        additionalInfo.append("\"address\":").append(getStringValueForJSON(log.getAddress()));
        additionalInfo.append("\"blockHash\":").append(getStringValueForJSON(log.getBlockHash()));
        additionalInfo.append("\"data\":").append(getStringValueForJSON(log.getData()));
        additionalInfo.append("\"logIndex\":").append(getStringValueForJSON(log.getLogIndex()));
        additionalInfo.append("\"blockNumber\":").append(getStringValueForJSON(log.getBlockNumber()));
        additionalInfo.append("\"transactionIndex\":").append(getStringValueForJSON(log.getTransactionIndex()));
        additionalInfo.append("\"topics\":").append(getStringValueForJSON(log.getTopics().toString()));
        return additionalInfo.toString();
    }

    private static String getStringValueForJSON(String value) {
        return String.format(" \"%s\",", value);
    }

}

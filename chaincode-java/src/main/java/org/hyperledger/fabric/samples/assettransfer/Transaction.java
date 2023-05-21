package org.hyperledger.fabric.samples.assettransfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    String hash;

    String blockHash;

    String transactionIndex;

    String creationDate;

    String additionalInfo;

}

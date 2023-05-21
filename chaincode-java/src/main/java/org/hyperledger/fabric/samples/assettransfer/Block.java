package org.hyperledger.fabric.samples.assettransfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Block {

    String hash;

    String number;

    String creationDate;

    String additionalInfo;
}

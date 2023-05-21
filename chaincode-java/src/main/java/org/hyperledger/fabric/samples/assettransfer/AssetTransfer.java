/*
 * SPDX-License-Identifier: Apache-2.0
 */

package org.hyperledger.fabric.samples.assettransfer;

import com.owlike.genson.Genson;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contact;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.License;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;

@Contract(
        name = "basic",
        info = @Info(
                title = "Asset Transfer",
                description = "The hyperlegendary asset transfer",
                version = "0.0.1-SNAPSHOT",
                license = @License(
                        name = "Apache 2.0 License",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"),
                contact = @Contact(
                        email = "a.transfer@example.com",
                        name = "Adrian Transfer",
                        url = "https://hyperledger.example.com")))
@Default
public final class AssetTransfer implements ContractInterface {

    private final Genson genson = new Genson();

    private enum AssetTransferErrors {
        ASSET_NOT_FOUND,
        ASSET_ALREADY_EXISTS
    }

    /**
     * Creates some initial assets on the ledger.
     *
     * @param ctx the transaction context
     */
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public void init(final Context ctx) {

    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public String createBlock(final Context ctx, String type, String hash, String number,
                              String creationDate, String additionalInfo) {
        ChaincodeStub stub = ctx.getStub();
        String id = String.format("%s-BLOCK-%s", type, number);

        if (entityExists(ctx, id)) {
            String errorMessage = String.format("Block %s already exists", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_ALREADY_EXISTS.toString());
        }

        Block block = new Block();
        block.setHash(hash);
        block.setNumber(number);
        block.setCreationDate(creationDate);
        block.setAdditionalInfo(additionalInfo);

        String sortedJson = genson.serialize(block);
        stub.putStringState(id, sortedJson);

        return sortedJson;
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public String createTransaction(final Context ctx, String type, String hash, String blockHash,
                                    String creationDate, String transactionIndex, String additionalInfo) {
        ChaincodeStub stub = ctx.getStub();
        String id = String.format("%s-TRANSACTION-%s", type, blockHash);

        if (entityExists(ctx, id)) {
            String errorMessage = String.format("Transaction %s already exists", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_ALREADY_EXISTS.toString());
        }

        org.hyperledger.fabric.samples.assettransfer.Transaction transaction = new org.hyperledger.fabric.samples.assettransfer.Transaction();
        transaction.setHash(hash);
        transaction.setBlockHash(blockHash);
        transaction.setCreationDate(creationDate);
        transaction.setTransactionIndex(transactionIndex);
        transaction.setAdditionalInfo(additionalInfo);

        String sortedJson = genson.serialize(transaction);
        stub.putStringState(id, sortedJson);

        return sortedJson;
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public String createLog(final Context ctx, String type, String hash, String creationDate, String info) {
        ChaincodeStub stub = ctx.getStub();
        String id = String.format("%s-LOG-%s", type, hash);

        if (entityExists(ctx, id)) {
            String errorMessage = String.format("Asset %s already exists", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_ALREADY_EXISTS.toString());
        }

        Log log = new Log();
        log.setHash(hash);
        log.setCreationDate(creationDate);
        log.setInfo(info);

        String sortedJson = genson.serialize(log);
        stub.putStringState(id, sortedJson);

        return sortedJson;
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public boolean entityExists(final Context ctx, final String blockId) {
        ChaincodeStub stub = ctx.getStub();
        String assetJSON = stub.getStringState(blockId);

        return (assetJSON != null && !assetJSON.isEmpty());
    }
}

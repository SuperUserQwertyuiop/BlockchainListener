package com.spbstu.blockchain.listener.database.ledger.util;

import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.hyperledger.fabric.client.CommitException;
import org.hyperledger.fabric.client.CommitStatusException;
import org.hyperledger.fabric.client.Contract;
import org.hyperledger.fabric.client.EndorseException;
import org.hyperledger.fabric.client.Gateway;
import org.hyperledger.fabric.client.SubmitException;
import org.hyperledger.fabric.client.identity.Identities;
import org.hyperledger.fabric.client.identity.Identity;
import org.hyperledger.fabric.client.identity.Signer;
import org.hyperledger.fabric.client.identity.Signers;
import org.hyperledger.fabric.client.identity.X509Identity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.cert.CertificateException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class HyperledgerConnector implements Closeable {
	private static final String MSP_ID = System.getenv().getOrDefault("MSP_ID", "Org1MSP");
	private static final String CHANNEL_NAME = System.getenv().getOrDefault("CHANNEL_NAME", "mychannel");
	private static final String CHAINCODE_NAME = System.getenv().getOrDefault("CHAINCODE_NAME", "basic");

	private Path certPath;
	private Path keyDirPath;
	private Path tlsCertPath;

	private static final String PEER_ENDPOINT = "localhost:7051";
	private static final String OVERRIDE_AUTH = "peer0.org1.example.com";

	private static final Path ROOT_PATH = Paths.get("/Users/20307718/IdeaProjects/fabric-samples/test-network/organizations/peerOrganizations/org1.example.com");

	private Contract contract;
	private ManagedChannel channel;

	private ExecutorService blockSaverExecutorService;
	private ExecutorService transactionSaverExecutorService;
	private ExecutorService logSaverExecutorService;


	public HyperledgerConnector() throws Exception {
		certPath = ROOT_PATH.resolve(Paths.get("users/User1@org1.example.com/msp/signcerts/cert.pem"));
		tlsCertPath = ROOT_PATH.resolve(Paths.get("peers/peer0.org1.example.com/tls/ca.crt"));
		keyDirPath = ROOT_PATH.resolve(Paths.get("users/User1@org1.example.com/msp/keystore"));
		this.channel = newGrpcConnection();
		Gateway gateway = init();
		var network = gateway.getNetwork(CHANNEL_NAME);
		contract = network.getContract(CHAINCODE_NAME);

		blockSaverExecutorService = Executors.newFixedThreadPool(10);
		transactionSaverExecutorService = Executors.newFixedThreadPool(10);
		logSaverExecutorService = Executors.newFixedThreadPool(10);
	}

	private Gateway init() throws Exception {
		var builder = Gateway.newInstance().identity(newIdentity()).signer(newSigner()).connection(channel)
				.evaluateOptions(options -> options.withDeadlineAfter(5, TimeUnit.SECONDS))
				.endorseOptions(options -> options.withDeadlineAfter(15, TimeUnit.SECONDS))
				.submitOptions(options -> options.withDeadlineAfter(5, TimeUnit.SECONDS))
				.commitStatusOptions(options -> options.withDeadlineAfter(1, TimeUnit.MINUTES));
		return builder.connect();
	}

	private ManagedChannel newGrpcConnection() throws IOException, CertificateException {
		var tlsCertReader = Files.newBufferedReader(tlsCertPath);
		var tlsCert = Identities.readX509Certificate(tlsCertReader);

		return NettyChannelBuilder.forTarget(PEER_ENDPOINT)
				.sslContext(GrpcSslContexts.forClient().trustManager(tlsCert).build()).overrideAuthority(OVERRIDE_AUTH)
				.build();
	}

	private Identity newIdentity() throws IOException, CertificateException {
		var certReader = Files.newBufferedReader(certPath);
		var certificate = Identities.readX509Certificate(certReader);

		return new X509Identity(MSP_ID, certificate);
	}

	private Signer newSigner() throws IOException, InvalidKeyException {
		var keyReader = Files.newBufferedReader(getPrivateKeyPath());
		var privateKey = Identities.readPrivateKey(keyReader);

		return Signers.newPrivateKeySigner(privateKey);
	}

	private Path getPrivateKeyPath() throws IOException {
		try (var keyFiles = Files.list(keyDirPath)) {
			return keyFiles.findFirst().orElseThrow();
		}
	}

	public void initLedger() throws EndorseException, SubmitException, CommitStatusException, CommitException {
		contract.submitTransaction("init");
	}

	public void createBlock(String type, String hash, String number, String timestamp, String info) throws EndorseException, SubmitException, CommitStatusException, CommitException {
		blockSaverExecutorService.submit(() -> contract.submitTransaction("createBlock", type, hash, number, timestamp, info));
	}

	public void createTransaction(String type, String hash, String blockHash, String index, String creationDate, String info) {
		transactionSaverExecutorService.submit(() -> contract.submitTransaction("createTransaction", type, hash,
				blockHash, creationDate, index, info));
	}

	public void createLog(String type, String hash, String creationDate, String info) {
		logSaverExecutorService.submit(() -> contract.submitTransaction("createLog", type, hash, creationDate, info));
	}

	@Override
	public void close() throws IOException {
		try {
			channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

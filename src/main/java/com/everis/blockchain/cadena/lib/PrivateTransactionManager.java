package com.everis.blockchain.cadena.lib;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.besu.Besu;
import org.web3j.protocol.besu.response.privacy.PrivateTransactionReceipt;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthGetCode;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.eea.crypto.PrivateTransactionEncoder;
import org.web3j.protocol.eea.crypto.RawPrivateTransaction;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.exceptions.ContractCallException;
import org.web3j.tx.gas.BesuPrivacyGasProvider;
import org.web3j.tx.response.PollingPrivateTransactionReceiptProcessor;
import org.web3j.tx.response.PrivateTransactionReceiptProcessor;
import org.web3j.utils.Base64String;
import org.web3j.utils.Numeric;

import static org.web3j.utils.Restriction.RESTRICTED;
import static org.web3j.utils.RevertReasonExtractor.extractRevertReason;

public abstract class PrivateTransactionManager extends TransactionManager {
	private static final Logger log = LoggerFactory.getLogger(PrivateTransactionManager.class);

	private static final Integer ADITIONAL_GAS = 10000;
	private final PrivateTransactionReceiptProcessor transactionReceiptProcessor;

	private final Besu besu;
	private final BesuPrivacyGasProvider gasProvider;
	private final Credentials credentials;
	private final long chainId;
	private final Base64String privateFrom;

	protected PrivateTransactionManager(final Besu besu, final BesuPrivacyGasProvider gasProvider,
			final Credentials credentials, final long chainId, final Base64String privateFrom,
			final PrivateTransactionReceiptProcessor transactionReceiptProcessor) {
		super(transactionReceiptProcessor, credentials.getAddress());
		this.besu = besu;
		this.gasProvider = gasProvider;
		this.credentials = credentials;
		this.chainId = chainId;
		this.privateFrom = privateFrom;
		this.transactionReceiptProcessor = transactionReceiptProcessor;
	}

	protected PrivateTransactionManager(final Besu besu, final BesuPrivacyGasProvider gasProvider,
			final Credentials credentials, final long chainId, final Base64String privateFrom, final int attempts,
			final int sleepDuration) {
		this(besu, gasProvider, credentials, chainId, privateFrom,
				new PollingPrivateTransactionReceiptProcessor(besu, attempts, sleepDuration));
	}

	protected PrivateTransactionManager(final Besu besu, final BesuPrivacyGasProvider gasProvider,
			final Credentials credentials, final long chainId, final Base64String privateFrom) {
		this(besu, gasProvider, credentials, chainId, privateFrom,
				new PollingPrivateTransactionReceiptProcessor(besu, 3, 8));
	}

	@Override
	protected TransactionReceipt executeTransaction(BigInteger gasPrice, BigInteger gasLimit, String to, String data,
			BigInteger value) throws IOException, TransactionException {

		EthSendTransaction ethSendTransaction = sendTransaction(gasPrice, gasLimit, to, data, value);
		return processResponse(ethSendTransaction);
	}

	public Base64String getPrivateFrom() {
		return privateFrom;
	}

	protected abstract Base64String getPrivacyGroupId();

	protected abstract Object privacyGroupIdOrPrivateFor();

//	protected BigInteger getEstimateGasLimit(String to, String data) throws IOException {
//		// from, nonce, gasPrice, gasLimit, to, value, data
//		Transaction tx = new Transaction(credentials.getAddress(), null, null, null, to, null, data);
//		EthEstimateGas ethEstimateGas = besu.ethEstimateGas(tx).send();
//
//		return ethEstimateGas.getAmountUsed().add(new BigInteger(ADITIONAL_GAS.toString()));
//	}

	@SuppressWarnings("unchecked")
	@Override
	public EthSendTransaction sendTransaction(final BigInteger gasPrice, BigInteger gasLimit, final String to,
			final String data, final BigInteger value, boolean constructor) throws IOException {
			
		final BigInteger nonce = besu.privGetTransactionCount(credentials.getAddress(), getPrivacyGroupId()).send()
				.getTransactionCount();

		log.info("gasLimit ==> {}, nonce ==>{}", gasLimit, nonce);

		final Object privacyGroupIdOrPrivateFor = privacyGroupIdOrPrivateFor();

		final RawPrivateTransaction transaction;
		if (privacyGroupIdOrPrivateFor instanceof Base64String) {
			transaction = RawPrivateTransaction.createTransaction(nonce, gasPrice, gasLimit, to, data, privateFrom,
					(Base64String) privacyGroupIdOrPrivateFor, RESTRICTED);
		} else {
			transaction = RawPrivateTransaction.createTransaction(nonce, gasPrice, gasLimit, to, data, privateFrom,
					(List<Base64String>) privacyGroupIdOrPrivateFor, RESTRICTED);
		}

		final String rawSignedTransaction = Numeric
				.toHexString(PrivateTransactionEncoder.signMessage(transaction, chainId, credentials));

		return besu.eeaSendRawTransaction(rawSignedTransaction).send();
	}

	public EthSendTransaction sendTransactionEIP1559(BigInteger gasPremium, BigInteger feeCap, BigInteger gasLimit,
			String to, String data, BigInteger value, boolean constructor) throws IOException {
		final BigInteger nonce = besu.privGetTransactionCount(credentials.getAddress(), getPrivacyGroupId()).send()
				.getTransactionCount();

		final Object privacyGroupIdOrPrivateFor = privacyGroupIdOrPrivateFor();

		final RawPrivateTransaction transaction;
		if (privacyGroupIdOrPrivateFor instanceof Base64String) {
			transaction = RawPrivateTransaction.createTransactionEIP1559(nonce, gasPremium, feeCap, gasLimit, to, data,
					privateFrom, (Base64String) privacyGroupIdOrPrivateFor, RESTRICTED);
		} else {
			transaction = RawPrivateTransaction.createTransactionEIP1559(nonce, gasPremium, feeCap, gasLimit, to, data,
					privateFrom, (List<Base64String>) privacyGroupIdOrPrivateFor, RESTRICTED);
		}

		final String rawSignedTransaction = Numeric
				.toHexString(PrivateTransactionEncoder.signMessage(transaction, chainId, credentials));

		return besu.eeaSendRawTransaction(rawSignedTransaction).send();
	}

	@Override
	public String sendCall(final String to, final String data, final DefaultBlockParameter defaultBlockParameter)
			throws IOException {
		try {
			EthSendTransaction est = sendTransaction(gasProvider.getGasPrice(), gasProvider.getGasLimit(), to, data,
					BigInteger.ZERO);
			final TransactionReceipt ptr = processResponse(est);

			if (!ptr.isStatusOK()) {
				throw new ContractCallException(
						String.format(REVERT_ERR_STR, extractRevertReason(ptr, data, besu, false)));
			}
			return ((PrivateTransactionReceipt) ptr).getOutput();
		} catch (TransactionException e) {
			log.error("Failed to execute call", e);
			return null;
		}
	}

	private TransactionReceipt processResponse(final EthSendTransaction transactionResponse)
			throws IOException, TransactionException {
		if (transactionResponse.hasError()) {
			throw new RuntimeException(
					"Error processing transaction request: " + transactionResponse.getError().getMessage());
		}

		final String transactionHash = transactionResponse.getTransactionHash();

		return transactionReceiptProcessor.waitForTransactionReceipt(transactionHash);
	}

	@Override
	public EthGetCode getCode(final String contractAddress, final DefaultBlockParameter defaultBlockParameter)
			throws IOException {
		return this.besu.privGetCode(this.getPrivacyGroupId().toString(), contractAddress, defaultBlockParameter)
				.send();
	}
}

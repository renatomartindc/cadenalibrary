package com.everis.blockchain.cadena;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.protocol.besu.Besu;
import org.web3j.protocol.besu.response.privacy.PrivGetTransactionReceipt;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthBlock.Block;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.BesuPrivateTransactionManager;
import org.web3j.tx.gas.BesuPrivacyGasProvider;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Base64String;
import org.web3j.utils.Strings;

import com.everis.blockchain.cadena.lib.SslUtils;
import com.everis.blockchain.cadena.lib.Utils;
import com.everis.blockchain.cadena.lib.Web3;

import io.reactivex.Flowable;
import io.reactivex.annotations.Nullable;
import okhttp3.OkHttpClient;

public class EthCore {

	private static final Logger log = LoggerFactory.getLogger("EthCore");

  //  private static final Integer ADITIONAL_GAS = 10000; 
	private Web3 web3;
	private Besu besu;
	private Credentials credentials;
	private String networkId;
	private Base64String privacyGroupId;
	private Base64String privateFrom;
	private BesuPrivateTransactionManager transactionManager;
	private BigInteger gasPrice;
	private BigInteger gasLimit;

	public EthCore(EthCoreParams params) {
		this.web3 = Web3.build(new HttpService(params.host, createOkHttpClient(), true));
		initVars(params);
	}

	public EthCore(EthCoreParams params, String privGroupId, String from) {
		this.besu = Besu.build(new HttpService(params.host, createOkHttpClient(), true));
		this.web3 = Web3.build(new HttpService(params.host, createOkHttpClient(), true));

		initVars(params);
		this.privacyGroupId = Base64String.wrap(privGroupId);
		this.privateFrom = Base64String.wrap(from);

		this.transactionManager = new BesuPrivateTransactionManager(besu, getDefaultBesuPrivacyGasProvider(),
				credentials, Long.parseLong(networkId), privateFrom, privacyGroupId);

	}
	
	

	public Block getLastBlock() throws IOException {
		return web3.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send().getBlock();

	}

	private void initVars(EthCoreParams params) {
		try {
			String privateKey = generatePrivateKey(params);
			credentials = Credentials.create(privateKey);
			networkId = web3.netVersion().send().getResult();
			// host = params.host;
			gasPrice = params.gasPrice != null ? params.gasPrice : getGasPrice();
			gasLimit = params.gasLimit != null ? params.gasLimit : getGasLimit();
		} catch (Exception e) {
			log.error("Error.initVars", e);
			log.error(e.getMessage());
		}
	}

	private String generatePrivateKey(EthCoreParams params) {
		String privateKey = null;
		try {
			privateKey = params.privateKey != null ? params.privateKey : createCredentials(params.seed).privateKey;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return privateKey;
	}

	public Web3 getWeb3jInstance() {
		return web3;
	}

	Besu getBesuInstance() {
		return besu;
	}

	BesuPrivateTransactionManager getTransactionManagerBesu() {
		return transactionManager;
	}

	public ContractGasProvider getDefaultContractGasProvider() throws IOException {
		ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);
		return contractGasProvider;
	}

	BesuPrivacyGasProvider getDefaultBesuPrivacyGasProvider() {
		BesuPrivacyGasProvider besuPrivacyGasProvider = new BesuPrivacyGasProvider(gasPrice, gasLimit);
		return besuPrivacyGasProvider;
	}

	public Account createCredentials(String seed) throws CipherException, InvalidAlgorithmParameterException,
			NoSuchAlgorithmException, NoSuchProviderException {
		ECKeyPair ecKeyPair = Keys.createEcKeyPair();
		BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
		BigInteger publicKeyInDec = ecKeyPair.getPublicKey();
		String privatekeyInHex = privateKeyInDec.toString(16);
		String publickeyInHex = publicKeyInDec.toString(16);
		WalletFile wallet = Wallet.createLight(seed, ecKeyPair);
		String address = wallet.getAddress();
		Account account = new Account("0x" + address, "0x" + privatekeyInHex, publickeyInHex);
		return account;
	}

	public void updatePrivateKey(String privateKey) {
		credentials = Credentials.create(privateKey);
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public String getNetworkId() {
		return networkId;
	}

	public BigInteger getGasPrice() throws IOException {
		EthGasPrice ethGasPrice = web3.ethGasPrice().send();
		BigInteger gasPrice = ethGasPrice.getGasPrice();
		return gasPrice;
	}

	public BigInteger getGasLimit() throws IOException {
		BigInteger blockGasLimit = web3.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send().getBlock()
				.getGasLimit();
		return blockGasLimit;
	}

	public BigInteger getNonce(@Nullable String address) throws InterruptedException, ExecutionException {
		String addressAccount = address != null ? address : credentials.getAddress();
		EthGetTransactionCount ethGetTransactionCount = web3
				.ethGetTransactionCount(addressAccount, DefaultBlockParameterName.LATEST).sendAsync().get();
		BigInteger nonce = ethGetTransactionCount.getTransactionCount();
		return nonce;
	}

	public BigInteger getPrivNonce(@Nullable String address) throws IOException {
		String addressAccount = address != null ? address : credentials.getAddress();
		BigInteger nonce = besu.privGetTransactionCount(addressAccount, privacyGroupId).send().getTransactionCount();
		return nonce;
	}

	public PrivGetTransactionReceipt getPrivTransactionReceipt(String txHash, int retries) {
		PrivGetTransactionReceipt transactionReceipt = null;
		int cont = 0;
		while (cont <= retries) {
			try {
				transactionReceipt = besu.privGetTransactionReceipt(txHash).send();
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Error:privGetTransactionReceipt", e);
			}
			if (transactionReceipt != null && transactionReceipt.getResult() != null) {
				break;
			}
//			try {
//				if (sleep)
//					Thread.sleep(1 * 1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			cont++;

		}
		return transactionReceipt;
	}

	public PrivGetTransactionReceipt getPrivTransactionReceiptForSave(String txHash, int retries)
			throws IOException, InterruptedException {
		PrivGetTransactionReceipt transactionReceipt = null;
		int cont = 0;
		while (cont <= retries) {
			transactionReceipt = besu.privGetTransactionReceipt(txHash).send();
			if (transactionReceipt.getResult() != null) {
				break;
			}
			cont++;
			Thread.sleep(1 * 1000);
		}
		return transactionReceipt;
	}

	public Flowable<Transaction> subscribeLogsEventContract() {
		return web3.transactionFlowable();
	}

	public Flowable<Transaction> replayPastTransactionsFlowable(BigInteger startBlock, BigInteger endBlock) {
		return web3.replayPastTransactionsFlowable(DefaultBlockParameter.valueOf(startBlock),
				DefaultBlockParameter.valueOf(endBlock));
	}

	public Flowable<Transaction> replayPastAndFutureTransactionsFlowable(BigInteger blockNumber) throws IOException {

		return web3.replayPastAndFutureTransactionsFlowable(DefaultBlockParameter.valueOf(blockNumber));
	}

	public void getLogsWatch(BigInteger fromBlock, @Nullable BigInteger toBlock, int retries, String from,
			String[] events, List<Cadena> listCadena, IObserverCertificate observer) {
		try {
			Block lastBlock = web3.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send().getBlock();

			BigInteger toblock = toBlock != null ? toBlock : lastBlock.getNumber();

			for (long i = fromBlock.longValue(); i <= toblock.longValue(); i++) {
				// System.out.println("block=="+i);
				try {
					EthBlock block = web3.ethGetBlockByNumber(new DefaultBlockParameterNumber(i), false).send();

					if (block != null && block.getResult() != null) {
						block.getResult().getTransactions().forEach(tr -> {

							PrivGetTransactionReceipt receipt = getPrivTransactionReceipt((String) tr.get(), retries);

							if (receipt != null && receipt.getResult() != null
									&& !from.equals(receipt.getResult().getFrom())) {

								filterLogsCertificates(receipt.getResult().getLogs(), listCadena, observer, events,
										retries);
							}
						});
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void filterLogsCertificates(List<Log> logs, List<Cadena> listCadena, IObserverCertificate observer,
			String[] events, int retries) {

		for (Log logC : logs) {
			for (String event : events) {

				if (logC.getTopics().get(0).equals(Utils.shaKeccak256(event))) {
					getListTin(logC, listCadena, observer, retries);
				}
			}
		}
	}

	private void getListTin(Log logC, List<Cadena> listCadena, IObserverCertificate observer, int retries) {

		try {
			String tin = null;

			if (logC.getTopics().get(0).equals(Utils.shaKeccak256("CertificateAdded(string,address)"))) {
				tin = new String(DatatypeConverter.parseHexBinary(logC.getData().substring(2))).trim();
			} else {

				String TINHex = logC.getData().substring(130);// 240
				tin = new String(DatatypeConverter.parseHexBinary(TINHex)).trim();
			}
			watchObserver(tin.trim(), listCadena, observer, retries);

		} catch (Exception e) {
			log.error("error.getValue.TIN {}", e);
		}

	}

	private void watchObserver(String tin, List<Cadena> listCadena, IObserverCertificate observer, int retries) {

		listCadena.forEach(c -> {
			int i = 0;
			while (i <= retries) {
				try {
					Certificate cet = c.getCertificate(tin);

					if (!Strings.isEmpty(cet.getData())) {
						observer.watchCertificates(cet);
					}
					break;
				} catch (Exception e) {
					i++;
					if (i > 2)
						e.printStackTrace();
				}
			}
		});

	}

	private OkHttpClient createOkHttpClient() {
		OkHttpClient.Builder builder = SslUtils.trustAllSslClient();
		// builder.retryOnConnectionFailure(true);
		// configureLogging(builder);
		configureTimeouts(builder);
		return builder.build();
	}

	private void configureTimeouts(OkHttpClient.Builder builder) {
		long tos = 90;

		builder.connectTimeout(tos, TimeUnit.SECONDS);
		builder.readTimeout(tos, TimeUnit.SECONDS); // Sets the socket timeout too
		builder.writeTimeout(tos, TimeUnit.SECONDS);

	}
	/*
	 * private static void configureLogging(OkHttpClient.Builder builder) { if
	 * (log.isDebugEnabled()) { HttpLoggingInterceptor logging = new
	 * HttpLoggingInterceptor(log::debug);
	 * logging.setLevel(HttpLoggingInterceptor.Level.BODY);
	 * builder.addInterceptor(logging); } }
	 */

	void closeConnection() {
		web3.shutdown();
		besu.shutdown();
	}

	
//	 public BigInteger getEstimateGasLimit(String to, String data) throws IOException {
//			EthEstimateGas ethEstimateGas = web3.ethEstimateGas(credentials.getAddress(), credentials.getAddress(), data).send();
//			return ethEstimateGas.getAmountUsed().add(new BigInteger(ADITIONAL_GAS.toString()));
//	}
}

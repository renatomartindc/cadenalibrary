package com.everis.blockchain.cadena.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.concurrent.ExecutionException;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;

import com.everis.blockchain.cadena.Account;
import com.everis.blockchain.cadena.EthCore;
import com.everis.blockchain.cadena.EthCoreParams;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EthCoreTest {

	private EthCoreParams ethCoreParams = new EthCoreParams("http://eth-lacchain.mytrust.id", "0xf90bdb5081479c300e94e4aa0a6aed68b9b6a13d8185f8f9382872494ff938db", null, BigInteger.valueOf(0), BigInteger.valueOf(4000000));
	private EthCore ethCore = new EthCore(ethCoreParams);
	private static final Logger log = LoggerFactory.getLogger(EthCoreTest.class);

	//@Test
	public void privateToAddress() {
		Credentials credentials = ethCore.getCredentials();
		assertEquals(credentials.getAddress(), "0xf2843dc9abe46e64e9a68c52adbce6593affb1b9");
	}

	//@Test
	public void networkId() {
		assertEquals(ethCore.getNetworkId(), "648529");
	}

	//@Test
	public void gasPrice() throws IOException {
		assertNotNull(ethCore.getGasPrice());
	}

	//@Test
	public void gasGasLimit() throws IOException {
		assertNotNull(ethCore.getGasLimit());
	}

	//@Test
	public void getNonce() throws InterruptedException, ExecutionException {
		String address = "0x189cf6D846793613aE8Af2F11190F28FDBAEd905";
		assertTrue(ethCore.getNonce(address).intValue() > 0);
		assertTrue(ethCore.getNonce(null).intValue() > 0);
	}

	//@Test
	public void createCredentials() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, CipherException {
		Account account = ethCore.createCredentials("");
		log.info("PrivateKey: " + account.privateKey);
		//log.info("PublicKey: " + account.publicKey);
		log.info("Address: " + account.address);
		assertNotNull(account.address);
	}

	//@Test
	public void updateCredentials() {
		ethCore.updatePrivateKey("0x970602f6ed1be83d5bb6b8831a43577d6ab37e447324d8383b63c040de18a35a");
		Credentials credentials = ethCore.getCredentials();
		assertEquals(credentials.getAddress(), "0x6fcb262de46b6057d41a9627d6e309db7e807147");
	}

	// Test contract
	//@Test
	/*public void getInteger() throws Exception {
		com.everis.blockchain.cadena.contracts.Test testContract = com.everis.blockchain.cadena.contracts.Test.load("0xb8A5e80EC309d80CDbF9A0A0Bd4d37C44607FD96", ethCore.getWeb3jInstance(), ethCore.getCredentials(), ethCore.getDefaultContractGasProvider());
		BigInteger result = testContract.getInteger().send();
		assertEquals(result, BigInteger.valueOf(3));
	}

	//@Test
	public void setTest() throws Exception {
		com.everis.blockchain.cadena.contracts.Test testContract = com.everis.blockchain.cadena.contracts.Test.load("0xb8A5e80EC309d80CDbF9A0A0Bd4d37C44607FD96", ethCore.getWeb3jInstance(), ethCore.getCredentials(), ethCore.getDefaultContractGasProvider());
		TransactionReceipt transactionReceipt = testContract.setTest("Test Library Java").send();
		assertEquals(transactionReceipt.getStatus(), "0x1");
	}*/
	
	////@Test
	/*public void getLogsEventContract() throws IOException {
		List<LogResult> logs = ethCore.getLogsEventContract(BigInteger.valueOf(2424416), null, "0x5A80e4AB7864EFACFC9aBb932dcFeCc4a49099ab", null);
		/* for (LogResult item : logs) {
            LogObject i = (LogObject) item;
            log.info(i.getAddress());
            log.info(i.getType());
		} 
		assertFalse(logs.isEmpty());
	}*/

	////@Test
	/*public void getLogsEvent() throws IOException {
		Test1 test1 = Test1.load("0x81b2f280717B410A10Dc88e17415B3e51E2eDeBA", ethCore.getWeb3jInstance(), ethCore.getCredentials(), ethCore.getDefaultContractGasProvider());
		List<Log> logs = ethCore.getLogsEvent("2424416", null, test1.getContractAddress(), "TestEvent2(string,string)", "0xb3a65a402a6c0efb49c17882119d860bfd1f7e5b48dee78510d9cba554a7128b", "0xa16fa20ec8ff2a1b2d70bd72ba46ab472920df790d5093669067302c07677a8d");
		assertFalse(logs.isEmpty());
	}*/

}
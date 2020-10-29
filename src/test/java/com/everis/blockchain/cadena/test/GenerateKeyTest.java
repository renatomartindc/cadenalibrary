package com.everis.blockchain.cadena.test;

import java.math.BigInteger;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.everis.blockchain.cadena.Account;
import com.everis.blockchain.cadena.EthCore;
import com.everis.blockchain.cadena.EthCoreParams;

public class GenerateKeyTest {

	private static final Logger log = LoggerFactory.getLogger(GenerateKeyTest.class);

	private String ipTlsNodoAduna = "PASTE_HERE_CORRECT_VALUE"; //<-- Reemplazar por el IP_NODE del nodo BESU de la aduana.

	@Test
	public void generateKeyPrivate() throws Exception {

		EthCoreParams ethCoreParams = new EthCoreParams(ipTlsNodoAduna,
				"0xf90bdb5081479c300e94e4aa0a6aed68b9b6a13d8185f8f9382872494ff938db", null, BigInteger.valueOf(0),
				BigInteger.valueOf(4000000));
		EthCore ethCore = new EthCore(ethCoreParams);
		Account account = ethCore.createCredentials("");
		String privateKeyAduana = account.privateKey;
		String addressPublicAduana = account.address;

		log.info("privateKeyAduana       " + privateKeyAduana);
		log.info("addressPublicAduana    " + addressPublicAduana);
	
	}
}

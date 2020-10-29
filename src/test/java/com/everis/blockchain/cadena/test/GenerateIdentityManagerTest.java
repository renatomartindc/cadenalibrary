package com.everis.blockchain.cadena.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.utils.Strings;

import com.everis.blockchain.cadena.Aeo;
import com.everis.blockchain.cadena.Cadena;
import com.everis.blockchain.cadena.DigitalIdentity;
import com.everis.blockchain.cadena.EthCoreParams;
import com.everis.blockchain.cadena.Verificationregistry;

public class GenerateIdentityManagerTest {

	private static final Logger log = LoggerFactory.getLogger(GenerateIdentityManagerTest.class);

	private String contractAddressVR = "0x9f8c1e196F5696e014F4d1E4961B92db866BE271";
	private String contractAddressDIM = "0xa6b4540a2bfbe8663caa78027c83d0dcb1b7c837";

	private String ipTlsAduana  = "PASTE_HERE_CORRECT_VALUE"; //<-- Reemplazar por el IP_NODE del nodo BESU de la aduana.
	private String privateKeyAduana = "PASTE_HERE_CORRECT_VALUE"; //<-- Reemplazar por la llave privada generada para el backend de la aduana.
	
	String contractAddressAEOM = "";
	EthCoreParams ethCoreParams;
	Verificationregistry VR;
	DigitalIdentity DIM;
	Aeo AEOM;
	String proxyAddressAduana = "";
	String proxyAddressAdministrator = "";
	Cadena cadena;

	@Test
	public void deployGeneralContracts() throws Exception {

		ethCoreParams = new EthCoreParams(ipTlsAduana , privateKeyAduana, null, BigInteger.valueOf(0), BigInteger.valueOf(4000000));

		deployVRorSet(ethCoreParams);

		deployIMorSet(ethCoreParams);

		createIdentity();

		deployAEOM();

		addRoleUserAEOManager();

		setForward();

		checkForward();

	}
	
	public void addRoleUserAEOManager() throws Exception {

		String txHash = DIM.addRoleUserAEOManager(proxyAddressAdministrator, contractAddressAEOM,
				proxyAddressAdministrator, "ROLE_2");
		txHash = DIM.addRoleUserAEOManager(proxyAddressAdministrator, contractAddressAEOM, proxyAddressAdministrator,
				"ROLE_3");
		assertThat(txHash, containsString("0x"));
	}
	

	public void checkForward() throws Exception {
		String cap = "fw";
		Boolean hasCap = DIM.checkCap(proxyAddressAduana, contractAddressAEOM, cap);
		assertTrue(hasCap);
	}

	public void setForward() throws Exception {
		String cap = "fw";
		Date date = new Date();
		long time = date.getTime() / 1000;
		String txHash = DIM.setCap(proxyAddressAduana, contractAddressAEOM, cap, BigInteger.valueOf(time),
				BigInteger.valueOf(0));
		assertThat(txHash, containsString("0x"));
	}

	
	public void deployAEOM() throws Exception {
		Aeo aeold = new Aeo("0x3edf0e5b55a3b146faf19e6fed5ff1c5c9cb8904", ethCoreParams);

		contractAddressAEOM = aeold.deployAEO(proxyAddressAduana, proxyAddressAdministrator);
		log.info("contractAddressAEOM: " + contractAddressAEOM);
		AEOM = new Aeo(contractAddressAEOM, ethCoreParams);

	}

	public void createIdentity() throws Exception {
		String keyMnemonic = "admin";
		String keyProfile = "public";
		String urlProfile = "Qmxxx";
		String username = "test";
		String salt = "salt";
		proxyAddressAduana = DIM.createIdentity(keyMnemonic, keyProfile, urlProfile, username, salt);
		log.info("proxyAddressAduana: " + proxyAddressAduana);

		proxyAddressAdministrator = DIM.createIdentity(keyMnemonic, keyProfile, urlProfile, username, salt);
		log.info("proxyAddressAdministrator: " + proxyAddressAdministrator);

	}

	public void deployVRorSet(EthCoreParams ethCoreParams) throws Exception {
		if (Strings.isEmpty(contractAddressVR)) {
			// create VerificationRegistry
			Verificationregistry VRold = new Verificationregistry(null, ethCoreParams);
			contractAddressVR = VRold.deployVR();
		}

		log.info("contractAddressVR =" + contractAddressVR);

		VR = new Verificationregistry(contractAddressVR, ethCoreParams);

	}

	private void deployIMorSet(EthCoreParams ethCoreParams) throws Exception {
		if (Strings.isEmpty(contractAddressDIM)) {
			// create IM
			DigitalIdentity DIold = new DigitalIdentity(null, ethCoreParams);
			contractAddressDIM = DIold.deployIM();
		}

		log.info("contractAddressIM =" + contractAddressDIM);

		DIM = new DigitalIdentity(contractAddressDIM, ethCoreParams);

	}
}

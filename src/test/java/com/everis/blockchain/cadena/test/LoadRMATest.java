package com.everis.blockchain.cadena.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.everis.blockchain.cadena.Cadena;
import com.everis.blockchain.cadena.EthCoreParams;

public class LoadRMATest {
	private static final Logger log = LoggerFactory.getLogger(LoadRMATest.class);

	private String ipNodoAduanaA = "PASTE_HERE_CORRECT_VALUE";
	private String privateKeyAduanaA = "PASTE_HERE_CORRECT_VALUE";
	private String addressPublicAduanaA = "PASTE_HERE_CORRECT_VALUE";
	private String fromNodeAduanaA = "PASTE_HERE_CORRECT_VALUE";
	private boolean flagVisibilityAduanaA = true;

	private String contractAddressRMA = "";
	private String privacyGroupId = "PASTE_HERE_CORRECT_VALUE";

	private String ipNodoAduanaB = "PASTE_HERE_CORRECT_VALUE";
	private String privateKeyAduanaB = "PASTE_HERE_CORRECT_VALUE";
	private String addressPublicAduanaB = "PASTE_HERE_CORRECT_VALUE";
	private String fromNodeAduanaB = "PASTE_HERE_CORRECT_VALUE";
	private boolean flagVisibilityAduanaB = true;

	/////////////////////////////////////

	EthCoreParams ethCoreParams;

	Cadena cadena;

	@Test
	public void deployARMaduanaA() throws Exception {
		ethCoreParams = new EthCoreParams(ipNodoAduanaA, privateKeyAduanaA, null, BigInteger.valueOf(0),
				BigInteger.valueOf(4000000));

		cadena = new Cadena(null, ethCoreParams, privacyGroupId, fromNodeAduanaA);
		contractAddressRMA = cadena.getAddressMRA();

		log.info("contractAddressRMA : " + contractAddressRMA);

		cadena = new Cadena(contractAddressRMA, ethCoreParams, privacyGroupId, fromNodeAduanaA);
		String txHash = cadena.approveMember(addressPublicAduanaB);
		log.info("txHash : " + txHash);

		BigInteger totalMembers = cadena.totalMembers();
		log.info("totalMembers " + totalMembers);

		cadena.setVisibilityToMember(addressPublicAduanaB, flagVisibilityAduanaA);


		boolean visibility = cadena.getVisibility(addressPublicAduanaA, addressPublicAduanaB);

		log.info("addressPublicAduanaA ->addressPublicAduanaB  : " + visibility);

		visibility = cadena.getVisibility(addressPublicAduanaB, addressPublicAduanaA);

		log.info("addressPublicAduanaB ->addressPublicAduanaA  : " + visibility);


		log.info("acceptance : " + cadena.isMember(addressPublicAduanaB));

		//Boolean isMember = cadena.isMember(addressPublicAduanaB);
		//assertTrue(isMember);
		 assertThat(txHash, containsString("0x"));
	}

	@Test
	public void approveARMaduanaB() throws Exception {
		ethCoreParams = new EthCoreParams(ipNodoAduanaB, privateKeyAduanaB, null, BigInteger.valueOf(0),
				BigInteger.valueOf(4000000));

		cadena = new Cadena(contractAddressRMA, ethCoreParams, privacyGroupId, fromNodeAduanaB);
 		String txHash = cadena.approveMember(addressPublicAduanaB);
 		log.info("txHash : " + txHash);

		BigInteger totalMembers = cadena.totalMembers();
		log.info("totalMembers " + totalMembers);

		cadena.setVisibilityToMember(addressPublicAduanaA, flagVisibilityAduanaB);


		boolean visibility = cadena.getVisibility(addressPublicAduanaA, addressPublicAduanaB);

		log.info("addressPublicAduanaA ->addressPublicAduanaB  : " + visibility);

		visibility = cadena.getVisibility(addressPublicAduanaB, addressPublicAduanaA);

		log.info("addressPublicAduanaB ->addressPublicAduanaA  : " + visibility);

		Boolean isMemberA = cadena.isMember(addressPublicAduanaA);
		log.info("acceptance A : " + isMemberA);
		 
		log.info("acceptance B : " + cadena.isMember(addressPublicAduanaB));

	
		assertTrue(isMemberA);

	}

}

package com.everis.blockchain.cadena.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.everis.blockchain.cadena.Aeo;
import com.everis.blockchain.cadena.Cadena;
import com.everis.blockchain.cadena.Certificate;
import com.everis.blockchain.cadena.EthCoreParams;
import com.everis.blockchain.cadena.Verificationregistry;
import com.everis.blockchain.cadena.lib.Utils;

public class LoadRMATestCOMX {
	private static final Logger log = LoggerFactory.getLogger(LoadRMATestCOMX.class);

	private String ipNodoAduanaA = "http://localhost:8547";//colombia
	private String privateKeyAduanaA = "0x123ecd2cccbe2ca6d705d4133145ed3062e276d2a8724616de1f5791c2bbe8e";
	private String addressPublicAduanaA = "0x55f28627ee21ca218725790d7a74c7b231cd9a34";
	private String fromNodeAduanaA = "HzrdZbdTzG9UxtkIpUv8uH1LaVcQUxbGeXfG6yev7WM=";
	private boolean flagVisibilityAduanaA = true;

	private String contractAddressRMA = "0x279d060896b0fe86feeaa305cac16bbbd8ed31d0";
	private String privacyGroupId = "TK3XY40sGrP3LOGsBwC6v8bsIMfd6/kUNX42hfDKZWw=";

	private String ipNodoAduanaB = "http://localhost:8547";
	private String privateKeyAduanaB = "0x7802b5bc279ff3f27bd0ca1295bede8ab1a9757e1f016bc41dc4d54b4a902602";
	private String addressPublicAduanaB = "0xee246cb1e6f25b753d12261b8151a222e5022b30";
	private String fromNodeAduanaB = "ErsINIuH1go6pJaHOgXu8yz5ABFG669gOtkwq8qI3CE=";
	private boolean flagVisibilityAduanaB = false;

	EthCoreParams ethCoreParams = new EthCoreParams("http://localhost:8547", "0xdb32769684b7ede7c9adb56cf47e5acd008ebaf87fd5e63e9ef92e9401c8e825", null, BigInteger.valueOf(0), BigInteger.valueOf(4000000));
	  private String IM = "0xa6b4540a2bfbe8663caa78027c83d0dcb1b7c837";
	  
	private Verificationregistry VR ;
	 private Aeo aeo = null;
	{
	 try {
		VR = new Verificationregistry("0x9f8c1e196F5696e014F4d1E4961B92db866BE271", ethCoreParams);
		   ethCoreParams = new EthCoreParams("http://localhost:8547", "0x123ecd2cccbe2ca6d705d4133145ed3062e276d2a8724616de1f5791c2bbe8e", null, BigInteger.valueOf(0), BigInteger.valueOf(4000000));
			aeo = new Aeo("0x87f0e2664f41fd047395ce2606247a5354f9a6f7", ethCoreParams);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	/////////////////////////////////////


	Cadena cadena;

	//@Test
	public void deployARMaduanaA() {
		try {
			ethCoreParams = new EthCoreParams(ipNodoAduanaA, privateKeyAduanaA, null, BigInteger.valueOf(0),
					BigInteger.valueOf(4000000));

			cadena = new Cadena(null, ethCoreParams, privacyGroupId, fromNodeAduanaA);
			contractAddressRMA = cadena.getAddressMRA();

			log.info("contractAddressRMA : {}", contractAddressRMA);
			cadena = new Cadena(contractAddressRMA, ethCoreParams, privacyGroupId, fromNodeAduanaA);
			
			String txHash = cadena.approveMember(addressPublicAduanaB);
			log.info("txHash : {}", txHash);

			BigInteger totalMembers = cadena.totalMembers();
			log.info("totalMembers {}", totalMembers);

			cadena.setVisibilityToMember(addressPublicAduanaB, flagVisibilityAduanaA);

			boolean visibility = cadena.getVisibility(addressPublicAduanaA, addressPublicAduanaB);

			log.info("addressPublicAduanaA ->addressPublicAduanaB {} : " + visibility);
			visibility = cadena.getVisibility(addressPublicAduanaB, addressPublicAduanaA);

			log.info("addressPublicAduanaB ->addressPublicAduanaA {} : " + visibility);
			log.info("acceptance : {}" + cadena.isMember(addressPublicAduanaB));

			// Boolean isMember = cadena.isMember(addressPublicAduanaB);
			// assertTrue(isMember);
			assertThat(txHash, containsString("0x"));
		} catch (Exception e) {
			log.error("Error.deployARMaduanaA", e);
			fail();
		}
	}

	// @Test
	public void approveARMaduanaB() {
		try {
			ethCoreParams = new EthCoreParams(ipNodoAduanaB, privateKeyAduanaB, null, BigInteger.valueOf(0),
					BigInteger.valueOf(4000000));

			cadena = new Cadena(contractAddressRMA, ethCoreParams, privacyGroupId, fromNodeAduanaB);
//
			BigInteger totalMembers = cadena.totalMembers();
//
			while (totalMembers.intValue() <= 1) {
				String txHash = cadena.approveMember(addressPublicAduanaB);
				log.info("txHash : " + txHash);
				Thread.sleep(5000);
				totalMembers = cadena.totalMembers();
				log.info("m: {}", totalMembers);
			}

			log.info("totalMembers " + totalMembers);
//
			cadena.setVisibilityToMember(addressPublicAduanaA, flagVisibilityAduanaB);
			boolean visibility = cadena.getVisibility(addressPublicAduanaA, addressPublicAduanaB);

			log.info("addressPublicAduanaA ->addressPublicAduanaB  : " + visibility);

			visibility = cadena.getVisibility(addressPublicAduanaB, addressPublicAduanaA);
			log.info("addressPublicAduanaB ->addressPublicAduanaA  : " + visibility);
			Boolean isMemberA = cadena.isMember(addressPublicAduanaA);
			log.info("acceptance A : " + isMemberA);
			log.info("acceptance B : " + cadena.isMember(addressPublicAduanaB));
			assertTrue(isMemberA && (totalMembers.intValue() == 2));
		} catch (Exception e) {
			log.error("Error.approveARMaduanaB", e);
			fail();
		}

	}

	//@Test
	public void sendOEA() {
		try {

			String oea = "{\"shortName\":\"16 OCT\",\"fullName\":\"empresa prueba 16 OCT\",\"certificateDate\":\"16/10/2020\",\"valideDate\":\"16/10/2021\",\"tradeIdentificationNumber\":\"COTIN0020\",\"typeOperator\":\"CB\",\"typeIndustryCompany\":\"servicios\",\"numberCertificateOEA\":\"TIN0010\",\"companyAddress\":[{\"streetAddress\":\"\",\"country\":\"CO\",\"postalCode\":\"\",\"state\":\"\",\"city\":\"\"}],\"visible\":true}";

			aeo.registerAEO(Utils.calculateHashString(oea));
		
			
			aeo.approveAEO(Utils.calculateHashString(oea), BigInteger.valueOf(0), IM, "0x9f8c1e196F5696e014F4d1E4961B92db866BE271");
			
			ethCoreParams = new EthCoreParams(ipNodoAduanaB, privateKeyAduanaB, null, BigInteger.valueOf(0),
					BigInteger.valueOf(4000000));

			cadena = new Cadena(contractAddressRMA, ethCoreParams, privacyGroupId, fromNodeAduanaB);

			String tx = cadena.addCertificate("COTIN0020", oea, "10", "new cetificate oea", true);

			assertThat(tx, containsString("0x"));
		} catch (Exception e) {
			log.error("Error.approveARMaduanaB", e);
			fail();
		}

	}
	
	//@Test
	public void getOEA() {
		try {

			
			ethCoreParams = new EthCoreParams(ipNodoAduanaA, privateKeyAduanaA, null, BigInteger.valueOf(0),
					BigInteger.valueOf(4000000));

			cadena = new Cadena(contractAddressRMA, ethCoreParams, privacyGroupId, fromNodeAduanaA);

			Certificate oea = cadena.getCertificate("COTIN0020");
			
			System.out.println(oea.getData());
			
			List<BigInteger> validDates = VR.getDatesVerification(oea.getData(), "0x55f28627ee21ca218725790d7a74c7b231cd9a34");
			   
			
			assertNotNull(oea);

		} catch (Exception e) {
			log.error("Error.approveARMaduanaB", e);
			fail();
		}

	}

}

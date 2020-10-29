package com.everis.blockchain.cadena.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.utils.Numeric;

import com.everis.blockchain.cadena.DigitalIdentity;
import com.everis.blockchain.cadena.EthCoreParams;

public class DigitalIdentityTest {

    EthCoreParams ethCoreParams = new EthCoreParams("http://localhost:8547", "0x123ecd2cccbe2ca6d705d4133145ed3062e276d2a8724616de1f5791c2bbe8e", null, BigInteger.valueOf(0), BigInteger.valueOf(4000000));
    private DigitalIdentity IM;
    private String aduana = "0xe0ef4c43eeedefa560b2cad5923e3c6d85c4e075";
    private String AEO = "0x62555a84f124e34d59bbc8cb119cce60d959e6a7";

    public DigitalIdentityTest() throws IOException, Exception{
    	IM = new DigitalIdentity("0xa6b4540a2bfbe8663caa78027c83d0dcb1b7c837", ethCoreParams);
    }
    /////
    private String user = "0x33785af17f824b3f64751c0676c8aa385e9e42a6";
    private String VR = "0x9f8c1e196F5696e014F4d1E4961B92db866BE271";
	private static final Logger log = LoggerFactory.getLogger(DigitalIdentityTest.class);
	String certificate = "{\"@context\":[\"https://www.w3.org/2018/credentials/v1\",\"https://schema.org/Organization\"],\"type\":[\"VerifiableCredential\",\"AuthorizedEconomicOperatorCredential\"],\"issuer\":\"did:ev:cwMLNwYaVNAaJttfCDQ5enbwUCvTdEjDGNorx\",\"issuanceDate\":\"2020-05-14T00:00:00Z\",\"credentialSubject\":{\"id\":\"urn:tin:COoea20102\",\"alternateName\":\"oea20102s\",\"legalName\":\"oea20102s\",\"issuanceDate\":\"2020-05-14T00:00:00Z\",\"taxID\":\"COoea20102\",\"additionalType\":\"AT\",\"additionalTypeIndustry\":\"oea20102\",\"referenceNumber\":\"oea20102\",\"location\":[{\"streetAddress\":\"oea20102\",\"country\":\"CO\",\"postalCode\":\"oea20102\",\"state\":\"CO-AMA\",\"city\":\"oea20102\"}],\"contactPoint\":[],\"visible\":true,\"locationCreated\":\"CO\"},\"proof\":{\"type\":\"EthereumAttestationRegistry2019\",\"networkId\":\"0x9e551\",\"contractAddress\":\"0x9f8c1e196F5696e014F4d1E4961B92db866BE271\"}}";
    
	 // @Test
    public void approveAEO() throws Exception {
       // log.info(Utils.calculateHashString("{\"context\":[\"https://www.w3.org/2018/credentials/v1\",\"https://schema.org/Organization\"],\"type\":[\"VerifiableCredential\",\"AuthorizedEconomicOperatorCredential\"],\"issuer\":\"did:ev:cwML2cBSvFDbULW1gRywujfEw8byxVDHAY1v4\",\"issuanceDate\":\"2019-10-31T19:00:00-05:00\",\"credentialSubject\":{\"id\":\"urn:tin:PE98769687698\",\"alternateName\":\"Alicorp\",\"legalName\":\"Alicorp\",\"issuanceDate\":\"2019-10-31T19:00:00-05:00\",\"taxID\":\"PE98769687698\",\"additionalType\":\"Importador y Exportador\",\"additionalTypeIndustry\":\"Imp\",\"referenceNumber\":\"98769687698\",\"location\":[{\"streetAddress\":\"av colonial\",\"country\":\"PE\",\"postalCode\":\"987\",\"state\":\"Lima\",\"city\":\"lima\"}],\"contactPoint\":[],\"visible\":true,\"locationCreated\":\"PE\"},\"proof\":{\"type\":\"EthereumAttestationRegistry2019\",\"networkId\":\"0x9e551\",\"contractAddress\":\"0xa40fd13f2122f5f64d8c5caebaae60796a928560\"}}"));
        String txHash = IM.approveAEO(certificate, BigInteger.valueOf(0), user, AEO, VR);
        assertThat(txHash, containsString("0x"));
    }
	   
	  // @Test
	    public void registerAEO() throws Exception {
	       // log.info(Utils.calculateHashString("certificate"));
	        String txHash = IM.registerAEO(certificate, user, AEO);
	        assertThat(txHash, containsString("0x"));
	    }


  @Test
    public void revokeAEO() throws Exception {
        //log.info(Utils.calculateHashString("certificate"));
        String txHash = IM.revokeAEO(certificate, user, AEO, VR);
        assertThat(txHash, containsString("0x"));
    }
    
    //@Test
	public void deployIM() throws IOException, Exception {
		String contractAddress = IM.deployIM();
		log.info("contractAddress: " + contractAddress);
		assertThat(contractAddress, containsString("0x"));
	}
    
   // @Test
	public void createIdentity() throws Exception {
        String keyMnemonic = "admin";
        String keyProfile = "public";
        String urlProfile = "Qmxxx";
        String username = "test";
        String salt = "salt";
		String proxy = IM.createIdentity(keyMnemonic, keyProfile, urlProfile, username, salt);
		log.info("New identity: " + proxy);
		assertThat(proxy, containsString("0x"));
    }

    //@Test
    public void setCap() throws Exception {
        String cap = "fw";
        Date date= new Date();
        long time = date.getTime() / 1000;
        String txHash = IM.setCap(aduana, AEO, cap, BigInteger.valueOf(time), BigInteger.valueOf(0));
        assertThat(txHash, containsString("0x"));
    }

   //@Test
    public void checkCap() throws Exception {
        String cap = "fw";
        Boolean hasCap = IM.checkCap(aduana, AEO, cap);
        assertTrue(hasCap);
    }
    
    //@Test
    public void forwardToTest() throws IOException, Exception {
        String destination = "0xd69399c5f03762315946dd44bAdcdeC2451D6633";
        Function function = new Function("setTest", // Function name
            Arrays.asList(new Utf8String("Test")), // Function input parameters
            Collections.emptyList());
        String encodedFunction = FunctionEncoder.encode(function);
        byte[] data = Numeric.hexStringToByteArray(Numeric.cleanHexPrefix(encodedFunction));
        String txHash = IM.forwardTo(aduana, destination, BigInteger.valueOf(0), data);
        assertThat(txHash, containsString("0x"));
    }

   //@Test
    public void addRoleUserAEOManager() throws Exception {
        String txHash = IM.addRoleUserAEOManager(user, AEO, user, "ROLE_3");
        assertThat(txHash, containsString("0x"));
    }

   // @Test
    public void removeRoleUserAEOManager() throws Exception {
        String txHash = IM.removeRoleUserAEOManager(user, AEO, user, "ROLE_2");
        assertThat(txHash, containsString("0x"));
    }

}
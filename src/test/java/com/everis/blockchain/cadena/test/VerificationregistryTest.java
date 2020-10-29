package com.everis.blockchain.cadena.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.everis.blockchain.cadena.EthCoreParams;
import com.everis.blockchain.cadena.Verificationregistry;

public class VerificationregistryTest {

    EthCoreParams ethCoreParams = new EthCoreParams("https://35.247.241.166", "0xdb32769684b7ede7c9adb56cf47e5acd008ebaf87fd5e63e9ef92e9401c8e825", null, BigInteger.valueOf(0), BigInteger.valueOf(4000000));
    private Verificationregistry VR;
    private static final Logger log = LoggerFactory.getLogger(DigitalIdentityTest.class);

    public VerificationregistryTest() throws IOException, Exception {
    	 VR = new Verificationregistry("0x9f8c1e196F5696e014F4d1E4961B92db866BE271", ethCoreParams);
    }
    //@Test
    public void deployIM() throws IOException, Exception {
        String contractAddress = VR.deployVR();
        log.info("contractAddress: " + contractAddress);
        assertThat(contractAddress, containsString("0x"));
    }

   // @Test
    public void verify() throws Exception {
        String txHash = VR.verify("Test", BigInteger.valueOf(5));
        assertThat(txHash, containsString("0x"));
    }
 

   // @Test
    public void revoke() throws Exception {
        String txHash = VR.revoke("Test");
        assertThat(txHash, containsString("0x"));
    }

  //  @Test
    public void getDatesVerification() throws Exception {
        List<BigInteger> validDates = VR.getDatesVerification("Test", "0xf2843dc9abe46e64e9a68c52adbce6593affb1b9");
        assertEquals(validDates.get(0).compareTo(BigInteger.valueOf(0)), 1);
    }

}
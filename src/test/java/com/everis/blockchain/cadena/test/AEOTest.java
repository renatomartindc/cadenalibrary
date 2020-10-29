package com.everis.blockchain.cadena.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.everis.blockchain.cadena.Aeo;
import com.everis.blockchain.cadena.EthCoreParams;
import com.everis.blockchain.cadena.lib.Utils;

public class AEOTest {

    EthCoreParams ethCoreParams = new EthCoreParams("https://35.197.76.152", "0x123ecd2cccbe2ca6d705d4133145ed3062e276d2a8724616de1f5791c2bbe8e", null, BigInteger.valueOf(0), BigInteger.valueOf(4000000));
   // private Aeo aeo = new Aeo("0x62555a84f124e34d59bbc8cb119cce60d959e6a7", ethCoreParams);
    private Aeo aeo = null;
    private static final Logger log = LoggerFactory.getLogger(AEOTest.class);
    private String aduana = "0xe0ef4c43eeedefa560b2cad5923e3c6d85c4e075";
    private String firstAdmin = "";
    private String user = "0x3ca43f1a61d95f417cce92d9603247481089eee0";
    private String IM = "0x3f79eeef4ce85af3b2591d77a659672af3959b68";
    private String VR = "0x9f8c1e196F5696e014F4d1E4961B92db866BE271";
    private String data = "certificate";

    public AEOTest() throws IOException {
    	aeo = new Aeo("0x62555a84f124e34d59bbc8cb119cce60d959e6a7", ethCoreParams);
    }
    //@Test
    public void deployAEO() throws IOException, Exception {
        String contractAddress = aeo.deployAEO(aduana, firstAdmin);
        log.info("contractAddress: " + contractAddress);
        assertThat(contractAddress, containsString("0x"));
    }
    
    //@Test
    public void existsAEO() throws Exception {
        boolean isRegistered = aeo.existsAEO(data);
        assertTrue(isRegistered);
    }

    //@Test
    public void addRole() throws Exception {
        String txHash = aeo.addRole(user, "ROLE_2");
        //String txHash = aeo.addRole(user, "ROLE_3");
        assertThat(txHash, containsString("0x"));
    }
    
   // @Test
    public void hasRole() throws Exception {
        boolean isRole = aeo.hasRole(user, "ROLE_2");
        log.info("ROLE_2=" + isRole);
        isRole = aeo.hasRole(user, "ROLE_3");
        
        log.info("ROLE_3=" + isRole);
        assertTrue(isRole);
    }

    //@Test
    public void registerAEO() throws Exception {
        String txHash = aeo.registerAEO(data);
        assertThat(txHash, containsString("0x"));
    }
    
    //@Test
    public void approveAEO() throws Exception {
        log.info(Utils.calculateHashString(data));
        String txHash = aeo.approveAEO(data, BigInteger.valueOf(0), IM, VR);
        assertThat(txHash, containsString("0x"));
    }

    //@Test
    public void revokeAEO() throws Exception {
        log.info(Utils.calculateHashString(data));
        String txHash = aeo.revokeAEO(data, IM, VR);
        assertThat(txHash, containsString("0x"));
    }

    ////@Test
    public void removeRole() throws Exception {
        String txHash = aeo.removeRole(user, "ROLE_2");
        assertThat(txHash, containsString("0x"));
    }
    
}
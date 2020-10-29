package com.everis.blockchain.cadena;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;

import com.everis.blockchain.cadena.contracts.VerificationRegistry;
import com.everis.blockchain.cadena.lib.Utils;

import io.reactivex.annotations.Nullable;

public class Verificationregistry implements IVerificationregistry {

    public String addressVR;
    private EthCore ethCore;
    private VerificationRegistry vrContract;

    public Verificationregistry(@Nullable String verificationRegistry, EthCoreParams ethCoreParams) throws IOException, Exception {
        ethCore = new EthCore(ethCoreParams);
//        try {
            addressVR = verificationRegistry == null ? deployVR() : verificationRegistry;
//        } catch (IOException e) {
//            addressVR = "0x0000000000000000000000000000000000000000";
//            e.printStackTrace();
//        } catch (Exception e) {
//            addressVR = "0x0000000000000000000000000000000000000000";
//            e.printStackTrace();
//        }
        updateVRContract();
    }

    public String deployVR() throws IOException, Exception {
        VerificationRegistry contract = VerificationRegistry
                .deploy(ethCore.getWeb3jInstance(), ethCore.getCredentials(), ethCore.getDefaultContractGasProvider())
                .send();
        return contract.getContractAddress();
    }

    public String verify(String data, BigInteger validDays) throws Exception {
        byte[] hash = Utils.calculateHash(data);
        TransactionReceipt transactionReceipt = vrContract.verify(hash, validDays).send();
        return transactionReceipt.getTransactionHash();
    }

    public String revoke(String data) throws Exception {
        byte[] hash = Utils.calculateHash(data);
        TransactionReceipt transactionReceipt = vrContract.revoke(hash).send();
        return transactionReceipt.getTransactionHash();
    }

    public List<BigInteger> getDatesVerification(String data, String identity) throws Exception {
        byte[] hash = Utils.calculateHash(data);
        Tuple2<BigInteger, BigInteger> dates = vrContract.verifications(hash, identity).send();
        List<BigInteger> validDates = new ArrayList<BigInteger>();
        validDates.add(dates.component1());
        validDates.add(dates.component2());
		return validDates;
	}

    private void updateVRContract() {
        try {
            vrContract = VerificationRegistry.load(addressVR, ethCore.getWeb3jInstance(), ethCore.getCredentials(), ethCore.getDefaultContractGasProvider());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
package com.everis.blockchain.cadena;

import java.io.IOException;
import java.math.BigInteger;

import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;

import com.everis.blockchain.cadena.contracts.AEOManager;
import com.everis.blockchain.cadena.lib.Utils;;

public class Aeo implements IAEO {

    public String addressAEO;
    private EthCore ethCore;
    private AEOManager aeoContract;

    public Aeo(String aeo, EthCoreParams ethCoreParams) throws IOException {
        ethCore = new EthCore(ethCoreParams);
        if (aeo == null) throw new Error("AEO contract address required");
        addressAEO = aeo;
        updateAEOContract();
    }

    public String deployAEO(String aduana, String firstAdmin) throws IOException, Exception {
        AEOManager contract = AEOManager.deploy(ethCore.getWeb3jInstance(), ethCore.getCredentials(), ethCore.getDefaultContractGasProvider(), aduana, firstAdmin).send();
        addressAEO = contract.getContractAddress();
        updateAEOContract();
        return addressAEO;
    }

    private void updateAEOContract() throws IOException {
        
            aeoContract = AEOManager.load(addressAEO, ethCore.getWeb3jInstance(), ethCore.getCredentials(), ethCore.getDefaultContractGasProvider());
        
    }

    public String addRole(String user, String role) throws Exception {
        TransactionReceipt transactionReceipt = aeoContract.addRole(user, role).send();
        return transactionReceipt.getTransactionHash();
    }

    public boolean hasRole(String user, String role) throws Exception {
        return aeoContract.hasRole(user, role).send();
    }

    public String removeRole(String user, String role) throws Exception {
        TransactionReceipt transactionReceipt = aeoContract.removeRole(user, role).send();
        return transactionReceipt.getTransactionHash();
    }

    public String registerAEO(String data) throws Exception {
        byte[] hash = Utils.calculateHash(data);
        TransactionReceipt transactionReceipt = aeoContract.registerAEO(hash).send();
        return transactionReceipt.getTransactionHash();
    }

    public String approveAEO(String data, BigInteger validDays, String identityManager, String verificationRegistry) throws Exception {
        try {
            byte[] hash = Utils.calculateHash(data);
            TransactionReceipt transactionReceipt = aeoContract.approveAEO(hash, validDays, identityManager, verificationRegistry).send();
            return transactionReceipt.getTransactionHash();
        } catch (TransactionException e) {
            //log.info("++++++++++++++++++++++++++++" + Utils.getRevertReason(e.getTransactionReceipt().get().getRevertReason()));
            throw e;
        }
    }

    public String revokeAEO(String data, String identityManager, String verificationRegistry) throws Exception {
        byte[] hash = Utils.calculateHash(data);
        TransactionReceipt transactionReceipt = aeoContract.revokeAEO(hash, identityManager, verificationRegistry).send();
        return transactionReceipt.getTransactionHash();
    }

    public boolean existsAEO(String data) throws Exception {
        byte[] hash = Utils.calculateHash(data);
        boolean exists = aeoContract.registryAEO(hash).send();
        return exists;
    }

}
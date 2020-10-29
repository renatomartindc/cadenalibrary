package com.everis.blockchain.cadena;

import java.io.IOException;
import java.math.BigInteger;

public interface IAEO {

    /**
     * This method is used to deploy a new AEO contract.
     * @param aduana Proxy address of the Aduana.
     * @param firstAdmin Address of the first administrator. This user can add/remove users.
     * @return This returns the contract address.
    */
    String deployAEO(String aduana, String firstAdmin) throws IOException, Exception;

    /**
     * This method is used to add a role to a user.
     * @param user Proxy address of the user.
     * @param role Role to set to the user.
     * @return Transaction hash.
    */
    String addRole(String user, String role) throws Exception;
    
    /**
     * This method is used to verify if user has a role.
     * @param user Proxy address of the user.
     * @param role Role to verify.
     * @return Boolean.
    */
    boolean hasRole(String user, String role) throws Exception;

    /**
     * This method is used to remove a role to a user.
     * @param user Proxy address of the user.
     * @param role Role to remove to the user.
     * @return Transaction hash.
    */
    String removeRole(String user, String role) throws Exception;

    /**
     * This method is used to register a certificate hash in contract.
     * @param data Certificate data (OEA).
     * @return Transaction hash.
    */
    String registerAEO(String data) throws Exception;

    /**
     * This method is used to verify a Certificate using its hash.
     * @param data Certificate data (OEA).
     * @param validDays Number of days the certificate will be valid.
     * @param identityManager Contract address where Aduana identity is registered.
     * @param verificationRegistry Contract address where verifications are made.
     * @return Transaction hash.
    */
    String approveAEO(String data, BigInteger validDays, String identityManager, String verificationRegistry) throws Exception;

    /**
     * This method is used to revoke a Certificate using its hash.
     * @param data Certificate data (OEA).
     * @param identityManager Contract address where Aduana identity is registered.
     * @param verificationRegistry Contract address where verifications are made.
     * @return Transaction hash.
    */
    String revokeAEO(String data, String identityManager, String verificationRegistry) throws Exception;
    
    /**
     * This method is used to verify if certain AEO is registered.
     * @param data Certificate data (OEA).
     * @return Boolean.
    */
    boolean existsAEO(String data) throws Exception;
}
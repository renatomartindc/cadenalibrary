package com.everis.blockchain.cadena.lib;


import org.web3j.crypto.Credentials;
import org.web3j.protocol.besu.Besu;
import org.web3j.tx.gas.BesuPrivacyGasProvider;
import org.web3j.utils.Base64String;

/** PrivateTransactionManager implementation for using a Besu node to transact. */
public class BesuPrivateTransactionManager extends PrivateTransactionManager {

    private final Base64String privacyGroupId;

    public BesuPrivateTransactionManager(
            final Besu besu,
            final BesuPrivacyGasProvider gasProvider,
            final Credentials credentials,
            final long chainId,
            final Base64String privateFrom,
            final Base64String privacyGroupId,
            final int attempts,
            final int sleepDuration) {
        super(besu, gasProvider, credentials, chainId, privateFrom, attempts, sleepDuration);
        this.privacyGroupId = privacyGroupId;
    }

    public BesuPrivateTransactionManager(
            final Besu besu,
            final BesuPrivacyGasProvider gasProvider,
            final Credentials credentials,
            final long chainId,
            final Base64String privateFrom,
            final Base64String privacyGroupId) {
        this(
                besu,
                gasProvider,
                credentials,
                chainId,
                privateFrom,
                privacyGroupId,
                DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH,
                15 * 1000);
    }

    @Override
    public Base64String getPrivacyGroupId() {
        return privacyGroupId;
    }

    @Override
    protected Object privacyGroupIdOrPrivateFor() {
        return privacyGroupId;
    }
}

package com.everis.blockchain.cadena.lib;

import org.web3j.crypto.Credentials;
import org.web3j.ens.EnsResolver;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

public abstract class ContractSimple extends Contract {
	
	private static final int DEFAULT_POLLING_FREQUENCY = 2 * 1000;
	
	public static final int DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH = TransactionManager.DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH;


	protected ContractSimple(String contractBinary, String contractAddress, Web3 web3j, Credentials credentials,
			ContractGasProvider gasProvider) {
		super(new EnsResolver(web3j), contractBinary, contractAddress, web3j, // long chainId, int attempts, long
				// sleepDuration)
				new RawTransactionSimple(web3j, credentials,  DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH,
						DEFAULT_POLLING_FREQUENCY),
				gasProvider);

	}
}

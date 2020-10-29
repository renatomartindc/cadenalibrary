package com.everis.blockchain.cadena;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.besu.response.privacy.PrivGetTransactionReceipt;
import org.web3j.tuples.generated.Tuple5;

import com.everis.blockchain.cadena.contracts.MRA;
import com.everis.blockchain.cadena.lib.TransactionNoMinedException;

import io.reactivex.annotations.Nullable;

public class Cadena implements ICadena {

	private static final Logger log = LoggerFactory.getLogger("Cadena");

	private String addressMRA;
	private EthCore ethCore;
	private MRA mraContract;
	private BigInteger lastBlock;
	private short retries = 5;

	private static final String[] EVENTS_CADENA = new String[] { "CertificateAdded(string,address)",
			"CertificateModified(string,string,address)" };

	public Cadena(@Nullable String mra, EthCoreParams ethCoreParams, String privGroupId, String fromNode) throws IOException, Exception {
		ethCore = new EthCore(ethCoreParams, privGroupId, fromNode);
//		try {
			addressMRA = mra == null ? deployMRA() : mra;
//		} catch (Exception e) {
//			addressMRA = "0x0000000000000000000000000000000000000000";
//			e.printStackTrace();
//		}
		updateMRAContract();
	}

	private void updateMRAContract() {
		try {
			mraContract = MRA.load(addressMRA, ethCore.getBesuInstance(), ethCore.getTransactionManagerBesu(),
					ethCore.getDefaultBesuPrivacyGasProvider());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	// Deploy Contract
	public String deployMRA() throws IOException, Exception {
		MRA contract = MRA.deploy(ethCore.getBesuInstance(), ethCore.getTransactionManagerBesu(),
				ethCore.getDefaultBesuPrivacyGasProvider()).send();
		return contract.getContractAddress();
	}

	// MRA functions
	public void setAddressMRA(String newAddressMRA) {
		addressMRA = newAddressMRA;
		updateMRAContract();
	}

	public String approveMember(@Nullable String member) {
		try {
			String memberToApprove = member;
			if (member == null) {
				Credentials credentials = ethCore.getCredentials();
				memberToApprove = credentials.getAddress();
				log.info("memberApprove: " + memberToApprove);
			}
			String encodeFunction = mraContract.approve(memberToApprove).encodeFunctionCall();
			
			
			
			String hash = sendPrivTransaction(encodeFunction);
			return hash;
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	public Certificate getCertificate(String tin) throws Exception {

		// Data certificate, state, observation, visible, owner
		Tuple5<String, String, String, Boolean, String> certificate = mraContract.getCertificate(tin).send();

		String data = certificate.component1();
		String state = certificate.component2();
		String observation = certificate.component3();
		Boolean visible = certificate.component4();
		String owner = certificate.component5();
		Certificate cert = new Certificate(tin, data, state, observation, visible, owner);
		return cert;
	}

	public String addCertificate(String TIN, String certificate, String state, String observation, Boolean visible)
			throws Exception {
		String encodeFunction = mraContract.addCertificate(TIN, certificate, state, observation, visible)
				.encodeFunctionCall();
		String hash = sendPrivTransactionSaveLogs(encodeFunction);
		return hash;
	}

//	public String updateCertificateData(String TIN, String data) {
//		try {
//			String encodeFunction = mraContract.updateCertificateData(TIN, data).encodeFunctionCall();
//			String hash = sendPrivTransactionSaveLogs(encodeFunction);
//			return hash;
//		} catch (Exception e) {
//			return e.getMessage();
//		}
//	}

//	public String updateCertificateState(String TIN, String state) {
//		try {
//			String encodeFunction = mraContract.updateCertificateState(TIN, state).encodeFunctionCall();
//			String hash = sendPrivTransactionSaveLogs(encodeFunction);
//			return hash;
//		} catch (Exception e) {
//			return e.getMessage();
//		}
//	}

//	public String updateCertificateObservation(String TIN, String observation) {
//		try {
//			String encodeFunction = mraContract.updateCertificateObservation(TIN, observation).encodeFunctionCall();
//			String hash = sendPrivTransactionSaveLogs(encodeFunction);
//			return hash;
//		} catch (Exception e) {
//			return e.getMessage();
//		}
//	}

	public String updateCertificateVisible(String TIN, Boolean visible) {
		try {
			String encodeFunction = mraContract.updateCertificateVisible(TIN, visible).encodeFunctionCall();
			String hash = sendPrivTransaction(encodeFunction);
			return hash;
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	public Boolean isMember(String member) throws Exception {
		Boolean isMember = mraContract.members(member).send();
		return isMember;
	}

	public BigInteger totalMembers() throws Exception {
		BigInteger total = mraContract.totalMembers().send();
		return total;
	}

	public String setVisibilityToMember(String member, Boolean visible) {
		try {
			String encodeFunction = mraContract.setVisibilityToMember(member, visible).encodeFunctionCall();
			String hash = sendPrivTransaction(encodeFunction);
			return hash;
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	public Boolean getVisibilityFromMember(String member) throws Exception {
		Boolean visible = mraContract.getVisibilityFromMember(member).send();
		return visible;
	}

	public Boolean getVisibility(String memberOrigin, String memberTarget) throws Exception {
		Boolean visible = mraContract.visibility(memberOrigin, memberTarget).send();
		return visible;
	}

	public String retire() {
		try {
			String encodeFunction = mraContract.retire().encodeFunctionCall();
			String hash = sendPrivTransaction(encodeFunction);
			return hash;
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@Override
	public void getCertificatesHistory(BigInteger fromBlock, BigInteger toBlock, List<Cadena> listCadena,
			IObserverCertificate observerCertificate) {

		try {
			String addressMember = ethCore.getCredentials().getAddress();

			ethCore.getLogsWatch(fromBlock, toBlock, retries, addressMember, EVENTS_CADENA,

					listCadena, observerCertificate);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	@Override
	public void watchCertificates(List<Cadena> listCadena, IObserverCertificate observerCertificate) {


		try {

			BigInteger finalBlock = ethCore.getLastBlock().getNumber();

			if (this.lastBlock == null)
				this.lastBlock = finalBlock;

			String addressMember = ethCore.getCredentials().getAddress();
			
		//	System.err.println("start:"+this.lastBlock.longValue() +" -end:"+finalBlock.longValue() );
			
			ethCore.getLogsWatch(this.lastBlock, finalBlock, retries, addressMember, EVENTS_CADENA, listCadena,
					observerCertificate);

			BigInteger finalBlockSum = finalBlock.add(new BigInteger("1"));

			listCadena.forEach( c -> c.lastBlock = finalBlockSum);
			
			this.lastBlock = finalBlockSum;

		} catch (Exception e) {
			e.printStackTrace();
			log.error("CAdena.ERROR", e);
		}


	}


	private String sendPrivTransaction(String encodeFunction) throws Exception {
		
		
		String hash = ethCore.getTransactionManagerBesu().sendTransaction(ethCore.getGasPrice(), ethCore.getGasLimit(),
				addressMRA, encodeFunction, BigInteger.valueOf(0), false).getTransactionHash();
		
		PrivGetTransactionReceipt receipt = ethCore.getPrivTransactionReceiptForSave(hash, retries);
		if (receipt == null || receipt.getResult() == null || receipt.getResult().getLogs() == null
				|| receipt.getResult().getLogs().size() <= 0)
			throw new TransactionNoMinedException();
		
		

		return hash;
	}
	
	private String sendPrivTransactionSaveLogs(String encodeFunction) throws Exception {
		
		
		String hash = ethCore.getTransactionManagerBesu().sendTransaction(ethCore.getGasPrice(), ethCore.getGasLimit(),
				addressMRA, encodeFunction, BigInteger.valueOf(0), false).getTransactionHash();
		
		PrivGetTransactionReceipt receipt = ethCore.getPrivTransactionReceiptForSave(hash, retries);
		if (receipt == null || receipt.getResult() == null || receipt.getResult().getLogs() == null
				|| receipt.getResult().getLogs().size() <= 0)
			throw new TransactionNoMinedException();
		

		return hash;
	}
	
	


	public BigInteger getLastBlock() {
		return lastBlock;
	}

	public void closeConnection() {
		ethCore.closeConnection();
	}

	public BigInteger getBlockActualNodeBesu() throws IOException {
		return ethCore.getLastBlock().getNumber();
	}

	public String getAddressMRA() {
		return this.addressMRA;
	}

}
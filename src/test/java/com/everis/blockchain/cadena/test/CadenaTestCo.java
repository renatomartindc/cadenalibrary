package com.everis.blockchain.cadena.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.CipherException;

import com.everis.blockchain.cadena.Cadena;
import com.everis.blockchain.cadena.Certificate;
import com.everis.blockchain.cadena.EthCoreParams;
import com.everis.blockchain.cadena.IObserverCertificate;

public class CadenaTestCo {

	EthCoreParams ethCoreParams = new EthCoreParams("https://35.197.76.152",
			"0x123ecd2cccbe2ca6d705d4133145ed3062e276d2a8724616de1f5791c2bbe8e", null, BigInteger.valueOf(0),
			BigInteger.valueOf(4000000));
	private String fromNode = "XjQ2ZERWty+3dgBcqk3KSFFSwDbgYqrV9ia2/Udzl2M=";
	private String privGroupId = "KOHlCg7oI63EZEtdTpRme1WnskAvWZBtAv3sQK8ylRI="; // Node 1 - 2
	private Cadena cadena ;
	
	
	public CadenaTestCo() throws IOException, Exception{
		cadena = new Cadena("0x895773123f4b1eaecaa94ce3455e087ef4321689", ethCoreParams, privGroupId,
				fromNode);
	}

	private static final Logger log = LoggerFactory.getLogger("CadenaTest");

	private String certificateId = generateCertificateId();

	private static final int EVENT_COUNT = 5;
	private static final int TIMEOUT_MINUTES = 20;

	private String generateCertificateId() {
		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());
		String certificateId = ts.toString();
		log.info(certificateId);
		return certificateId;
	}

	 @Test
	public void deployMRADefault() throws IOException, Exception {
		Cadena testCadena = new Cadena(null, ethCoreParams, privGroupId, fromNode);
		String contractAddress = testCadena.getAddressMRA();
		log.info("contractAddress: " + contractAddress);
		assertThat(contractAddress, containsString("0x"));
	}

	// @Test
	public void approveMember() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException,
			NoSuchProviderException, CipherException {
		String txHash = cadena.approveMember("0xcAA77c093fFc3f53bc53846c29193C22417939d4");
		assertThat(txHash, containsString("0x"));
	}

	//@Test
	public void getCertificate() throws Exception {
		Certificate certificate = cadena.getCertificate("CL6837412349071234");
		log.info("certificate == " + certificate.getData());

		assertNotNull(certificate);
		assertEquals(certificate.getState(), "active");
	}
	

	//@Test
	public void addCertificate() throws Exception {
		 
		 String oea = " {\"@context\":[\"https://www.w3.org/2018/credentials/v1\",\"https://schema.org/Organization\"],\"type\":[\"VerifiableCredential\",\"AuthorizedEconomicOperatorCredential\"],\"issuer\":\"did:ev:cwMLNwYaVNAaJttfCDQ5enbwUCvTdEjDGNorx\",\"issuanceDate\":\"2020-05-14T00:00:00Z\",\"credentialSubject\":{\"id\":\"urn:tin:CO860025900\",\"alternateName\":\"Alpina Productos Alimenticios S. A\",\"legalName\":\"Alpina Productos Alimenticios S. A\",\"issuanceDate\":\"2020-05-14T00:00:00Z\",\"taxID\":\"CO860025900\",\"additionalType\":\"CB\",\"additionalTypeIndustry\":\"\",\"referenceNumber\":\"860025900\",\"location\":[{\"streetAddress\":\"Alpina Productos Alimenticios S. A\",\"country\":\"CO\",\"postalCode\":\"\",\"state\":\"CO-DC\",\"city\":\"\"}],\"contactPoint\":[],\"visible\":false,\"locationCreated\":\"CO\"},\"proof\":{\"type\":\"EthereumAttestationRegistry2019\",\"networkId\":\"0x9e551\",\"contractAddress\":\"0x9f8c1e196F5696e014F4d1E4961B92db866BE271\"}}";
		 
		String txHash = cadena.addCertificate("a000100", oea, "active", "my observation",
				true);
		assertThat(txHash, containsString("0x"));
		Certificate newCertificate = cadena.getCertificate(certificateId);
		assertNotNull(newCertificate);
		assertEquals(newCertificate.getData(), "{name:Content Certificate}");
		assertEquals(newCertificate.getState(), "active");
		assertEquals(newCertificate.getObservation(), "my observation");
		assertEquals(newCertificate.getVisible(), true);
		assertEquals(newCertificate.getOwner(), "0xf2843dc9abe46e64e9a68c52adbce6593affb1b9");
	}

	// @Test
//	public void updateCertificateData() throws Exception {
//		String id = "2019-11-13 14:30:16.056";
//		Certificate certificate = cadena.getCertificate(id);
//		assertNotNull(certificate);
//		String newData = "{id:" + generateCertificateId().toString() + "}";
//		String txHash = cadena.updateCertificateData(id, newData);
//		assertThat(txHash, containsString("0x"));
//		certificate = cadena.getCertificate(id);
//		assertEquals(certificate.getData(), newData);
//	}

	// @Test
//	public void updateCertificateState() throws Exception {
//		String id = "2019-11-13 14:30:16.056";
//		Certificate certificate = cadena.getCertificate(id);
//		assertNotNull(certificate);
//		String newState = generateCertificateId().toString();
//		String txHash = cadena.updateCertificateState(id, newState);
//		assertThat(txHash, containsString("0x"));
//		certificate = cadena.getCertificate(id);
//		assertEquals(certificate.getState(), newState);
//	}

	// @Test
//	public void updateCertificateObservation() throws Exception {
//		String id = "2019-11-13 14:30:16.056";
//		Certificate certificate = cadena.getCertificate(id);
//		assertNotNull(certificate);
//		String newObservation = generateCertificateId().toString();
//		String txHash = cadena.updateCertificateObservation(id, newObservation);
//		assertThat(txHash, containsString("0x"));
//		certificate = cadena.getCertificate(id);
//		assertEquals(certificate.getObservation(), newObservation);
//	}

	// @Test
	public void updateCertificateVisible() throws Exception {
		String id = "2019-11-13 14:30:16.056";
		Certificate certificate = cadena.getCertificate(id);
		assertNotNull(certificate);
		Boolean newVisibility = !certificate.getVisible();
		String txHash = cadena.updateCertificateVisible(id, newVisibility);
		assertThat(txHash, containsString("0x"));
		certificate = cadena.getCertificate(id);
		assertEquals(certificate.getVisible(), newVisibility);
	}

	// @Test
	public void deployMRA() throws IOException, Exception {
		String contractAddress = cadena.deployMRA();
		log.info("contractAddress: " + contractAddress);
		assertThat(contractAddress, containsString("0x"));
	}

	// @Test
	public void validMember() throws Exception {
		Boolean isMember = cadena.isMember("0x69791fedaad023a25a4b4c8a15540ca161364078");
		assertTrue(isMember);
	}

	//@Test
	public void totalMembers() throws Exception {
		BigInteger totalMembers = cadena.totalMembers();
		log.info("" + totalMembers);
	}

	// @Test
	public void setVisibilityToMember() throws Exception {
		String txHash = cadena.setVisibilityToMember("0xf2843dc9abe46e64e9a68c52adbce6593affb1b9", true);
		assertThat(txHash, containsString("0x"));
	}

	// @Test
	public void getVisibilityFromMember() throws Exception {
		Boolean visible = cadena.getVisibilityFromMember("0xf2843dc9abe46e64e9a68c52adbce6593affb1b9");
		assertTrue(visible);
	}

	// @Test
	public void getVisibility() throws Exception {
		Boolean visible = cadena.getVisibility("0x207408537de7e16b811d7894f52133538ba2784d",
				"0x55f28627ee21ca218725790d7a74c7b231cd9a34");
		assertTrue(visible);
	}

	// @Test
	public void xCloseConnection() {
		cadena.closeConnection();
	}

	// mvn clean test
	// -Dtest=com.everis.blockchain.cadena.test.CadenaTest#getOEANative
	//@Test
	public void getOEANative() throws Exception {
		CountDownLatch countDownLatch = new CountDownLatch(EVENT_COUNT);
//			finalBlock = cadena.getBlockActualNodeBesu();
//			initialBlock = cadena.getBlockActualNodeBesu();

		// long delay = 2*60*1000L;
		long period = 15 * 1000L;
		
		List<Cadena> list = new ArrayList<>();
		list.add(cadena);

		TimerTask repeatedTask = new TimerTask() {
			public void run() {
				System.out.println("Task performed on " + new Date());

				cadena.watchCertificates(list, new IObserverCertificate() {

					@Override
					public void watchCertificates(Certificate cert)
							throws IOException {
						System.out.println(cert.getTin());
						System.out.println(cert.getData());
					}
				});

			}
		};
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

		executor.scheduleAtFixedRate(repeatedTask, 0, period, TimeUnit.MILLISECONDS);

		countDownLatch.await(TIMEOUT_MINUTES, TimeUnit.MINUTES);

		executor.shutdown();
	}

	//@Test
	public void getOEAHistory() throws Exception {
		//CountDownLatch countDownLatch = new CountDownLatch(EVENT_COUNT);
//			finalBlock = cadena.getBlockActualNodeBesu();
//			initialBlock = cadena.getBlockActualNodeBesu();

		// long delay = 2*60*1000L;
		BigInteger blockNumber = new BigInteger("11424998"); 
		BigInteger blockNumberLast = new BigInteger("11425045"); 
		List<Cadena> list = new ArrayList<>();
		list.add(cadena);

		cadena.getCertificatesHistory(blockNumber, blockNumberLast,list,  new IObserverCertificate() {

			@Override
			public void watchCertificates(com.everis.blockchain.cadena.Certificate cert) throws IOException {
				log.info("HISTORY_BLOCK.item.data:{}", cert.getData());

				log.info(cert.getTin());
				log.info(cert.getData());

			}
		});

		log.info("finish");
		
		assertTrue(true);
		// ScheduledExecutorService executor =
		// Executors.newSingleThreadScheduledExecutor();

		// executor.scheduleAtFixedRate(repeatedTask, 0, period, TimeUnit.MILLISECONDS);

		//countDownLatch.await(TIMEOUT_MINUTES, TimeUnit.MINUTES);

		// executor.shutdown();
	}

}
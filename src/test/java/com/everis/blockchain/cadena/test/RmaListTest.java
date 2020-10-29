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

public class RmaListTest {

	EthCoreParams ethCoreParams = new EthCoreParams("https://35.224.4.131",
			"0x7802b5bc279ff3f27bd0ca1295bede8ab1a9757e1f016bc41dc4d54b4a902602", null, BigInteger.valueOf(0),
			BigInteger.valueOf(4000000));
	private String fromNode = "ErsINIuH1go6pJaHOgXu8yz5ABFG669gOtkwq8qI3CE=";
	private String privGroupId = "/ZYXyY3E1Sak0yWb2VL4muTDI5AG4X6J5gDPIzCKNl4="; // Node 1 - 2
	private Cadena cadena;

	public RmaListTest() throws IOException, Exception {
		cadena = new Cadena("0x32655f94981adce248fe776de3d125024082ce99", ethCoreParams, privGroupId, fromNode);
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

	// @Test
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

	// @Test
	public void getCertificate() throws Exception {
		Certificate certificate = cadena.getCertificate("CL6837412349071234");
		log.info("certificate == " + certificate.getData());

		assertNotNull(certificate);
		assertEquals(certificate.getState(), "active");
	}

	@Test
	public void addCertificate() throws Exception {
		String txHash = cadena.addCertificate(certificateId, "{name:Content Certificate}", "active", "my observation",
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

	// @Test
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
		Boolean visible = cadena.getVisibility("0xf2843dc9abe46e64e9a68c52adbce6593affb1b9",
				"0xf2843dc9abe46e64e9a68c52adbce6593affb1b9");
		assertTrue(visible);
	}

	// @Test
	public void xCloseConnection() {
		cadena.closeConnection();
	}

	// mvn clean test
	// -Dtest=com.everis.blockchain.cadena.test.CadenaTest#getOEANative
	// @Test
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
					public void watchCertificates(Certificate cert) throws IOException {
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

	// @Test
	public void getOEAHistory() throws Exception {
		// CountDownLatch countDownLatch = new CountDownLatch(EVENT_COUNT);
//			finalBlock = cadena.getBlockActualNodeBesu();
//			initialBlock = cadena.getBlockActualNodeBesu();

		// long delay = 2*60*1000L;
		BigInteger blockNumber = new BigInteger("11424998");
		BigInteger blockNumberLast = new BigInteger("11425045");

		List<Cadena> list = new ArrayList<>();
		list.add(cadena);

		cadena.getCertificatesHistory(blockNumber, blockNumberLast, list, new IObserverCertificate() {

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

		// countDownLatch.await(TIMEOUT_MINUTES, TimeUnit.MINUTES);

		// executor.shutdown();
	}

}
package com.everis.blockchain.cadena.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.everis.blockchain.cadena.Cadena;
import com.everis.blockchain.cadena.Certificate;
import com.everis.blockchain.cadena.EthCoreParams;
import com.everis.blockchain.cadena.IObserverCertificate;

public class CadenaTest {

	private static final Logger log = LoggerFactory.getLogger(CadenaTest.class);

	private static final int EVENT_COUNT = 5;
	private static final int TIMEOUT_MINUTES = 20;

	EthCoreParams ethCoreParams = new EthCoreParams("https://35.247.241.166",
			"0x3e1d245062a42913c882cb4df9e2263d991bcd1454e6ded05078e7652fc6761b", null, BigInteger.valueOf(0),
			BigInteger.valueOf(4000000));
		private List<Cadena> listCadena= new ArrayList<>();

	@Before
	public void before() throws IOException, Exception {
		
		 String fromNode = "bKWqTPee92mHSj+X5EdIuOjv2XZgg32RrgAM/+yvUW4=";
		 
		 String privGroupId = "KOHlCg7oI63EZEtdTpRme1WnskAvWZBtAv3sQK8ylRI="; // Node 1 - 2

		 Cadena cadena = new Cadena("0x895773123f4b1eaecaa94ce3455e087ef4321689", ethCoreParams, privGroupId,
				fromNode);
		 
		 listCadena.add(cadena);
		 
		  privGroupId = "uJhRrsQkUedxXIxxFZMpvx3d5KRLU9rnIiPAZF/g980="; // Node 1 - 2

		  cadena = new Cadena("0xee9b1720c8a2ff056906c0f9b8899089aaf0b3bb", ethCoreParams, privGroupId,
				fromNode);
		 
		 listCadena.add(cadena);
	}

	// mvn clean test
	// -Dtest=com.everis.blockchain.cadena.test.CadenaTest#getOEANative
	@Test
	public void getOEANative() throws Exception {
		CountDownLatch countDownLatch = new CountDownLatch(EVENT_COUNT);
//			finalBlock = cadena.getBlockActualNodeBesu();
//			initialBlock = cadena.getBlockActualNodeBesu();
		log.info("getOEANative");
		// long delay = 2*60*1000L;
		long period = 15 * 1000L;

		final Cadena cadena = listCadena.get(0);

		TimerTask repeatedTask = new TimerTask() {
			public void run() {
				//System.out.println("Task performed on " + new Date());

				cadena.watchCertificates(listCadena, new IObserverCertificate() {

					@Override
					public void watchCertificates(Certificate cert) throws IOException {
						System.out.println(cert.getTin());
					//	System.out.println(cert.getData());
					}
				});

			}
		};
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

		executor.scheduleAtFixedRate(repeatedTask, 0, period, TimeUnit.MILLISECONDS);

		countDownLatch.await(TIMEOUT_MINUTES, TimeUnit.MINUTES);

		log.info("finish");
		executor.shutdown();
	}

	 //@Test
	public void getOEAHistory() throws Exception {
		// CountDownLatch countDownLatch = new CountDownLatch(EVENT_COUNT);
//			finalBlock = cadena.getBlockActualNodeBesu();
//			initialBlock = cadena.getBlockActualNodeBesu();

		// long delay = 2*60*1000L;
		BigInteger blockNumber = new BigInteger("12676323");
		BigInteger blockNumberLast = new BigInteger("12676390");

		final Cadena cadena = listCadena.get(0);

		cadena.getCertificatesHistory(blockNumber, blockNumberLast, listCadena, new IObserverCertificate() {

			@Override
			public void watchCertificates(com.everis.blockchain.cadena.Certificate cert) throws IOException {
				//log.info("HISTORY_BLOCK.item.data:{}", cert.getData());
				System.out.println(cert.getTin());
//				log.info(cert.getTin());
//				log.info(cert.getData());

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
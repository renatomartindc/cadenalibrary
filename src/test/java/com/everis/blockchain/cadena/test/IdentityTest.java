package com.everis.blockchain.cadena.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigInteger;
import java.util.Date;

import org.junit.Test;

import com.everis.blockchain.cadena.DigitalIdentity;
import com.everis.blockchain.cadena.EthCoreParams;

public class IdentityTest {
	DigitalIdentity listIm;

	{
		String rpcConnection = "http://localhost:8547";
		long startTime = System.nanoTime();
		
		
		EthCoreParams ethCoreParams = new EthCoreParams(rpcConnection,
				"0x3859cb964033996f63d2417feeb2e5c04c6407bbc5f6f74c1c9e61956ae98e9a", null, BigInteger.valueOf(0),
				BigInteger.valueOf(4000000));
		

		String str = "0xa6b4540a2bfbe8663caa78027c83d0dcb1b7c837";

		try {
			listIm = new DigitalIdentity(str, ethCoreParams);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		long endTime = System.nanoTime();
		long timeElapsed = endTime - startTime;

		System.out.println("Execution time in milliseconds : " + timeElapsed / 1000000);

		assertTrue(true);
	}

	@Test
	public void setCap() throws Exception {
		try {
			String txHash = ";";
			String proxy = "0x9c3724f4fe88b52e22a0a6629c2864674334fd91";

			String[] devices = { 
					"0x0CF27B36CAA124600DCBB9A7136583B5863CB755",
					"0x3f4bfd55b46e02820c98b1bcc082e7a24b701461", "0x502c68da994b737f5f486c081a64e7d837d9aa9c",
					"0xc386b0c69eca717abde536fc463aaa6469914347", "0x54a5e18597c425a33c8480445bcc03f234671a17",
					"0x97b654c7daf60595d32928afe031ffe46d11d9d5", "0x6783396f269b7f4b15ad9f7d2c128ebb8972c56e",
					"0x716c6566870f218009d11ab4b052970a0a44bff9", "0x05ae5303340d7b602ec7e7107ddf498d3f4ad540",
					"0x98a1941f3901684f67283a006905fc9ae971762e", "0x955ba0fe3e8d07fad0ebd380cc0f73dfa0a9c29d",
					"0xcb2759926a1e79d8c4aa9f19e1e7273684cd178f", "0xadbf5f6eee7662b96569bb220c3d749307ebd882",
					"0xc23b43eb45d3b3816d3ea3d5e15894db7fac82fd", "0x17567dbb5fcd2c4c3453ef1e83eb31fe0208832e",
					"0x2f7584b470e80a1620d81eab6d4a201fd9ce917f", "0xffdcaf7995f852f667daee68b9b9820fc93cd2cc",
					"0xac9ca1f2c85e1ca5efb174f0b515e87c2c40ab78", "0xd61dec6a50bbb87121baa88a9b04d9ceaf2fd331",
					"0x0dbbabd22ef4c466d0730744e68890617b06e562", "0x20698c94e4f65ddae292824de45f8f515aecb242",
					"0xcd821a32962764a01ccb1f58ebb1b8c21505827b", "0xe967131bca63078f2cbb6eb0d83ff613f79e716f",
					"0xd9d2504d03b7fdf988bfc2ba70b107680b49851b", "0xca96dc6d62d1c95eedc4416822e78269fc0f03c2",
					"0xa66e03879a7181d8ac64cf290914e06ed91ff8de", "0x3c30a6d1ea4d0dc8207ff93334bb16aa9f733096",
					"0xc5ff1a9fa6e2fc487aece062c13dba6fca82fea1", "0xda2673ee6b3af079562536e7fc200925081c3988",
					"0xd147d476d5ddd570f2ea3843e04c6d07edce94d4", "0x5778221583eea589830b84de1111c6012e0adb0d",
					"0x7f3c9e0375a618a0bb140a9652754fc022a3a890", "0x09b83edf7b57f40da213a4bf00f79a88df81d3e5",
					"0xf647fbfd777b0416de4c808d724a309deea82e0d", "0xac5efd19171294d340efa10f0fef65ba3f0d7b04",
					"0xac1bfaafd81bbe4b5e8bbd26280a94cb4a2ae569", "0x366b34f5e1c1010f50cc0e82480a9e0c78aa3d3b",
					"0xde37c8fd7ecc3b05ab9f87036f9894baff2e2955", "0x7c44b603fd111888173f275acd6c83dc6b2fd9fe",
					"0xfc1031a610524d80ccb4b2f141552141ad3b4697", "0x82721efa0595c895ad180d0f2bcf971d608e7887",
					"0xce69458273cc16c3f4c233f83047c7ffc6b99645", "0x8452bbf0a557a2719874c8068256b3e391eb8dd6",
					"0xbd8f51dfb61687ac7a4f6a4651c5381baffcb921", "0x7c379d00aad5e7222ca4bb44e45f482e21a5b482",
					"0x6a57ce62d8f35fa66b40f5e6fd007ca68f0b892c", "0xbbc845e52f15f023e0cbcd4cdf4f65c0297a8963",
					"0xb64062ee34d151ea03c331225674ca158b274684", "0x0270ed4f83d26893997e78b516dcae27bcf6e302",
					"0x4791eafef0a4d6967277f75536d09dfbc7880bec", "0xddaf15486121a2fa38753aeb1dbb21341c802d96" };

			for (int i = 0; i < devices.length; i++) {
				long time =  new Date().getTime() / 1000;
				
				txHash = listIm.setCap(proxy, devices[i], "fw", BigInteger.valueOf(time), BigInteger.valueOf(0));
				
				System.out.println(txHash);

			}

			assertThat(txHash, containsString("0x"));
		} catch (Exception e) {

			e.printStackTrace();
			fail();
		}
	}

	// @Test
//	public void hasCap() throws Exception {
//		long startTime = System.nanoTime();
//
//		String address = MnidUtils.decode("cwMLLMoygTthyJoFfuB8TWFuzGJe2pNL1oQk4").getAddress();
//
//		String device = "0x504a11935c98d90ea7c933cc9ef777193609af89";
//
//		String cap = "fw";
//
//		Boolean hasCap = false;
//		for (IDigitalIdentity im : listIm) {
//			hasCap = im.hasCap(address, device, cap);
//			System.out.println(hasCap);
//			if (hasCap) {
//				break;
//			}
//		}
//
//		assertTrue(hasCap);
//
//		long endTime = System.nanoTime();
//		long timeElapsed = endTime - startTime;
//
//		System.out.println("Execution time in milliseconds : " + timeElapsed / 1000000);
//	}
//
//	@Test public void forwardToTest() { long startTime = System.nanoTime();
//	  
//	  // did:ev:cwMLLMoygTthyJoFfuB8TWFuzGJe2pNL1oQk4 //
////	  did:ev:cwMLHzXCtuwZPQ5cuxAaUFWfU6iWwaU7GjmLU //
////	  did:ev:cwMLPgHhgQsHEnzRyqzjh31A651gWbBdLfr53
//	  
//	  // String destination = "0x504a11935c98d90ea7c933cc9ef777193609af89"; try {
//	  String identity =
//	  MnidUtils.decode("cwMLLMoygTthyJoFfuB8TWFuzGJe2pNL1oQk4").getAddress();
//	  
//	  //identity = "0x504a11935c98d90ea7c933cc9ef777193609af89";
//	  
//	  @SuppressWarnings("rawtypes") Function functionAddRole = new
//	  Function("isOwner", Arrays.<Type>asList(new
//	  org.web3j.abi.datatypes.Address(160, identity)),
//	  Collections.<TypeReference<?>>emptyList());
//	  
//	  String encodedFunction = FunctionEncoder.encode(functionAddRole); byte[]
//	  dataBytes =
//	  Numeric.hexStringToByteArray(Numeric.cleanHexPrefix(encodedFunction));
//	  
//	  String txHash = listIm.get(0).forwardTo(identity, identity,
//	  BigInteger.valueOf(0), dataBytes);
//	  
//	  System.out.println(txHash); assertThat(txHash, containsString("0x")); }catch(
//
//	Exception e)
//	{
//		e.printStackTrace();
//		fail();
//	}
//
//	long endTime = System.nanoTime();
//	long timeElapsed = endTime - startTime;
//
//	System.out.println("Execution time in milliseconds : "+timeElapsed/1000000);
//
//}

//	@Test
//	public  void validateTime( )  {
//		// 1579365383
//		// 1590463607
//		
//		long nano = System.nanoTime(); 
//
//		
//		
//		Instant instant = Instant.now();
//		long timeStampSeconds = instant.getEpochSecond();
//		
//		
//		
//		long exp = 1590643607;
//		long iat = 1590463607;
//		
//		System.out.println(timeStampSeconds);
//		System.out.println(  nano / );
//
//				
//		boolean flag = (timeStampSeconds <= exp|| exp == 0);
//		
//		
//		assertTrue(flag);
//	}
}

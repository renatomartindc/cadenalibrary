package com.everis.blockchain.cadena.lib;

import org.apache.commons.codec.binary.Hex;
import org.web3j.crypto.Hash;
import org.web3j.protocol.exceptions.TransactionException;


public class Utils {
	public static  final String INVALID_ADDRESS_MNID_ERROR = "INVALID_ADDRESS_MNID_ERROR";

	private static String getRevertReason(String revertReason) {
		String errorMethodId = "0x08c379a0"; // Numeric.toHexString(Hash.sha3("Error(string)".getBytes())).substring(0,
												// 10)
		if (revertReason != null && revertReason.startsWith(errorMethodId)) {
			return hexToAscii(revertReason.substring(errorMethodId.length())).trim();
		}
		return revertReason;
	}
	public static String hexToAscii(String hexStr) {
		StringBuilder output = new StringBuilder("");
		for (int i = 0; i < hexStr.length(); i += 2) {
			String str = hexStr.substring(i, i + 2);
			output.append((char) Integer.parseInt(str, 16));
		}
		return output.toString();
	}
	public static String getRevertReason(Exception e) {
		if (e instanceof TransactionException) {
			TransactionException tx = (TransactionException) e;
			if (tx.getTransactionReceipt() != null && tx.getTransactionReceipt().isPresent()) {
				return Utils.getRevertReason(tx.getTransactionReceipt().get().getRevertReason());
			}
		}
		return e != null ? e.getMessage() : "";
	}
	public static byte[] calculateHash(String data) {
		byte[] hash = Hash.sha256(data.getBytes());
		return hash;
	}

	public static String hashByteToString(byte[] hash) {
		return "0x" + Hex.encodeHexString(hash);
	}

	public static String calculateHashString(String data) {
		byte[] hash = calculateHash(data);
		return hashByteToString(hash);
	}

	public static byte[] stringToBytes(String string, int lenght) {
		byte[] byteValue = string.getBytes();
		byte[] byteValueLen = new byte[lenght];
		System.arraycopy(byteValue, 0, byteValueLen, 0, byteValue.length);
		return byteValueLen;
	}

	public static String shaKeccak256(String data) {
		return Hash.sha3String(data);
	}


	

}
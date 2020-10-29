package com.everis.blockchain.cadena;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import io.reactivex.annotations.Nullable;

public interface ICadena {

	// MRA
	/**
	 * Constructor
	 * 
	 * @param mra           Address of the contract MRA.
	 * @param ethCoreParams Parameters for instantiating web3j.
	 * @param privGroupId   Private group Id of the Orion nodes participants.
	 * @param fromNode      Base64 public key of the Orion node sender.
	 */
	// void Cadena(@Nullable String mra, EthCoreParams ethCoreParams)

	/**
	 * This method is used to deploy a new MRA contract.
	 * 
	 * @return This returns the contract address.
	 */
	String deployMRA() throws IOException, Exception;

	/**
	 * This method is used to update the address of the MRA contract used in the
	 * library.
	 * 
	 * @param newAddressMRA The new MRA contract address.
	 * @return Nothing.
	 */
	void setAddressMRA(String newAddressMRA);

	/**
	 * This method is used to add a member in the MRA contract.
	 * 
	 * @param member The new member to add/approve in MRA contract. If member is
	 *               null, the address of who makes the transaction is used
	 * @return Transaction hash.
	 */
	String approveMember(@Nullable String member);

	/**
	 * This method is used to get the information of certain certificate using its
	 * TIN.
	 * 
	 * @param TIN The identification of the certificate.
	 * @return (Sring data, String state, String observation, Boolean visible,
	 *         String owner). To get information of the Tuple use .component1(),
	 *         .component2(), component3(), ...
	 */
	Certificate getCertificate(String TIN) throws Exception;

	/**
	 * This method is used to add/update a certificate.
	 * 
	 * @param TIN         The identification of the certificate.
	 * @param certificate The string content data of the certificate.
	 * @param state       The state of the certificate (approved, pending, revoked,
	 *                    ...).
	 * @param observation The observation of the certificate.
	 * @param visible     The visibility of the certificate.
	 * @return Transaction hash.
	 */
	String addCertificate(String TIN, String certificate, String state, String observation, Boolean visible)
			throws Exception;

//	/**
//	 * This method is used to update the content data of a certificate.
//	 * 
//	 * @param TIN  The identification of the certificate.
//	 * @param data The string content data of the certificate.
//	 * @return Transaction hash.
//	 */
//	String updateCertificateData(String TIN, String data);

	/**
	 * This method is used to update the visibility of a certificate.
	 * 
	 * @param TIN     The identification of the certificate.
	 * @param visible The visibility of the certificate.
	 * @return Transaction hash.
	 */
	String updateCertificateVisible(String TIN, Boolean visible);

//	/**
//	 * This method is used to update the state of a certificate.
//	 * 
//	 * @param TIN   The identification of the certificate.
//	 * @param state The state of the certificate (approved, pending, revoked, ...)
//	 * @return Transaction hash.
//	 */
//	String updateCertificateState(String TIN, String state);

//	/**
//	 * This method is used to update the state of a certificate.
//	 * 
//	 * @param TIN         The identification of the certificate.
//	 * @param observation The observation of the certificate.
//	 * @return Transaction hash.
//	 */
//	//String updateCertificateObservation(String TIN, String observation);

	/**
	 * This method is used to get all certificates in contract MRA .
	 * 
	 * @return List with all Certificates in range.
	 * @throws Exception
	 */
	void watchCertificates(List<Cadena> listCadena, IObserverCertificate observerCertificate);
	
	/**
	 * get List Certificates
	 * @param initialBlock
	 * @param finalBlock
	 * @return
	 */
	void getCertificatesHistory(BigInteger initialBlock, BigInteger finalBlock,List<Cadena> listCadena, IObserverCertificate observerCertificate);

	/**
	 * This method is used to get the last value save for the last block number. Run
	 * after calling the getCertificatesAdded or getCertificatesModified function.
	 * 
	 * @return Last block number.
	 */
	BigInteger getLastBlock();

	/**
	 * This method is used to validate if an address is member in a MRA contract.
	 * 
	 * @param member Address of the member to validate in contract.
	 * @return Boolean that indicates if the member belongs to the contract.
	 */
	Boolean isMember(String member) throws Exception;

	/**
	 * This method is used to get the total members in contract.
	 * 
	 * @return Numeber of total members.
	 */
	BigInteger totalMembers() throws Exception;

	/**
	 * This method is used to indicate the possibility that a member has over all
	 * shared certificates.
	 * 
	 * @param member  Member who will be given some visibility.
	 * @param visible Visibility granted to a member.
	 * @return Transaction hash.
	 */
	String setVisibilityToMember(String member, Boolean visible) throws Exception;

	/**
	 * This method is used to get the visibility over all shared certificates from
	 * other member.
	 * 
	 * @param member Member to get the visibility.
	 * @return Visibility for certain member.
	 */
	Boolean getVisibilityFromMember(String member) throws Exception;

	/**
	 * This method is used to get the visibility over all shared certificates from
	 * other member.
	 * 
	 * @param memberOrigin Member which will grant visibility.
	 * @param memberTarget Member who will verify their visibility for a
	 *                     memberOrigin.
	 * @return Visibility that a target member has about an origin.
	 */
	Boolean getVisibility(String memberOrigin, String memberTarget) throws Exception;

	/**
	 * This method is used to leave the contract MRA. By doing this you can no
	 * longer have any action on the certificates or vote to add new members.
	 * 
	 * @return Transaction hash.
	 */
	String retire();
	
	/**
	 * get last block from node BESU
	 * @return last block number
	 * @throws IOException
	 */
	 BigInteger getBlockActualNodeBesu() throws IOException ;
}
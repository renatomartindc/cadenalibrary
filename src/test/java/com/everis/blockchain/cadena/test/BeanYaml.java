package com.everis.blockchain.cadena.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BeanYaml {
	
	public String getIpNodoAduna() {
		return ipNodoAduna;
	}
	public void setIpNodoAduna(String ipNodoAduna) {
		this.ipNodoAduna = ipNodoAduna;
	}
	public String getPrivateKeyAduana() {
		return privateKeyAduana;
	}
	public void setPrivateKeyAduana(String privateKeyAduana) {
		this.privateKeyAduana = privateKeyAduana;
	}
	public String getAddressEthereumAduana() {
		return addressEthereumAduana;
	}
	public void setAddressEthereumAduana(String addressEthereumAduana) {
		this.addressEthereumAduana = addressEthereumAduana;
	}
	public String getContractAddressAEOM() {
		return contractAddressAEOM;
	}
	public void setContractAddressAEOM(String contractAddressAEOM) {
		this.contractAddressAEOM = contractAddressAEOM;
	}
	public String getContractAddressVR() {
		return contractAddressVR;
	}
	public void setContractAddressVR(String contractAddressVR) {
		this.contractAddressVR = contractAddressVR;
	}
	public String getContractAddressDIM() {
		return contractAddressDIM;
	}
	public void setContractAddressDIM(String contractAddressDIM) {
		this.contractAddressDIM = contractAddressDIM;
	}
	public String getProxyAddressAduana() {
		return proxyAddressAduana;
	}
	public void setProxyAddressAduana(String proxyAddressAduana) {
		this.proxyAddressAduana = proxyAddressAduana;
	}
	public String getProxyAddressAdministrator() {
		return proxyAddressAdministrator;
	}
	public void setProxyAddressAdministrator(String proxyAddressAdministrator) {
		this.proxyAddressAdministrator = proxyAddressAdministrator;
	}
	public boolean isFlagVisibilityAduana() {
		return flagVisibilityAduana;
	}
	public void setFlagVisibilityAduana(boolean flagVisibilityAduana) {
		this.flagVisibilityAduana = flagVisibilityAduana;
	}
	public String getContractAddressRMA() {
		return contractAddressRMA;
	}
	public void setContractAddressRMA(String contractAddressRMA) {
		this.contractAddressRMA = contractAddressRMA;
	}
	public String getPrivacyGroupId() {
		return privacyGroupId;
	}
	public void setPrivacyGroupId(String privacyGroupId) {
		this.privacyGroupId = privacyGroupId;
	}
	public String getFromNodeAduana() {
		return fromNodeAduana;
	}
	public void setFromNodeAduana(String fromNodeAduana) {
		this.fromNodeAduana = fromNodeAduana;
	}
	public String getIpNodoAduanaB() {
		return ipNodoAduanaB;
	}
	public void setIpNodoAduanaB(String ipNodoAduanaB) {
		this.ipNodoAduanaB = ipNodoAduanaB;
	}
	public String getPrivateKeyAduanaB() {
		return privateKeyAduanaB;
	}
	public void setPrivateKeyAduanaB(String privateKeyAduanaB) {
		this.privateKeyAduanaB = privateKeyAduanaB;
	}
	public String getFromNodeAduanaB() {
		return fromNodeAduanaB;
	}
	public void setFromNodeAduanaB(String fromNodeAduanaB) {
		this.fromNodeAduanaB = fromNodeAduanaB;
	}
	public String getAddressEthereumAduanaB() {
		return addressEthereumAduanaB;
	}
	public void setAddressEthereumAduanaB(String addressEthereumAduanaB) {
		this.addressEthereumAduanaB = addressEthereumAduanaB;
	}
	public boolean isFlagVisibilityAduanaB() {
		return flagVisibilityAduanaB;
	}
	public void setFlagVisibilityAduanaB(boolean flagVisibilityAduanaB) {
		this.flagVisibilityAduanaB = flagVisibilityAduanaB;
	}
	@JsonProperty("BLOCKCHAIN_SERVER")
	private String ipNodoAduna;
	
	@JsonProperty("PRIVATE_KEY")
	private String privateKeyAduana;
	
	@JsonProperty("ADDRESS")
	private String addressEthereumAduana;
	
	@JsonProperty("CONTRACT_ADDRESS_AEO_MANAGER")
	private String contractAddressAEOM;
	
	@JsonProperty("CONTRACT_ADDRESS_VERIFICATION_REGISTRY")
	private String contractAddressVR;
	
	@JsonProperty("CONTRACT_ADDRESS_IDENTITY_MANAGER")
	private String contractAddressDIM;
	
	@JsonProperty("CONTRACT_ADDRESS_PROXY_CUSTOM")
	private String proxyAddressAduana;
	
	@JsonProperty("CONTRACT_ADDRESS_PROXY_ADMINISTRATOR")
	private String proxyAddressAdministrator;
	
	@JsonProperty("FLAG_VISIBILITY_ADUANA")
	private boolean flagVisibilityAduana;
	
	@JsonProperty("CONTRACT_ADDRESS_RMA")
	private String contractAddressRMA ;

	@JsonProperty("GROUP_ID")
	private String privacyGroupId;
	
	@JsonProperty("FROM_NODE")
	private String fromNodeAduana;
	
	private String ipNodoAduanaB;
	private String privateKeyAduanaB;
	private String fromNodeAduanaB;
	private String addressEthereumAduanaB ;
	private boolean flagVisibilityAduanaB;
	


}

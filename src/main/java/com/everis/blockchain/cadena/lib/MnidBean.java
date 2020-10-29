package com.everis.blockchain.cadena.lib;

public class MnidBean {
	private String network;
	private String address;
	
	public MnidBean(String network, String address) {
		this.setAddress(address);
		this.setNetwork(network);
	}
	public MnidBean() {}
	public String getNetwork() {
		return network;
	}
	public void setNetwork(String network) {
		this.network = network;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}

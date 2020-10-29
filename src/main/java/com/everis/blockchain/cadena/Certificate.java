package com.everis.blockchain.cadena;

public class Certificate {

	private String tin;
	private String data;
	private String state;
	private String observation;
	private Boolean visible;
	private String owner;

	public Certificate(String cTIN, String cData, String cState, String cObservation, Boolean cVisible, String cOwner) {
		tin = cTIN;
		data = cData;
		setState(cState);
		observation = cObservation;
		visible = cVisible;
		owner = cOwner;
	}

	public String getTin() {
		return tin;
	}

	public String getData() {
		return data;
	}

	public String getObservation() {
		return observation;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOwner() {
		return owner;
	}

	public Boolean getVisible() {
		return visible;
	}

}
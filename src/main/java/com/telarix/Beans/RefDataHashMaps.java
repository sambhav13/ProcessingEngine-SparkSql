package com.telarix.Beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class RefDataHashMaps implements Serializable{

	//IF NOT NEEDED, REMOVE - PGB
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<String, String> inTrunkData = new HashMap<>();
	private HashMap<String, String> outTrunkData = new HashMap<>();
	private HashMap<String, String> inAgreementData = new HashMap<>();	
	private HashMap<String, String> outAgreementData = new HashMap<>();	
	private HashMap<String, String> SwitchData = new HashMap<>();
	
	public HashMap<String, String> getInTrunkData() {
		return inTrunkData;
	}
	public void setInTrunkData(HashMap<String, String> inTrunkData) {
		this.inTrunkData = inTrunkData;
	}
	public HashMap<String, String> getOutTrunkData() {
		return outTrunkData;
	}
	public void setOutTrunkData(HashMap<String, String> outTrunkData) {
		this.outTrunkData = outTrunkData;
	}
	public HashMap<String, String> getInAgreementData() {
		return inAgreementData;
	}
	public void setInAgreementData(HashMap<String, String> inAgreementData) {
		this.inAgreementData = inAgreementData;
	}
	public HashMap<String, String> getOutAgreementData() {
		return outAgreementData;
	}
	public void setOutAgreementData(HashMap<String, String> outAgreementData) {
		this.outAgreementData = outAgreementData;
	}
	public HashMap<String, String> getSwitchData() {
		return SwitchData;
	}
	public void setSwitchData(HashMap<String, String> switchData) {
		SwitchData = switchData;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}







}

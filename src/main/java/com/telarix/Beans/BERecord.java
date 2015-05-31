package com.telarix.Beans;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by ashley.rose on 3/30/2015.
 */
public class BERecord implements Serializable {

    //IF NOT NEEDED, REMOVE - PGB
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String callingNumber;
    private String calledNumber;
    private String inTrunkName;
    private String outTrunkName;
    private Timestamp callDate;
    private int callHour;
    private float callDuration;
    private float circuitDuration;
    private String callType;
    private int faultCode;
    private String nprn;
    private String customField1;
    private String customField2;
    private String customField3;
    private String networkCode;
    private int portabilityPreResolvedFlag;
    private float inJitter;
    private float outJitter;
    private int inPacketDelay;
    private int outPacketDelay;
    private float inPacketLoss;
    private float outPacketLoss;
    private float inMOS;
    private float outMOS;
    private String internalCodecNumber;
    private String inExternalCodecNumber;
    private String outExternalCodecNumber;

    public BERecord() { }

    public String getCallingNumber() {
        return callingNumber;
    }

    public void setCallingNumber(String callingNumber) {
        this.callingNumber = callingNumber;
    }

    public String getCalledNumber() {
        return calledNumber;
    }

    public void setCalledNumber(String calledNumber) {
        this.calledNumber = calledNumber;
    }

    public String getInTrunkName() {
        return inTrunkName;
    }

    public void setInTrunkName(String inTrunkName) {
        this.inTrunkName = inTrunkName;
    }

    public String getOutTrunkName() {
        return outTrunkName;
    }

    public void setOutTrunkName(String outTrunkName) {
        this.outTrunkName = outTrunkName;
    }

    public Timestamp getCallDate() {
        return callDate;
    }

    public void setCallDate(Timestamp callDate) {
        this.callDate = callDate;
    }

    public int getCallHour() {
        return callHour;
    }

    public void setCallHour(int callHour) {
        this.callHour = callHour;
    }

    public float getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(float callDuration) {
        this.callDuration = callDuration;
    }

    public float getCircuitDuration() {
        return circuitDuration;
    }

    public void setCircuitDuration(float circuitDuration) {
        this.circuitDuration = circuitDuration;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public int getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(int faultCode) {
        this.faultCode = faultCode;
    }

    public String getNprn() {
        return nprn;
    }

    public void setNprn(String nprn) {
        this.nprn = nprn;
    }

    public String getCustomField1() {
        return customField1;
    }

    public void setCustomField1(String customField1) {
        this.customField1 = customField1;
    }

    public String getCustomField2() {
        return customField2;
    }

    public void setCustomField2(String customField2) {
        this.customField2 = customField2;
    }

    public String getCustomField3() {
        return customField3;
    }

    public void setCustomField3(String customField3) {
        this.customField3 = customField3;
    }

    public String getNetworkCode() {
        return networkCode;
    }

    public void setNetworkCode(String networkCode) {
        this.networkCode = networkCode;
    }

    public int getPortabilityPreResolvedFlag() {
        return portabilityPreResolvedFlag;
    }

    public void setPortabilityPreResolvedFlag(int portabilityPreResolvedFlag) {
        this.portabilityPreResolvedFlag = portabilityPreResolvedFlag;
    }

    public float getInJitter() {
        return inJitter;
    }

    public void setInJitter(float inJitter) {
        this.inJitter = inJitter;
    }

    public float getOutJitter() {
        return outJitter;
    }

    public void setOutJitter(float outJitter) {
        this.outJitter = outJitter;
    }

    public int getInPacketDelay() {
        return inPacketDelay;
    }

    public void setInPacketDelay(int inPacketDelay) {
        this.inPacketDelay = inPacketDelay;
    }

    public int getOutPacketDelay() {
        return outPacketDelay;
    }

    public void setOutPacketDelay(int outPacketDelay) {
        this.outPacketDelay = outPacketDelay;
    }

    public float getInPacketLoss() {
        return inPacketLoss;
    }

    public void setInPacketLoss(float inPacketLoss) {
        this.inPacketLoss = inPacketLoss;
    }

    public float getOutPacketLoss() {
        return outPacketLoss;
    }

    public void setOutPacketLoss(float outPacketLoss) {
        this.outPacketLoss = outPacketLoss;
    }

    public float getInMOS() {
        return inMOS;
    }

    public void setInMOS(float inMOS) {
        this.inMOS = inMOS;
    }

    public float getOutMOS() {
        return outMOS;
    }

    public void setOutMOS(float outMOS) {
        this.outMOS = outMOS;
    }

    public String getInternalCodecNumber() {
        return internalCodecNumber;
    }

    public void setInternalCodecNumber(String internalCodecNumber) {
        this.internalCodecNumber = internalCodecNumber;
    }

    public String getInExternalCodecNumber() {
        return inExternalCodecNumber;
    }

    public void setInExternalCodecNumber(String inExternalCodecNumber) {
        this.inExternalCodecNumber = inExternalCodecNumber;
    }

    public String getOutExternalCodecNumber() {
        return outExternalCodecNumber;
    }

    public void setOutExternalCodecNumber(String outExternalCodecNumber) {
        this.outExternalCodecNumber = outExternalCodecNumber;
    }
}

package com.redhat.reportengine.udppacket;

import java.io.Serializable;

public class REUdpPacket implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5898718474196133337L;
	
	public static final String RESOURCE_TYPE = "RESOURCE_TYPE";
	
	private MSG_TYPE msgType;
	private String objectString;
	private int serverId;
	private long time;
	
	public MSG_TYPE getMsgType() {
		return msgType;
	}
	public void setMsgType(MSG_TYPE msgType) {
		this.msgType = msgType;
	}
	
	public int getServerId() {
		return serverId;
	}
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getObjectString() {
		return objectString;
	}
	public void setObjectString(String objectString) {
		this.objectString = objectString;
	}
}

package com.redhat.reportengine.udppacket;

import java.io.Serializable;

public class REUdpPacket implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5898718474196133337L;
	
	private MSG_TYPE msgType;
	private Object object;
	private int serverId;
	private long time;
	
	public MSG_TYPE getMsgType() {
		return msgType;
	}
	public void setMsgType(MSG_TYPE msgType) {
		this.msgType = msgType;
	}
	
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
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
}

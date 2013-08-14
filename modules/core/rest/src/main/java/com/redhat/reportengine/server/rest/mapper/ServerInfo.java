package com.redhat.reportengine.server.rest.mapper;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 31, 2013
 */
public class ServerInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3609929707428238374L;
	
	private int serverId;
	private String serverName;
	private InetAddress serverAddress;
	private int reServerUdpPort;
	
	public int getServerId() {
		return serverId;
	}
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public int getReServerUdpPort() {
		return reServerUdpPort;
	}
	public void setReServerUdpPort(int reServerUdpPort) {
		this.reServerUdpPort = reServerUdpPort;
	}
	public InetAddress getServerAddress() {
		return serverAddress;
	}
	public void setServerAddress(InetAddress serverAddress) {
		this.serverAddress = serverAddress;
	}
	
}

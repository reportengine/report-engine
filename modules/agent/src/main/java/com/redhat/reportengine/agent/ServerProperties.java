package com.redhat.reportengine.agent;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerProperties {
	private static int serverId;
	private static String serverName;
	private static boolean enabled = false;
	private static InetAddress serverAddress ;
	private static int serverUdpPort = 20500;

	
	
	public static InetAddress getServerAddress() throws UnknownHostException {
		if(serverAddress == null){
			serverAddress = InetAddress.getByName("localhost");
		}
		return serverAddress;
	}
	public static void setServerAddress(InetAddress serverAddress) {
		ServerProperties.serverAddress = serverAddress;
	}
	public static int getServerUdpPort() {
		return serverUdpPort;
	}
	public static void setServerUdpPort(int udpPort) {
		ServerProperties.serverUdpPort = udpPort;
	}

	public static int getServerId() {
		return serverId;
	}
	public static void setServerId(int serverId) {
		ServerProperties.serverId = serverId;
	}
	public static String getServerName() {
		return serverName;
	}
	public static void setServerName(String serverName) {
		ServerProperties.serverName = serverName;
	}
	public static boolean isEnabled() {
		return enabled;
	}
	public static void setEnabled(boolean enabled) {
		ServerProperties.enabled = enabled;
	}
}

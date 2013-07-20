package com.redhat.reportengine.agent.rest.mapper;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;


import org.hyperic.sigar.NetInfo;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;


/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * May 28, 2013
 */
@XmlRootElement
public class NetworkInfo extends AgentBaseMap implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5162973172102931837L;
	
	
	private NetInfo netInfo;
	private NetInterfaceConfig netInterfaceConfig;
	private NetInterfaceStat netInterfaceStat;
	private long time;
	
	/**
	 * @return the netInfo
	 */
	public NetInfo getNetInfo() {
		return netInfo;
	}
	/**
	 * @param netInfo the netInfo to set
	 */
	public void setNetInfo(NetInfo netInfo) {
		this.netInfo = netInfo;
	}
	/**
	 * @return the netInterfaceConfig
	 */
	public NetInterfaceConfig getNetInterfaceConfig() {
		return netInterfaceConfig;
	}
	/**
	 * @param netInterfaceConfig the netInterfaceConfig to set
	 */
	public void setNetInterfaceConfig(NetInterfaceConfig netInterfaceConfig) {
		this.netInterfaceConfig = netInterfaceConfig;
	}
	/**
	 * @return the netInterfaceStat
	 */
	public NetInterfaceStat getNetInterfaceStat() {
		return netInterfaceStat;
	}
	/**
	 * @param netInterfaceStat the netInterfaceStat to set
	 */
	public void setNetInterfaceStat(NetInterfaceStat netInterfaceStat) {
		this.netInterfaceStat = netInterfaceStat;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
}

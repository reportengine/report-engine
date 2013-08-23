package com.redhat.reportengine.server.dbmap;

import java.util.Date;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 08, 2013
 */
public class Server {
	private Integer id;
	private String 	name;
	private String	hostIp;
	private Boolean reachable;
	private String 	platform;
	private Boolean agentStatus;
	private Integer agentPort;
	private String 	referenceKey;
	private String 	discoveryStatus;
	private Integer updateInterval;
	private Date 	creationTime;
	private Date 	localTime;
	private String  macAddr;

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHostIp() {
		return hostIp;
	}
	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}
	public Boolean isReachable() {
		return reachable;
	}
	public void setReachable(Boolean reachable) {
		this.reachable = reachable;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public Boolean isAgentStatus() {
		return agentStatus;
	}
	public void setAgentStatus(Boolean agentStatus) {
		this.agentStatus = agentStatus;
	}
	public Integer getAgentPort() {
		return agentPort;
	}
	public void setAgentPort(Integer agentPort) {
		this.agentPort = agentPort;
	}
	public String getReferenceKey() {
		return referenceKey;
	}
	public void setReferenceKey(String referenceKey) {
		this.referenceKey = referenceKey;
	}
	public String getDiscoveryStatus() {
		return discoveryStatus;
	}
	public void setDiscoveryStatus(String discoveryStatus) {
		this.discoveryStatus = discoveryStatus;
	}
	public Integer getUpdateInterval() {
		return updateInterval;
	}
	public void setUpdateInterval(Integer updateInterval) {
		this.updateInterval = updateInterval;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public Date getLocalTime() {
		return localTime;
	}
	public void setLocalTime(Date localTime) {
		this.localTime = localTime;
	}
	public String getMacAddr() {
		return macAddr;
	}
	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	} 
	
}

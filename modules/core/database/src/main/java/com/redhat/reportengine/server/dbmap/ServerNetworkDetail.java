package com.redhat.reportengine.server.dbmap;

import java.util.Date;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 19, 2013
 */
public class ServerNetworkDetail {
	private Integer id;
	private Integer serverId;
	private Date remoteTime;
	private Date 	localTime;
	private String hostname;
	private String defaultGateway;
	private String primaryDns;
	private String secondaryDns;
	private String domainName;
	private String interfaceName;
	private String interfaceDescription;
	private String interfaceType;
	private String ipAddress;
	private String subnetmask;
	private String broadcast;
	private String destination;
	private String mac;
	private Long flags;
	private Long metric;
	private Long mtu;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getServerId() {
		return serverId;
	}
	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}
	public Date getRemoteTime() {
		return remoteTime;
	}
	public void setRemoteTime(Date remoteTime) {
		this.remoteTime = remoteTime;
	}
	public Date getLocalTime() {
		return localTime;
	}
	public void setLocalTime(Date localTime) {
		this.localTime = localTime;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getDefaultGateway() {
		return defaultGateway;
	}
	public void setDefaultGateway(String defaultGateway) {
		this.defaultGateway = defaultGateway;
	}
	public String getPrimaryDns() {
		return primaryDns;
	}
	public void setPrimaryDns(String primaryDns) {
		this.primaryDns = primaryDns;
	}
	public String getSecondaryDns() {
		return secondaryDns;
	}
	public void setSecondaryDns(String secondaryDns) {
		this.secondaryDns = secondaryDns;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getInterfaceDescription() {
		return interfaceDescription;
	}
	public void setInterfaceDescription(String interfaceDescription) {
		this.interfaceDescription = interfaceDescription;
	}
	public String getInterfaceType() {
		return interfaceType;
	}
	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getSubnetmask() {
		return subnetmask;
	}
	public void setSubnetmask(String subnetmask) {
		this.subnetmask = subnetmask;
	}
	public String getBroadcast() {
		return broadcast;
	}
	public void setBroadcast(String broadcast) {
		this.broadcast = broadcast;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public Long getFlags() {
		return flags;
	}
	public void setFlags(Long flags) {
		this.flags = flags;
	}
	public Long getMetric() {
		return metric;
	}
	public void setMetric(Long metric) {
		this.metric = metric;
	}
	public Long getMtu() {
		return mtu;
	}
	public void setMtu(Long mtu) {
		this.mtu = mtu;
	}
	
	
	
	}

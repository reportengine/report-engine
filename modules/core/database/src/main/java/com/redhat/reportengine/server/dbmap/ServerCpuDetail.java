package com.redhat.reportengine.server.dbmap;

import java.util.Date;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 15, 2013
 */
public class ServerCpuDetail {
	private Integer id;
	private Integer serverId;
	private Date remoteTime;
	private Date 	localTime;
	private Long cacheSize;
	private Integer coresPerSocket;
	private Integer mhz;
	private Integer totalCores;
	private Integer totalSockets;
	private String vendor;
	private String model;
	
	
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
	public Long getCacheSize() {
		return cacheSize;
	}
	public void setCacheSize(Long cacheSize) {
		this.cacheSize = cacheSize;
	}
	public Integer getCoresPerSocket() {
		return coresPerSocket;
	}
	public void setCoresPerSocket(Integer coresPerSocket) {
		this.coresPerSocket = coresPerSocket;
	}
	public Integer getMhz() {
		return mhz;
	}
	public void setMhz(Integer mhz) {
		this.mhz = mhz;
	}
	public Integer getTotalCores() {
		return totalCores;
	}
	public void setTotalCores(Integer totalCores) {
		this.totalCores = totalCores;
	}
	public Integer getTotalSockets() {
		return totalSockets;
	}
	public void setTotalSockets(Integer totalSockets) {
		this.totalSockets = totalSockets;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	}

package com.redhat.reportengine.server.dbmap;

import java.util.Date;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 19, 2013
 */
public class ServerMemoryDetail {
	private Integer id;
	private Integer serverId;
	private Date remoteTime;
	private Date 	localTime;
	private Long physical;
	private Long swap;
	
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
	public Long getPhysical() {
		return physical;
	}
	public void setPhysical(Long physical) {
		this.physical = physical;
	}
	public Long getSwap() {
		return swap;
	}
	public void setSwap(Long swap) {
		this.swap = swap;
	}
	
		
	
	}

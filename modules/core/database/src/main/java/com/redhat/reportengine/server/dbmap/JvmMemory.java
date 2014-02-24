/**
 * 
 */
package com.redhat.reportengine.server.dbmap;

import java.util.Date;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Feb 3, 2014
 */
public class JvmMemory {
	
	private Long id;
	private Date localTime;
	private Date remoteTime;
	
	private long committed;
	private long init;
	private long max;
	private long used;
	private boolean heapMemory;
	
	//Not used in DB
	private String tableSubName;
	private Date fromTime;
	private Date toTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getLocalTime() {
		return localTime;
	}
	public void setLocalTime(Date localTime) {
		this.localTime = localTime;
	}
	public Date getRemoteTime() {
		return remoteTime;
	}
	public void setRemoteTime(Date remoteTime) {
		this.remoteTime = remoteTime;
	}
	public long getCommitted() {
		return committed;
	}
	public void setCommitted(long committed) {
		this.committed = committed;
	}
	public long getInit() {
		return init;
	}
	public void setInit(long init) {
		this.init = init;
	}
	public long getMax() {
		return max;
	}
	public void setMax(long max) {
		this.max = max;
	}
	public long getUsed() {
		return used;
	}
	public void setUsed(long used) {
		this.used = used;
	}
	public boolean isHeapMemory() {
		return heapMemory;
	}
	public void setHeapMemory(boolean heapMemory) {
		this.heapMemory = heapMemory;
	}
	
	
	//Not used in DB
	public String getTableSubName() {
		return tableSubName;
	}
	public void setTableSubName(String tableSubName) {
		this.tableSubName = tableSubName;
	}
	public Date getFromTime() {
		return fromTime;
	}
	public void setFromTime(Date fromTime) {
		this.fromTime = fromTime;
	}
	public Date getToTime() {
		return toTime;
	}
	public void setToTime(Date toTime) {
		this.toTime = toTime;
	}

}

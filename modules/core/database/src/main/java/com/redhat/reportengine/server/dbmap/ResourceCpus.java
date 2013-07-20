package com.redhat.reportengine.server.dbmap;

import java.util.Date;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 05, 2013
 */
public class ResourceCpus {
	private Integer id;
	private Date localTime;
	private Date remoteTime;
	private Float[] user;
	private Float[] sys;
	private Float[] nice;
	private Float[] idle;
	private Float[] wait;
	private Float[] irq;
	private Float[] softIrq;
	private Float[] stolen;
	private Float[] combined;
	
	private String tableSubName;
	private Date fromTime;
	private Date toTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public Float[] getUser() {
		return user;
	}
	public void setUser(Float[] user) {
		this.user = user;
	}
	public Float[] getSys() {
		return sys;
	}
	public void setSys(Float[] sys) {
		this.sys = sys;
	}
	public Float[] getNice() {
		return nice;
	}
	public void setNice(Float[] nice) {
		this.nice = nice;
	}
	public Float[] getIdle() {
		return idle;
	}
	public void setIdle(Float[] idle) {
		this.idle = idle;
	}
	public Float[] getWait() {
		return wait;
	}
	public void setWait(Float[] wait) {
		this.wait = wait;
	}
	public Float[] getIrq() {
		return irq;
	}
	public void setIrq(Float[] irq) {
		this.irq = irq;
	}
	public Float[] getSoftIrq() {
		return softIrq;
	}
	public void setSoftIrq(Float[] softIrq) {
		this.softIrq = softIrq;
	}
	public Float[] getStolen() {
		return stolen;
	}
	public void setStolen(Float[] stolen) {
		this.stolen = stolen;
	}
	public Float[] getCombined() {
		return combined;
	}
	public void setCombined(Float[] combined) {
		this.combined = combined;
	}
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

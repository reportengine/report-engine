package com.redhat.reportengine.server.dbmap;

import java.util.Date;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 11, 2013
 */
public class ResourceMemory {
	private Integer id;
	private Date localTime;
	private Date remoteTime;
	private long total;
	private long used;
	private long actualUsed;
	private long swapTotal;
	private long swapUsed;
	private long swapPageIn;
	private long swapPageOut;
	
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
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public long getUsed() {
		return used;
	}
	public void setUsed(long used) {
		this.used = used;
	}
	public long getActualUsed() {
		return actualUsed;
	}
	public void setActualUsed(long actualUsed) {
		this.actualUsed = actualUsed;
	}
	public long getSwapTotal() {
		return swapTotal;
	}
	public void setSwapTotal(long swapTotal) {
		this.swapTotal = swapTotal;
	}
	public long getSwapUsed() {
		return swapUsed;
	}
	public void setSwapUsed(long swapUsed) {
		this.swapUsed = swapUsed;
	}
	public long getSwapPageIn() {
		return swapPageIn;
	}
	public void setSwapPageIn(long swapPageIn) {
		this.swapPageIn = swapPageIn;
	}
	public long getSwapPageOut() {
		return swapPageOut;
	}
	public void setSwapPageOut(long swapPageOut) {
		this.swapPageOut = swapPageOut;
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

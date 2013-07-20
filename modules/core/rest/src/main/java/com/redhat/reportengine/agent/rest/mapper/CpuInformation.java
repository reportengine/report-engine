package com.redhat.reportengine.agent.rest.mapper;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;



/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * May 14, 2013
 */
@XmlRootElement
public class CpuInformation extends AgentBaseMap implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long cacheSize;
	private Integer coresPerSocket;
	private Integer mhz;
	private Integer totalCores;
	private Integer totalSockets;
	private String vendor;
	private String model;
	private long time;
	
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
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	

}

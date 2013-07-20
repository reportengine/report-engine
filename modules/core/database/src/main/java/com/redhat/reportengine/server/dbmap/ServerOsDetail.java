package com.redhat.reportengine.server.dbmap;

import java.util.Date;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 19, 2013
 */
public class ServerOsDetail {
	private Integer id;
	private Integer serverId;
	private Date remoteTime;
	private Date 	localTime;
	private String name;
	private String description;
	private String arch;
	private String machine;
	private String kernelVersion;
	private String patchLevel;
	private String vendor;
	private String vendorVersion;
	private String vendorCodeName;
	private String dataModel;
	private String cpuEndian;
	private String vendorName;

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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getArch() {
		return arch;
	}
	public void setArch(String arch) {
		this.arch = arch;
	}
	public String getMachine() {
		return machine;
	}
	public void setMachine(String machine) {
		this.machine = machine;
	}
	public String getKernelVersion() {
		return kernelVersion;
	}
	public void setKernelVersion(String kernelVersion) {
		this.kernelVersion = kernelVersion;
	}
	public String getPatchLevel() {
		return patchLevel;
	}
	public void setPatchLevel(String patchLevel) {
		this.patchLevel = patchLevel;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getVendorVersion() {
		return vendorVersion;
	}
	public void setVendorVersion(String vendorVersion) {
		this.vendorVersion = vendorVersion;
	}
	public String getVendorCodeName() {
		return vendorCodeName;
	}
	public void setVendorCodeName(String vendorCodeName) {
		this.vendorCodeName = vendorCodeName;
	}
	public String getDataModel() {
		return dataModel;
	}
	public void setDataModel(String dataModel) {
		this.dataModel = dataModel;
	}
	public String getCpuEndian() {
		return cpuEndian;
	}
	public void setCpuEndian(String cpuEndian) {
		this.cpuEndian = cpuEndian;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}


}

/**
 * 
 */
package com.redhat.reportengine.agent.rest.mapper.jvm;

import java.lang.management.OperatingSystemMXBean;

import com.redhat.reportengine.agent.rest.mapper.AgentBaseMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jan 25, 2014
 */
public class JvmOperatingSystemMXBean extends AgentBaseMap{

	/**
	 * 
	 */
	private static final long serialVersionUID = 554164695132076731L;
	public JvmOperatingSystemMXBean(){
		
	}
	public JvmOperatingSystemMXBean(OperatingSystemMXBean operatingSystemMXBean){
		this.arch = operatingSystemMXBean.getArch();
		this.name = operatingSystemMXBean.getName();
		this.version = operatingSystemMXBean.getVersion();
		this.availableProcessors = operatingSystemMXBean.getAvailableProcessors();
		this.systemLoadAverage = operatingSystemMXBean.getSystemLoadAverage();
	}
	
	private String arch;
	private String name;
	private String version;
	private int availableProcessors;
	private double systemLoadAverage;
	public String getArch() {
		return arch;
	}
	public void setArch(String arch) {
		this.arch = arch;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public int getAvailableProcessors() {
		return availableProcessors;
	}
	public void setAvailableProcessors(int availableProcessors) {
		this.availableProcessors = availableProcessors;
	}
	public double getSystemLoadAverage() {
		return systemLoadAverage;
	}
	public void setSystemLoadAverage(double systemLoadAverage) {
		this.systemLoadAverage = systemLoadAverage;
	}
}

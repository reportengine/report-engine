/**
 * 
 */
package com.redhat.reportengine.agent.rest.mapper.jvm;

import java.io.Serializable;
import java.lang.management.CompilationMXBean;

import com.redhat.reportengine.agent.rest.mapper.AgentBaseMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jan 24, 2014
 */
public class JvmCompilationMXBean extends AgentBaseMap implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 70330823731997814L;
	
	private String name;
	private long totalCompilationTime;
	private boolean compilationTimeMonitoringSupported;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getTotalCompilationTime() {
		return totalCompilationTime;
	}
	public void setTotalCompilationTime(long totalCompilationTime) {
		this.totalCompilationTime = totalCompilationTime;
	}
	public boolean isCompilationTimeMonitoringSupported() {
		return compilationTimeMonitoringSupported;
	}
	public void setCompilationTimeMonitoringSupported(
			boolean compilationTimeMonitoringSupported) {
		this.compilationTimeMonitoringSupported = compilationTimeMonitoringSupported;
	}
	public JvmCompilationMXBean(CompilationMXBean compilationMXBean){
		this.name = compilationMXBean.getName();
		this.totalCompilationTime = compilationMXBean.getTotalCompilationTime();
		this.compilationTimeMonitoringSupported = compilationMXBean.isCompilationTimeMonitoringSupported();
	}
	public JvmCompilationMXBean(){
		
	}
}

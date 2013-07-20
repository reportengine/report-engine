package com.redhat.reportengine.agent.rest.mapper;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.hyperic.sigar.CpuPerc;


/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jun 26, 2013
 */
@XmlRootElement
public class UsageCpus extends AgentBaseMap implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4364621962884839149L;

	private CpuPerc[] cpus;
	private CpuPerc cpu;
	private long time;
	
	public CpuPerc[] getCpus() {
		return cpus;
	}
	public void setCpus(CpuPerc[] cpus) {
		this.cpus = cpus;
	}
	public CpuPerc getCpu() {
		return cpu;
	}
	public void setCpu(CpuPerc cpu) {
		this.cpu = cpu;
	}	
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
}

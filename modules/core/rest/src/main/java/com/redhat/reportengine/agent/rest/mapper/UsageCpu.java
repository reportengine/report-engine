package com.redhat.reportengine.agent.rest.mapper;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.hyperic.sigar.CpuPerc;


/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jun 26, 2013
 */
@XmlRootElement
public class UsageCpu extends AgentBaseMap implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CpuPerc cpu;
	private long time;
	

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

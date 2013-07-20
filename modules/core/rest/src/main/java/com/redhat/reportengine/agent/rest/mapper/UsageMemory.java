package com.redhat.reportengine.agent.rest.mapper;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Swap;


/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 10, 2013
 */
@XmlRootElement
public class UsageMemory extends AgentBaseMap implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Mem memory;
	private Swap swap;
	private long time;
	

	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public Mem getMemory() {
		return memory;
	}
	public void setMemory(Mem memory) {
		this.memory = memory;
	}
	public Swap getSwap() {
		return swap;
	}
	public void setSwap(Swap swap) {
		this.swap = swap;
	}

}

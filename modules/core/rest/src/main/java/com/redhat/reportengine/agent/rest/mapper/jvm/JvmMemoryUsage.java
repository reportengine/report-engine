/**
 * 
 */
package com.redhat.reportengine.agent.rest.mapper.jvm;

import java.io.Serializable;
import java.lang.management.MemoryUsage;

import com.redhat.reportengine.agent.rest.mapper.AgentBaseMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jan 24, 2014
 */
public class JvmMemoryUsage extends AgentBaseMap implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2867554729605147960L;
	
	private long committed;
	private long init;
	private long max;
	private long used;
	
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
	public JvmMemoryUsage(){
		
	}
	public JvmMemoryUsage(MemoryUsage memoryUsage){
		if(memoryUsage != null){
			this.committed = memoryUsage.getCommitted();
			this.init = memoryUsage.getInit();
			this.max = memoryUsage.getMax();
			this.used = memoryUsage.getUsed();
		}		
	}
	
	public String toString(){
		return new StringBuilder("Init:").append(this.init).append(", Used: ").append(this.used).append(", Committed: ").append(this.committed).append(", Max; ").append(this.max).toString();
	}
}

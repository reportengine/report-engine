/**
 * 
 */
package com.redhat.reportengine.agent.rest.mapper.jvm;

import java.io.Serializable;
import java.lang.management.GarbageCollectorMXBean;

import com.redhat.reportengine.agent.rest.mapper.AgentBaseMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jan 24, 2014
 */
public class JvmGarbageCollectorMXBean extends AgentBaseMap implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4866694479987112391L;
	
	private long collectionCount;
	private long collectionTime;
	private String name;
	private String[] memoryPollNames;
	private boolean valid;
	
	public long getCollectionCount() {
		return collectionCount;
	}
	public void setCollectionCount(long collectionCount) {
		this.collectionCount = collectionCount;
	}
	public long getCollectionTime() {
		return collectionTime;
	}
	public void setCollectionTime(long collectionTime) {
		this.collectionTime = collectionTime;
	}	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getMemoryPollNames() {
		return memoryPollNames;
	}
	public void setMemoryPollNames(String[] memoryPollNames) {
		this.memoryPollNames = memoryPollNames;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public JvmGarbageCollectorMXBean(GarbageCollectorMXBean garbageCollectorMXBean){
		this.collectionCount = garbageCollectorMXBean.getCollectionCount();
		this.collectionTime = garbageCollectorMXBean.getCollectionTime();
		this.memoryPollNames = garbageCollectorMXBean.getMemoryPoolNames();
		this.name = garbageCollectorMXBean.getName();
		garbageCollectorMXBean.isValid();
	}
	public JvmGarbageCollectorMXBean(){
		
	}

}

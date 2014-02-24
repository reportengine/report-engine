/**
 * 
 */
package com.redhat.reportengine.agent.rest.mapper.jvm;

import java.io.Serializable;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;

import com.redhat.reportengine.agent.rest.mapper.AgentBaseMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jan 24, 2014
 */
public class JvmMemoryPoolMXBean extends AgentBaseMap implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4971988369113645514L;
	
	private JvmMemoryUsage collectionUsage;
	private long collectionUsageThreshold;
	private long collectionUsageThresholdCount;
	private String[] memoryManagerNames;
	private String name;
	private JvmMemoryUsage peakUsage;
	private MemoryType type;
	private JvmMemoryUsage usage;
	private long usageThreshold;
	private long usageThresholdCount;
	private boolean collectionUsageThresholdExceeded;
	private boolean collectionUsageThresholdSupported;
	private boolean usageThresholdExceeded;
	private boolean usageThresholdSupported;
	private boolean valid;
	
	public JvmMemoryPoolMXBean(){
		
	}
	public JvmMemoryPoolMXBean(MemoryPoolMXBean memoryPoolMXBean){
		this.collectionUsageThresholdSupported = memoryPoolMXBean.isCollectionUsageThresholdSupported();
		this.usageThresholdSupported = memoryPoolMXBean.isUsageThresholdSupported();
		this.valid = memoryPoolMXBean.isValid();
		if(this.collectionUsageThresholdSupported){
			this.collectionUsageThreshold = memoryPoolMXBean.getCollectionUsageThreshold();
			this.collectionUsageThresholdCount = memoryPoolMXBean.getCollectionUsageThresholdCount();
			this.collectionUsageThresholdExceeded = memoryPoolMXBean.isCollectionUsageThresholdExceeded();
		}
		if(this.usageThresholdSupported){
			this.usageThreshold = memoryPoolMXBean.getUsageThreshold();
			this.usageThresholdExceeded = memoryPoolMXBean.isUsageThresholdExceeded();
			this.usageThresholdCount = memoryPoolMXBean.getUsageThresholdCount();
		}
		this.collectionUsage = new JvmMemoryUsage(memoryPoolMXBean.getCollectionUsage());
		this.memoryManagerNames = memoryPoolMXBean.getMemoryManagerNames();
		this.name = memoryPoolMXBean.getName();
		this.peakUsage = new JvmMemoryUsage(memoryPoolMXBean.getPeakUsage());
		this.type = memoryPoolMXBean.getType();
		this.usage =  new JvmMemoryUsage(memoryPoolMXBean.getUsage());
	}
	
	public JvmMemoryUsage getCollectionUsage() {
		return collectionUsage;
	}
	public void setCollectionUsage(JvmMemoryUsage collectionUsage) {
		this.collectionUsage = collectionUsage;
	}
	public long getCollectionUsageThreshold() {
		return collectionUsageThreshold;
	}
	public void setCollectionUsageThreshold(long collectionUsageThreshold) {
		this.collectionUsageThreshold = collectionUsageThreshold;
	}
	public long getCollectionUsageThresholdCount() {
		return collectionUsageThresholdCount;
	}
	public void setCollectionUsageThresholdCount(long collectionUsageThresholdCount) {
		this.collectionUsageThresholdCount = collectionUsageThresholdCount;
	}
	public String[] getMemoryManagerNames() {
		return memoryManagerNames;
	}
	public void setMemoryManagerNames(String[] memoryManagerNames) {
		this.memoryManagerNames = memoryManagerNames;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public JvmMemoryUsage getPeakUsage() {
		return peakUsage;
	}
	public void setPeakUsage(JvmMemoryUsage peakUsage) {
		this.peakUsage = peakUsage;
	}
	public MemoryType getType() {
		return type;
	}
	public void setType(MemoryType type) {
		this.type = type;
	}
	public JvmMemoryUsage getUsage() {
		return usage;
	}
	public void setUsage(JvmMemoryUsage usage) {
		this.usage = usage;
	}
	public long getUsageThreshold() {
		return usageThreshold;
	}
	public void setUsageThreshold(long usageThreshold) {
		this.usageThreshold = usageThreshold;
	}
	public long getUsageThresholdCount() {
		return usageThresholdCount;
	}
	public void setUsageThresholdCount(long usageThresholdCount) {
		this.usageThresholdCount = usageThresholdCount;
	}
	public boolean isCollectionUsageThresholdExceeded() {
		return collectionUsageThresholdExceeded;
	}
	public void setCollectionUsageThresholdExceeded(
			boolean collectionUsageThresholdExceeded) {
		this.collectionUsageThresholdExceeded = collectionUsageThresholdExceeded;
	}
	public boolean isCollectionUsageThresholdSupported() {
		return collectionUsageThresholdSupported;
	}
	public void setCollectionUsageThresholdSupported(
			boolean collectionUsageThresholdSupported) {
		this.collectionUsageThresholdSupported = collectionUsageThresholdSupported;
	}
	public boolean isUsageThresholdExceeded() {
		return usageThresholdExceeded;
	}
	public void setUsageThresholdExceeded(boolean usageThresholdExceeded) {
		this.usageThresholdExceeded = usageThresholdExceeded;
	}
	public boolean isUsageThresholdSupported() {
		return usageThresholdSupported;
	}
	public void setUsageThresholdSupported(boolean usageThresholdSupported) {
		this.usageThresholdSupported = usageThresholdSupported;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	
}

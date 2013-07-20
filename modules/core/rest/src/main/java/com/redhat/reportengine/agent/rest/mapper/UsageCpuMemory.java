package com.redhat.reportengine.agent.rest.mapper;

import java.io.Serializable;

public class UsageCpuMemory  extends AgentBaseMap implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UsageCpus usageCpus;
	private UsageMemory usageMemory;
	private long time;
	
	public UsageCpus getUsageCpus() {
		return usageCpus;
	}
	public void setUsageCpus(UsageCpus usageCpus) {
		this.usageCpus = usageCpus;
	}
	public UsageMemory getUsageMemory() {
		return usageMemory;
	}
	public void setUsageMemory(UsageMemory usageMemory) {
		this.usageMemory = usageMemory;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
}

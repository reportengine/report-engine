/**
 * 
 */
package com.redhat.reportengine.agent.rest.mapper.jvm;

import java.io.Serializable;
import java.lang.management.MemoryManagerMXBean;

import com.redhat.reportengine.agent.rest.mapper.AgentBaseMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jan 24, 2014
 */
public class JvmMemoryManagerMXBean extends AgentBaseMap implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4488739535989347038L;
	
	private String[] memoryPoolNames;
	private String name;
	private boolean valid;
	
	public JvmMemoryManagerMXBean(){
		
	}
	public JvmMemoryManagerMXBean(MemoryManagerMXBean memoryManagerMXBean){
		this.memoryPoolNames = memoryManagerMXBean.getMemoryPoolNames();
		this.name = memoryManagerMXBean.getName();
		this.valid = memoryManagerMXBean.isValid();
	}

	public String[] getMemoryPoolNames() {
		return memoryPoolNames;
	}

	public void setMemoryPoolNames(String[] memoryPoolNames) {
		this.memoryPoolNames = memoryPoolNames;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
		
}

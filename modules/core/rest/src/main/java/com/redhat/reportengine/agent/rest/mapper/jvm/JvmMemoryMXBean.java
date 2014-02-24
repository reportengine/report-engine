/**
 * 
 */
package com.redhat.reportengine.agent.rest.mapper.jvm;

import java.io.Serializable;
import java.lang.management.MemoryMXBean;
import com.redhat.reportengine.agent.rest.mapper.AgentBaseMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jan 24, 2014
 */
public class JvmMemoryMXBean extends AgentBaseMap implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4793969864750072059L;
	
	private JvmMemoryUsage heapMemoryUsage;
	private JvmMemoryUsage nonHeapMemoryUsage;
	private int objectPendingFinalizationCount;
	private boolean verbose;
	
	public JvmMemoryUsage getHeapMemoryUsage() {
		return heapMemoryUsage;
	}
	public void setHeapMemoryUsage(JvmMemoryUsage heapMemoryUsage) {
		this.heapMemoryUsage = heapMemoryUsage;
	}
	public JvmMemoryUsage getNonHeapMemoryUsage() {
		return nonHeapMemoryUsage;
	}
	public void setNonHeapMemoryUsage(JvmMemoryUsage nonHeapMemoryUsage) {
		this.nonHeapMemoryUsage = nonHeapMemoryUsage;
	}
	public int getObjectPendingFinalizationCount() {
		return objectPendingFinalizationCount;
	}
	public void setObjectPendingFinalizationCount(int objectPendingFinalizationCount) {
		this.objectPendingFinalizationCount = objectPendingFinalizationCount;
	}
	public boolean isVerbose() {
		return verbose;
	}
	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}
	
	public JvmMemoryMXBean(){
		
	}
	public JvmMemoryMXBean(MemoryMXBean memoryMXBean){
		this.heapMemoryUsage = new JvmMemoryUsage(memoryMXBean.getHeapMemoryUsage());
		this.nonHeapMemoryUsage = new JvmMemoryUsage(memoryMXBean.getNonHeapMemoryUsage());
		this.objectPendingFinalizationCount = memoryMXBean.getObjectPendingFinalizationCount();
		this.verbose = memoryMXBean.isVerbose();
	}
}

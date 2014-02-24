/**
 * 
 */
package com.redhat.reportengine.agent.rest.mapper.jvm;

import java.io.Serializable;
import java.lang.management.ClassLoadingMXBean;

import com.redhat.reportengine.agent.rest.mapper.AgentBaseMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jan 24, 2014
 */
public class JvmClassLoadingMXBean extends AgentBaseMap implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8977379427136535900L;
	
	private int loadedClassCount;
	private long totalLoadedClassCount;
	private long unloadedClassCount;
	private boolean verbose;
	
	public int getLoadedClassCount() {
		return loadedClassCount;
	}
	public void setLoadedClassCount(int loadedClassCount) {
		this.loadedClassCount = loadedClassCount;
	}
	public long getTotalLoadedClassCount() {
		return totalLoadedClassCount;
	}
	public void setTotalLoadedClassCount(long totalLoadedClassCount) {
		this.totalLoadedClassCount = totalLoadedClassCount;
	}
	public long getUnloadedClassCount() {
		return unloadedClassCount;
	}
	public void setUnloadedClassCount(long unloadedClassCount) {
		this.unloadedClassCount = unloadedClassCount;
	}
	public boolean isVerbose() {
		return verbose;
	}
	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}
	
	public JvmClassLoadingMXBean(){
	
	}
	
	public JvmClassLoadingMXBean(ClassLoadingMXBean classLoadingMXBean){
		this.loadedClassCount = classLoadingMXBean.getLoadedClassCount();
		this.totalLoadedClassCount = classLoadingMXBean.getTotalLoadedClassCount();
		this.unloadedClassCount = classLoadingMXBean.getUnloadedClassCount();
		this.verbose = classLoadingMXBean.isVerbose();
	}
}

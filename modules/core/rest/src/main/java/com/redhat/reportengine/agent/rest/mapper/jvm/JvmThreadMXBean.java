/**
 * 
 */
package com.redhat.reportengine.agent.rest.mapper.jvm;

import java.lang.management.ThreadMXBean;

import com.redhat.reportengine.agent.rest.mapper.AgentBaseMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jan 25, 2014
 */
public class JvmThreadMXBean extends AgentBaseMap{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4768398430754666196L;
	
	private long[] allThreadsIds;
	private long currentThreadCpuTime;
	private long currentThreadUserTime;
	private int daemonThreadCount;
	private int peakThreadCount;
	private int threadCount;
	private long totalStartedThreadCount;
	private boolean currentThreadCpuTimeSupported;
	private boolean objectMonitorUsageSupported;
	private boolean synchronizerUsageSupported;
	private boolean threadContentionMonitoringEnabled;
	private boolean threadContentionMonitoringSupported;
	private boolean threadCpuTimeEnabled;
	private boolean threadCpuTimeSupported;
	
	public JvmThreadMXBean(){
		
	}
	public JvmThreadMXBean(ThreadMXBean threadMXBean){
		this.allThreadsIds = threadMXBean.getAllThreadIds();
		this.currentThreadCpuTime = threadMXBean.getCurrentThreadCpuTime();
		this.currentThreadUserTime = threadMXBean.getCurrentThreadUserTime();
		this.daemonThreadCount = threadMXBean.getDaemonThreadCount();
		this.peakThreadCount = threadMXBean.getPeakThreadCount();
		this.threadCount = threadMXBean.getThreadCount();
		this.totalStartedThreadCount = threadMXBean.getTotalStartedThreadCount();
		this.currentThreadCpuTimeSupported = threadMXBean.isCurrentThreadCpuTimeSupported();
		this.objectMonitorUsageSupported = threadMXBean.isObjectMonitorUsageSupported();
		this.synchronizerUsageSupported = threadMXBean.isSynchronizerUsageSupported();
		this.threadContentionMonitoringEnabled = threadMXBean.isThreadContentionMonitoringEnabled();
		this.threadContentionMonitoringSupported = threadMXBean.isThreadContentionMonitoringSupported();
		this.threadCpuTimeEnabled = threadMXBean.isThreadCpuTimeEnabled();
		this.threadCpuTimeSupported = threadMXBean.isThreadCpuTimeSupported();
	}
	
	public long[] getAllThreadsIds() {
		return allThreadsIds;
	}
	public void setAllThreadsIds(long[] allThreadsIds) {
		this.allThreadsIds = allThreadsIds;
	}
	public long getCurrentThreadCpuTime() {
		return currentThreadCpuTime;
	}
	public void setCurrentThreadCpuTime(long currentThreadCpuTime) {
		this.currentThreadCpuTime = currentThreadCpuTime;
	}
	public long getCurrentThreadUserTime() {
		return currentThreadUserTime;
	}
	public void setCurrentThreadUserTime(long currentThreadUserTime) {
		this.currentThreadUserTime = currentThreadUserTime;
	}
	public int getDaemonThreadCount() {
		return daemonThreadCount;
	}
	public void setDaemonThreadCount(int daemonThreadCount) {
		this.daemonThreadCount = daemonThreadCount;
	}
	public int getPeakThreadCount() {
		return peakThreadCount;
	}
	public void setPeakThreadCount(int peakThreadCount) {
		this.peakThreadCount = peakThreadCount;
	}
	public int getThreadCount() {
		return threadCount;
	}
	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}
	public long getTotalStartedThreadCount() {
		return totalStartedThreadCount;
	}
	public void setTotalStartedThreadCount(long totalStartedThreadCount) {
		this.totalStartedThreadCount = totalStartedThreadCount;
	}
	public boolean isCurrentThreadCpuTimeSupported() {
		return currentThreadCpuTimeSupported;
	}
	public void setCurrentThreadCpuTimeSupported(
			boolean currentThreadCpuTimeSupported) {
		this.currentThreadCpuTimeSupported = currentThreadCpuTimeSupported;
	}
	public boolean isObjectMonitorUsageSupported() {
		return objectMonitorUsageSupported;
	}
	public void setObjectMonitorUsageSupported(boolean objectMonitorUsageSupported) {
		this.objectMonitorUsageSupported = objectMonitorUsageSupported;
	}
	public boolean isSynchronizerUsageSupported() {
		return synchronizerUsageSupported;
	}
	public void setSynchronizerUsageSupported(boolean synchronizerUsageSupported) {
		this.synchronizerUsageSupported = synchronizerUsageSupported;
	}
	public boolean isThreadContentionMonitoringEnabled() {
		return threadContentionMonitoringEnabled;
	}
	public void setThreadContentionMonitoringEnabled(
			boolean threadContentionMonitoringEnabled) {
		this.threadContentionMonitoringEnabled = threadContentionMonitoringEnabled;
	}
	public boolean isThreadContentionMonitoringSupported() {
		return threadContentionMonitoringSupported;
	}
	public void setThreadContentionMonitoringSupported(
			boolean threadContentionMonitoringSupported) {
		this.threadContentionMonitoringSupported = threadContentionMonitoringSupported;
	}
	public boolean isThreadCpuTimeEnabled() {
		return threadCpuTimeEnabled;
	}
	public void setThreadCpuTimeEnabled(boolean threadCpuTimeEnabled) {
		this.threadCpuTimeEnabled = threadCpuTimeEnabled;
	}
	public boolean isThreadCpuTimeSupported() {
		return threadCpuTimeSupported;
	}
	public void setThreadCpuTimeSupported(boolean threadCpuTimeSupported) {
		this.threadCpuTimeSupported = threadCpuTimeSupported;
	}

}

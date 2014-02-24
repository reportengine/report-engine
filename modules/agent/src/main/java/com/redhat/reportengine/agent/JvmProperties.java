package com.redhat.reportengine.agent;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jan 23, 2014
 */
public class JvmProperties {
	private static String monitorJVMs = null;	// Default
	private static boolean monitorJVMEnabled = false; 	// Default false
	private static long scanPeriod = 1000*30; 	// Default 30 Second once
	
	
	public static String getMonitorJVMs() {
		return monitorJVMs;
	}
	public static void setMonitorJVMs(String monitorJVMs) {
		JvmProperties.monitorJVMs = monitorJVMs;
	}
	public static long getScanPeriod() {
		return scanPeriod;
	}
	public static void setScanPeriod(long scanPeriod) {
		JvmProperties.scanPeriod = scanPeriod;
	}
	public static boolean isMonitorJVMEnabled() {
		return monitorJVMEnabled;
	}
	public static void setMonitorJVMEnabled(boolean monitorJVMEnabled) {
		JvmProperties.monitorJVMEnabled = monitorJVMEnabled;
	}
}

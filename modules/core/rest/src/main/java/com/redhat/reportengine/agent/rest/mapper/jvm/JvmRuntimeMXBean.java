/**
 * 
 */
package com.redhat.reportengine.agent.rest.mapper.jvm;

import java.lang.management.RuntimeMXBean;
import java.util.List;
import java.util.Map;

import com.redhat.reportengine.agent.rest.mapper.AgentBaseMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jan 26, 2014
 */
public class JvmRuntimeMXBean extends AgentBaseMap{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 32869491193716395L;
	
	private String bootClassPath;
	private String classPath;
	private List<String> inputArguments;
	private String libraryPath;
	private String managementSpecVersion;
	private String name;
	private String specName;
	private String specVendor;
	private String specVersion;
	private long startTime;
	private Map<String, String> systemProperties;
	private long upTime;
	private String vmName;
	private String vmVendor;
	private String vmVersion;
	private boolean bootClassPathSupported;
	
	public JvmRuntimeMXBean(){
		
	}
	public JvmRuntimeMXBean(RuntimeMXBean runtimeMXBean){
		this.bootClassPath = runtimeMXBean.getBootClassPath();
		this.classPath = runtimeMXBean.getClassPath();
		this.inputArguments = runtimeMXBean.getInputArguments();
		this.libraryPath = runtimeMXBean.getLibraryPath();
		this.managementSpecVersion = runtimeMXBean.getManagementSpecVersion();
		this.name = runtimeMXBean.getName();
		this.specName = runtimeMXBean.getSpecName();
		this.specVendor = runtimeMXBean.getSpecVendor();
		this.specVersion = runtimeMXBean.getSpecVersion();
		this.startTime = runtimeMXBean.getStartTime();
		this.systemProperties = runtimeMXBean.getSystemProperties();
		this.upTime = runtimeMXBean.getUptime();
		this.vmName = runtimeMXBean.getVmName();
		this.vmVendor = runtimeMXBean.getVmVendor();
		this.vmVersion = runtimeMXBean.getVmVersion();
		this.bootClassPathSupported = runtimeMXBean.isBootClassPathSupported();
	}
	public String getBootClassPath() {
		return bootClassPath;
	}
	public void setBootClassPath(String bootClassPath) {
		this.bootClassPath = bootClassPath;
	}
	public String getClassPath() {
		return classPath;
	}
	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}
	public List<String> getInputArguments() {
		return inputArguments;
	}
	public void setInputArguments(List<String> inputArguments) {
		this.inputArguments = inputArguments;
	}
	public String getLibraryPath() {
		return libraryPath;
	}
	public void setLibraryPath(String libraryPath) {
		this.libraryPath = libraryPath;
	}
	public String getManagementSpecVersion() {
		return managementSpecVersion;
	}
	public void setManagementSpecVersion(String managementSpecVersion) {
		this.managementSpecVersion = managementSpecVersion;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSpecName() {
		return specName;
	}
	public void setSpecName(String specName) {
		this.specName = specName;
	}
	public String getSpecVendor() {
		return specVendor;
	}
	public void setSpecVendor(String specVendor) {
		this.specVendor = specVendor;
	}
	public String getSpecVersion() {
		return specVersion;
	}
	public void setSpecVersion(String specVersion) {
		this.specVersion = specVersion;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public Map<String, String> getSystemProperties() {
		return systemProperties;
	}
	public void setSystemProperties(Map<String, String> systemProperties) {
		this.systemProperties = systemProperties;
	}
	public long getUpTime() {
		return upTime;
	}
	public void setUpTime(long upTime) {
		this.upTime = upTime;
	}
	public String getVmName() {
		return vmName;
	}
	public void setVmName(String vmName) {
		this.vmName = vmName;
	}
	public String getVmVendor() {
		return vmVendor;
	}
	public void setVmVendor(String vmVendor) {
		this.vmVendor = vmVendor;
	}
	public String getVmVersion() {
		return vmVersion;
	}
	public void setVmVersion(String vmVersion) {
		this.vmVersion = vmVersion;
	}
	public boolean isBootClassPathSupported() {
		return bootClassPathSupported;
	}
	public void setBootClassPathSupported(boolean bootClassPathSupported) {
		this.bootClassPathSupported = bootClassPathSupported;
	}
}

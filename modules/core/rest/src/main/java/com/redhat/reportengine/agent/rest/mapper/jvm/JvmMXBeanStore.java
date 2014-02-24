package com.redhat.reportengine.agent.rest.mapper.jvm;

import java.io.Serializable;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryManagerMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;

import com.redhat.reportengine.agent.rest.mapper.AgentBaseMap;
import com.sun.tools.attach.VirtualMachineDescriptor;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jan 23, 2014
 */
public class JvmMXBeanStore  extends AgentBaseMap implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5760919029514174429L;
	
	/*private OperatingSystemMXBean operatingSystemMXBean;
    private MemoryMXBean memoryMXBean;
    private ClassLoadingMXBean classLoadingMXBean;
    private ThreadMXBean threadMXBean;
    private CompilationMXBean compilationMXBean;
    private RuntimeMXBean runtimeMXBean;
    private ArrayList<GarbageCollectorMXBean> garbageCollectorMXBean = new ArrayList<GarbageCollectorMXBean>();
    private ArrayList<MemoryPoolMXBean> memoryPoolMXBean = new ArrayList<MemoryPoolMXBean>();
    private ArrayList<MemoryManagerMXBean> memoryManagerMXBean = new ArrayList<MemoryManagerMXBean>();
    private VirtualMachineDescriptor virtualMachineDescriptor;*/
	
	/*private Object operatingSystemMXBean;
    private Object memoryMXBean;
    private Object classLoadingMXBean;
    private Object threadMXBean;
    private Object compilationMXBean;
    private Object runtimeMXBean;
    private Object garbageCollectorMXBean;
    private Object memoryPoolMXBean;
    private Object memoryManagerMXBean;
    private Object virtualMachineDescriptor;*/
	
    private JvmMemoryMXBean memoryMXBean;
    private JvmClassLoadingMXBean classLoadingMXBean;
    private JvmVirtualMachineDescriptor virtualMachineDescriptor;
    private JvmOperatingSystemMXBean operatingSystemMXBean;
    private JvmThreadMXBean threadMXBean;
    private JvmCompilationMXBean compilationMXBean;
    private JvmRuntimeMXBean runtimeMXBean;
    private ArrayList<JvmGarbageCollectorMXBean> garbageCollectorMXBean;
    private ArrayList<JvmMemoryPoolMXBean> memoryPoolMXBean;
    private ArrayList<JvmMemoryManagerMXBean> memoryManagerMXBean = new ArrayList<JvmMemoryManagerMXBean>();    
    
	public JvmMemoryMXBean getMemoryMXBean() {
		return memoryMXBean;
	}
	public void setMemoryMXBean(JvmMemoryMXBean memoryMXBean) {
		this.memoryMXBean = memoryMXBean;
	}
	public JvmClassLoadingMXBean getClassLoadingMXBean() {
		return classLoadingMXBean;
	}
	public void setClassLoadingMXBean(JvmClassLoadingMXBean classLoadingMXBean) {
		this.classLoadingMXBean = classLoadingMXBean;
	}
	public JvmVirtualMachineDescriptor getVirtualMachineDescriptor() {
		return virtualMachineDescriptor;
	}
	public void setVirtualMachineDescriptor(
			JvmVirtualMachineDescriptor virtualMachineDescriptor) {
		this.virtualMachineDescriptor = virtualMachineDescriptor;
	}
	public JvmOperatingSystemMXBean getOperatingSystemMXBean() {
		return operatingSystemMXBean;
	}
	public void setOperatingSystemMXBean(
			JvmOperatingSystemMXBean operatingSystemMXBean) {
		this.operatingSystemMXBean = operatingSystemMXBean;
	}
	public JvmThreadMXBean getThreadMXBean() {
		return threadMXBean;
	}
	public void setThreadMXBean(JvmThreadMXBean threadMXBean) {
		this.threadMXBean = threadMXBean;
	}
	public JvmCompilationMXBean getCompilationMXBean() {
		return compilationMXBean;
	}
	public void setCompilationMXBean(JvmCompilationMXBean compilationMXBean) {
		this.compilationMXBean = compilationMXBean;
	}
	public JvmRuntimeMXBean getRuntimeMXBean() {
		return runtimeMXBean;
	}
	public void setRuntimeMXBean(JvmRuntimeMXBean runtimeMXBean) {
		this.runtimeMXBean = runtimeMXBean;
	}
	public ArrayList<JvmGarbageCollectorMXBean> getGarbageCollectorMXBean() {
		return garbageCollectorMXBean;
	}
	public void setGarbageCollectorMXBean(ArrayList<JvmGarbageCollectorMXBean> garbageCollectorMXBean) {
		this.garbageCollectorMXBean = garbageCollectorMXBean;
	}
	public void setGarbageCollectorMXBeanFromOrg(ArrayList<GarbageCollectorMXBean> garbageCollectorMXBeans) {
		ArrayList<JvmGarbageCollectorMXBean> garbageCollectorMXBeanNew = new ArrayList<JvmGarbageCollectorMXBean>();
		for(GarbageCollectorMXBean garbageCollectorMXBean : garbageCollectorMXBeans){
			garbageCollectorMXBeanNew.add(new JvmGarbageCollectorMXBean(garbageCollectorMXBean));
		}
		this.garbageCollectorMXBean = garbageCollectorMXBeanNew;
	}
	public ArrayList<JvmMemoryPoolMXBean> getMemoryPoolMXBean() {
		return memoryPoolMXBean;
	}
	public void setMemoryPoolMXBean(ArrayList<JvmMemoryPoolMXBean> memoryPoolMXBean) {
		this.memoryPoolMXBean = memoryPoolMXBean;
	}
	public void setMemoryPoolMXBeanFromOrg(ArrayList<MemoryPoolMXBean> memoryPoolMXBeans) {
		ArrayList<JvmMemoryPoolMXBean> memoryPoolMXBeanNew = new ArrayList<JvmMemoryPoolMXBean>();
		for(MemoryPoolMXBean memoryPoolMXBean : memoryPoolMXBeans){
			memoryPoolMXBeanNew.add(new JvmMemoryPoolMXBean(memoryPoolMXBean));
		}
		this.memoryPoolMXBean = memoryPoolMXBeanNew;
	}
	public ArrayList<JvmMemoryManagerMXBean> getMemoryManagerMXBean() {
		return memoryManagerMXBean;
	}
	public void setMemoryManagerMXBean(
			ArrayList<JvmMemoryManagerMXBean> memoryManagerMXBean) {
		this.memoryManagerMXBean = memoryManagerMXBean;
	}
	public void setMemoryManagerMXBeanFromOrg(ArrayList<MemoryManagerMXBean> memoryManagerMXBeans) {
		ArrayList<JvmMemoryManagerMXBean> memoryManagerMXBeanNew = new ArrayList<JvmMemoryManagerMXBean>();
		for(MemoryManagerMXBean memoryManagerMXBean : memoryManagerMXBeans){
			memoryManagerMXBeanNew.add(new JvmMemoryManagerMXBean(memoryManagerMXBean));
		}
		this.memoryManagerMXBean = memoryManagerMXBeanNew;
	}
}

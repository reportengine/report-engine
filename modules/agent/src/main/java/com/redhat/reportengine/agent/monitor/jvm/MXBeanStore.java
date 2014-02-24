package com.redhat.reportengine.agent.monitor.jvm;


import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.MemoryManagerMXBean;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.redhat.reportengine.agent.rest.mapper.jvm.JvmClassLoadingMXBean;
import com.redhat.reportengine.agent.rest.mapper.jvm.JvmCompilationMXBean;
import com.redhat.reportengine.agent.rest.mapper.jvm.JvmMXBeanStore;
import com.redhat.reportengine.agent.rest.mapper.jvm.JvmMemoryMXBean;
import com.redhat.reportengine.agent.rest.mapper.jvm.JvmOperatingSystemMXBean;
import com.redhat.reportengine.agent.rest.mapper.jvm.JvmRuntimeMXBean;
import com.redhat.reportengine.agent.rest.mapper.jvm.JvmThreadMXBean;
import com.redhat.reportengine.agent.rest.mapper.jvm.JvmVirtualMachineDescriptor;
import com.sun.tools.attach.VirtualMachineDescriptor;

public class MXBeanStore {
	private OperatingSystemMXBean osMXBean;
    private MemoryMXBean memoryMXBean;
    private ClassLoadingMXBean classLoadingMXBean;
    private ThreadMXBean threadMXBean;
    private CompilationMXBean compilationMXBean;
    private RuntimeMXBean runtimeMXBean;
    private ArrayList<GarbageCollectorMXBean> gcMXBean = new ArrayList<GarbageCollectorMXBean>();
    private ArrayList<MemoryPoolMXBean> memPoolMXBean = new ArrayList<MemoryPoolMXBean>();
    private ArrayList<MemoryManagerMXBean> memMamagerMXBean = new ArrayList<MemoryManagerMXBean>();
    private VirtualMachineDescriptor virtualMachineDescriptor;
    
    private static HashMap<String, MXBeanStore> mxBeanStoreObjectMap = new HashMap<String, MXBeanStore>();
    private static Lock mxBeanStoreLock = new ReentrantLock();
    
    public static Lock getMXBeanStoreLock() {
        return mxBeanStoreLock;
    }
    
    public OperatingSystemMXBean getOSMXBean() {
        return osMXBean;
    }
    
    public void setOSMXBean(OperatingSystemMXBean _osMXBean){
        osMXBean = _osMXBean;
    }

    public MemoryMXBean getMemoryMXBean() {
        return memoryMXBean;
    }
    
    public void setMemoryMXBean(MemoryMXBean _memoryMXBean){
        memoryMXBean = _memoryMXBean;
    }

    public ClassLoadingMXBean getClassLoadingMXBean() {
        return classLoadingMXBean;
    }
    
    public void setClassLoadingMXBean(ClassLoadingMXBean _classLoadingMXBean) {
        classLoadingMXBean = _classLoadingMXBean;
    }
    
    
    public ThreadMXBean getThreadMXBean() {
        return threadMXBean;
    }
    
    public void setThreadMXBean(ThreadMXBean _threadMXBean) {
        threadMXBean = _threadMXBean;
    }
    
    
    public CompilationMXBean getCompilationMXBean() {
        return compilationMXBean;
    }
    
    public void setCompilationMXBean(CompilationMXBean _compilationMXBean) {
        compilationMXBean = _compilationMXBean;
    }
    
    
    public ArrayList<GarbageCollectorMXBean> getGCMXBean() {
        return gcMXBean;
    }
    
    public void setGCMXBean(ArrayList<GarbageCollectorMXBean> _gcMXBean) {
        gcMXBean = _gcMXBean;
    }
    
    public ArrayList<MemoryPoolMXBean> getMemPoolMXBean() {
        return memPoolMXBean;
    }
    
    public void setMemPoolMXBean(ArrayList<MemoryPoolMXBean> _memPoolMXBean) {
        memPoolMXBean = _memPoolMXBean;
    }
    
    public static HashMap<String, MXBeanStore> getMXBeanStoreObjectMap(){
        return mxBeanStoreObjectMap;
    }

	public RuntimeMXBean getRuntimeMXBean() {
		return runtimeMXBean;
	}

	public void setRuntimeMXBean(RuntimeMXBean runtimeMXBean) {
		this.runtimeMXBean = runtimeMXBean;
	}

	public ArrayList<MemoryManagerMXBean> getMemMamagerMXBean() {
		return memMamagerMXBean;
	}

	public void setMemMamagerMXBean(ArrayList<MemoryManagerMXBean> memMamagerMXBean) {
		this.memMamagerMXBean = memMamagerMXBean;
	}
	
	public VirtualMachineDescriptor getVirtualMachineDescriptor() {
		return virtualMachineDescriptor;
	}

	public void setVirtualMachineDescriptor(
			VirtualMachineDescriptor virtualMachineDescriptor) {
		this.virtualMachineDescriptor = virtualMachineDescriptor;
	}
	
	public static JvmMXBeanStore loadJvmMXBeanStore(MXBeanStore mxBeanStore){
		if(mxBeanStore != null){
			JvmMXBeanStore jvmMXBeanStore = new JvmMXBeanStore();
			jvmMXBeanStore.setVirtualMachineDescriptor(new JvmVirtualMachineDescriptor(mxBeanStore.getVirtualMachineDescriptor()));
			jvmMXBeanStore.setClassLoadingMXBean(new JvmClassLoadingMXBean(mxBeanStore.getClassLoadingMXBean()));
			jvmMXBeanStore.setCompilationMXBean(new JvmCompilationMXBean(mxBeanStore.getCompilationMXBean()));
			jvmMXBeanStore.setGarbageCollectorMXBeanFromOrg(mxBeanStore.getGCMXBean());
			jvmMXBeanStore.setMemoryManagerMXBeanFromOrg(mxBeanStore.getMemMamagerMXBean());
			jvmMXBeanStore.setMemoryMXBean(new JvmMemoryMXBean(mxBeanStore.getMemoryMXBean()));
			jvmMXBeanStore.setMemoryPoolMXBeanFromOrg(mxBeanStore.getMemPoolMXBean());
			jvmMXBeanStore.setOperatingSystemMXBean(new JvmOperatingSystemMXBean(mxBeanStore.getOSMXBean()));
			jvmMXBeanStore.setRuntimeMXBean(new JvmRuntimeMXBean(mxBeanStore.getRuntimeMXBean()));
			jvmMXBeanStore.setThreadMXBean(new JvmThreadMXBean(mxBeanStore.getThreadMXBean()));
			return jvmMXBeanStore;
		}else{
			return null;
		}
		
	}

}
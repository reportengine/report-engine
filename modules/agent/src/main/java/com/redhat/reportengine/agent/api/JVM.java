package com.redhat.reportengine.agent.api;

import java.util.Date;

import org.apache.log4j.Logger;

import com.redhat.reportengine.agent.monitor.jvm.MXBeanStore;
import com.redhat.reportengine.agent.monitor.jvm.RunningJVMs;
import com.redhat.reportengine.agent.monitor.jvm.ScanVM;
import com.redhat.reportengine.agent.rest.mapper.jvm.JvmMXBeanStore;
import com.redhat.reportengine.agent.rest.mapper.jvm.JvmsRunningList;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jan 23, 2014
 */
public class JVM {
	private static Logger _logger = Logger.getLogger(JVM.class);
	public static JvmsRunningList getRunningJVMs(){
		JvmsRunningList list = new JvmsRunningList();
		list.setList(RunningJVMs.getList());
		return list;
	}
	
	public static JvmMXBeanStore getJvmMXBeanStoreByName(String jvmName){
		JvmMXBeanStore beanStore = MXBeanStore.loadJvmMXBeanStore(ScanVM.getMXBeanStore(jvmName, null));
		_logger.debug("JVM Descriptor: "+beanStore.getVirtualMachineDescriptor()+", Memoery Heap: "+beanStore.getMemoryMXBean().getHeapMemoryUsage());
		return beanStore;
	}
	
	public static JvmMXBeanStore getJvmMXBeanStoreByPid(String jvmPid){
		JvmMXBeanStore beanStore = MXBeanStore.loadJvmMXBeanStore(ScanVM.getMXBeanStore(null, jvmPid));
		_logger.debug("JVM Descriptor: "+beanStore.getVirtualMachineDescriptor()+", Memoery Heap: "+beanStore.getMemoryMXBean().getHeapMemoryUsage());
		return beanStore;
	}
	
	public static JvmMXBeanStore getJvmMemoryMXBeanByName(String jvmName){
		JvmMXBeanStore beanStore = MXBeanStore.loadJvmMXBeanStore(ScanVM.getMXBeanStore(jvmName, null));
		beanStore.setClassLoadingMXBean(null);
		beanStore.setCompilationMXBean(null);
		beanStore.setGarbageCollectorMXBean(null);
		beanStore.setMemoryManagerMXBean(null);
		beanStore.setMemoryPoolMXBean(null);
		beanStore.setOperatingSystemMXBean(null);
		beanStore.setRuntimeMXBean(null);
		beanStore.setThreadMXBean(null);
		beanStore.setTime(new Date().getTime());
		return beanStore;
	}
}
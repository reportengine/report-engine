package com.redhat.reportengine.agent.api;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.redhat.reportengine.agent.monitor.jvm.MXBeanStore;
import com.redhat.reportengine.agent.monitor.jvm.RunningJVMs;
import com.redhat.reportengine.agent.monitor.jvm.ScanVM;
import com.redhat.reportengine.agent.rest.mapper.jvm.JVMnotAvailableException;
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
	
	public static JvmMXBeanStore getJvmMXBeanStoreByName(String jvmName) throws JVMnotAvailableException{
		JvmMXBeanStore beanStore=null;
		try{
			beanStore = MXBeanStore.loadJvmMXBeanStore(ScanVM.getMXBeanStore(jvmName, null));
			_logger.debug("Selected JVM Descriptor: "+beanStore.getVirtualMachineDescriptor());
			
		}catch(UndeclaredThrowableException undeclaredThrowableException){
			_logger.info("Seems selected JVM[Name: "+jvmName+"] restarted... Reloading reference...");
			ScanVM.removeMXBeanStore(jvmName, null);
			beanStore = MXBeanStore.loadJvmMXBeanStore(ScanVM.getMXBeanStore(jvmName, null));
		}
		return beanStore;
		
	}
	
	public static JvmMXBeanStore getJvmMXBeanStoreByPid(String jvmPid) throws JVMnotAvailableException{
		JvmMXBeanStore beanStore=null;
		try{
			beanStore = MXBeanStore.loadJvmMXBeanStore(ScanVM.getMXBeanStore(null, jvmPid));
			_logger.debug("Selected JVM Descriptor: "+beanStore.getVirtualMachineDescriptor());
			
		}catch(UndeclaredThrowableException undeclaredThrowableException){
			_logger.info("Seems selected JVM[PID: "+jvmPid+"] restarted... Reloading reference...");
			ScanVM.removeMXBeanStore(null, jvmPid);
			beanStore = MXBeanStore.loadJvmMXBeanStore(ScanVM.getMXBeanStore(null, jvmPid));
		}
		return beanStore;
	}
	
	public static JvmMXBeanStore getJvmMemoryMXBeanByName(String jvmName) throws JVMnotAvailableException{		
		JvmMXBeanStore beanStore = getJvmMXBeanStoreByName(jvmName);
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
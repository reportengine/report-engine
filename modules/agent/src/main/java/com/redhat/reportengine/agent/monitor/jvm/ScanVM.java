package com.redhat.reportengine.agent.monitor.jvm;


import java.io.File;
import java.io.IOException;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryManagerMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;

import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.log4j.Logger;

import com.redhat.reportengine.agent.JvmProperties;
import com.redhat.reportengine.agent.rest.mapper.jvm.JVMnotAvailableException;
import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.util.ArrayList;


public class ScanVM extends TimerTask {
	private static String targetVMDescs[] = null;
	//private String localVMDesc = "com.redhat.reportengine.agent.monitor.jvm.Test";
	private static Logger _logger = Logger.getLogger(ScanVM.class);
	
	public static void removeMXBeanStore(String jvmName, String jvmPid){
		Lock mxBeanStoreLock = MXBeanStore.getMXBeanStoreLock();
		try{			
			mxBeanStoreLock.lock();
			if(jvmName != null){
				if(MXBeanStore.getMXBeanStoreObjectMap().get(jvmName) != null){
					MXBeanStore.getMXBeanStoreObjectMap().remove(jvmName);
					_logger.debug("Removed JVM[Name: "+jvmName+"] from MXBeanStore....");
				}
			}else if(jvmPid != null){
				for(String key : MXBeanStore.getMXBeanStoreObjectMap().keySet()){
					if(MXBeanStore.getMXBeanStoreObjectMap().get(key).getVirtualMachineDescriptor().id().equals(jvmPid)){
						MXBeanStore.getMXBeanStoreObjectMap().remove(key);
						_logger.debug("Removed JVM[Name: "+key+"] from MXBeanStore....");
					}
				}
			}else{
				_logger.warn("Should pass either JVM Name (or) JVM PID...");
			}		
		}finally{
			mxBeanStoreLock.unlock();
		}
		
	}
	
	public static MXBeanStore getMXBeanStore(String jvmName, String jvmPid) throws JVMnotAvailableException{
		if(jvmName != null){
			if(MXBeanStore.getMXBeanStoreObjectMap().get(jvmName) == null){
				addMXBeanStore(jvmName, jvmPid);
			}
		}else if(jvmPid != null){
			for(String key : MXBeanStore.getMXBeanStoreObjectMap().keySet()){
				if(MXBeanStore.getMXBeanStoreObjectMap().get(key).getVirtualMachineDescriptor().id().equals(jvmPid)){
					return MXBeanStore.getMXBeanStoreObjectMap().get(key);
				}
			}
			addMXBeanStore(jvmName, jvmPid);
		}else{
			_logger.warn("Should pass either JVM Name (or) JVM PID...");
			return null;
		}
		if(MXBeanStore.getMXBeanStoreObjectMap().get(jvmName) != null){
			return MXBeanStore.getMXBeanStoreObjectMap().get(jvmName);
		}else{
			throw new JVMnotAvailableException("Selected JVM[Name: "+jvmName+", id: "+jvmPid+"] not available (or) not in running state...");
		}
	}

	private static void addMXBeanStoreCore(HashMap<String, MXBeanStore> mxBeanStoreObjectMap, VirtualMachineDescriptor vmDesc, String vmName) throws AttachNotSupportedException, IOException, AgentLoadException, AgentInitializationException, MalformedObjectNameException, NullPointerException{
		MXBeanStore mxBeanStore = new MXBeanStore();
		VirtualMachine vm = VirtualMachine.attach(vmDesc);
		String localConnectorAddr = vm.getAgentProperties()
				.getProperty("com.sun.management.jmxremote.localConnectorAddress");
		_logger.debug("System Properties: "+vm.getSystemProperties());
		_logger.debug("Agent Properties: "+vm.getAgentProperties());
		if (localConnectorAddr == null) {
			String javaHome = vm.getSystemProperties().getProperty("java.home");
			String agentJar = javaHome + File.separator + "lib" + File.separator + "management-agent.jar";
			vm.loadAgent(agentJar);
			localConnectorAddr = vm.getAgentProperties()
					.getProperty("com.sun.management.jmxremote.localConnectorAddress");
		}
		vm.detach();

		JMXServiceURL serviceURL = new JMXServiceURL(localConnectorAddr);
		JMXConnector connector = JMXConnectorFactory.connect(serviceURL);
		MBeanServerConnection mbsc = connector.getMBeanServerConnection();

		//Virtual Machine Descriptor								
		mxBeanStore.setVirtualMachineDescriptor(vmDesc);


		// os bean
		ObjectName osMXObject = new ObjectName(ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME);
		OperatingSystemMXBean osMXBean = ManagementFactory.newPlatformMXBeanProxy(mbsc,
				osMXObject.toString(), OperatingSystemMXBean.class);
		mxBeanStore.setOSMXBean(osMXBean);


		// memory bean
		ObjectName memoryMXObject = new ObjectName(ManagementFactory.MEMORY_MXBEAN_NAME);
		MemoryMXBean memoryMXBean = ManagementFactory.newPlatformMXBeanProxy(mbsc,
				memoryMXObject.toString(), MemoryMXBean.class);
		mxBeanStore.setMemoryMXBean(memoryMXBean);

		// class bean
		ObjectName classLoadingMXObject = new ObjectName(ManagementFactory.CLASS_LOADING_MXBEAN_NAME);
		ClassLoadingMXBean classLoadingMXBean = ManagementFactory.newPlatformMXBeanProxy(mbsc,
				classLoadingMXObject.toString(), ClassLoadingMXBean.class);
		mxBeanStore.setClassLoadingMXBean(classLoadingMXBean);

		// thread bean
		ObjectName threadMXObject = new ObjectName(ManagementFactory.THREAD_MXBEAN_NAME);
		ThreadMXBean threadMXBean = ManagementFactory.newPlatformMXBeanProxy(mbsc,
				threadMXObject.toString(), ThreadMXBean.class);
		mxBeanStore.setThreadMXBean(threadMXBean);

		// compilation bean
		ObjectName compilationMXObject = new ObjectName(ManagementFactory.COMPILATION_MXBEAN_NAME);
		CompilationMXBean compilationMXBean = ManagementFactory.newPlatformMXBeanProxy(mbsc, 
				compilationMXObject.toString(), CompilationMXBean.class);
		mxBeanStore.setCompilationMXBean(compilationMXBean);

		// gc bean
		ObjectName gcObjectNames = new ObjectName(ManagementFactory.GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE + ",*");
		ArrayList<GarbageCollectorMXBean> gcMXBeanList = new ArrayList<GarbageCollectorMXBean>();
		for (ObjectName gcMXObject : mbsc.queryNames(gcObjectNames, null)) {
			GarbageCollectorMXBean gcMXBean = ManagementFactory.newPlatformMXBeanProxy(
					mbsc, gcMXObject.getCanonicalName(), GarbageCollectorMXBean.class); 
			gcMXBeanList.add(gcMXBean);
		}
		mxBeanStore.setGCMXBean(gcMXBeanList);


		// runtime bean
		ObjectName runtimeMXObject = new ObjectName(ManagementFactory.RUNTIME_MXBEAN_NAME);
		RuntimeMXBean runtimeMXBean = ManagementFactory.newPlatformMXBeanProxy(mbsc,
				runtimeMXObject.toString(), RuntimeMXBean.class);
		mxBeanStore.setRuntimeMXBean(runtimeMXBean);


		// memory pool bean
		ArrayList<MemoryPoolMXBean> memPoolMXBeanList = new ArrayList<MemoryPoolMXBean>();
		ObjectName memPoolObjectNames = new ObjectName(ManagementFactory.MEMORY_POOL_MXBEAN_DOMAIN_TYPE + ",*");
		for (ObjectName memPoolMXObject : mbsc.queryNames(memPoolObjectNames, null)) {
			MemoryPoolMXBean memPoolMXBean = ManagementFactory.newPlatformMXBeanProxy(
					mbsc, memPoolMXObject.getCanonicalName(), MemoryPoolMXBean.class); 

			memPoolMXBeanList.add(memPoolMXBean);
		}
		mxBeanStore.setMemPoolMXBean(memPoolMXBeanList);

		// memory Manaer bean
		ArrayList<MemoryManagerMXBean> memManagerMXBeanList = new ArrayList<MemoryManagerMXBean>();
		ObjectName memManagerObjectNames = new ObjectName(ManagementFactory.MEMORY_MANAGER_MXBEAN_DOMAIN_TYPE + ",*");
		for (ObjectName memManagerMXObject : mbsc.queryNames(memManagerObjectNames, null)) {
			MemoryManagerMXBean memManagerMXBean = ManagementFactory.newPlatformMXBeanProxy(
					mbsc, memManagerMXObject.getCanonicalName(), MemoryManagerMXBean.class); 

			memManagerMXBeanList.add(memManagerMXBean);
		}
		mxBeanStore.setMemPoolMXBean(memPoolMXBeanList);

		// add the mxBeanStore object to the map
		mxBeanStoreObjectMap.put(vmName, mxBeanStore);
		_logger.info("VM: "+vmDesc.displayName()+" found and loaded...");
		_logger.debug("mxbeanStore Size: "+mxBeanStoreObjectMap.size());

	}

	private static void addMXBeanStore(String jvmName, String jvmPid){
		try {            
			// lock MXBeanStoreObjectMap while scanning for new JVMs
			// as the main thread uses the same map to read 
			// existing JVMs in the map
			Lock mxBeanStoreLock = MXBeanStore.getMXBeanStoreLock();
			mxBeanStoreLock.lock();
			
			HashMap<String, MXBeanStore> mxBeanStoreObjectMap = MXBeanStore.getMXBeanStoreObjectMap();


			// get the list of running JVMs
			List<VirtualMachineDescriptor> vmDescriptorList = VirtualMachine.list();
			try {
				// for each target VM, check if the description 
				// string matches the description of the running VM
				// the user wants to monitor
				for (VirtualMachineDescriptor vmDesc : vmDescriptorList) {
					if(jvmName != null){
						String vmDisplayName = vmDesc.displayName();
						targetVMDescs = jvmName.split(",");
						for (String targetVMDesc : targetVMDescs) {

							// if (vmDisplayName.contains(targetVMDesc) && !vmDisplayName.contains(localVMDesc)) {
							if (vmDisplayName.contains(targetVMDesc)) {
								// for matcing JVM, create an VM id by appending proc_id with 
								// JVM description string the user entered
								//TODO: String vmId = vmDesc.id() + "_" + targetVMDesc;
								String vmName = targetVMDesc;

								// if the JVM is not in the MXBeanStoreObjectMap
								// add it to the map and attach to the JVM
								// and start MBean Server
								if (!mxBeanStoreObjectMap.containsKey(vmName)) {
									addMXBeanStoreCore(mxBeanStoreObjectMap, vmDesc, vmName);
								}
							}
						}
					}else if(jvmPid != null){
						if(vmDesc.id().equals(jvmPid)){

							// for matcing JVM, create an VM id by appending proc_id with 
							// JVM description string the user entered
							//TODO: String vmId = vmDesc.id() + "_" + targetVMDesc;
							String vmName = vmDesc.displayName();

							// if the JVM is not in the MXBeanStoreObjectMap
							// add it to the map and attach to the JVM
							// and start MBean Server
							if (!mxBeanStoreObjectMap.containsKey(vmName)) {
								addMXBeanStoreCore(mxBeanStoreObjectMap, vmDesc, vmName);
							}
						}
					}
					
				}
			} finally {
				mxBeanStoreLock.unlock();
			}
		} catch (Exception ex) {
			_logger.error("Error while scanning JVMs,", ex);
		}
	}

	@Override
	public void run() {
		if(JvmProperties.getMonitorJVMs() != null){
			addMXBeanStore(JvmProperties.getMonitorJVMs(), null);
		}
	}
}
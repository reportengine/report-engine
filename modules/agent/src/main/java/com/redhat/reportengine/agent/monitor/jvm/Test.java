package com.redhat.reportengine.agent.monitor.jvm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

import org.apache.log4j.Logger;

import com.redhat.reportengine.agent.AgentMain;
import com.redhat.reportengine.agent.AgentProperties;
import com.redhat.reportengine.agent.JvmProperties;




public class Test {

	private static final Logger _logger = Logger.getLogger(Test.class);

	public static void main(String...args) throws InterruptedException, FileNotFoundException, IOException{
		AgentMain.loadProperties("/opt/applications/agent-0.0.2-SNAPSHOT/re-agent/");
		System.out.println("Home: "+AgentProperties.getHomeLocation());
		AgentMain.initializeLog4j();
		RunningJVMs.getList();

		JvmProperties.setMonitorJVMs("com.redhat.reportengine.agent.AgentMain");
		JVMscan.startScan();
		
		// Keep scanning for JVMs every 5 secs until at least 1 is found
		_logger.info( "Searching for JVMs...");
		while (MXBeanStore.getMXBeanStoreObjectMap().isEmpty()) {
			Thread.sleep(5000);
		}
		
		String vm = "com.redhat.reportengine.agent.AgentMain";
		Set<String> keySet = MXBeanStore.getMXBeanStoreObjectMap().keySet();
		String actualName = null;
		for(String key : keySet){
			if(key.endsWith(vm)){
				actualName = key;
				break;
			}
		}
		
		if(actualName != null){
			MXBeanStore mxBeanStore = MXBeanStore.getMXBeanStoreObjectMap().get(actualName);
			
			_logger.info("*********************ClassLoadingMXBean********************"
					+"\nCurrentLoaddedClassCount: "+mxBeanStore.getClassLoadingMXBean().getLoadedClassCount()
					+"\nTotalLoaddedClassCount: "+mxBeanStore.getClassLoadingMXBean().getTotalLoadedClassCount()
					+"\nUnLoaddedClassCount: "+mxBeanStore.getClassLoadingMXBean().getUnloadedClassCount()
					+"\n*************************************************************"
					);
			
			_logger.info("*********************CompilationMXBean*********************"
					+"\nName: "+mxBeanStore.getCompilationMXBean().getName()
					+"\nTotal Compilation Time: "+mxBeanStore.getCompilationMXBean().getTotalCompilationTime()
					+"\n*************************************************************"
					);
			
			_logger.info("*********************MemoryMXBean*********************"
					+"\nObjectPendingFinalizationCount: "+mxBeanStore.getMemoryMXBean().getObjectPendingFinalizationCount()
					+"\nHP-Commited: "+mxBeanStore.getMemoryMXBean().getHeapMemoryUsage().getCommitted()
					+"\nHP-Init: "+mxBeanStore.getMemoryMXBean().getHeapMemoryUsage().getInit()
					+"\nHP-Max: "+mxBeanStore.getMemoryMXBean().getHeapMemoryUsage().getMax()
					+"\nHP-Used: "+mxBeanStore.getMemoryMXBean().getHeapMemoryUsage().getUsed()
					+"\nObjectPendingFinalizationCount: "+mxBeanStore.getMemoryMXBean().getNonHeapMemoryUsage()
					+"\nObjectPendingFinalizationCount: "+mxBeanStore.getMemoryMXBean().getObjectPendingFinalizationCount()
					+"\n*************************************************************"
					);
		}
		
		
		JVMscan.stopScan();
		
	}

}

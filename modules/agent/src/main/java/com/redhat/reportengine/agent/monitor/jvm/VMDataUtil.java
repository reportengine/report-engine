package com.redhat.reportengine.agent.monitor.jvm;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VMDataUtil {

    public static String getVMInfo() {

        Lock mxBeanStoreLock = MXBeanStore.getMXBeanStoreLock();
        mxBeanStoreLock.lock();
        mxBeanStoreObjectMap = MXBeanStore.getMXBeanStoreObjectMap();
        
        StringBuilder outputBuilder = new StringBuilder();
        try {
            String snapshotTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
            
            int totalVMs = mxBeanStoreObjectMap.size();
            
            // process MXBeans only if there is at least one JVM in the mxBeanStoreMap
            if (totalVMs > 0) {
                
                // iterate through each VM in the map
                Iterator<String> vmIdSet = mxBeanStoreObjectMap.keySet().iterator();
                while(vmIdSet.hasNext()){
                    String vmId = vmIdSet.next();
                    try {
                        String osMXBeanData = osMXBeanUtil(vmId);
                        String memMXBeanData = memMXBeanDataUtil(vmId);
                        String classLoadingMXBeanData = classLoadingMXBeanDataUtil(vmId);
                        String threadMXBeanData = threadMXBeanDataUtil(vmId);
                        String compilationMXBeanData = compilationMXBeanDataUtil(vmId);
                        String gcMXBeanData = gcMXBeanDataUtil(vmId);
                        String memPoolMXBeanData = memPoolMXBeanDataUtil(vmId);

                        outputBuilder.append(snapshotTime).append(",")
                                     .append(vmId).append(",")
                                     .append(osMXBeanData).append(",")
                                     .append(memMXBeanData).append(",")
                                     .append(classLoadingMXBeanData).append(",")
                                     .append(threadMXBeanData).append(",")
                                     .append(compilationMXBeanData).append(", ")
                                     .append(gcMXBeanData).append(", ")
                                     .append(memPoolMXBeanData).append(";");
                    } catch (Exception e){
                        // remove the vm entry from the map 
                        // as the vm is dead
                        vmIdSet.remove();
                        logger.log(Level.FINE, e.toString(), e);
                        logger.log(Level.FINE, "VM with proc_id {0} is dead", vmId);
                    }
                }
            }
        } finally {
            mxBeanStoreLock.unlock();
        }
        return outputBuilder.toString();
    }

       
    private static String osMXBeanUtil(String vmId) throws Exception{
        MXBeanStore mxBeanStore = mxBeanStoreObjectMap.get(vmId);
        
        OperatingSystemMXBean osMXBean = mxBeanStore.getOSMXBean();

        return osMXBean.getSystemLoadAverage() + "";
    }

    private static String memMXBeanDataUtil(String vmId) throws Exception {
        StringBuilder memMXBeanOutput = new StringBuilder();
        
        MXBeanStore mxBeanStore = mxBeanStoreObjectMap.get(vmId);
        MemoryMXBean memMXBean = mxBeanStore.getMemoryMXBean();
        
        MemoryUsage heapMemUsage = memMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemUsage = memMXBean.getNonHeapMemoryUsage();

        long usedHeap = heapMemUsage.getUsed() / (1024 * 1024); // in MB
        long committedHeap = heapMemUsage.getCommitted() / (1024 * 1024); // in MB
        long usedNonHeap = nonHeapMemUsage.getUsed() / (1024 * 1024); // in MB
        long committedNonHeap = nonHeapMemUsage.getCommitted() / (1024 * 1024); // in MB

        return memMXBeanOutput.append(usedHeap).append(",").
                        append(committedHeap).append(",").
                        append(usedNonHeap).append(",").
                        append(committedNonHeap).toString();
    }

    private static String classLoadingMXBeanDataUtil(String vmId) throws Exception {
        StringBuilder classLoadingMXBeanOutput = new StringBuilder();
        
        MXBeanStore mxBeanStore = mxBeanStoreObjectMap.get(vmId);
        ClassLoadingMXBean classLoadingMXBean = mxBeanStore.getClassLoadingMXBean();

        long currentLoadedClasses = classLoadingMXBean.getLoadedClassCount();
        long totalLoadedClasses = classLoadingMXBean.getTotalLoadedClassCount();
        long totalUnLoadedClasses = classLoadingMXBean.getUnloadedClassCount();

        return classLoadingMXBeanOutput.append(currentLoadedClasses).append(",").
                                append(totalLoadedClasses).append(",").
                                append(totalUnLoadedClasses).toString();
    }

    private static String threadMXBeanDataUtil(String vmId) throws Exception {
        StringBuilder threadMXBeanOutput = new StringBuilder();
    
        MXBeanStore mxBeanStore = mxBeanStoreObjectMap.get(vmId);
        ThreadMXBean threadMXBean = mxBeanStore.getThreadMXBean();
         
        // current live daemon threads
        int daemonThreadCount = threadMXBean.getDaemonThreadCount(); 
        int peakThreadCount = threadMXBean.getPeakThreadCount();
        // current thread count (live + daemon)
        int currentThreadCount = threadMXBean.getThreadCount(); 
         // total number of threads started
        long totalStartedThreadCount = threadMXBean.getTotalStartedThreadCount();

        return threadMXBeanOutput.append(daemonThreadCount).append(",").
                            append(peakThreadCount).append(",").
                            append(currentThreadCount).append(",").
                            append(totalStartedThreadCount).toString();
    }
    
    private static String compilationMXBeanDataUtil(String vmId) throws Exception {
        StringBuilder compilationMXBeanOutput = new StringBuilder();
        
        MXBeanStore mxBeanStore = mxBeanStoreObjectMap.get(vmId);
        CompilationMXBean compilationMXBean = mxBeanStore.getCompilationMXBean();
        
        long totalCompilationTime = compilationMXBean.getTotalCompilationTime();
        return compilationMXBeanOutput.append(totalCompilationTime).toString();
    }
    
    private static String gcMXBeanDataUtil(String vmId) throws Exception {
        StringBuilder gcMXBeanOutput = new StringBuilder();
        
        MXBeanStore mxBeanStore = mxBeanStoreObjectMap.get(vmId);
        ArrayList<GarbageCollectorMXBean> gcBeanList = mxBeanStore.getGCMXBean();
        
        boolean firstItr = true;
        for(GarbageCollectorMXBean gcBean : gcBeanList){
            
            String gcName = gcBean.getName();
            long gcCollectionCount = gcBean.getCollectionCount();
            long gcCollectionTime = gcBean.getCollectionTime();
            
            if(!firstItr){
                gcMXBeanOutput.append(",");
            }
            
            gcMXBeanOutput.append(gcName).append(",").
                            append(gcCollectionCount).append(",").
                            append(gcCollectionTime);
            
            firstItr = false;
        }
          
        return gcMXBeanOutput.toString();
    }
    
    private static String memPoolMXBeanDataUtil(String vmId) throws Exception {
        StringBuilder memPoolMXBeanOutput = new StringBuilder();
        
        MXBeanStore mxBeanStore = mxBeanStoreObjectMap.get(vmId);
        ArrayList<MemoryPoolMXBean> memPoolBeanList = mxBeanStore.getMemPoolMXBean();
      
        boolean firstItr = true;
        for(MemoryPoolMXBean memPoolBean : memPoolBeanList) {
            
            MemoryUsage memUsage = memPoolBean.getUsage();
            String memPoolName = memPoolBean.getName();
            long usedMemory = memUsage.getUsed() / (1024 * 1024);
            long commMemory = memUsage.getCommitted() / (1024 * 1024);
            if(!firstItr){
                memPoolMXBeanOutput.append(",");
            }
            memPoolMXBeanOutput.append(memPoolName).append(",").
                                append(usedMemory).append(",").
                                append(commMemory);
            firstItr = false;
        }
         
        return memPoolMXBeanOutput.toString();
    }
   
    private static final Logger logger = Logger.getLogger(VMDataUtil.class.getName());
    private static HashMap<String, MXBeanStore> mxBeanStoreObjectMap;
}
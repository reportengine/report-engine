package com.redhat.reportengine.agent.api;

import java.util.Date;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.SigarException;
import com.redhat.reportengine.agent.rest.mapper.CpuInformation;
import com.redhat.reportengine.agent.rest.mapper.UsageCpu;
import com.redhat.reportengine.agent.rest.mapper.UsageCpuMemory;
import com.redhat.reportengine.agent.rest.mapper.UsageCpus;


/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * May 14, 2013
 */
public class Cpu {
	
	private static final Logger _logger = Logger.getLogger(Cpu.class.getName());
	
	private static void setCpuInfo(CpuInformation cpuInfo, CpuInfo cpu){
			cpuInfo.setTime(new Date().getTime());
			cpuInfo.setCacheSize(cpu.getCacheSize());
			cpuInfo.setCoresPerSocket(cpu.getCoresPerSocket());
			cpuInfo.setMhz(cpu.getMhz());
			cpuInfo.setTotalCores(cpu.getTotalCores());
			cpuInfo.setTotalSockets(cpu.getTotalSockets());
			cpuInfo.setVendor(cpu.getVendor());
			cpuInfo.setModel(cpu.getModel());
	}

	public static CpuInformation getCpuInfo(){
		CpuInformation cpuInfo = new CpuInformation();
		try {
			setCpuInfo(cpuInfo, SigarUtils.getSigar().getCpuInfoList()[0]);
		} catch (SigarException ex) {
			_logger.error("Exception,", ex);
		}
		return cpuInfo;
	}
	
	public static LinkedList<CpuInformation> getCpuInfoAll(){
		LinkedList<CpuInformation> cpuInfos = new LinkedList<CpuInformation>();
		try{		
			CpuInfo[] cpus = SigarUtils.getSigar().getCpuInfoList();
			for(CpuInfo cpu : cpus){
				CpuInformation cpuInfo = new CpuInformation();
				setCpuInfo(cpuInfo, cpu);
				cpuInfos.addLast(cpuInfo);
			}
		}catch(SigarException se){
			_logger.error("Error,", se);
		}
		return cpuInfos;
	}
	
	public static UsageCpus getCpusUsage(){
		UsageCpus cpus = new UsageCpus();
		try{
			cpus.setCpus(SigarUtils.getSigar().getCpuPercList());
			cpus.setCpu(SigarUtils.getSigar().getCpuPerc());
			cpus.setTime(new Date().getTime());
		}catch(SigarException se){
			_logger.error("Error,", se);
		}
		return cpus;
	}

	public static UsageCpu getCpuUsage(){
		UsageCpu cpu = new UsageCpu();
		try{
			cpu.setCpu(SigarUtils.getSigar().getCpuPerc());
			cpu.setTime(new Date().getTime());
		}catch(SigarException se){
			_logger.error("Error,", se);
		}
		return cpu;
	}
	
	public static UsageCpuMemory getCpuMemory(){
		UsageCpuMemory usageCpuMemory = new UsageCpuMemory();
		try{
			usageCpuMemory.setUsageCpus(getCpusUsage());
			usageCpuMemory.setUsageMemory(Memory.getMemoryUsage());
			usageCpuMemory.setTime(new Date().getTime());
		}catch(SigarException se){
			_logger.error("Error,", se);
		}
		return usageCpuMemory;
	}
	
}

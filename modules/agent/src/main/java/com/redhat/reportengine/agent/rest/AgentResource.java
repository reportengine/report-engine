package com.redhat.reportengine.agent.rest;

import java.util.Date;
import java.util.LinkedList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hyperic.sigar.DirUsage;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SysInfo;

import com.redhat.reportengine.agent.api.AgentInfo;
import com.redhat.reportengine.agent.api.Cpu;
import com.redhat.reportengine.agent.api.Memory;
import com.redhat.reportengine.agent.api.Network;
import com.redhat.reportengine.agent.api.Pid;
import com.redhat.reportengine.agent.api.SigarUtils;
import com.redhat.reportengine.agent.rest.mapper.AgentDetails;
import com.redhat.reportengine.agent.rest.mapper.CpuInformation;
import com.redhat.reportengine.agent.rest.mapper.NetworkInfo;
import com.redhat.reportengine.agent.rest.mapper.OsDetail;
import com.redhat.reportengine.agent.rest.mapper.PidDetail;
import com.redhat.reportengine.agent.rest.mapper.UsageCpu;
import com.redhat.reportengine.agent.rest.mapper.UsageCpuMemory;
import com.redhat.reportengine.agent.rest.mapper.UsageCpus;
import com.redhat.reportengine.agent.rest.mapper.UsageMemory;
import com.redhat.reportengine.restapi.urimap.AgentRestUriMap;

@Path(AgentRestUriMap.AGENT_ROOT_URI)
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class AgentResource {


	@GET
	@Path(AgentRestUriMap.CONF_AGENT)
	public static AgentDetails getAgentSummary() {
		return AgentInfo.getAgentDetails();
	}
	
	@GET
	@Path(AgentRestUriMap.CONF_SYSTEM)
	public static SysInfo getSystemSummary() throws SigarException{
		SysInfo sysInfo = new SysInfo();
		sysInfo.gather(SigarUtils.getSigar());
		return sysInfo;
	}
	
	@GET
	@Path(AgentRestUriMap.CONF_OS)
	public static OsDetail getOperatingSystemSummary() throws SigarException{
		OsDetail osDetail = new OsDetail();
		osDetail.setOperatingSystem( OperatingSystem.getInstance());
		osDetail.setTime(new Date().getTime());
		return osDetail;
	}
	
	@GET
	@Path(AgentRestUriMap.CONF_CPU)
	public static CpuInformation getCpu() {
		return Cpu.getCpuInfo();
	}
	
	@GET
	@Path(AgentRestUriMap.CONF_PID+"/{pid}")
	public static PidDetail getPidDetail(@PathParam("pid") long pid) {
		return Pid.getPidDetail(pid);
	}
	
	@GET
	@Path(AgentRestUriMap.LIST_PIDS)
	public static LinkedList<PidDetail> getPids(){
		return Pid.getPidList();
	}
	
	@GET
	@Path(AgentRestUriMap.USAGE_CPU)
	public static UsageCpu getUsageCpu() throws SigarException{
		return Cpu.getCpuUsage();
	}
	
	@GET
	@Path(AgentRestUriMap.USAGE_CPUS)
	public static UsageCpus getUsageCpus() throws SigarException{
		return Cpu.getCpusUsage();
	}
	
	@GET
	@Path(AgentRestUriMap.USAGE_CPU_MEMORY)
	public static UsageCpuMemory getUsageCpuMemory() throws SigarException{
		return Cpu.getCpuMemory();
	}
	
	@GET
	@Path(AgentRestUriMap.USAGE_MEMORY)
	public static UsageMemory getUsageMemory() throws SigarException{
		return Memory.getMemoryUsage();
	}
	
	@GET
	@Path(AgentRestUriMap.USAGE_SWAP)
	public static UsageMemory getUsageSwap() throws SigarException{
		return Memory.getMemoryUsage();
	}
	
	
	@GET
	@Path(AgentRestUriMap.CONF_NW)
	public static NetworkInfo getDetailNw() throws SigarException{
		return Network.getNetworkInfo();
	}
	
	@GET
	@Path(AgentRestUriMap.LIST_NW_IFS)
	public static String[] getListNwIfs() throws SigarException{
		return SigarUtils.getSigar().getNetInterfaceList();
	}
	
	@GET
	@Path(AgentRestUriMap.CONF_NW_IF+"/{ifc}")
	public static NetworkInfo getDetailNwIf(@PathParam("ifc") String ifc) throws SigarException{
		return Network.getNetworkInfo(ifc);
	}
	
	@GET
	@Path(AgentRestUriMap.LIST_FS)
	public static FileSystem[] getListFs() throws SigarException{
		return SigarUtils.getSigar().getFileSystemList();
	}
	
	@GET
	@Path(AgentRestUriMap.USAGE_FS+"/{fs}")
	public static FileSystemUsage getUsageFs(@PathParam("fs") String fsName) throws SigarException{
		return SigarUtils.getSigar().getFileSystemUsage(fsName);
	}
	
	@GET
	@Path(AgentRestUriMap.USAGE_DIR+"/{dir}")
	public static DirUsage getUsageDir(@PathParam("dir") String dirName) throws SigarException{
		return SigarUtils.getSigar().getDirUsage(dirName);
	}	

}

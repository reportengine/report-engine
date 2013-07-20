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
import com.redhat.reportengine.agent.rest.mapper.URIReferenceAgent;
import com.redhat.reportengine.agent.rest.mapper.UsageCpu;
import com.redhat.reportengine.agent.rest.mapper.UsageCpuMemory;
import com.redhat.reportengine.agent.rest.mapper.UsageCpus;
import com.redhat.reportengine.agent.rest.mapper.UsageMemory;

@Path(URIReferenceAgent.AGENT_ROOT_URI)
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class AgentResource {


	@GET
	@Path(URIReferenceAgent.CONF_AGENT)
	public AgentDetails getAgentSummary() {
		return AgentInfo.getAgentDetails();
	}
	
	@GET
	@Path(URIReferenceAgent.CONF_SYSTEM)
	public SysInfo getSystemSummary() throws SigarException{
		SysInfo sysInfo = new SysInfo();
		sysInfo.gather(SigarUtils.getSigar());
		return sysInfo;
	}
	
	@GET
	@Path(URIReferenceAgent.CONF_OS)
	public OsDetail getOperatingSystemSummary() throws SigarException{
		OsDetail osDetail = new OsDetail();
		osDetail.setOperatingSystem( OperatingSystem.getInstance());
		osDetail.setTime(new Date().getTime());
		return osDetail;
	}
	
	@GET
	@Path(URIReferenceAgent.CONF_CPU)
	public CpuInformation getCpu() {
		return Cpu.getCpuInfo();
	}
	
	@GET
	@Path(URIReferenceAgent.CONF_PID+"/{pid}")
	public PidDetail getPidDetail(@PathParam("pid") long pid) {
		return Pid.getPidDetail(pid);
	}
	
	@GET
	@Path(URIReferenceAgent.LIST_PIDS)
	public LinkedList<PidDetail> getPids(){
		return Pid.getPidList();
	}
	
	@GET
	@Path(URIReferenceAgent.USAGE_CPU)
	public UsageCpu getUsageCpu() throws SigarException{
		return Cpu.getCpuUsage();
	}
	
	@GET
	@Path(URIReferenceAgent.USAGE_CPUS)
	public UsageCpus getUsageCpus() throws SigarException{
		return Cpu.getCpusUsage();
	}
	
	@GET
	@Path(URIReferenceAgent.USAGE_CPU_MEMORY)
	public UsageCpuMemory getUsageCpuMemory() throws SigarException{
		return Cpu.getCpuMemory();
	}
	
	@GET
	@Path(URIReferenceAgent.USAGE_MEMORY)
	public UsageMemory getUsageMemory() throws SigarException{
		return Memory.getMemoryUsage();
	}
	
	@GET
	@Path(URIReferenceAgent.USAGE_SWAP)
	public UsageMemory getUsageSwap() throws SigarException{
		return Memory.getMemoryUsage();
	}
	
	
	@GET
	@Path(URIReferenceAgent.CONF_NW)
	public NetworkInfo getDetailNw() throws SigarException{
		return Network.getNetworkInfo();
	}
	
	@GET
	@Path(URIReferenceAgent.LIST_NW_IFS)
	public String[] getListNwIfs() throws SigarException{
		return SigarUtils.getSigar().getNetInterfaceList();
	}
	
	@GET
	@Path(URIReferenceAgent.CONF_NW_IF+"/{ifc}")
	public NetworkInfo getDetailNwIf(@PathParam("ifc") String ifc) throws SigarException{
		return Network.getNetworkInfo(ifc);
	}
	
	@GET
	@Path(URIReferenceAgent.LIST_FS)
	public FileSystem[] getListFs() throws SigarException{
		return SigarUtils.getSigar().getFileSystemList();
	}
	
	@GET
	@Path(URIReferenceAgent.USAGE_FS+"/{fs}")
	public FileSystemUsage getUsageFs(@PathParam("fs") String fsName) throws SigarException{
		return SigarUtils.getSigar().getFileSystemUsage(fsName);
	}
	
	@GET
	@Path(URIReferenceAgent.USAGE_DIR+"/{dir}")
	public DirUsage getUsageDir(@PathParam("dir") String dirName) throws SigarException{
		return SigarUtils.getSigar().getDirUsage(dirName);
	}	

}

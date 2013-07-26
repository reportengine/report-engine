package com.redhat.reportengine.server.jobs.system;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hyperic.sigar.SysInfo;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.redhat.reportengine.agent.rest.mapper.CpuInformation;
import com.redhat.reportengine.agent.rest.mapper.NetworkInfo;
import com.redhat.reportengine.agent.rest.mapper.OsDetail;
import com.redhat.reportengine.agent.rest.mapper.UsageMemory;
import com.redhat.reportengine.restapi.urimap.AgentRestUriMap;
import com.redhat.reportengine.server.dbdata.ServerCpuDetailTable;
import com.redhat.reportengine.server.dbdata.ServerMemoryDetailTable;
import com.redhat.reportengine.server.dbdata.ServerNetworkDetailTable;
import com.redhat.reportengine.server.dbdata.ServerOsDetailTable;
import com.redhat.reportengine.server.dbdata.ServerStatusTable;
import com.redhat.reportengine.server.dbdata.ServerTable;
import com.redhat.reportengine.server.dbmap.Server;
import com.redhat.reportengine.server.dbmap.ServerCpuDetail;
import com.redhat.reportengine.server.dbmap.ServerMemoryDetail;
import com.redhat.reportengine.server.dbmap.ServerNetworkDetail;
import com.redhat.reportengine.server.dbmap.ServerOsDetail;
import com.redhat.reportengine.server.dbmap.ServerStatus;
import com.redhat.reportengine.server.reports.Keys;
import com.redhat.reportengine.server.restclient.agent.AgentsConnection;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 08, 2013
 */
public class ServerAgentReachableStatus implements Job{
	private static Logger _logger = Logger.getLogger(ServerAgentReachableStatus.class);
	
	public void updateServerCpuDetail(int serverId){
		ServerCpuDetail serverCpuDetail ;
		try {
			CpuInformation cpuInformation = (CpuInformation) AgentsConnection.getRestJSONclient(serverId).get(AgentRestUriMap.CONF_CPU, CpuInformation.class);
			serverCpuDetail = new ServerCpuDetailTable().getByServerId(serverId);
			if(serverCpuDetail == null){
				serverCpuDetail = new ServerCpuDetail();
			}
			serverCpuDetail.setCacheSize(cpuInformation.getCacheSize());
			serverCpuDetail.setCoresPerSocket(cpuInformation.getCoresPerSocket());
			serverCpuDetail.setMhz(cpuInformation.getMhz());
			serverCpuDetail.setModel(cpuInformation.getModel());
			serverCpuDetail.setRemoteTime(new Date(cpuInformation.getTime()));
			serverCpuDetail.setTotalCores(cpuInformation.getTotalCores());
			serverCpuDetail.setTotalSockets(cpuInformation.getTotalSockets());
			serverCpuDetail.setVendor(cpuInformation.getVendor());
			
			serverCpuDetail.setRemoteTime(new Date(cpuInformation.getTime()));
			serverCpuDetail.setServerId(serverId);			
			
			if(serverCpuDetail.getId() == null){
				new ServerCpuDetailTable().add(serverCpuDetail);
			}else{
				new ServerCpuDetailTable().update(serverCpuDetail);
			}
			
		} catch (Exception ex) {
			if(!ex.getMessage().contains("refused")){
				_logger.error("Error, ", ex);
			}
		}
	}
	
	public void updateServerOsDetail(int serverId){
		ServerOsDetail serverOsDetail ;
		try {
			OsDetail  osDetail = (OsDetail) AgentsConnection.getRestJSONclient(serverId).get(AgentRestUriMap.CONF_OS, OsDetail.class);
			
			serverOsDetail = new ServerOsDetailTable().getByServerId(serverId);
			if(serverOsDetail == null){
				serverOsDetail = new ServerOsDetail();
			}
			
			serverOsDetail.setArch(osDetail.getOperatingSystem().getArch());
			serverOsDetail.setCpuEndian(osDetail.getOperatingSystem().getCpuEndian());
			serverOsDetail.setDataModel(osDetail.getOperatingSystem().getDataModel());
			serverOsDetail.setDescription(osDetail.getOperatingSystem().getDescription());
			serverOsDetail.setMachine(osDetail.getOperatingSystem().getMachine());
			serverOsDetail.setName(osDetail.getOperatingSystem().getName());
			serverOsDetail.setPatchLevel(osDetail.getOperatingSystem().getPatchLevel());
			serverOsDetail.setVendor(osDetail.getOperatingSystem().getVendor());
			serverOsDetail.setVendorCodeName(osDetail.getOperatingSystem().getVendorCodeName());
			serverOsDetail.setVendorName(osDetail.getOperatingSystem().getVendorName());
			serverOsDetail.setVendorVersion(osDetail.getOperatingSystem().getVendorVersion());
			serverOsDetail.setKernelVersion(osDetail.getOperatingSystem().getVersion());
			
			serverOsDetail.setRemoteTime(new Date(osDetail.getTime()));
			
			serverOsDetail.setServerId(serverId);

			
			if(serverOsDetail.getId() == null){
				new ServerOsDetailTable().add(serverOsDetail);
			}else{
				new ServerOsDetailTable().update(serverOsDetail);
			}
			
		} catch (Exception ex) {
			if(!ex.getMessage().contains("refused")){
				_logger.error("Error, ", ex);
			}
		}
	}
	
	public void updateServerNetworkDetail(int serverId){
		ServerNetworkDetail serverNetworkDetail ;
		try {
			NetworkInfo networkInfo = (NetworkInfo)AgentsConnection.getRestJSONclient(serverId).get(AgentRestUriMap.CONF_NW, NetworkInfo.class);
			serverNetworkDetail = new ServerNetworkDetailTable().getByServerId(serverId);
			if(serverNetworkDetail == null){
				serverNetworkDetail = new ServerNetworkDetail();
			}
			serverNetworkDetail.setBroadcast(networkInfo.getNetInterfaceConfig().getBroadcast());
			serverNetworkDetail.setDefaultGateway(networkInfo.getNetInfo().getDefaultGateway());
			serverNetworkDetail.setDestination(networkInfo.getNetInterfaceConfig().getDestination());
			serverNetworkDetail.setDomainName(networkInfo.getNetInfo().getDomainName());
			serverNetworkDetail.setFlags(networkInfo.getNetInterfaceConfig().getFlags());
			serverNetworkDetail.setHostname(networkInfo.getNetInfo().getHostName());
			serverNetworkDetail.setInterfaceDescription(networkInfo.getNetInterfaceConfig().getDescription());
			serverNetworkDetail.setInterfaceName(networkInfo.getNetInterfaceConfig().getName());
			serverNetworkDetail.setInterfaceType(networkInfo.getNetInterfaceConfig().getType());
			serverNetworkDetail.setIpAddress(networkInfo.getNetInterfaceConfig().getAddress());
			serverNetworkDetail.setMac(networkInfo.getNetInterfaceConfig().getHwaddr());
			serverNetworkDetail.setMetric(networkInfo.getNetInterfaceConfig().getMetric());
			serverNetworkDetail.setMtu(networkInfo.getNetInterfaceConfig().getMtu());
			serverNetworkDetail.setPrimaryDns(networkInfo.getNetInfo().getPrimaryDns());
			serverNetworkDetail.setSecondaryDns(networkInfo.getNetInfo().getSecondaryDns());
			serverNetworkDetail.setSubnetmask(networkInfo.getNetInterfaceConfig().getNetmask());
			
			serverNetworkDetail.setRemoteTime(new Date(networkInfo.getTime()));
		
			serverNetworkDetail.setServerId(serverId);			
			
			if(serverNetworkDetail.getId() == null){
				new ServerNetworkDetailTable().add(serverNetworkDetail);
			}else{
				new ServerNetworkDetailTable().update(serverNetworkDetail);
			}
			
		} catch (Exception ex) {
			if(!ex.getMessage().contains("refused")){
				_logger.error("Error, ", ex);
			}
		}
	}

	public void updateServerMemoryDetail(int serverId){
		ServerMemoryDetail serverMemoryDetail ;
		try {
			UsageMemory usageMemory = (UsageMemory)AgentsConnection.getRestJSONclient(serverId).get(AgentRestUriMap.USAGE_MEMORY, UsageMemory.class);
			serverMemoryDetail = new ServerMemoryDetailTable().getByServerId(serverId);
			if(serverMemoryDetail == null){
				serverMemoryDetail = new ServerMemoryDetail();
			}
			serverMemoryDetail.setPhysical(usageMemory.getMemory().getTotal());
			serverMemoryDetail.setSwap(usageMemory.getSwap().getTotal());
			
			serverMemoryDetail.setRemoteTime(new Date(usageMemory.getTime()));	
			
			serverMemoryDetail.setServerId(serverId);			
			
			if(serverMemoryDetail.getId() == null){
				new ServerMemoryDetailTable().add(serverMemoryDetail);
			}else{
				new ServerMemoryDetailTable().update(serverMemoryDetail);
			}
			
		} catch (Exception ex) {
			if(!ex.getMessage().contains("refused")){
				_logger.error("Error, ", ex);
			}
		}
	}
	
	public void updateServerAgentReachableStatus(int serverId){
		ServerStatusTable serverStatusTable = new ServerStatusTable();
		Server server = null ;
		ServerStatus serverStatus = new ServerStatus() ;
		try {
			server = new ServerTable().getById(serverId);
			InetAddress inet = InetAddress.getByName(server.getHostIp());
			
			serverStatus.setServerId(server.getId());
			serverStatus.setReachable(false);
			serverStatus.setAgentStatus(false);
			
			SysInfo sysInfo = null;
			try {
				sysInfo = (SysInfo) AgentsConnection.getRestJSONclient(serverId).get(AgentRestUriMap.CONF_SYSTEM, SysInfo.class);
			} catch (Exception ex) {
				if(!ex.getMessage().contains("refused")){
					_logger.error("Agent Status Error, ", ex);
				}
			}
			
			if(sysInfo != null){
				if(server.getPlatform() == null){
					server.setPlatform(sysInfo.getName());
					new ServerTable().update(server);
				}
				if(server.getPlatform().equals(sysInfo.getName())){
					serverStatus.setAgentStatus(true);
				}
			}
			
			if(inet.isReachable(5000)){
				serverStatus.setReachable(true);
			}else{
				serverStatus.setReachable(false);
			}			
			serverStatusTable.add(serverStatus);
			
		} catch (SQLException ex) {
			_logger.error("Exception, ", ex);
		} catch (UnknownHostException ex) {
			try {
				serverStatus.setReachable(false);
				serverStatusTable.add(serverStatus);
			} catch (SQLException e) {
				_logger.error("Exception, ", ex);
			}
		} catch (IOException ex) {
			_logger.error("Exception, ", ex);
		}
		updateServerCpuDetail(serverId);
		updateServerNetworkDetail(serverId);
		updateServerOsDetail(serverId);
		updateServerMemoryDetail(serverId);
	}
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = (JobDataMap)context.getJobDetail().getJobDataMap();
		try {
			updateServerAgentReachableStatus(jobDataMap.getInt(Keys.DATA_REFERENCDE_ID));
		} catch (Exception ex) {
			_logger.error("Exception, ", ex);
		}
	}

}

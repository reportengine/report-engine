package com.redhat.reportengine.server.gui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.ProcCpu;
import org.hyperic.sigar.ProcCred;
import org.hyperic.sigar.ProcCredName;
import org.hyperic.sigar.ProcExe;
import org.hyperic.sigar.ProcFd;
import org.hyperic.sigar.ProcMem;
import org.hyperic.sigar.ProcState;
import org.hyperic.sigar.ProcTime;

import com.redhat.reportengine.agent.rest.mapper.AgentDetails;
import com.redhat.reportengine.agent.rest.mapper.CpuInformation;
import com.redhat.reportengine.agent.rest.mapper.DiskInfo;
import com.redhat.reportengine.agent.rest.mapper.NetworkInfo;
import com.redhat.reportengine.agent.rest.mapper.OsDetail;
import com.redhat.reportengine.agent.rest.mapper.PidDetail;
import com.redhat.reportengine.agent.rest.mapper.PidList;
import com.redhat.reportengine.agent.rest.mapper.UsageMemory;
import com.redhat.reportengine.restapi.urimap.AgentRestUriMap;
import com.redhat.reportengine.server.dbdata.ServerCpuDetailTable;
import com.redhat.reportengine.server.dbdata.ServerMemoryDetailTable;
import com.redhat.reportengine.server.dbdata.ServerNetworkDetailTable;
import com.redhat.reportengine.server.dbdata.ServerOsDetailTable;
import com.redhat.reportengine.server.dbdata.ServerTable;
import com.redhat.reportengine.server.dbmap.Server;
import com.redhat.reportengine.server.dbmap.ServerCpuDetail;
import com.redhat.reportengine.server.dbmap.ServerMemoryDetail;
import com.redhat.reportengine.server.dbmap.ServerNetworkDetail;
import com.redhat.reportengine.server.dbmap.ServerOsDetail;
import com.redhat.reportengine.server.reports.General;
import com.redhat.reportengine.server.reports.Keys;
import com.redhat.reportengine.server.restclient.agent.AgentsConnection;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 19, 2013
 */
public class AjaxServerInfo {
	final static Logger _logger = Logger.getLogger(AjaxServerInfo.class);

	private void setTableHead(StringBuilder stringBuilder){
		//stringBuilder.append("\n<font size=\"2\" face=\"Courier\">");
		stringBuilder.append("\n<table border=\"0\" cellpadding=\"3\" align=\"left\">");
	}

	private void setTableTail(StringBuilder stringBuilder){
		stringBuilder.append("</table>\n");
		//stringBuilder.append("</font>\n");
	}

	private void setKeyValue(StringBuilder stringBuilder, String key, Object value){
		stringBuilder.append("\n<TR>");
		stringBuilder.append("\n<td align=\"left\">");
		stringBuilder.append("<I>").append(key).append("</I>").append("</td>");
		stringBuilder.append("\n<TD>:</TD>");
		stringBuilder.append("\n<td>").append(value).append("</td>");
		stringBuilder.append("\n</TR>\n");
	}

	private void setKeyValueTabsHeading(StringBuilder tabHeader, String heading){
		setKeyValueTabsHeading(tabHeader, heading, null);
	}

	private void setKeyValueTabsHeading(StringBuilder tabHeader, String heading, String url){
		tabHeader.append("\n<li><a href=\"");
		if(url == null){
			tabHeader.append("#").append(heading.toLowerCase().replaceAll("\\s+", "-"));
		}else{
			tabHeader.append(url);
		}
		tabHeader.append("\">").append(heading).append("</a></li>");
	}


	private void setKeyValueTabs(StringBuilder tabHeader, StringBuilder tabContent, String heading, Object value){
		setKeyValueTabsHeading(tabHeader, heading);
		tabContent.append("\n<div id=\"").append(heading.toLowerCase().replaceAll("\\s+", "-")).append("\">");
		tabContent.append(value);
		tabContent.append("</div>");
	}

	private String getHtmlTableFromMap(Map map){
		StringBuilder builder = new StringBuilder();
		if(map != null){
			setTableHead(builder);
			Set<String> keys = map.keySet();
			for(String key: keys){
				setKeyValue(builder, key, map.get(key));
			}
			setTableTail(builder);
			return builder.toString();
		}else{
			return "-";
		}		
	}

	private void writeInResponse(HttpServletResponse response, StringBuilder stringBuilder) throws IOException{
		response.setContentType(MediaType.TEXT_HTML);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(stringBuilder.toString());
	}

	public void getLiveOSInfo(HttpServletRequest request, HttpServletResponse response) throws SQLException, Exception{
		int serverId = Integer.valueOf(request.getParameter(Keys.SERVER_ID));
		OsDetail osDetail = (OsDetail)AgentsConnection.getRestJSONclient(serverId).get(AgentRestUriMap.CONF_OS, OsDetail.class);
		StringBuilder osInfo = new StringBuilder();

		setTableHead(osInfo);
		setKeyValue(osInfo, "Name", osDetail.getOperatingSystem().getName());
		setKeyValue(osInfo, "Decription", osDetail.getOperatingSystem().getDescription());
		setKeyValue(osInfo, "Arch", osDetail.getOperatingSystem().getArch());
		setKeyValue(osInfo, "Machine", osDetail.getOperatingSystem().getMachine());
		setKeyValue(osInfo, "Kernel Version", osDetail.getOperatingSystem().getVersion());
		setKeyValue(osInfo, "Patch Level", osDetail.getOperatingSystem().getPatchLevel());
		setKeyValue(osInfo, "Vendor Name",  osDetail.getOperatingSystem().getVendorName());
		setKeyValue(osInfo, "Vendor", osDetail.getOperatingSystem().getVendor());
		setKeyValue(osInfo, "Vendor Version", osDetail.getOperatingSystem().getVendorVersion());
		setKeyValue(osInfo, "Vendor Code Name", osDetail.getOperatingSystem().getVendorCodeName());
		setKeyValue(osInfo, "Data Model", osDetail.getOperatingSystem().getDataModel());
		setKeyValue(osInfo, "CPU Endian", osDetail.getOperatingSystem().getCpuEndian());
		setTableTail(osInfo);

		writeInResponse(response, osInfo);		
	}

	public void getOSInfo(HttpServletRequest request, HttpServletResponse response) throws SQLException, Exception{
		int serverId = Integer.valueOf(request.getParameter(Keys.SERVER_ID));
		ServerOsDetail serverOsDetail = new ServerOsDetailTable().getByServerId(serverId);
		StringBuilder osInfo = new StringBuilder();

		setTableHead(osInfo);

		if(serverOsDetail != null){
			setKeyValue(osInfo, "Name", serverOsDetail.getName());
			setKeyValue(osInfo, "Decription", serverOsDetail.getDescription());
			setKeyValue(osInfo, "Arch", serverOsDetail.getArch());
			setKeyValue(osInfo, "Machine", serverOsDetail.getMachine());
			setKeyValue(osInfo, "Kernel Version", serverOsDetail.getKernelVersion());
			setKeyValue(osInfo, "Patch Level", serverOsDetail.getPatchLevel());
			setKeyValue(osInfo, "Vendor Name", serverOsDetail.getVendorName());
			setKeyValue(osInfo, "Vendor", serverOsDetail.getVendor());
			setKeyValue(osInfo, "Vendor Version", serverOsDetail.getVendorVersion());
			setKeyValue(osInfo, "Vendor Code Name", serverOsDetail.getVendorCodeName());
			setKeyValue(osInfo, "Data Model", serverOsDetail.getDataModel());
			setKeyValue(osInfo, "CPU Endian", serverOsDetail.getCpuEndian());
		}

		setTableTail(osInfo);

		writeInResponse(response, osInfo);

	}

	public void getLiveNetworkInfo(HttpServletRequest request, HttpServletResponse response) throws SQLException, Exception{
		int serverId = Integer.valueOf(request.getParameter(Keys.SERVER_ID));
		NetworkInfo networkInfo = (NetworkInfo)AgentsConnection.getRestJSONclient(serverId).get(AgentRestUriMap.CONF_NW, NetworkInfo.class);
		StringBuilder info = new StringBuilder();

		setTableHead(info);
		setKeyValue(info, "Hostname", networkInfo.getNetInfo().getHostName());
		setKeyValue(info, "Default Gatway", networkInfo.getNetInfo().getDefaultGateway());
		setKeyValue(info, "Primary DNS", networkInfo.getNetInfo().getPrimaryDns());
		setKeyValue(info, "Secondary DNS", networkInfo.getNetInfo().getSecondaryDns());
		setKeyValue(info, "Domain Name", networkInfo.getNetInfo().getDomainName());

		setKeyValue(info, "Interface Name", networkInfo.getNetInterfaceConfig().getName());
		setKeyValue(info, "Description", networkInfo.getNetInterfaceConfig().getDescription());
		setKeyValue(info, "Type", networkInfo.getNetInterfaceConfig().getType());

		setKeyValue(info, "IP Address", networkInfo.getNetInterfaceConfig().getAddress());
		setKeyValue(info, "Subnetmask", networkInfo.getNetInterfaceConfig().getNetmask());
		setKeyValue(info, "Broadcast", networkInfo.getNetInterfaceConfig().getBroadcast());
		setKeyValue(info, "Destination", networkInfo.getNetInterfaceConfig().getDestination());
		setKeyValue(info, "MAC", networkInfo.getNetInterfaceConfig().getHwaddr());
		setKeyValue(info, "Flags", networkInfo.getNetInterfaceConfig().getFlags());
		setKeyValue(info, "Metric", networkInfo.getNetInterfaceConfig().getMetric());
		setKeyValue(info, "MTU", networkInfo.getNetInterfaceConfig().getMtu());

		setTableTail(info);

		writeInResponse(response, info);

	}

	public void getNetworkInfo(HttpServletRequest request, HttpServletResponse response) throws SQLException, Exception{
		int serverId = Integer.valueOf(request.getParameter(Keys.SERVER_ID));
		ServerNetworkDetail serverNetworkDetail = new ServerNetworkDetailTable().getByServerId(serverId);

		StringBuilder info = new StringBuilder();

		setTableHead(info);

		if(serverNetworkDetail != null){
			setKeyValue(info, "Hostname", serverNetworkDetail.getHostname());
			setKeyValue(info, "Default Gatway", serverNetworkDetail.getDefaultGateway());
			setKeyValue(info, "Primary DNS", serverNetworkDetail.getPrimaryDns());
			setKeyValue(info, "Secondary DNS", serverNetworkDetail.getSecondaryDns());
			setKeyValue(info, "Domain Name", serverNetworkDetail.getDomainName());

			setKeyValue(info, "Interface Name", serverNetworkDetail.getInterfaceName());
			setKeyValue(info, "Description", serverNetworkDetail.getInterfaceDescription());
			setKeyValue(info, "Type", serverNetworkDetail.getInterfaceType());

			setKeyValue(info, "IP Address", serverNetworkDetail.getIpAddress());
			setKeyValue(info, "Subnetmask", serverNetworkDetail.getSubnetmask());
			setKeyValue(info, "Broadcast", serverNetworkDetail.getBroadcast());
			setKeyValue(info, "Destination", serverNetworkDetail.getDestination());
			setKeyValue(info, "MAC", serverNetworkDetail.getMac());
			setKeyValue(info, "Flags", serverNetworkDetail.getFlags());
			setKeyValue(info, "Metric", serverNetworkDetail.getMetric());
			setKeyValue(info, "MTU", serverNetworkDetail.getMtu());
		}

		setTableTail(info);

		writeInResponse(response, info);

	}

	public void getLiveCpuInfo(HttpServletRequest request, HttpServletResponse response) throws SQLException, Exception{
		int serverId = Integer.valueOf(request.getParameter(Keys.SERVER_ID));
		CpuInformation cpuInformation = (CpuInformation)AgentsConnection.getRestJSONclient(serverId).get(AgentRestUriMap.CONF_CPU, CpuInformation.class);
		StringBuilder info = new StringBuilder();

		setTableHead(info);
		setKeyValue(info, "Vendor", cpuInformation.getVendor());
		setKeyValue(info, "Model", cpuInformation.getModel());
		setKeyValue(info, "Speed", cpuInformation.getMhz()+" MHz");
		setKeyValue(info, "Total Sockets", cpuInformation.getTotalSockets());
		setKeyValue(info, "Cores Per Socket", cpuInformation.getCoresPerSocket());
		setKeyValue(info, "Total Cores", cpuInformation.getTotalCores());
		setKeyValue(info, "Cache", cpuInformation.getCacheSize());

		setTableTail(info);

		writeInResponse(response, info);		
	}

	public void getCpuInfo(HttpServletRequest request, HttpServletResponse response) throws SQLException, Exception{
		int serverId = Integer.valueOf(request.getParameter(Keys.SERVER_ID));
		ServerCpuDetail serverCpuDetail = new ServerCpuDetailTable().getByServerId(serverId);
		StringBuilder info = new StringBuilder();

		setTableHead(info);
		if(serverCpuDetail != null){
			setKeyValue(info, "Vendor", serverCpuDetail.getVendor());
			setKeyValue(info, "Model", serverCpuDetail.getModel());
			setKeyValue(info, "Speed", serverCpuDetail.getMhz()+" MHz");
			setKeyValue(info, "Total Sockets", serverCpuDetail.getTotalSockets());
			setKeyValue(info, "Cores Per Socket", serverCpuDetail.getCoresPerSocket());
			setKeyValue(info, "Total Cores", serverCpuDetail.getTotalCores());
			setKeyValue(info, "Cache", serverCpuDetail.getCacheSize());
		}

		setTableTail(info);

		writeInResponse(response, info);		
	}

	public void getLiveMemoryInfo(HttpServletRequest request, HttpServletResponse response) throws SQLException, Exception{
		int serverId = Integer.valueOf(request.getParameter(Keys.SERVER_ID));
		UsageMemory usageMemory = (UsageMemory)AgentsConnection.getRestJSONclient(serverId).get(AgentRestUriMap.USAGE_MEMORY, UsageMemory.class);
		StringBuilder info = new StringBuilder();

		setTableHead(info);
		setKeyValue(info, "Physical", usageMemory.getMemory().getTotal()/(1024*1024)+" MB");
		setKeyValue(info, "Swap", usageMemory.getSwap().getTotal()/(1024*1024)+" MB");

		setTableTail(info);

		writeInResponse(response, info);		
	}

	public void getMemoryInfo(HttpServletRequest request, HttpServletResponse response) throws SQLException, Exception{
		int serverId = Integer.valueOf(request.getParameter(Keys.SERVER_ID));
		ServerMemoryDetail serverMemoryDetail = new ServerMemoryDetailTable().getByServerId(serverId);
		StringBuilder info = new StringBuilder();

		setTableHead(info);
		if(serverMemoryDetail != null){
			setKeyValue(info, "Physical", serverMemoryDetail.getPhysical()/(1024*1024)+" MB");
			setKeyValue(info, "Swap", serverMemoryDetail.getSwap()/(1024*1024)+" MB");

		}

		setTableTail(info);

		writeInResponse(response, info);		
	}


	public String getLiveNetworkFullDetail(HttpServletRequest request, HttpServletResponse response){
		try{
			int serverId = Integer.valueOf(request.getParameter(Keys.SERVER_ID));
			String[] interfaces = (String[])AgentsConnection.getRestJSONclient(serverId).get(AgentRestUriMap.LIST_NW_IFS, String[].class);

			StringBuilder tabHeader = new StringBuilder("<ul>\n");			
			setKeyValueTabsHeading(tabHeader, "Network", "ajaxLiveServerNetworkData.jsp?"+Keys.SERVER_ID+"="+serverId+"&"+Keys.INTERFACE_NAME+"="+Keys.NETWORK_INFO);
			for(String iFace : interfaces){
				setKeyValueTabsHeading(tabHeader, iFace, "ajaxLiveServerNetworkData.jsp?"+Keys.SERVER_ID+"="+serverId+"&"+Keys.INTERFACE_NAME+"="+iFace);
			}			
			tabHeader.append("\n</ul>");
			return tabHeader.toString();

		}catch(Exception ex){
			_logger.error("exception,", ex);
		}
		return null;		
	}

	public void getLiveNetworkInterfaceFullDetail(HttpServletRequest request, HttpServletResponse response) throws SQLException, Exception{
		int serverId = Integer.valueOf(request.getParameter(Keys.SERVER_ID));
		String interfaceName = request.getParameter(Keys.INTERFACE_NAME);

		NetworkInfo ifInfo = null;
		StringBuilder info = new StringBuilder();
		setTableHead(info);

		if(interfaceName.equalsIgnoreCase(Keys.NETWORK_INFO)){
			ifInfo = (NetworkInfo)AgentsConnection.getRestJSONclient(serverId).get(AgentRestUriMap.CONF_NW, NetworkInfo.class);
			setKeyValue(info, "Hostname", ifInfo.getNetInfo().getHostName());
			setKeyValue(info, "Default Gateway", ifInfo.getNetInfo().getDefaultGateway());
			setKeyValue(info, "Domain Name", ifInfo.getNetInfo().getDomainName());
			setKeyValue(info, "Primary DNS", ifInfo.getNetInfo().getPrimaryDns());
			setKeyValue(info, "Secondary DNS", ifInfo.getNetInfo().getSecondaryDns());
		}else{
			ifInfo = (NetworkInfo)AgentsConnection.getRestJSONclient(serverId).get(AgentRestUriMap.CONF_NW_IF+"/"+interfaceName, NetworkInfo.class);
			setKeyValue(info, "Interface Name", ifInfo.getNetInterfaceConfig().getName());
			setKeyValue(info, "Description", ifInfo.getNetInterfaceConfig().getDescription());
			setKeyValue(info, "Type", ifInfo.getNetInterfaceConfig().getType());
			setKeyValue(info, "HWaddr(MAC)", ifInfo.getNetInterfaceConfig().getHwaddr());
			setKeyValue(info, "IP Address", ifInfo.getNetInterfaceConfig().getAddress());
			setKeyValue(info, "Netmask", ifInfo.getNetInterfaceConfig().getNetmask());
			setKeyValue(info, "Broadcast", ifInfo.getNetInterfaceConfig().getBroadcast());
			setKeyValue(info, "Destination", ifInfo.getNetInterfaceConfig().getDestination());
			setKeyValue(info, "MTU", ifInfo.getNetInterfaceConfig().getMtu());
			setKeyValue(info, "Flags", ifInfo.getNetInterfaceConfig().getFlags());
			setKeyValue(info, "Metric", ifInfo.getNetInterfaceConfig().getMetric());

			setKeyValue(info, "RxBytes", ifInfo.getNetInterfaceStat().getRxBytes());
			setKeyValue(info, "RxDropped", ifInfo.getNetInterfaceStat().getRxDropped());
			setKeyValue(info, "RxErrors", ifInfo.getNetInterfaceStat().getRxErrors());
			setKeyValue(info, "RxFrames", ifInfo.getNetInterfaceStat().getRxFrame());
			setKeyValue(info, "RxOverruns", ifInfo.getNetInterfaceStat().getRxOverruns());
			setKeyValue(info, "RxPackets", ifInfo.getNetInterfaceStat().getRxPackets());
			setKeyValue(info, "Speed", ifInfo.getNetInterfaceStat().getSpeed());
			setKeyValue(info, "TxBytes", ifInfo.getNetInterfaceStat().getTxBytes());
			setKeyValue(info, "TxCarrier", ifInfo.getNetInterfaceStat().getTxCarrier());
			setKeyValue(info, "TxCollisions", ifInfo.getNetInterfaceStat().getTxCollisions());
			setKeyValue(info, "TxDropped", ifInfo.getNetInterfaceStat().getTxDropped());
			setKeyValue(info, "TxErrors", ifInfo.getNetInterfaceStat().getTxErrors());
			setKeyValue(info, "TxOverruns", ifInfo.getNetInterfaceStat().getTxOverruns());
			setKeyValue(info, "TxPackets", ifInfo.getNetInterfaceStat().getTxPackets());
		}
		setTableTail(info);

		writeInResponse(response, info);
	}
	
	public DiskInfo getLiveFileSystemsList(HttpServletRequest request, HttpServletResponse response) throws SQLException, Exception{
		int serverId = Integer.valueOf(request.getParameter(Keys.SERVER_ID));
		return (DiskInfo)AgentsConnection.getRestJSONclient(serverId).get(AgentRestUriMap.LIST_FS, DiskInfo.class);
	}
	
	public DiskInfo getLiveFileSystemsUsage(HttpServletRequest request, HttpServletResponse response) throws SQLException, Exception{
		int serverId = Integer.valueOf(request.getParameter(Keys.SERVER_ID));		
		return (DiskInfo)AgentsConnection.getRestJSONclient(serverId).get(AgentRestUriMap.USAGE_FS, DiskInfo.class);
	}

	public PidList getPidsList(HttpServletRequest request, HttpServletResponse response) throws SQLException, Exception{
		int serverId = Integer.valueOf(request.getParameter(Keys.SERVER_ID));
		PidList pids = (PidList)AgentsConnection.getRestJSONclient(serverId).get(AgentRestUriMap.LIST_PIDS, PidList.class);
		return pids;		
	}

	public String getPidDetail(HttpServletRequest request, HttpServletResponse response){
		try{
			int serverId = Integer.valueOf(request.getParameter(Keys.SERVER_ID));
			String pidStr = request.getParameter(Keys.PID);
			PidDetail pid = (PidDetail)AgentsConnection.getRestJSONclient(serverId).get(AgentRestUriMap.CONF_PID+"/"+pidStr, PidDetail.class);
			AgentDetails agentDetails = (AgentDetails)AgentsConnection.getRestJSONclient(serverId).get(AgentRestUriMap.CONF_AGENT, AgentDetails.class);

			StringBuilder info = new StringBuilder();
			StringBuilder tabHeader = new StringBuilder("<ul>\n");

			StringBuilder agentDet = new StringBuilder();
			setTableHead(agentDet);
			setKeyValue(agentDet, "Name", agentDetails.getName());
			setKeyValue(agentDet, "Host Name", agentDetails.getHostName());
			setKeyValue(agentDet, "IP", agentDetails.getIp());
			setKeyValue(agentDet, "Current Time", General.getGuiDateTime(new Date(pid.getAgentDate())));
			setKeyValue(agentDet, "Viewing Pid", pid.getPid());
			setTableTail(agentDet);

			setKeyValueTabs(tabHeader, info, "Agent Details", agentDet.toString());
			setKeyValueTabs(tabHeader, info, "Process Cpu", getString(pid.getProcCpu()));
			setKeyValueTabs(tabHeader, info, "Process Cred", getString(pid.getProcCred()));
			setKeyValueTabs(tabHeader, info, "Process Cred Name", getString(pid.getProcCredName()));
			setKeyValueTabs(tabHeader, info, "Process Exe", getString(pid.getProcExe()));
			setKeyValueTabs(tabHeader, info, "Process Fd", getString(pid.getProcFd()));
			setKeyValueTabs(tabHeader, info, "Process Memory", getString(pid.getProcMem()));
			setKeyValueTabs(tabHeader, info, "Process State", getString(pid.getProcState()));
			setKeyValueTabs(tabHeader, info, "Process Time", getString(pid.getProcTime()));

			setKeyValueTabs(tabHeader, info, "Process Args", General.getString(pid.getProgArgs(), "<BR>"));
			setKeyValueTabs(tabHeader, info, "Process Env", getHtmlTableFromMap(pid.getProcEnv()));
			setKeyValueTabs(tabHeader, info, "Process Modules", General.getString(pid.getProcModules(), "<BR>"));


			tabHeader.append("\n</ul>");
			tabHeader.append(info);

			return tabHeader.toString();		
		}catch(Exception ex){
			_logger.error("exception,", ex);
		}
		return null;		
	}

	private String getString(ProcTime proc){
		if(proc != null){
			return getHtmlTableFromMap(proc.toMap());
		}else{
			return "-";
		}
	}
	private String getString(ProcState proc){
		if(proc != null){
			return getHtmlTableFromMap(proc.toMap());
		}else{
			return "-";
		}
	}
	private String getString(ProcMem proc){
		if(proc != null){
			return getHtmlTableFromMap(proc.toMap());
		}else{
			return "-";
		}
	}
	private String getString(ProcFd proc){
		if(proc != null){
			return getHtmlTableFromMap(proc.toMap());
		}else{
			return "-";
		}
	}
	private String getString(ProcExe proc){
		if(proc != null){
			return getHtmlTableFromMap(proc.toMap());
		}else{
			return "-";
		}
	}

	private String getString(ProcCredName proc){
		if(proc != null){
			return getHtmlTableFromMap(proc.toMap());
		}else{
			return "-";
		}
	}
	private String getString(ProcCpu proc){
		if(proc != null){
			return getHtmlTableFromMap(proc.toMap());
		}else{
			return "-";
		}
	}
	private String getString(ProcCred procCred){
		if(procCred != null){
			return getHtmlTableFromMap(procCred.toMap());
		}else{
			return "-";
		}
	}
}

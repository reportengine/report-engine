package com.redhat.reportengine.server.gui;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import com.redhat.reportengine.agent.rest.mapper.CpuInformation;
import com.redhat.reportengine.agent.rest.mapper.NetworkInfo;
import com.redhat.reportengine.agent.rest.mapper.OsDetail;
import com.redhat.reportengine.agent.rest.mapper.URIReferenceAgent;
import com.redhat.reportengine.agent.rest.mapper.UsageMemory;
import com.redhat.reportengine.server.dbdata.ServerCpuDetailTable;
import com.redhat.reportengine.server.dbdata.ServerMemoryDetailTable;
import com.redhat.reportengine.server.dbdata.ServerNetworkDetailTable;
import com.redhat.reportengine.server.dbdata.ServerOsDetailTable;
import com.redhat.reportengine.server.dbmap.ServerCpuDetail;
import com.redhat.reportengine.server.dbmap.ServerMemoryDetail;
import com.redhat.reportengine.server.dbmap.ServerNetworkDetail;
import com.redhat.reportengine.server.dbmap.ServerOsDetail;
import com.redhat.reportengine.server.reports.Keys;
import com.redhat.reportengine.server.restclient.agent.AgentsConnection;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 19, 2013
 */
public class AjaxServerInfo {
	
	private void setTableHead(StringBuilder stringBuilder){
		stringBuilder.append("\n<font size=\"2\" face=\"Courier\">");
		stringBuilder.append("\n<table border=\"0\" cellpadding=\"3\" align=\"left\">");
	}
	
	private void setTableTail(StringBuilder stringBuilder){
		stringBuilder.append("</table>\n</font>\n");
	}
	
	private void setKeyValue(StringBuilder stringBuilder, String key, Object value){
		stringBuilder.append("\n<TR>");
		stringBuilder.append("\n<td align=\"left\">");
		stringBuilder.append("<I>").append(key).append("</I>").append("</td>");
		stringBuilder.append("\n<TD>:</TD>");
		stringBuilder.append("\n<td>").append(value).append("</td>");
		stringBuilder.append("\n</TR>\n");
	}
	
	private void writeInResponse(HttpServletResponse response, StringBuilder stringBuilder) throws IOException{
		response.setContentType(MediaType.TEXT_HTML);
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(stringBuilder.toString());
	}
	
	public void getLiveOSInfo(HttpServletRequest request, HttpServletResponse response) throws SQLException, Exception{
		int serverId = Integer.valueOf(request.getParameter(Keys.SERVER_ID));
		OsDetail osDetail = (OsDetail)AgentsConnection.getRestJSONclient(serverId).get(URIReferenceAgent.CONF_OS, OsDetail.class);
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
		NetworkInfo networkInfo = (NetworkInfo)AgentsConnection.getRestJSONclient(serverId).get(URIReferenceAgent.CONF_NW, NetworkInfo.class);
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
		CpuInformation cpuInformation = (CpuInformation)AgentsConnection.getRestJSONclient(serverId).get(URIReferenceAgent.CONF_CPU, CpuInformation.class);
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
		UsageMemory usageMemory = (UsageMemory)AgentsConnection.getRestJSONclient(serverId).get(URIReferenceAgent.USAGE_MEMORY, UsageMemory.class);
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
}

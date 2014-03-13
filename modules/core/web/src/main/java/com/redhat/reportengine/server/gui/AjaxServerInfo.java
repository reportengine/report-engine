package com.redhat.reportengine.server.gui;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
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
import com.redhat.reportengine.agent.rest.mapper.jvm.JvmClassLoadingMXBean;
import com.redhat.reportengine.agent.rest.mapper.jvm.JvmCompilationMXBean;
import com.redhat.reportengine.agent.rest.mapper.jvm.JvmGarbageCollectorMXBean;
import com.redhat.reportengine.agent.rest.mapper.jvm.JvmMXBeanStore;
import com.redhat.reportengine.agent.rest.mapper.jvm.JvmMemoryMXBean;
import com.redhat.reportengine.agent.rest.mapper.jvm.JvmMemoryManagerMXBean;
import com.redhat.reportengine.agent.rest.mapper.jvm.JvmMemoryPoolMXBean;
import com.redhat.reportengine.agent.rest.mapper.jvm.JvmMemoryUsage;
import com.redhat.reportengine.agent.rest.mapper.jvm.JvmOperatingSystemMXBean;
import com.redhat.reportengine.agent.rest.mapper.jvm.JvmRuntimeMXBean;
import com.redhat.reportengine.agent.rest.mapper.jvm.JvmThreadMXBean;
import com.redhat.reportengine.agent.rest.mapper.jvm.JvmVirtualMachineDescriptor;
import com.redhat.reportengine.agent.rest.mapper.jvm.JvmsRunningList;
import com.redhat.reportengine.restapi.urimap.AgentRestUriMap;
import com.redhat.reportengine.server.dbdata.DynamicTableNameTable;
import com.redhat.reportengine.server.dbdata.ServerCpuDetailTable;
import com.redhat.reportengine.server.dbdata.ServerMemoryDetailTable;
import com.redhat.reportengine.server.dbdata.ServerNetworkDetailTable;
import com.redhat.reportengine.server.dbdata.ServerOsDetailTable;
import com.redhat.reportengine.server.dbdata.TestReferenceServerMapTable;
import com.redhat.reportengine.server.dbdata.TestSuiteTable;
import com.redhat.reportengine.server.dbmap.DynamicTableName;
import com.redhat.reportengine.server.dbmap.DynamicTableName.TYPE;
import com.redhat.reportengine.server.dbmap.ServerCpuDetail;
import com.redhat.reportengine.server.dbmap.ServerMemoryDetail;
import com.redhat.reportengine.server.dbmap.ServerNetworkDetail;
import com.redhat.reportengine.server.dbmap.ServerOsDetail;
import com.redhat.reportengine.server.dbmap.TestReferenceServerMap;
import com.redhat.reportengine.server.dbmap.TestSuite;
import com.redhat.reportengine.server.reports.General;
import com.redhat.reportengine.server.reports.Keys;
import com.redhat.reportengine.server.restclient.agent.AgentsConnection;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 19, 2013
 */
public class AjaxServerInfo extends AjaxCommon{
	
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
	
	public String getServerDetailsForTestSuite(HttpServletRequest request, HttpServletResponse response){
		try{
			int testSuiteId = Integer.valueOf(request.getParameter(Keys.TEST_SUITE_ID));
			TestSuite testSuite = new TestSuiteTable().get(testSuiteId);
			ArrayList<TestReferenceServerMap> serverMaps = new TestReferenceServerMapTable().getDetailByTestRefId(testSuite.getTestReferenceId());
			
			StringBuilder tabHeader = new StringBuilder("<ul>\n");			
			for(TestReferenceServerMap server : serverMaps){
				setKeyValueTabsHeading(tabHeader, server.getServerName(), "ajaxChartCpuMemoryFull.jsp?"+Keys.SUBMIT+"="+"TestSuite&"+Keys.SERVER_ID+"="+server.getServerId()+"&"+Keys.TEST_SUITE_ID+"="+testSuiteId);
			}			
			tabHeader.append("\n</ul>");
			return tabHeader.toString();

		}catch(Exception ex){
			_logger.error("exception,", ex);
		}
		return null;		
	}
	
	public JvmsRunningList getRunningJvmList(HttpServletRequest request, HttpServletResponse response) throws SQLException, Exception{
		int serverId = Integer.valueOf(request.getParameter(Keys.SERVER_ID));
		return (JvmsRunningList)AgentsConnection.getRestJSONclient(serverId).get(AgentRestUriMap.JVM_LIST, JvmsRunningList.class);
	}
	
	public String getLiveJvmDetail(HttpServletRequest request, HttpServletResponse response){
		try{
			int serverId = Integer.valueOf(request.getParameter(Keys.SERVER_ID));
			String jvmPid = request.getParameter(Keys.JVM_PID);
			String jvmName = request.getParameter(Keys.JVM_NAME);
			JvmMXBeanStore jvm = null;
			
			if(jvmPid != null){
				jvm = (JvmMXBeanStore)AgentsConnection.getRestJSONclient(serverId).get(AgentRestUriMap.JVM_MXBEAN_STORE_BY_PID+"/"+jvmPid, JvmMXBeanStore.class);
			}else if(jvmName != null){
				jvm = (JvmMXBeanStore)AgentsConnection.getRestJSONclient(serverId).get(AgentRestUriMap.JVM_MXBEAN_STORE_BY_NAME+"/"+jvmName, JvmMXBeanStore.class);
			}else{
				_logger.error("Should pass either JVM id or name....Both should not be null...");
				return null;
			}
			
			StringBuilder info = new StringBuilder();
			StringBuilder tabHeader = new StringBuilder("<ul>\n");

			setKeyValueTabs(tabHeader, info, "Virtual Machine Descriptor", getVirtualMachineDescriptor(jvm.getVirtualMachineDescriptor()));
			setKeyValueTabs(tabHeader, info, "Run Time", getRunTime(jvm.getRuntimeMXBean()));
			setKeyValueTabs(tabHeader, info, "Classes", getClasses(jvm.getClassLoadingMXBean()));
			setKeyValueTabs(tabHeader, info, "Threads", getThreads(jvm.getThreadMXBean()));
			setKeyValueTabs(tabHeader, info, "Compilation", getCompilation(jvm.getCompilationMXBean()));
			setKeyValueTabs(tabHeader, info, "Operating System", getOperatingSystem(jvm.getOperatingSystemMXBean()));
			setKeyValueTabs(tabHeader, info, "Garbage Collector", getGarbageCollector(jvm.getGarbageCollectorMXBean()));
			//setKeyValueTabs(tabHeader, info, "Memory Manager", getMemoryManager(jvm.getMemoryManagerMXBean()));
			setKeyValueTabs(tabHeader, info, "Memory Pool", getMemoryPool(jvm.getMemoryPoolMXBean()));
			setKeyValueTabs(tabHeader, info, "Memory", getMemory(jvm.getMemoryMXBean()));
			

			tabHeader.append("\n</ul>");
			tabHeader.append(info);

			return tabHeader.toString();
		}catch(Exception ex){
			_logger.error("exception,", ex);
		}
		return null;		
	}
	
	private String getRunTime(JvmRuntimeMXBean mxBean){
		StringBuilder stringBuilder = new StringBuilder();
		setTableHead(stringBuilder);
		setKeyValue(stringBuilder, "Name", mxBean.getName());
		setKeyValue(stringBuilder, "Up Time", mxBean.getUpTime());
		setKeyValue(stringBuilder, "Start Time", new SimpleDateFormat(General.guiInputDateTimeFormat).format(new Date(mxBean.getStartTime())));
		setKeyValue(stringBuilder, "VM Name", mxBean.getVmName());
		setKeyValue(stringBuilder, "VM Vendor", mxBean.getVmVendor());
		setKeyValue(stringBuilder, "VM Version", mxBean.getVmVersion());
		setKeyValue(stringBuilder, "Spec Name", mxBean.getSpecName());
		setKeyValue(stringBuilder, "Spec Vendor", mxBean.getSpecVendor());
		setKeyValue(stringBuilder, "Spec Version", mxBean.getSpecVersion());
		setKeyValue(stringBuilder, "Management Spec Version", mxBean.getManagementSpecVersion());
		setKeyValue(stringBuilder, "Input Arguments", getString(mxBean.getInputArguments(), "<BR>"));
		setKeyValue(stringBuilder, "Library Path", mxBean.getLibraryPath());
		setKeyValue(stringBuilder, "Boot Class Path", mxBean.getBootClassPath());
		setKeyValue(stringBuilder, "Class Path", mxBean.getClassPath());		
		setKeyValue(stringBuilder, "System Properties", General.getString(mxBean.getSystemProperties(), "<BR>"));
		setTableTail(stringBuilder);
		return stringBuilder.toString();		
	}
	private String getClasses(JvmClassLoadingMXBean mxBean){
		StringBuilder stringBuilder = new StringBuilder();
		setTableHead(stringBuilder);
		setKeyValue(stringBuilder, "Loaded Class Count", mxBean.getLoadedClassCount());
		setKeyValue(stringBuilder, "Unloaded Class Count", mxBean.getUnloadedClassCount());
		setKeyValue(stringBuilder, "Total Class Count", mxBean.getTotalLoadedClassCount());
		setKeyValue(stringBuilder, "Is Verbose", mxBean.isVerbose());
		setTableTail(stringBuilder);
		return stringBuilder.toString();		
	}
	private String getThreads(JvmThreadMXBean mxBean){
		StringBuilder stringBuilder = new StringBuilder();
		setTableHead(stringBuilder);
		setKeyValue(stringBuilder, "CurrentThreadCpuTime", mxBean.getCurrentThreadCpuTime());
		setKeyValue(stringBuilder, "CurrentThreadUserTime", mxBean.getCurrentThreadUserTime());
		setKeyValue(stringBuilder, "DaemonThreadCount", mxBean.getDaemonThreadCount());
		setKeyValue(stringBuilder, "PeakThreadCount", mxBean.getPeakThreadCount());
		setKeyValue(stringBuilder, "ThreadCount", mxBean.getThreadCount());
		setKeyValue(stringBuilder, "TotalStartedThreadCoun", mxBean.getTotalStartedThreadCount());
		setKeyValue(stringBuilder, "Is Current Thread CPU Time Supported", mxBean.isCurrentThreadCpuTimeSupported());
		setKeyValue(stringBuilder, "Is Object Monitor Usage Supported", mxBean.isObjectMonitorUsageSupported());
		setKeyValue(stringBuilder, "Is Synchronizer Usage Supported", mxBean.isSynchronizerUsageSupported());
		setKeyValue(stringBuilder, "Is Thread Contention Monitoring Enabled", mxBean.isThreadContentionMonitoringEnabled());
		setKeyValue(stringBuilder, "Is Thread Contention Monitoring Supported", mxBean.isThreadContentionMonitoringSupported());
		setKeyValue(stringBuilder, "Is Thread Cpu Time Enabled", mxBean.isThreadCpuTimeEnabled());
		setKeyValue(stringBuilder, "Is Thread Cpu Time Supported", mxBean.isThreadCpuTimeSupported());
		
		setTableTail(stringBuilder);
		return stringBuilder.toString();		
	}	
	private String getCompilation(JvmCompilationMXBean mxBean){
		StringBuilder stringBuilder = new StringBuilder();
		setTableHead(stringBuilder);
		setKeyValue(stringBuilder, "Name", mxBean.getName());
		setKeyValue(stringBuilder, "Total Compilation Time", mxBean.getTotalCompilationTime());
		setKeyValue(stringBuilder, "Is Compilation Time Monitoring Supported", mxBean.isCompilationTimeMonitoringSupported());
		setTableTail(stringBuilder);
		return stringBuilder.toString();		
	}
	private String getOperatingSystem(JvmOperatingSystemMXBean mxBean){
		StringBuilder stringBuilder = new StringBuilder();
		setTableHead(stringBuilder);
		setKeyValue(stringBuilder, "Name", mxBean.getName());
		setKeyValue(stringBuilder, "Arch", mxBean.getArch());
		setKeyValue(stringBuilder, "Version", mxBean.getVersion());
		setKeyValue(stringBuilder, "Available Processors", mxBean.getAvailableProcessors());
		setKeyValue(stringBuilder, "System Load Average", mxBean.getSystemLoadAverage());
		setTableTail(stringBuilder);
		return stringBuilder.toString();		
	}
	private String getGarbageCollector(ArrayList<JvmGarbageCollectorMXBean> mxBeans){
		StringBuilder stringBuilder = new StringBuilder();
		setTableHead(stringBuilder);
		/*for(JvmGarbageCollectorMXBean mxBean : mxBeans){
			setKeyValue(stringBuilder, "Name", mxBean.getName());
			setKeyValue(stringBuilder, "Collection Count", mxBean.getCollectionCount());
			setKeyValue(stringBuilder, "Collection Time", mxBean.getCollectionTime());
			setKeyValue(stringBuilder, "Memory Poll Names", General.getString(mxBean.getMemoryPollNames(), "<BR>"));
			setKeyValue(stringBuilder, "Is Valid", mxBean.isValid());
		}*/
		List<Map<String, Object>> rows = new LinkedList<Map<String, Object>>();
		Map<String, Object> row;
		for(JvmGarbageCollectorMXBean mxBean : mxBeans){
			row = new LinkedHashMap<String, Object>();
			row.put("Name", mxBean.getName());
			row.put("Memory Poll Names", General.getString(mxBean.getMemoryPollNames(), "<BR>"));
			row.put("Collection Count", mxBean.getCollectionCount());
			row.put("Collection Time", mxBean.getCollectionTime());
			//row.put("Is Valid", mxBean.isValid());
			rows.add(row);
		}
		stringBuilder.append(getJQueryTable(rows, "GarbageCollector"));
		setTableTail(stringBuilder);
		return stringBuilder.toString();		
	}
	
	private String getMemoryManager(ArrayList<JvmMemoryManagerMXBean> mxBeans){
		StringBuilder stringBuilder = new StringBuilder();
		setTableHead(stringBuilder);
		List<Map<String, Object>> rows = new LinkedList<Map<String, Object>>();
		Map<String, Object> row;
		for(JvmMemoryManagerMXBean mxBean : mxBeans){
			row = new HashMap<String, Object>();
			/*setKeyValue(stringBuilder, "Name", mxBean.getName());
			setKeyValue(stringBuilder, "Memory Pool Names", General.getString(mxBean.getMemoryPoolNames(), "<BR>"));
			setKeyValue(stringBuilder, "Is Valid", mxBean.isValid());*/
			row.put("Name", mxBean.getName());
			row.put("Memory Pool Names", General.getString(mxBean.getMemoryPoolNames(), "<BR>"));
			//row.put("Is Valid", mxBean.isValid());
			rows.add(row);
		}
		stringBuilder.append(getJQueryTable(rows, "MemoryManager"));
		setTableTail(stringBuilder);
		return stringBuilder.toString();
	}
	private String getMemoryPool(ArrayList<JvmMemoryPoolMXBean> mxBeans){
		StringBuilder stringBuilder = new StringBuilder();
		setTableHead(stringBuilder);
		/*for(JvmMemoryPoolMXBean mxBean : mxBeans){
			setKeyValue(stringBuilder, "Name", mxBean.getName());
			setKeyValue(stringBuilder, "Memory Manager Names", General.getString(mxBean.getMemoryManagerNames(), "<BR>"));
			setKeyValue(stringBuilder, "Collection Usage", getMemoryUsage(mxBean.getCollectionUsage()));
			setKeyValue(stringBuilder, "Collection Usage Threshold", mxBean.getCollectionUsageThreshold());
			setKeyValue(stringBuilder, "Collection Usage Threshold Count", mxBean.getCollectionUsageThresholdCount());
			setKeyValue(stringBuilder, "Peak Usage", getMemoryUsage(mxBean.getPeakUsage()));
			setKeyValue(stringBuilder, "Type", mxBean.getType());
			setKeyValue(stringBuilder, "Usage", getMemoryUsage(mxBean.getUsage()));
			setKeyValue(stringBuilder, "Usage Threshold", mxBean.getUsageThreshold());
			setKeyValue(stringBuilder, "Usage Threshold Count", mxBean.getUsageThresholdCount());
			setKeyValue(stringBuilder, "Is Collection Usage Threshold Exceeded", mxBean.isCollectionUsageThresholdExceeded());
			setKeyValue(stringBuilder, "Is Collection Usage Threshold Supported", mxBean.isCollectionUsageThresholdSupported());
			setKeyValue(stringBuilder, "Is Usage Threshold Exceeded", mxBean.isUsageThresholdExceeded());
			setKeyValue(stringBuilder, "Is Usage Threshold Supported", mxBean.isUsageThresholdSupported());
			setKeyValue(stringBuilder, "Is Valid", mxBean.isValid());
		}*/
		
		List<Map<String, Object>> rows = new LinkedList<Map<String, Object>>();
		Map<String, Object> row;		
		for(JvmMemoryPoolMXBean mxBean : mxBeans){
			row = new LinkedHashMap<String,Object>();
			row.put("Name", mxBean.getName());
			row.put("Memory Manager Names", General.getString(mxBean.getMemoryManagerNames(), "<BR>"));
			row.put("Type", mxBean.getType());
			row.put("Usage", getMemoryUsage(mxBean.getUsage()));
			row.put("Peak Usage", getMemoryUsage(mxBean.getPeakUsage()));
			row.put("Collection Usage", getMemoryUsage(mxBean.getCollectionUsage()));
			//row.put("Collection Usage Threshold", mxBean.getCollectionUsageThreshold());
			//row.put("Collection Usage Threshold Count", mxBean.getCollectionUsageThresholdCount());
			//row.put("Usage Threshold", mxBean.getUsageThreshold());
			//row.put("Usage Threshold Count", mxBean.getUsageThresholdCount());
			//row.put("Is Collection Usage Threshold Exceeded", mxBean.isCollectionUsageThresholdExceeded());
			//row.put("Is Collection Usage Threshold Supported", mxBean.isCollectionUsageThresholdSupported());
			//row.put("Is Usage Threshold Exceeded", mxBean.isUsageThresholdExceeded());
			//row.put("Is Usage Threshold Supported", mxBean.isUsageThresholdSupported());
			//row.put("Is Valid", mxBean.isValid());
			rows.add(row);
		}
		stringBuilder.append(getJQueryTable(rows, "MemoryPool"));
		setTableTail(stringBuilder);
		return stringBuilder.toString();		
	}
	
	private String getMemory(JvmMemoryMXBean mxBean){
		StringBuilder stringBuilder = new StringBuilder();
		setTableHead(stringBuilder);
		/*setKeyValue(stringBuilder, "Heap Memory Usage", getMemoryUsage(mxBean.getHeapMemoryUsage()));
		setKeyValue(stringBuilder, "Non Heap Memory Usage", getMemoryUsage(mxBean.getNonHeapMemoryUsage()));
		setKeyValue(stringBuilder, "Object Pending Finalization Count", mxBean.getObjectPendingFinalizationCount());
		setKeyValue(stringBuilder, "Is Verbose", mxBean.isVerbose());*/
		List<Map<String, Object>> rows = new LinkedList<Map<String, Object>>();
		
		Map<String, Object> row = new LinkedHashMap<String, Object>();
		row.put("Memory Usage", "Heap");
		setMemoryUsage(mxBean.getHeapMemoryUsage(), row);
		rows.add(row);
		
		row = new LinkedHashMap<String, Object>();
		row.put("Memory Usage", "Non Heap");
		setMemoryUsage(mxBean.getNonHeapMemoryUsage(), row);
		rows.add(row);
		
		stringBuilder.append(getJQueryTable(rows, "Memory"));
		setKeyValue(stringBuilder, "Object Pending Finalization Count", mxBean.getObjectPendingFinalizationCount());
		//setKeyValue(stringBuilder, "Is Verbose", mxBean.isVerbose());
		
		setTableTail(stringBuilder);
		return stringBuilder.toString();		
	}
	
	private String getVirtualMachineDescriptor(JvmVirtualMachineDescriptor descriptor){
		StringBuilder stringBuilder = new StringBuilder();
		setTableHead(stringBuilder);
		setKeyValue(stringBuilder, "Display Name", descriptor.getDisplayName());
		setKeyValue(stringBuilder, "Id", descriptor.getId());
		setTableTail(stringBuilder);
		return stringBuilder.toString();		
	}
	
	private void setMemoryUsage(JvmMemoryUsage usage, Map<String, Object> row){
		if(usage != null){
			row.put("Init", General.getFileSizeFromBytes(usage.getInit()));
			row.put("Committed", General.getFileSizeFromBytes(usage.getCommitted()));
			row.put("Used", General.getFileSizeFromBytes(usage.getUsed()));
			row.put("Max", General.getFileSizeFromBytes(usage.getMax()));
		}
	}
	
	private String getMemoryUsage(JvmMemoryUsage usage){
		StringBuilder stringBuilder = new StringBuilder();
		setTableHead(stringBuilder);
		if(usage != null){
			setKeyValue(stringBuilder, "Init", General.getFileSizeFromBytes(usage.getInit()));
			setKeyValue(stringBuilder, "Committed", General.getFileSizeFromBytes(usage.getCommitted()));
			setKeyValue(stringBuilder, "Used", General.getFileSizeFromBytes(usage.getUsed()));
			setKeyValue(stringBuilder, "Max", General.getFileSizeFromBytes(usage.getMax()));
		}else{
			return "-";
		}
		setTableTail(stringBuilder);
		return stringBuilder.toString();
	}
	
	public static void getJVMlist(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{
		String serverId = request.getParameter(Keys.SERVER_ID);
		Map<String, String> options = new HashMap<String, String>();
		
		ArrayList<DynamicTableName> jvms = DynamicTableNameTable.get(Integer.valueOf(serverId), TYPE.JVM_MEMORY);
		for(DynamicTableName jvm: jvms){
			options.put(String.valueOf(jvm.getId()), jvm.getName().replace("_"+serverId, ""));
		}

		Writer writer = new StringWriter();
		JsonGenerator jsonGenerator = new JsonFactory().createJsonGenerator(writer);
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(jsonGenerator, options);
		jsonGenerator.close();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(writer.toString());
	}
	
	private String getJQueryTable(List<Map<String, Object>> rows, String tableId){
		StringBuilder builder = new StringBuilder();
		builder.append("\n\n<script type=\"text/javascript\" charset=\"utf-8\">\n"
					+"$(document).ready(function() {\n"
					+ "oTable = $('#").append(tableId).append("').dataTable({\n"
					+ "\"bJQueryUI\": true,\n"
			//		+ "\"bPaginate\": true,\n"
			//		+ "\"sPaginationType\": \"full_numbers\",\n"
					+ "\"aaSorting\": [[0,'asc']],\n"
					+ "\"aoColumns\": [\n");
		
		Set<String> keys;
		if(rows.size() > 0){
			keys = rows.get(0).keySet();
		}else{
			return null;
		}
		
		for(int i=1; i<keys.size();i++){
			builder.append("null,\n");
		}
		builder.append("null\n");
		
		builder.append("],\n"
				//+ "\"iDisplayLength\":15\n"
				+ "});\n"
				+ "} );\n"
				+ "</script>\n");
		
		builder.append("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"display\"\n"
				+ "id=\"").append(tableId).append("\">\n"
				+ "<thead>\n"
				+ "<tr>\n");
		for(String key : keys){
			builder.append("<th>").append(key).append("</th>\n");
		}
		builder.append("</tr>\n"
				+ "</thead>\n"
				+ "<tbody>\n");
		
		for(Map<String, Object> row : rows){
			builder.append("<tr>\n");
			for(String key : keys){
				builder.append("<td align=\"left\">").append(row.get(key)).append("</td>\n");
			}
			builder.append("</tr>\n");
		}
		
		builder.append("</tbody>\n"
				+ "</table>\n\n");
		
		return builder.toString();
	}	
}

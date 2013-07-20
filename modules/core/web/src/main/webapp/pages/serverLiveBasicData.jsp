<%@page import="org.hyperic.sigar.OperatingSystem"%>
<%@page import="org.hyperic.sigar.SysInfo"%>
<%@page import="com.redhat.reportengine.agent.rest.mapper.UsageMemory"%>
<%@page import="com.redhat.reportengine.agent.rest.mapper.CpuInformation"%>
<%@page import="com.redhat.reportengine.server.restclient.agent.AgentsConnection"%>
<%@page import="com.redhat.reportengine.agent.rest.mapper.URIReferenceAgent"%>
<%@page import="com.redhat.reportengine.agent.rest.mapper.NetworkInfo"%>
<%@ include file="index-part1.jsp"%>
<%
			int serverId = Integer.parseInt((String) request.getParameter(Keys.SERVER_ID));	
			Server server = new ServerTable().getById(serverId);
			NetworkInfo networkInfo = (NetworkInfo)AgentsConnection.getRestJSONclient(serverId).get(URIReferenceAgent.CONF_NW, NetworkInfo.class);
			CpuInformation cpuInformation = (CpuInformation)AgentsConnection.getRestJSONclient(serverId).get(URIReferenceAgent.CONF_CPU, CpuInformation.class);
			UsageMemory usageMemory = (UsageMemory)AgentsConnection.getRestJSONclient(serverId).get(URIReferenceAgent.USAGE_MEMORY, UsageMemory.class);
			OperatingSystem sysInfo = (OperatingSystem)AgentsConnection.getRestJSONclient(serverId).get(URIReferenceAgent.CONF_OS, OperatingSystem.class);
		
			
%>

<script type="text/javascript">
	
</script>

<%@ include file="index-part2.jsp"%>

<div id="dt_page">
	<div id="container">

		<h1>
			Basic Info: [Server:<%=server.getName()%>(<%=server.getHostIp()%>)]
		</h1>

		<h1>
			<font size="2">Network Info:</font>
		</h1>
		<font size="2" face="Courier">
			<table border="0" cellpadding="3" align="left">
				<TR>
					<td align="left">Hostname</td>
					<TD>:</TD>
					<td><%=networkInfo.getNetInfo().getHostName()%></td>
				</tr>
				<TR>
					<td align="left">Interface Name</td>
					<TD>:</TD>
					<td><%=networkInfo.getNetInterfaceConfig().getName()%></td>
				</tr>
				<TR>
					<td align="left">IP Address</td>
					<TD>:</TD>
					<td><%=networkInfo.getNetInterfaceConfig().getAddress()%></td>
				</tr>
				<TR>
					<td align="left">Subnetmask</td>
					<TD>:</TD>
					<td><%=networkInfo.getNetInterfaceConfig().getNetmask()%></td>
				</tr>
				<TR>
					<td align="left">Default Gatway</td>
					<TD>:</TD>
					<td><%=networkInfo.getNetInfo().getDefaultGateway()%></td>
				</tr>
				<TR>
					<td align="left">Primary DNS</td>
					<TD>:</TD>
					<td><%=networkInfo.getNetInfo().getPrimaryDns()%></td>
				</tr>
				<TR>
					<td align="left">Secondary DNS</td>
					<TD>:</TD>
					<td><%=networkInfo.getNetInfo().getSecondaryDns()%></td>
				</tr>
				<TR>
					<td align="left">Domain Name</td>
					<TD>:</TD>
					<td><%=networkInfo.getNetInfo().getDomainName()%></td>
				</tr>
				<TR>
					<td align="left">MAC</td>
					<TD>:</TD>
					<td><%=networkInfo.getNetInterfaceConfig().getHwaddr()%></td>
				</tr>

			</table>
		</font>

		<h1>
			<font size="2">CPU Info:</font>
		</h1>
		<font size="2" face="Courier">
			<table border="0" cellpadding="3" align="left">
				<TR>
					<td align="left">Vendor</td>
					<TD>:</TD>
					<td><%=cpuInformation.getVendor()%></td>
				</tr>
				<TR>
					<td align="left">Model</td>
					<TD>:</TD>
					<td><%=cpuInformation.getModel()%></td>
				</tr>
				<TR>
					<td align="left">Speed</td>
					<TD>:</TD>
					<td><%=cpuInformation.getMhz()%> MHz</td>
				</tr>
				<TR>
					<td align="left">Total Sockets</td>
					<TD>:</TD>
					<td><%=cpuInformation.getTotalSockets()%></td>
				</tr>
				<TR>
					<td align="left">Cores Per Socket</td>
					<TD>:</TD>
					<td><%=cpuInformation.getCoresPerSocket()%></td>
				</tr>
				<TR>
					<td align="left">Total Cores</td>
					<TD>:</TD>
					<td><%=cpuInformation.getTotalCores()%></td>
				</tr>
				<TR>
					<td align="left">Cache</td>
					<TD>:</TD>
					<td>
						<%if((""+cpuInformation.getCacheSize()).equalsIgnoreCase("-1")){out.println("-");}else{out.println(cpuInformation.getCacheSize());}%>
					</td>
				</tr>

			</table>
		</font>

		<h1>
			<font size="2">Memory Info:</font>
		</h1>
		<font size="2" face="Courier">
			<table border="0" cellpadding="3" align="left">
				<TR>
					<td align="left">Physical</td>
					<TD>:</TD>
					<td><%=usageMemory.getMemory().getTotal()/(1024*1024)%> MB</td>
				</tr>
				<TR>
					<td align="left">Swap</td>
					<TD>:</TD>
					<td><%=usageMemory.getSwap().getTotal()/(1024*1024)%> MB</td>
				</tr>
			</table>
		</font>

		
		<h1>
			<font size="2">OS Info:</font>
		</h1>
		<font size="2" face="Courier">
			<table border="0" cellpadding="3" align="left">

				<TR>
					<td align="left">Decription</td>
					<TD>:</TD>
					<td><%=sysInfo.getDescription()%></td>
				</tr>
				<TR>
					<td align="left">Name</td>
					<TD>:</TD>
					<td><%=sysInfo.getName()%></td>
				</tr>
				<TR>
					<td align="left">Arch</td>
					<TD>:</TD>
					<td><%=sysInfo.getArch()%></td>
				</tr>
				<TR>
					<td align="left">Machine</td>
					<TD>:</TD>
					<td><%=sysInfo.getMachine()%></td>
				</tr>
				<TR>
					<td align="left">Kernel Version</td>
					<TD>:</TD>
					<td><%=sysInfo.getVersion()%></td>
				</tr>
				<TR>
					<td align="left">Patch Level</td>
					<TD>:</TD>
					<td><%=sysInfo.getPatchLevel()%></td>
				</tr>
				<TR>
					<td align="left">Vendor</td>
					<TD>:</TD>
					<td><%=sysInfo.getVendor()%></td>
				</tr>
				<TR>
					<td align="left">Vendor Name</td>
					<TD>:</TD>
					<td><%=sysInfo.getVendorName()%></td>
				</tr>
				<TR>
					<td align="left">Vendor Version</td>
					<TD>:</TD>
					<td><%=sysInfo.getVendorVersion()%></td>
				</tr>
				<TR>
					<td align="left">Vendor Code Name</td>
					<TD>:</TD>
					<td><%=sysInfo.getVendorCodeName()%></td>
				</tr>
				<TR>
					<td align="left">Data Model</td>
					<TD>:</TD>
					<td><%=sysInfo.getDataModel()%></td>
				</tr>
				<TR>
					<td align="left">CPU Endian</td>
					<TD>:</TD>
					<td><%=sysInfo.getCpuEndian()%></td>
				</tr>

			</table>
		</font>
	<h1></h1>

	</div>
</div>


<%@ include file="index-part3.jsp"%>

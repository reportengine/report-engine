<%@ include file="index-part1.jsp"%>
<%
String buttonName 	= (String)request.getParameter(Keys.SUBMIT);
String pidStr		= (String)request.getParameter(Keys.PID);
Integer serverId=null;
Server server = null;

if(request.getParameter(Keys.SERVER_ID) != null){
	serverId = Integer.valueOf(request.getParameter(Keys.SERVER_ID));
	server = new ServerTable().getById(serverId);
}

if((buttonName == null) && (pidStr == null)){
	%>
	<%@ include file="index-part2.jsp"%>
	<div id="dt_page">
	<div id="container">
		<h1>Live Pid Info:</h1>

		<form method="post" name="cpuChart" action="serverLivePidData.jsp">
			<table border="0" cellpadding="3" align="left" id="dt_table">

				<tr>
					<td align="left">Server</td>
					<td>:</td>
					<td colspan="2"><select data-placeholder="Choose a Server..."  tabindex="1" class="chosen" id="<%=Keys.SERVER_ID%>" name="<%=Keys.SERVER_ID%>">            <option value=""></option> 
							<%
								ArrayList<Server> servers = new ServerTable().get();
										for (Server serverTmp : servers) {
											out.println("<option value=\"" + serverTmp.getId() + "\">"
													+ serverTmp.getName() + " [" + serverTmp.getHostIp()
													+ "]" + "</option>");
										}
							%>
					</select></td>
				</tr>

				
				<tr>
					<td colspan="4" align="right">
						<div class="jbutton">
							<input type="submit" name="SUBMIT" value="Get"
								style="width: 80px;">
						</div>
					</td>
				</tr>
			</table>
<script type="text/javascript"> 
      $('.chosen').chosen({ width: "300px" });
  </script>


		</form>

	</div>
</div>
<%
}else if((pidStr != null) && (serverId != null)){	
%>

<script>
  $(function() {
    $( "#pid-tabs" ).tabs();
  });
  </script>
  
	<%@ include file="index-part2.jsp"%>
	<div id="dt_page">
		<div id="container">

			<h1>Pid Info: <font size="2">[<b>Server: </b><I><%=server.getName()%>,</I>
					<b>Host/IP:</b> <I><%=server.getHostIp()%>,</I> <b>Platform:</b> <I><%=server.getPlatform()%>,</I> <b>PID:</b> <I><%=pidStr%></I>]
				</font>
		</h1>
		<div id="pid-tabs" style="overflow:hidden;">
	<%
		String data = new AjaxServerInfo().getPidDetail(request, response);
		if(data != null){
			out.println(data);
		}else{
			%>
			<div id="error-info" class="ui-widget">
				<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0pt 0.7em;"> 
				<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: 0.3em;"></span>
				<div id="error-info-msg">Unable to access the agent of "<%=server.getName()%>[<%=server.getHostIp()%>]", 
				<BR>If agent is running state, kindly check the firewall status on agent port! <I>[Agent Port: <%=server.getAgentPort()%>]</I></div></p>
			</div>
			</div>
			
			
			<%
		}
	%>
	</div>
	<%
}else if(buttonName.equalsIgnoreCase("Get") && (serverId != null)){	
%>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		oTable = $('#dt_table').dataTable({
			"bJQueryUI": true,
			"bPaginate": true,
			"sPaginationType": "full_numbers",
			"aaSorting": [[1,'asc']],
			"aoColumns": [
			{ "sType": "num-html" },
			{ "sType": "num-html" },
			null,
			null,
			{ "sType": "date-gui" },
			{ "sType": "cpu-usage" },
			{ "sType": "file-size" },
			null
			],
			"iDisplayLength":15
			});
} );			
</script>
<%@ include file="index-part2.jsp"%>
<div id="dt_page">
	<div id="container">

		<h1>Pid Info: <font size="2">[<b>Server: </b><I><%=server.getName()%>,</I>
				<b>Host/IP:</b> <I><%=server.getHostIp()%>,</I> <b>Platform:</b> <I><%=server.getPlatform()%></I>]
			</font>
	</h1>
	
<form method="post" id="server-pid-list" name="server-pid-list" action="serverLivePidData.jsp" >
<table cellpadding="0" cellspacing="0" border="0" class="display" id="dt_table">
	<thead>
		<tr>
			<th>PID</th>
			<th>PPID</th>
			<th>UID</th>
			<th>CMD</th>
            <th>Start Time</th>		
			<th>CPU</th>
			<th>Memory</th>
			<th>tty</th>
		</tr>
	</thead>
	<tbody>
<%
	PidList pids = new AjaxServerInfo().getPidsList(request, response);
	StringBuilder builder = new StringBuilder();
	for(PidDetail pid : pids.getPidDetail()){
		builder.append("<tr>");
		
		builder.append("<td align=\"left\"><a class=\"alink\" href=\"serverLivePidData.jsp?").append(Keys.SERVER_ID);
		builder.append("=").append(serverId).append("&").append(Keys.PID).append("=").append(pid.getPid());
		builder.append("\">").append(pid.getPid()).append("</a></td>");
		
		builder.append("<td align=\"left\">").append(pid.getPpid()).append("</td>");
		builder.append("<td align=\"left\">").append(pid.getUid()).append("</td>");
		builder.append("<td align=\"left\">").append(pid.getCmd()).append("</td>");
		builder.append("<td align=\"left\">").append(General.getGuiDateTime(new Date(pid.getStartTime()))).append("</td>");
		builder.append("<td align=\"left\">").append(General.decimalDigit2.format((Double)pid.getCpu()*100)).append(" %</td>");
		builder.append("<td align=\"left\">").append(General.decimalDigit2.format((Long)pid.getMemory()/(1024*1024.0))).append(" MB</td>");
		builder.append("<td align=\"left\">").append(pid.getTty()).append("</td>");
		builder.append("</tr>\n");
	}
	out.println(builder.toString());
%>
 </tbody>
</table>
<input type="hidden" id="<%=Keys.SERVER_ID%>" name="<%=Keys.SERVER_ID%>" value="<%=serverId%>">
</form>

	</div>
</div>
<%
}
%>
<%@ include file="index-part3.jsp"%>

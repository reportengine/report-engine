<%@ include file="index-part1.jsp"%>
<%
String buttonName 	= (String)request.getParameter(Keys.SUBMIT);
String jvmPid		= (String)request.getParameter(Keys.JVM_PID);
String jvmName		= (String)request.getParameter(Keys.JVM_NAME);

Integer serverId=null;
Server server = null;

if(request.getParameter(Keys.SERVER_ID) != null){
	serverId = Integer.valueOf(request.getParameter(Keys.SERVER_ID));
	server = new ServerTable().getById(serverId);
}

if((buttonName == null) && (serverId == null)){
%>
	<%@ include file="index-part2.jsp"%>
	<div id="dt_page">
	<div id="container">
		<h1>Live JVM Info:</h1>

		<form method="post" name="cpuChart" action="serverLiveJvmData.jsp">
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
}else if(((jvmName != null) || (jvmPid != null)) && (serverId != null)){
%>
<script>
  $(function() {
    $( "#jvm-tabs" ).tabs();
  });
  </script>
  
	<%@ include file="index-part2.jsp"%>
	<div id="dt_page">
		<div id="container">

			<h1>JVM Info: <font size="2">[<b>Server: </b><I><%=server.getName()%>,</I>
					<b>Host/IP:</b> <I><%=server.getHostIp()%>,</I> <b>Platform:</b> <I><%=server.getPlatform()%>,</I> <b>PID:</b> <I><%=jvmPid%></I>]
				</font>
		</h1>
		<div id="jvm-tabs" style="overflow:hidden;">
	<%
		String data = new AjaxServerInfo().getLiveJvmDetail(request, response);
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
			null
			],
			"iDisplayLength":15
			});
} );			
</script>
<%@ include file="index-part2.jsp"%>
<div id="dt_page">
	<div id="container">

		<h1>JVM Info: <font size="2">[<b>Server: </b><I><%=server.getName()%>,</I>
				<b>Host/IP:</b> <I><%=server.getHostIp()%>,</I> <b>Platform:</b> <I><%=server.getPlatform()%></I>]
			</font>
	</h1>
	
<form method="post" id="server-pid-list" name="server-pid-list" action="serverLiveJvmData.jsp" >
<table cellpadding="0" cellspacing="0" border="0" class="display" id="dt_table">
	<thead>
		<tr>
			<th>PID</th>
			<th>JVM Description</th>
		</tr>
	</thead>
	<tbody>
<%
	List<JvmVirtualMachineDescriptor> jvmList = new AjaxServerInfo().getRunningJvmList(request, response).getList();
	StringBuilder builder = new StringBuilder();
	for(JvmVirtualMachineDescriptor jvm : jvmList){
		builder.append("<tr>");
		
		builder.append("<td align=\"left\"><a class=\"alink\" href=\"serverLiveJvmData.jsp?").append(Keys.SERVER_ID);
		builder.append("=").append(serverId).append("&").append(Keys.JVM_PID).append("=").append(jvm.getId());
		builder.append("\">").append(jvm.getId()).append("</a></td>");
		
		builder.append("<td align=\"left\"><a class=\"alink\" href=\"serverLiveJvmData.jsp?").append(Keys.SERVER_ID);
		builder.append("=").append(serverId).append("&").append(Keys.JVM_NAME).append("=").append(URLEncoder.encode(jvm.getDisplayName(), "UTF-8"));
		builder.append("\">").append(jvm.getDisplayName()).append("</a></td>");
		
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

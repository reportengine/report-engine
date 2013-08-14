<%@ include file="index-part1.jsp"%>
<%
String buttonName 	= (String)request.getParameter(Keys.SUBMIT);
Integer serverId=null;
Server server = null;

if(request.getParameter(Keys.SERVER_ID) != null){
	serverId = Integer.valueOf(request.getParameter(Keys.SERVER_ID));
	server = new ServerTable().getById(serverId);
}

if(buttonName == null){
	%>
	<%@ include file="index-part2.jsp"%>
	<div id="dt_page">
	<div id="container">
		<h1>Live Disk Info:</h1>

		<form method="post" name="networkData" action="serverLiveDiskData.jsp">
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
}else if(buttonName.equalsIgnoreCase("Get")){	
%>
  <script type="text/javascript">
$(function() {
  $( "#disk-info-tabs" ).tabs({
    beforeLoad: function( event, ui ) {
      ui.jqXHR.error(function() {
        ui.panel.html(
          "Couldn't load this tab. We'll try to fix this as soon as possible. Please rport a bug!" );
      });
    }
  });
});
</script>
<%@ include file="index-part2.jsp"%>
<div id="dt_page">
	<div id="container">

		<h1>Live Disk Info: <font size="2">[<b>Server: </b><I><%=server.getName()%>,</I>
				<b>Host/IP:</b> <I><%=server.getHostIp()%>,</I> <b>Platform:</b> <I><%=server.getPlatform()%></I>]
			</font>
	</h1>
	
	<div id="disk-info-tabs" style="overflow:hidden;">
	 <ul>
    <li><a href="ajaxLiveServerDiskData.jsp?<%=Keys.SERVER_ID%>=<%=serverId%>&<%=Keys.DISK_QUERY_TYPE%>=<%=Keys.DISK_FILE_SYSTEM%>">File System</a></li>
    <li><a href="ajaxLiveServerDiskData.jsp?<%=Keys.SERVER_ID%>=<%=serverId%>&<%=Keys.DISK_QUERY_TYPE%>=<%=Keys.DISK_USAGE%>">Usage</a></li>
  </ul>
</div>
	</div>
</div>
<%
}
%>
<%@ include file="index-part3.jsp"%>

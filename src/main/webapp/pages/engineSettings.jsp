
<%@page import="com.redhat.reportengine.server.ServerSettings"%>
<%@ include file="index-part1.jsp"%>
<%
String buttonName 	= (String)request.getParameter("SUBMIT");
String updateStatus	= (String)request.getParameter("updateStatus");

if(buttonName == null){
%>
		<%@ include file="index-part2.jsp"%>
		
		

		<div id="dt_page">
		<div id="container">
		<%if(updateStatus != null){%>
		<div class="ui-widget">
			<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0pt 0.7em;"> 
				<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: 0.3em;"></span>
				Settings updated Successfully! Note: Server restart is required for some of the settings</p>
			</div>
		</div>
		<%}%>
		<form method="post" name="EngineSettings" action="engineSettings.jsp"> 
		<table border="0" cellpadding="3" align="left" id="dt_table">
		
		<tr><td colspan="5"><h1>Engine Settings:</h1></td></tr>
		
		<tr>
			<td align="left">Engine URL</td>
			<td>:</td>
			<td colspan="2">
				<input type="text" name="<%=ServerSettings.KEY_ENGINE_URL%>" id="<%=ServerSettings.KEY_ENGINE_URL%>" size="8" value="<%=ServerSettings.getEngineURL()%>"  style="width:320px;">
			</td>	
		</tr>
		
		<tr>
			<td align="left">Engine RMI Port</td>
			<td>:</td>
			<td colspan="2">
				<input type="text" name="<%=ServerSettings.KEY_ENGINE_RMI_PORT%>" id="<%=ServerSettings.KEY_ENGINE_RMI_PORT%>" size="8" value="<%=ServerSettings.getServerRmiPort()%>"  style="width:320px;">
			</td>	
		</tr>
		
		
		<tr><td colspan="5"><h1>E-mail Notification:</h1></td></tr>
		
		<tr>
			<td align="left">Email Server</td>
			<td>:</td>
			<td colspan="2">
				<input type="text" name="<%=ServerSettings.KEY_EMAIL_SERVER%>" id="<%=ServerSettings.KEY_EMAIL_SERVER%>" size="200" value="<%=ServerSettings.getEmailServer()%>"  style="width:320px;">
			</td>	
		</tr>
			
		<tr>
			<td align="left">Email Server Port</td>
			<td>:</td>
			<td colspan="2">
				<input type="text" name="<%=ServerSettings.KEY_EMAIL_SERVER_PORT%>" id="<%=ServerSettings.KEY_EMAIL_SERVER_PORT%>" size="8" value="<%=ServerSettings.getEmailServerPort()%>"  style="width:320px;">
			</td>	
		</tr>
		
		<tr>
			<td align="left">Sender E-mail Address</td>
			<td>:</td>
			<td colspan="2">
				<input type="text" name="<%=ServerSettings.KEY_SENDER_EMAIL%>" id="<%=ServerSettings.KEY_SENDER_EMAIL%>" size="200" value="<%=ServerSettings.getEmailFrom()%>"  style="width:320px;">
			</td>	
		</tr>
		
		<tr><td colspan="5"><h1>Test Suite Settings:</h1></td></tr>
		
		<tr>
			<td align="left">Inactive Timeout (Sec)</td>
			<td>:</td>
			<td colspan="2">
				<input type="text" name="<%=ServerSettings.KEY_TEST_SUITE_INACTIVE_TIME%>" id="<%=ServerSettings.KEY_TEST_SUITE_INACTIVE_TIME%>" size="8" value="<%=ServerSettings.getTestSuiteInactiveTime()%>"  style="width:320px;">
			</td>	
		</tr>		

		<tr> <td colspan="4" align="right"> <div class="jbutton"><input type="submit" name="SUBMIT" value="Update" style="width:80px;"></div></td></tr>
		</table>
		
		</form>

		</div>
		</div>

<%
	}else if(buttonName.equalsIgnoreCase("Update")){
		ServerSettings.updateSystemSettings(request, session);
		response.sendRedirect("engineSettings.jsp?updateStatus=success");
}%>

<%@ include file="index-part3.jsp"%>

<%@ include file="include/re_jsp.jsp"%>

<%if(new Login().checkSessionState(session)){%>

<%
String logId = request.getParameter("logId");

String suiteId 	= request.getParameter("suiteId");
String groupId 	= request.getParameter("groupId");
String caseId 	= request.getParameter("caseId");
boolean singleLog = false;
ArrayList<TestLogs> testLogs = null;
if(caseId != null){
	testLogs = new TestLogsReport().getLogsByCaseIdAjax(Integer.valueOf(caseId), Long.valueOf(""+session.getAttribute(Keys.TEST_LOG_AJAX_REF)));
}else if(groupId != null){
	testLogs = new TestLogsReport().getLogsByGroupIdAjax(Integer.valueOf(groupId), Long.valueOf(""+session.getAttribute(Keys.TEST_LOG_AJAX_REF)));
}else if(suiteId != null){
	testLogs = new TestLogsReport().getLogsBySuiteIdAjax(Integer.valueOf(suiteId), Long.valueOf(""+session.getAttribute(Keys.TEST_LOG_AJAX_REF)));
}else{
	TestLogs testLog = new TestLogsReport().getLogsById(Long.valueOf(logId));
	singleLog = true;
%>
<html>
<head>
</head>
<body>
<div id="dt_page">
<div id="container">
<h1>Test Log Detail:</h1>

	<table border="0" cellpadding="3" width="100%" align="left" id="dt_table">
	<TR>
		<td align="left">Sequence No</td>
		<TD>:</TD>
		<td><%=General.getNotNullString(testLog.getSequenceNumber())%></td>		
	</tr>
	<TR>
		<td align="left">Log Time</td>
		<TD>:</TD>
		<td><%=General.getGuiDateTime(testLog.getLogTime())%></td>		
	</tr>
	<TR>
		<td align="left">Local Time</td>
		<TD>:</TD>
		<td><%=General.getGuiDateTime(testLog.getLocalTime())%></td>		
	</tr>
	<TR id="LEVEL_<%=testLog.getLogLevel()%>">
		<td align="left">Level</td>
		<TD>:</TD>
		<td><%=testLog.getLogLevel()%></td>		
	</tr>
	<TR>
		<td align="left">Class.Method</td>
		<TD>:</TD>
		<td><%=testLog.getClassName()%>.<%=testLog.getMethodName()%></td>		
	</tr>	
	<TR>
		<td align="left">Message</td>
		<TD>:</TD>
		<td><%=General.getThrowableString(testLog.getMessage())%></td>		
	</tr>
	<TR>
		<td align="left">Throwable</td>
		<TD>:</TD>
		<td><%=General.getThrowableString(testLog.getThrowable())%></td>		
	</tr>

	
</table>
<BR>
<h1></h1>
</div>
</div>
</body>
</html>
<%
}
if(!singleLog){
	long id = 0;
	//out.println("Session Status: "+session.getAttribute(Keys.SESSION_STATUS));
	//out.println("Session User: "+session.getAttribute(Keys.SESSION_USER_NAME));
	//out.println("Session ID: "+session.getId());
	//out.println("REF ID: "+session.getAttribute(Keys.TEST_LOG_AJAX_REF));
	for(int i = 0; i<testLogs.size(); i++){
		out.println("<div id=\"LEVEL_"+testLogs.get(i).getLogLevel()+"\">["+General.getLogLevelStr(testLogs.get(i).getLogLevel())+"] "+General.getGuiLogDateTime(testLogs.get(i).getLogTime())+" ["+General.getNotNullString(testLogs.get(i).getSequenceNumber())+"] "+testLogs.get(i).getClassName()+"."+testLogs.get(i).getMethodName()+"\n"+General.getThrowableString(testLogs.get(i).getMessage())+"\n"+General.getThrowableString(testLogs.get(i).getThrowable())+"</div>");
		id = testLogs.get(i).getId();
		}
	if(id != 0){
		session.setAttribute(Keys.TEST_LOG_AJAX_REF, id);
	}


}

%>

<%}else{response.sendRedirect("login.jsp");}%>



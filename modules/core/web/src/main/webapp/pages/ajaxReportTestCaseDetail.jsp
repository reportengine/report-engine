<%@ include file="include/re_jsp.jsp"%>
<html>
<head>
</head>
<body>
<div id="dt_page">
<div id="container">
<h1>Test Case Detail:</h1>
<%
int caseId = Integer.valueOf(request.getParameter("caseId"));
TestCase testCase = new TestCaseReport().getTestCaseDetail(caseId);
%>

	<table border="0" cellpadding="3" width="100%" align="left" id="dt_table">
	<TR>
		<td align="left" style="width: 70px; overflow-x: hidden;">Test Name</td>
		<TD style="width: 2px; overflow-x: hidden;">:</TD>
		<td><%=testCase.getTestName()%></td>		
	</tr>
	<TR>
		<td align="left" valign="top">Arguments</td>
		<TD valign="top">:</TD>
		<td><%=General.getHtml(testCase.getTestArguments())%></td>		
	</tr>
	<TR>
		<td align="left">Result</td>
		<TD>:</TD>
		<td><img width="16" height="16"  src='<%=General.HTML_ICONS_LOCATION%><%=testCase.getTestResult()%>.png' alt='<%=testCase.getTestResult()%>'></td>		
	</tr>
	<TR>
		<td align="left">Duration</td>
		<TD>:</TD>
		<td><%=General.getGuiDuration(testCase.getTestDuration())%></td>		
	</tr>	
	<TR>
		<td align="left" valign="top">Comments</td>
		<TD valign="top">:</TD>
		<td><%if(testCase.getTestComments() != null){%><div id="log_page"><pre><%=General.getThrowableString(testCase.getTestComments())%></pre></div><%}%></td>		
	</tr>


	
</table>
<BR>
<h1></h1>
</div>
</div>
</body>
</html>

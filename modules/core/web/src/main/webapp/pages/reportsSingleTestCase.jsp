<%@ include file="index-part1.jsp"%>

	<script type="text/javascript">
	$(document).ready(function(){
		$(".alink").colorbox({scrolling:"true", iframe:true,width:"85%", height:"100%"});
	});
	</script>
<%@ include file="index-part2.jsp"%>

<%
int caseId = Integer.valueOf(request.getParameter(Keys.TEST_CASE_ID));
TestCase testCase = new TestCaseReport().getTestCaseDetail(caseId);
String screenShot = null;
ArrayList<FileStorage> screenShotFiles = new TestCaseReport().getScreenShotImages(caseId);
if(screenShotFiles.size() == 0){
	screenShot = "-";
}
for(FileStorage screenShotFile : screenShotFiles){
	screenShot = "<a href=\"getScreenShotImage.jsp?imageId="+screenShotFile.getId()+"\" title=\"File Name: "+screenShotFile.getFileName()+"\" class=\"alink\">"+screenShotFile.getFileName()+"</a></BR>";
}
/* 
if(screenShot != null){
	screenShot = "<a href=\"getScreenShotImage.jsp?imageId="+screenShot+"\" title=\"File Name: "+screenShot+"\" class=\"ajax\">"+screenShot+"</a>";
}else{
	screenShot = "-";
}	 */

%>

 
<div id="dt_page">
<div id="container">

<h1>Test Case Report:</h1>

	<table border="0" cellpadding="3" width="100%" align="left" id="dt_table">
	
	<TR>
		<td align="left" style="width: 70px; overflow-x: hidden;">Test Suite Name</td>
		<TD style="width: 2px; overflow-x: hidden;">:</TD>
		<td><%=testCase.getTestSuiteName()%></td>		
	</tr>
	<TR>
		<td align="left" valign="top">Build</td>
		<TD valign="top">:</TD>
		<td><%=General.getHtml(testCase.getTestBuild())%></td>		
	</tr>
	<TR>
		<td align="left" width="12%">Test Group</td>
		<TD>:</TD>
		<td><%=testCase.getTestGroup()%></td>		
	</tr>
	<TR>
		<td align="left">Test Name</td>
		<TD>:</TD>
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
		<td align="left">Debug Log</td>
		<TD>:</TD>
		<td><a href="reportsTestLogs.jsp?<%=Keys.TEST_CASE_ID%>=<%=testCase.getId()%>"><img width="16" height="16"  src='<%=General.HTML_ICONS_LOCATION%>debug.png'  alt='Debug'></a></td>
	</tr>
	<TR>
		<td align="left">Resource Utilization</td>
		<TD>:</TD>
		<td><a href="chartCpuMemory.jsp?<%=Keys.TEST_CASE_ID%>=<%=testCase.getId()%>&<%=Keys.SUBMIT%>=<%=Keys.TEST_CASE%>"><img width="16" height="16"  src='<%=General.HTML_ICONS_LOCATION%>bar-chart-icon-16x16.png'  alt='Resource Usage'></a></td>
	</tr>
	<TR>
		<td align="left">Local Start Time</td>
		<TD>:</TD>
		<td><%=General.getGuiDateTime(testCase.getLocalStartTime())%></td>		
	</tr>
	<TR>
		<td align="left">Local End Time</td>
		<TD>:</TD>
		<td><%=General.getGuiDateTime(testCase.getLocalEndTime())%></td>		
	</tr>
	<TR>
		<td align="left">Remote Start Time</td>
		<TD>:</TD>
		<td><%=General.getGuiDateTime(testCase.getRemoteStartTime())%></td>		
	</tr>
	<TR>
		<td align="left">Remote End Time</td>
		<TD>:</TD>
		<td><%=General.getGuiDateTime(testCase.getRemoteEndTime())%></td>		
	</tr>
	<TR>
		<td align="left">Duration</td>
		<TD>:</TD>
		<td><%=General.getGuiDuration(testCase.getTestDuration())%></td>		
	</tr>	
	<TR>
		<td align="left">Screen Shot</td>
		<TD>:</TD>
		<td><%=screenShot%></td>		
	</tr>	
	<TR>
		<td align="left" valign="top">Comments</td>
		<TD valign="top">:</TD>
		<td><%if(testCase.getTestComments() != null){%><div id="log_page"><pre><%=General.getThrowableString(testCase.getTestComments())%></pre></div><%}%>		
	</tr>	
				
</table>
<BR>
<h1></h1>
<BR>
<table cellpadding="0" cellspacing="0" border="0" id="dt_table">
<tr>
<td><img width="16" height="16"  src='<%=General.HTML_ICONS_LOCATION%>Running.png'  alt='Running'></td><td valign="top">- Running&nbsp;</td>
<td><img width="16" height="16"  src='<%=General.HTML_ICONS_LOCATION%>Completed.png' alt='Passed'></td><td valign="top">- Passed&nbsp;</td> 
<td><img width="16" height="16"  src='<%=General.HTML_ICONS_LOCATION%>Failed.png'  alt='Failed'></td><td valign="top">- Failed&nbsp;</td>
<td><img width="16" height="16"  src='<%=General.HTML_ICONS_LOCATION%>Skipped.png'  alt='Skipped'></td><td valign="top">- Skipped&nbsp;</td>
<td><img width="16" height="16"  src='<%=General.HTML_ICONS_LOCATION%>debug.png'  alt='Debug'></td><td valign="top">- Debug Logs&nbsp;</td>
<td><img width="16" height="16"  src='<%=General.HTML_ICONS_LOCATION%>bar-chart-icon-16x16.png'  alt='Resource Utilization'></td><td valign="top">- Resource Utilization&nbsp;</td>

<tr>
</table>

</div>
</div>

<%@ include file="index-part3.jsp"%>


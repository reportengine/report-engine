<%@ include file="index-part1.jsp"%>

<script type="text/javascript">
	function callMeOnTableChange(){
		$(".ajax").colorbox({rel:'ajax', scrolling:"true", width:"70%", height:"90%"});
	};
</script>

<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		oTable = $('#dt_table').dataTable({
			"fnDrawCallback": function() {      
				callMeOnTableChange();
    			},
			"bJQueryUI": true,
			"bPaginate": true,
			"sPaginationType": "full_numbers",
			"aaSorting": [[0,'asc']],
			"aoColumns": [
			null,
			null,
			null,
			{ "bSortable": false },
			{ "sType": "date-gui" },
			{ "sType": "date-gui" },
			{ "sType": "duration-gui" }
			],
			"iDisplayLength":15
			});
} );			
</script>

<script type="text/javascript">
	$(document).ready(function(){
		$(".ajax").colorbox({rel:'ajax', scrolling:"true", width:"70%", height:"90%"});
	});
</script>
<%@ include file="index-part2.jsp"%>

<%
String suiteid = (String)request.getParameter(Keys.TEST_SUITE_ID);
String groupid = (String)request.getParameter(Keys.TEST_GROUP_ID);
String testStatus = (String)request.getParameter(Keys.TEST_STATUS);

%>

 
<div id="dt_page">
<div id="container">
<%
TestGroup testGroup = null;
if(groupid != null){
	testGroup = new TestGroupReport().getTestGroupDetails(Integer.valueOf(groupid));
	%>
	<h1>Test Case(s) Report: <font size="2">[<b>Test Suite: </b><I><%=testGroup.getTestSuiteName()%>,</I> <b>Build:</b> <I><%=testGroup.getTestBuild()%>, </I><b>Group:</b> <I><%=testGroup.getTestGroup()%></I>]</font></h1>
	<%
}else{
	TestSuite testSuite = new TestSuiteReport().getTestSuiteDetails(Integer.valueOf(suiteid));
	%>
	<h1>Test Case(s) Report: <font size="2">[<b>Test Suite: </b><I><%=testSuite.getTestSuiteName()%>,</I> <b>Build:</b> <I><%=testSuite.getTestBuild()%></I>]</font></h1>
	<%
}

%>

<table cellpadding="0" cellspacing="0" border="0" class="display" id="dt_table">
	<thead>
		<tr>
            		<th>S.No</th>
            		<th>Test Name</th>
		        <th>Arguments</th>
            		<th>Result</th>	
            		<th>Start Time</th>
            		<th>End Time</th>
            		<th>Duration</th>
		</tr>
	</thead>
	<tbody>

<%
		ArrayList<TestCase> testCases = null;
		if(testStatus != null){
			if(suiteid != null){
				testCases = new TestCaseReport().getCasesBySuiteIdAndStatus(Integer.valueOf(suiteid), testStatus);
			}else if(groupid != null){
				testCases = new TestCaseReport().getCasesByGroupIdAndStatus(Integer.valueOf(groupid), testStatus);
			}
		}else{
			testCases = new TestCaseReport().getCases(Integer.valueOf(suiteid), Integer.valueOf(groupid));
		}
		StringBuilder content = new StringBuilder();
		for(int i = 0; i<testCases.size(); i++){	
			content.append("<tr>");
			content.append("<td>").append(i+1).append("</td><td><a class=\"alink\" href=\"reportsSingleTestCase.jsp?").append(Keys.TEST_CASE_ID).append("=").append(testCases.get(i).getId()).append("\">").append(testCases.get(i).getTestName()).append("</a></td>");
			content.append("<td>").append(testCases.get(i).getTestArguments()).append("</td>");
			content.append("<td align=\"center\">")
				.append("<a href=\"ajaxReportTestCaseDetail.jsp?").append(Keys.TEST_CASE_ID).append("=").append(testCases.get(i).getId()).append("\" class=\"ajax\"><img width=\"16\" height=\"16\"  src='").append(General.HTML_ICONS_LOCATION+testCases.get(i).getTestResult()).append(".png' alt='").append(testCases.get(i).getTestResult()).append("'></a>")
				.append("&nbsp;<a href=\"reportsTestLogs.jsp?").append(Keys.TEST_CASE_ID).append("=").append(testCases.get(i).getId()).append("\"><img width=\"16\" height=\"16\"  src='").append(General.HTML_ICONS_LOCATION).append("debug.png'  alt='Debug'></a>")
				.append("&nbsp;<a href=\"chartCpuMemory.jsp?").append(Keys.TEST_CASE_ID).append("=").append(testCases.get(i).getId()).append("&").append(Keys.SUBMIT).append("=").append(Keys.TEST_CASE).append("\"><img width=\"16\" height=\"16\"  src='").append(General.HTML_ICONS_LOCATION).append("bar-chart-icon-16x16.png'  alt='Resource Usage'></a>")
				.append("</td>");
			content.append("<td align=\"center\">").append(General.getGuiDateTime(testCases.get(i).getLocalStartTime())).append("</td>");
			content.append("<td align=\"center\">").append(General.getGuiDateTime(testCases.get(i).getLocalEndTime())).append("</td>");
			content.append("<td align=\"center\">").append(General.getGuiDuration(testCases.get(i).getTestDuration())).append("</td>");
			content.append("</tr>");		
		}
		out.println(content.toString());
%>

    </tbody>
</table>

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


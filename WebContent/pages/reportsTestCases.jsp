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
String suiteid = (String)request.getParameter("suiteid");
String groupid = (String)request.getParameter("groupid");
String testStatus = (String)request.getParameter("teststatus");

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
		
		for(int i = 0; i<testCases.size(); i++){	
			out.println("<tr><td>"+(i+1)+"</td><td><a href=\"reportsSingleTestCase.jsp?caseId="+testCases.get(i).getId()+"\">"+testCases.get(i).getTestName()+"</a></td><td>"+testCases.get(i).getTestArguments()+"</td><td align=\"center\"><a href=\"ajaxReportTestCaseDetail.jsp?caseId="+testCases.get(i).getId()+"\" class=\"ajax\"><img width=\"16\" height=\"16\"  src='../images/icons/"+testCases.get(i).getTestResult()+".png' alt='"+testCases.get(i).getTestResult()+"'></a>&nbsp;<a href=\"reportsTestLogs.jsp?caseId="+testCases.get(i).getId()+"\"><img width=\"16\" height=\"16\"  src='../images/icons/debug.png'  alt='Debug'></a></td><td align=\"center\">"+General.getGuiDateTime(testCases.get(i).getLocalStartTime())+"</td><td align=\"center\">"+General.getGuiDateTime(testCases.get(i).getLocalEndTime())+"</td><td align=\"center\">"+General.getGuiDuration(testCases.get(i).getTestDuration())+"</td></tr>");		
		}
%>

    </tbody>
</table>

<BR>
<table cellpadding="0" cellspacing="0" border="0" id="dt_table">
<tr>
<td><img width="16" height="16"  src='../images/icons/Running.png'  alt='Running'></td><td valign="top">- Running&nbsp;</td>
<td><img width="16" height="16"  src='../images/icons/Completed.png' alt='Passed'></td><td valign="top">- Passed&nbsp;</td> 
<td><img width="16" height="16"  src='../images/icons/Failed.png'  alt='Failed'></td><td valign="top">- Failed&nbsp;</td>
<td><img width="16" height="16"  src='../images/icons/Skipped.png'  alt='Skipped'></td><td valign="top">- Skipped&nbsp;</td>
<td><img width="16" height="16"  src='../images/icons/debug.png'  alt='Debug N/A'></td><td valign="top">- Debug Logs&nbsp;</td>
<tr>
</table>

</div>
</div>

<%@ include file="index-part3.jsp"%>


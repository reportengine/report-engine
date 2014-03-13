<%@ include file="index-part1.jsp"%>

<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		oTable = $('#dt_table').dataTable({
			"bJQueryUI": true,
			"bPaginate": true,
			"sPaginationType": "full_numbers",
			"aaSorting": [[0,'asc']],
			"aoColumns": [
			null,
			null,
			{ "sType": "num-html" },
			{ "sType": "num-html" },
			{ "sType": "num-html" },
			{ "sType": "num-html" },
			{ "bSortable": false }
			],
			"iDisplayLength":15
			});
} );			
</script>
<script type="text/javascript">
	$(document).ready(function(){
		$(".ajax").colorbox({rel:'ajax', scrolling:"true",width:"85%", height:"100%"});
	});
</script>

<%@ include file="index-part2.jsp"%>

<%
int testSuitId = Integer.valueOf(request.getParameter(Keys.TEST_SUITE_ID));
TestSuite testSuite = new TestSuiteReport().getTestSuiteDetails(testSuitId);
%>

<div id="dt_page">
<div id="container">

<h1>Test Group(s) Report: <font size="2">[<b>Test Suite: </b><I><%=testSuite.getTestSuiteName()%>,</I> <b>Build:</b> <I><%=testSuite.getTestBuild()%></I>]</font></h1>
<table cellpadding="0" cellspacing="0" border="0" class="display" id="dt_table">
	<thead>
		<tr>
			<th>S.No</th>
			<th>Group Name</th>
			<th>Total</th>
			<th>Passed</th>
			<th>Failed</th>
			<th>Skipped</th>
			<th>Debug Log</th>
		</tr>
	</thead>
	<tbody>

	<%
		ArrayList<TestGroup> testGroups= new TestGroupReport().getGroups(testSuitId);
		StringBuffer passedStr 	= new StringBuffer();
		StringBuffer failedStr 	= new StringBuffer();
		StringBuffer skippedStr = new StringBuffer();
		for(int i = 0; i<testGroups.size(); i++){	
			passedStr 		= new StringBuffer();
			failedStr 		= new StringBuffer();
			skippedStr 		= new StringBuffer();
			if(testGroups.get(i).getPassedCases()!=0){
				passedStr.append("<a href=\"reportsTestCases.jsp?").append(Keys.TEST_GROUP_ID).append("=").append(testGroups.get(i).getId()).append("&").append(Keys.TEST_STATUS).append("=").append(TestCase.PASSED).append("\"><font color=\"green\"><b>").append(testGroups.get(i).getPassedCases()).append("</b></font></a>");
			}else{
				passedStr.append("<font color=\"green\"><b>").append(testGroups.get(i).getPassedCases()).append("</b></font>");
			}
			
			if(testGroups.get(i).getFailedCases()!=0){
				failedStr.append("<a href=\"reportsTestCases.jsp?").append(Keys.TEST_GROUP_ID).append("=").append(testGroups.get(i).getId()).append("&").append(Keys.TEST_STATUS).append("=").append(TestCase.FAILED).append("\"><font color=\"red\"><b>").append(testGroups.get(i).getFailedCases()).append("</b></font></a>");
			}else{
				failedStr.append("<font color=\"red\"><b>").append(testGroups.get(i).getFailedCases()).append("</b></font>");
			}

			if(testGroups.get(i).getSkippedCases()!=0){
				skippedStr.append("<a href=\"reportsTestCases.jsp?").append(Keys.TEST_GROUP_ID).append("=").append(testGroups.get(i).getId()).append("&").append(Keys.TEST_STATUS).append("=").append(TestCase.SKIPPED).append("\"><font color=\"brown\"><b>").append(testGroups.get(i).getSkippedCases()).append("</b></font></a>");
			}else{
				skippedStr.append("<font color=\"brown\"><b>").append(testGroups.get(i).getSkippedCases()).append("</b></font>");
			}
			
			out.println("<tr><td>"+(i+1)+"</td><td><a class=\"alink\" href=\"reportsTestCases.jsp?"+Keys.TEST_SUITE_ID+"="+testGroups.get(i).getTestSuiteId()+"&"+Keys.TEST_GROUP_ID+"="+testGroups.get(i).getId()+"\">"+testGroups.get(i).getTestGroup()+"</a></td><td align=\"center\"><b>"+testGroups.get(i).getTotalCases()+"</b></td><td align=\"center\">"+passedStr.toString()+"</td></td><td align=\"center\">"+failedStr.toString()+"</td><td align=\"center\">"+skippedStr.toString()+"</td><td align=\"center\"><a href=\"reportsTestLogs.jsp?"+Keys.TEST_GROUP_ID+"="+testGroups.get(i).getId()+"\"><img width=\"16\" height=\"16\"  src='"+General.HTML_ICONS_LOCATION+"debug.png'  alt='Debug'></a></td></tr>");		
		}

	%>
    </tbody>
</table>

</div>
</div>

<%@ include file="index-part3.jsp"%>


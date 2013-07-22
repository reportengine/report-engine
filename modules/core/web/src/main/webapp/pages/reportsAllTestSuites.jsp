

<%@page import="com.redhat.reportengine.server.report.email.EmailGroupReport"%>
<%
String buttonName 	= (String)request.getParameter(Keys.SUBMIT);
String action		= (String)request.getParameter(Keys.REPORT_ACTION);
int numberOfRows     	= 15; //Default count for show all reports
String subTitle		= "ALL REPORTS";

if(action != null){
	numberOfRows      = 15; //Number of row(s) limit assigned here
	if(Keys.ORDER_BY_LOCAL_START_TIME.equalsIgnoreCase(action)){
		subTitle	  = "TOP "+numberOfRows+" LATEST RUNS";
	}else{
		subTitle	  = "TOP "+numberOfRows+" "+action.replaceAll("_", " ").toUpperCase();
	}	
}

if(buttonName != null){
%>
<%@ include file="include/re_jsp.jsp"%>
<%
	if(buttonName.equalsIgnoreCase("Delete")){
		action = (String)request.getParameter(Keys.REPORT_ACTION);		
		new PurgeLogs().deleteSuiteLogs(request, response);
		if(action != null){
			response.sendRedirect("reportsAllTestSuites.jsp?"+Keys.REPORT_ACTION+"="+action);	
		}else{
			response.sendRedirect("reportsAllTestSuites.jsp");
		}
	}
}else{

%>
<%@ include file="index-part1.jsp"%>

<script type="text/javascript">
	function callMeOnTableChange(){
		
		$(".ajax").colorbox({rel:'ajax', scrolling:"true",width:"65%", height:"92%", onLoad:function(){ callMeOnTableChange();}});

		//Delete Confirmation,
		$(function() {
			$( "#deleteDialog" ).dialog({
				autoOpen: false,
				resizable: false,
				height:140,
				modal: true,
				hide: "explode",
				buttons: {
					"Yes": function() {
						$(this).dialog('close'); 						
						 $('<input />').attr('type', 'hidden')
            					.attr('name', 'SUBMIT')
            					.attr('value', 'Delete')
            					.appendTo('#manage-reports');
						$('#manage-reports').submit();
						$( "input[Value=Delete]" ).button( "option", "disabled", true );
					},
					"No": function() {
						$( this ).dialog( "close" );
						return false;
					}
				}
			});

		});

		//Enable Delete Button if we click radio
		$('input[type=checkbox]').click(function() { 
			//$("input[Value=Delete]").removeAttr("disabled");
			$( "input[Value=Delete]" ).button( "option", "disabled", false );
		});

		//Unselect All radio buttons
		$('input[type=checkbox]').prop('checked', false);	


		//Call Confirmation once clicked Delete Button
		$("input[Value=Delete]").click(function() { 
			$("#deleteDialog").dialog('open');	 
			return false;
		});

		//Disable Delete Button on load
		//$("input[Value=Delete]").attr("disabled", "disabled");
		$( "input[Value=Delete]" ).button( "option", "disabled", true );

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
			"aaSorting": [[1,'asc']],
			"aoColumns": [
			{ "bSortable": false },
			null,
			null,
			{ "bSortable": false },
			{ "sType": "test-total" },
			{ "sType": "test-total" },
			{ "sType": "test-total" },
			{ "sType": "test-total" },
			null,
			{ "sType": "date-gui" },
			{ "sType": "date-gui" },
			{ "sType": "duration-gui" }
			],
			"iDisplayLength":<%=numberOfRows%>
			});
} );			
</script>
<script type="text/javascript">
	$(document).ready(function(){
		callMeOnTableChange();
	});
</script>

<%@ include file="index-part2.jsp"%>


 
<div id="dt_page">
<div id="container">

<h1>Test Suite(s) Report: <font size="2"><B><%=subTitle%></B></font></h1>
<form method="post" id="manage-reports" name="manageReports" action="reportsAllTestSuites.jsp" >
<%
	if(action != null){
		%>
		<input type="hidden" name="<%=Keys.REPORT_ACTION%>" value="<%=action%>">	
		<%
	}
%>

<table cellpadding="0" cellspacing="0" border="0" class="display" id="dt_table">
	<thead>
		<tr>
			<th></th>	
			<th>S.No</th>
			<th>Test Suite Name</th>
			<th>Status</th>
			<th>Total</th>
			<th>Passed</th>
			<th>Failed</th>
			<th>Skipped</th>
			<th>Build</th>
			<th>Start Time</th>
			<th>End Time</th>
			<th>Duration</th>
		</tr>
	</thead>
	<tbody>

	<%
		ArrayList<TestSuite> testSuites= null;
		if(action != null){
			testSuites = new TestSuiteReport().getTopNTestSuites(numberOfRows, action);
		}else{
			testSuites = new TestSuiteTable().getTopNByRefIds();
		}
		StringBuffer passedStr 	= new StringBuffer();
		StringBuffer failedStr 	= new StringBuffer();
		StringBuffer skippedStr = new StringBuffer();
		for(int i = 0; i<testSuites.size(); i++){
			passedStr 		= new StringBuffer();
			failedStr 		= new StringBuffer();
			skippedStr 		= new StringBuffer();
			if(testSuites.get(i).getPassedCases()!=0){
				passedStr.append("<a href=\"reportsTestCases.jsp?suiteid=").append(testSuites.get(i).getId()).append("&teststatus=").append(TestCase.PASSED).append("\"><font color=\"green\"><b>").append(testSuites.get(i).getPassedCases()).append("</b></font></a>");
			}else{
				passedStr.append("<font color=\"green\"><b>").append(testSuites.get(i).getPassedCases()).append("</b></font>");
			}
			
			if(testSuites.get(i).getFailedCases()!=0){
				failedStr.append("<a href=\"reportsTestCases.jsp?suiteid=").append(testSuites.get(i).getId()).append("&teststatus=").append(TestCase.FAILED).append("\"><font color=\"red\"><b>").append(testSuites.get(i).getFailedCases()).append("</b></font></a>");
			}else{
				failedStr.append("<font color=\"red\"><b>").append(testSuites.get(i).getFailedCases()).append("</b></font>");
			}

			if(testSuites.get(i).getSkippedCases()!=0){
				skippedStr.append("<a href=\"reportsTestCases.jsp?suiteid=").append(testSuites.get(i).getId()).append("&teststatus=").append(TestCase.SKIPPED).append("\"><font color=\"brown\"><b>").append(testSuites.get(i).getSkippedCases()).append("</b></font></a>");
			}else{
				skippedStr.append("<font color=\"brown\"><b>").append(testSuites.get(i).getSkippedCases()).append("</b></font>");
			}
			
			out.println("<tr><td align=\"center\"><input type=\"checkbox\" name=\""+Keys.DELETE_REPORTS+"\" value=\""+testSuites.get(i).getId()+"\"></td><td>"+(i+1)+"</td><td><a class=\"alink\" href=\"reportsTestGroups.jsp?id="+testSuites.get(i).getId()+"\">"+testSuites.get(i).getTestSuiteName()+"</a></td><td align=\"center\"><a href=\"ajaxReportTestSuite.jsp?id="+testSuites.get(i).getId()+"\" class='ajax'><img width=\"16\" height=\"16\"  src='"+General.HTML_ICONS_LOCATION+testSuites.get(i).getTestStatus()+".png' alt='"+testSuites.get(i).getTestStatus()+"'></a>&nbsp;<a href=\"reportsTestLogs.jsp?suiteId="+testSuites.get(i).getId()+"\"><img width=\"16\" height=\"16\"  src='"+General.HTML_ICONS_LOCATION+"debug.png'  alt='Debug'></a></td><td><b>"+testSuites.get(i).getTotalCases()+"</b>"+General.getColor(testSuites.get(i).getTotalChanges(), true)+"</td><td>"+passedStr.toString()+General.getColor(testSuites.get(i).getPassedChanges(), true)+"</td><td>"+failedStr.toString()+General.getColor(testSuites.get(i).getFailedChanges(), false)+"</td><td>"+skippedStr.toString()+General.getColor(testSuites.get(i).getSkippedChanges(), false)+"</td><td align=\"center\">"+General.getBuildDetails(testSuites.get(i).getTestBuild())+"</td><td align=\"center\">"+General.getGuiDateTime(testSuites.get(i).getLocalStartTime())+"</td><td align=\"center\">"+General.getGuiDateTime(testSuites.get(i).getLocalEndTime())+"</td><td align=\"center\">"+General.getGuiDuration(testSuites.get(i).getTestDuration())+"</td></tr>");	
		}

	%>
    </tbody>
</table>

<BR>
<div class="jbutton">
	<input type="submit" name="SUBMIT" value="Delete" style="width:80px;">
</div>
</form>
<BR>
<BR>
<table cellpadding="0" cellspacing="0" border="0" id="dt_table">
<tr>
<td><img width="16" height="16"  src='<%=General.HTML_ICONS_LOCATION%>Running.png'  alt='Running'></td><td valign="top">- Running&nbsp;</td>
<td><img width="16" height="16"  src='<%=General.HTML_ICONS_LOCATION%>Completed.png' alt='Completed'></td><td valign="top">- Completed&nbsp;</td> 
<td><img width="16" height="16"  src='<%=General.HTML_ICONS_LOCATION%>Failed.png'  alt='Failed'></td><td valign="top">- Failed&nbsp;</td>
<td><img width="16" height="16"  src='<%=General.HTML_ICONS_LOCATION%>NoStatus.png'  alt='Status N/A'></td><td valign="top">- Status Not Available&nbsp;</td>
<td><img width="16" height="16"  src='<%=General.HTML_ICONS_LOCATION%>debug.png'  alt='Debug N/A'></td><td valign="top">- Debug Logs&nbsp;</td>
<tr>
</table>

<div id="deleteDialog" title="Delete Confirmation!">
	<p>&nbsp;You are about to delete this item.<br> 
	&nbsp;It cannot be restored at a later time! Continue?</p>
</div>

</div>
</div>
<%@ include file="index-part3.jsp"%>
<%}%>


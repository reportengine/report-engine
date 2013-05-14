

<%
String buttonName 	= (String)request.getParameter("SUBMIT");

if(buttonName != null){
%>
<%@ include file="include/re_jsp.jsp"%>


<%
	if(buttonName.equalsIgnoreCase("Delete")){
		new ManageReportGroup().deleteReportGroupById(Integer.parseInt(request.getParameter(Keys.REPORT_EMAIL_GROUP_ID)));
		response.sendRedirect("emailReport.jsp");
	}else if(buttonName.equalsIgnoreCase("Run Now")){
		new ManageReportGroup().runReportNow(Integer.parseInt(request.getParameter(Keys.REPORT_EMAIL_GROUP_ID)));
		response.sendRedirect("emailReport.jsp");
	}else if(buttonName.equalsIgnoreCase("Edit")){
		int groupId = Integer.valueOf(request.getParameter(Keys.REPORT_EMAIL_GROUP_ID));
		ReportGroup reportGroup = new ManageReportGroup().getReportGroupById(groupId);
%>		
		<%@ include file="index-part1.jsp"%>
		
		<script type="text/javascript">
		
		$(document).ready(function(){
			$("#<%=Keys.REPORT_EMAIL_GROUP_REFERENCE%>").multiselect({
				//show: ["bounce", 300],
				hide: ["explode", 200]		
			}).multiselectfilter();
		});
</script>

		<%@ include file="index-part2.jsp"%>

		<div id="dt_page">
		<div id="container">
		<h1>Edit Email Report Group:</h1>

		<form method="post" name="jobDetails" action="emailReport.jsp"> 
		<table border="0" cellpadding="3" align="left" id="dt_table">
		
		<tr>
			<td align="left">Group Name</td>
			<td style="width: 2px; overflow-x: hidden;">:</td>
			<td colspan="2"><input type="hidden" name="<%=Keys.REPORT_EMAIL_GROUP_ID%>" value="<%=reportGroup.getId()%>"><input type="text" name="<%=Keys.REPORT_EMAIL_GROUP_NAME%>" size="8" value="<%=reportGroup.getGroupName()%>"  style="width:320px;"></td>
		</tr>
		
			
		<tr>
			<td align="left">Test Reference(s)</td>
			<td>:</td>
			<td colspan="2"><select id="<%=Keys.REPORT_EMAIL_GROUP_REFERENCE%>" name="<%=Keys.REPORT_EMAIL_GROUP_REFERENCE%>" multiple="multiple">
			<%
				ArrayList<TestReference> testReferences = new ManageTestReference().getAllTestReference();
				for(TestReference testReference : testReferences){
					String selectionStatus = "";
					for(ReportGroupReference reportGroupReference: reportGroup.getReportGroupReference()){
						if(reportGroupReference.getTestReferenceId() == testReference.getId()){
							selectionStatus = "selected";
							break;
						}
					}
					out.println("<option value=\""+testReference.getId()+"\" "+selectionStatus+">"+testReference.getTestReference()+"</option>");
				}
			%>
  				</select>
  			</td>	
		</tr>

		<tr>
			<td align="left">Test Suite Group (Include)</td>
			<td valign="top">:</td>
			<td colspan="2"><input type="checkbox" name="<%=Keys.REPORT_EMAIL_GROUP_GROUP_ENABLED%>" id="<%=Keys.REPORT_EMAIL_GROUP_GROUP_ENABLED%>" value="groupEnabled" 
			<%if(reportGroup.isTestSuiteGroupEnabled()){%> checked <%}%> ></td>	
		</tr>

		<tr>
			<td align="left" valign="top">Email To</td>
			<td valign="top">:</td>
			<td colspan="2"><textarea name="<%=Keys.REPORT_EMAIL_GROUP_EMAIL_TO%>" id="<%=Keys.REPORT_EMAIL_GROUP_EMAIL_TO%>" rows="4" cols="25" style="width:320px;"><%=reportGroup.getEmailTo()%></textarea></td>	
		</tr>
				
		<tr>
			<td align="left" valign="top">Email Cc</td>
			<td valign="top">:</td>
			<td colspan="2"><textarea name="<%=Keys.REPORT_EMAIL_GROUP_EMAIL_CC%>" id="<%=Keys.REPORT_EMAIL_GROUP_EMAIL_CC%>" rows="4" cols="25" style="width:320px;"><%=reportGroup.getEmailCc()%></textarea></td>	
		</tr>

		<tr> <td colspan="4" align="right"> <div class="jbutton"><input type="submit" name="SUBMIT" value="Update" style="width:80px;"></div></td></tr>
		</table>
		
		
		</form>



		</div>
		</div>
		<%@ include file="index-part3.jsp"%>
<%
	//new ManageReportGroup().addNewGroup(request, response);
	}else if(buttonName.equalsIgnoreCase("Update")){
		new ManageReportGroup().addUpdateGroup(request, response, false);
		response.sendRedirect("emailReport.jsp");
	}else if(buttonName.equalsIgnoreCase("Submit")){
		new ManageReportGroup().addUpdateGroup(request, response, true);
		response.sendRedirect("emailReport.jsp");
	}else if(buttonName.equalsIgnoreCase("Add")){
%>		
		<%@ include file="index-part1.jsp"%>
		
		<script type="text/javascript">
		
		$(document).ready(function(){
			$("#<%=Keys.REPORT_EMAIL_GROUP_REFERENCE%>").multiselect({
				//show: ["bounce", 300],
				hide: ["explode", 200]		
			}).multiselectfilter();
		});
</script>

		<%@ include file="index-part2.jsp"%>

		<div id="dt_page">
		<div id="container">
		<h1>Add New Email Report Group:</h1>

		<form method="post" name="jobDetails" action="emailReport.jsp"> 
		<table border="0" cellpadding="3" align="left" id="dt_table">
	
		<tr>
			<td align="left">Group Name</td>
			<td style="width: 2px; overflow-x: hidden;">:</td>
			<td colspan="2"><input type="text" name="<%=Keys.REPORT_EMAIL_GROUP_NAME%>" size="8" value=""  style="width:320px;"></td>
		</tr>
		
			
		<tr>
			<td align="left">Test Reference(s)</td>
			<td>:</td>
			<td colspan="2"><select id="<%=Keys.REPORT_EMAIL_GROUP_REFERENCE%>" name="<%=Keys.REPORT_EMAIL_GROUP_REFERENCE%>" multiple="multiple">
			<%
				ArrayList<TestReference> testReferences = new ManageTestReference().getAllTestReference();
				for(TestReference testReference : testReferences){
					out.println("<option value=\""+testReference.getId()+"\">"+testReference.getTestReference()+"</option>");
				}
			%>
  				</select>
  			</td>	
		</tr>

		<tr>
			<td align="left">Test Suite Group (Include)</td>
			<td valign="top">:</td>
			<td colspan="2"><input type="checkbox" name="<%=Keys.REPORT_EMAIL_GROUP_GROUP_ENABLED%>" id="<%=Keys.REPORT_EMAIL_GROUP_GROUP_ENABLED%>" value="groupEnabled" ></td>	
		</tr>
		
		
		<tr>
			<td align="left" valign="top">Email To</td>
			<td valign="top">:</td>
			<td colspan="2"><textarea name="<%=Keys.REPORT_EMAIL_GROUP_EMAIL_TO%>" id="<%=Keys.REPORT_EMAIL_GROUP_EMAIL_TO%>" rows="4" cols="25" style="width:320px;"></textarea></td>	
		</tr>
				
		<tr>
			<td align="left" valign="top">Email Cc</td>
			<td valign="top">:</td>
			<td colspan="2"><textarea name="<%=Keys.REPORT_EMAIL_GROUP_EMAIL_CC%>" id="<%=Keys.REPORT_EMAIL_GROUP_EMAIL_CC%>" rows="4" cols="25" style="width:320px;"></textarea></td>	
		</tr>

		<tr> <td colspan="4" align="right"> <div class="jbutton"><input type="submit" name="SUBMIT" value="Submit" style="width:80px;"></div></td></tr>
		</table>
		
		
		</form>



		</div>
		</div>
		<%@ include file="index-part3.jsp"%>
<%
	}
}else{

%>
<%@ include file="index-part1.jsp"%>

<script type="text/javascript">
	function callMeOnTableChange(){
		
		$(".ajax").colorbox({rel:'ajax', scrolling:"true",width:"40%", height:"70%"});

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
		$('input[type=radio]').click(function() { 
			//$("input[Value=Delete]").removeAttr("disabled");
			$( "input[Value=Delete]" ).button( "option", "disabled", false );
			$( "input[Value=Edit]" ).button( "option", "disabled", false );
			$( "input[Value='Run Now']" ).button( "option", "disabled", false );
			
		});

		//Unselect All radio buttons
		$('input[type=radio]').prop('checked', false);	


		//Call Confirmation once clicked Delete Button
		$("input[Value=Delete]").click(function() { 
			$("#deleteDialog").dialog('open');	 
			return false;
		});

		//Disable Delete Button on load
		$( "input[Value=Edit]" ).button( "option", "disabled", true );
		$( "input[Value=Delete]" ).button( "option", "disabled", true );
		$( "input[Value=Enable]" ).button( "option", "disabled", true );
		$( "input[Value=Disable]" ).button( "option", "disabled", true );
		$( "input[Value='Run Now']" ).button( "option", "disabled", true );
		
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
			null,
			null,
			null,
			null
			],
			"iDisplayLength":15
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

<h1>Groups: Email Report</h1>
<form method="post" id="manage-reports" name="manageReports" action="emailReport.jsp" >
<table cellpadding="0" cellspacing="0" border="0" class="display" id="dt_table">
	<thead>
		<tr>
			<th></th>	
			<th>S.No</th>
			<th>Email Group Name</th>
			<th>Test Suite Group</th>
			<th>Email To</th>
            <th>Email Cc</th>		
			<th>Creation Time</th>
		</tr>
	</thead>
	<tbody>

	<%
		ArrayList<ReportGroup> reportGroups = new ManageReportGroup().getAllReportGroup();
		
		for(int i = 0; i<reportGroups.size(); i++){			
			out.println("<tr><td align=\"center\"><input type=\"radio\" name=\""+Keys.REPORT_EMAIL_GROUP_ID+"\" value=\""+reportGroups.get(i).getId()+"\"></td><td>"+(i+1)+"</td><td align=\"left\">"+reportGroups.get(i).getGroupName()+"</td><td align=\"center\"><img width=\"16\" height=\"16\"  src='../images/icons/"+reportGroups.get(i).isTestSuiteGroupEnabled()+".png' alt='"+reportGroups.get(i).isTestSuiteGroupEnabled()+"'></td><td align=\"left\">"+reportGroups.get(i).getEmailTo()+"</td><td align=\"left\">"+General.getNotNullString(reportGroups.get(i).getEmailCc())+"</td><td align=\"left\">"+General.getGuiDateTime(reportGroups.get(i).getCreationTime())+"</td></tr>");	
		}

	%>
    </tbody>
</table>

<BR>
<div class="jbutton">
	<input type="submit" name="SUBMIT" value="Add" style="width:80px;"> <input type="submit" name="SUBMIT" value="Edit" style="width:80px;"> <input type="submit" name="SUBMIT" value="Delete" style="width:80px;"> <input type="submit" name="SUBMIT" value="Run Now" style="width:80px;">
</div>
</form>
<BR>
<BR>
<table cellpadding="0" cellspacing="0" border="0" id="dt_table">
<tr>
<td><img width="16" height="16"  src='../images/icons/true.png'  alt='Enabled'></td><td valign="top">- Enabled&nbsp;</td>
<td><img width="16" height="16"  src='../images/icons/false.png' alt='Disabled'></td><td valign="top">- Disabled&nbsp;</td> 
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


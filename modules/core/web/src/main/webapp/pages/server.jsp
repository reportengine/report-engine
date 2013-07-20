<%@page import="com.redhat.reportengine.server.dbdata.ServerTable"%>
<%@ include file="include/re_jsp.jsp"%>

<%
String buttonName 	= (String)request.getParameter("SUBMIT");

if(buttonName != null){
%>
<%@ include file="include/re_jsp.jsp"%>


<%
	if(buttonName.equalsIgnoreCase("Delete")){
		new ManageServer().remove(request, response);
		response.sendRedirect("server.jsp");
	}else if(buttonName.equalsIgnoreCase("Update Now")){
		new ManageServer().updateStatusNow(request, response);
		response.sendRedirect("server.jsp");
	}else if(buttonName.equalsIgnoreCase("Edit")){
		int serverId = Integer.valueOf(request.getParameter(Keys.SERVER_ID));
		Server server = new ServerTable().getById(serverId);
%>		
		<%@ include file="index-part1.jsp"%>
		
		<%@ include file="index-part2.jsp"%>

		<div id="dt_page">
		<div id="container">
		<h1>Edit Server:</h1>

		<form method="post" name="serverDetails" action="server.jsp"> 
		<table border="0" cellpadding="3" align="left" id="dt_table">
		
		<tr>
			<td align="left">Name</td>
			<td style="width: 2px; overflow-x: hidden;">:</td>
			<td colspan="2"><input type="hidden" name="<%=Keys.SERVER_ID%>" value="<%=server.getId()%>"><input type="text" name="<%=Keys.SERVER_NAME%>" size="8" value="<%=server.getName()%>"  style="width:320px;"></td>
		</tr>
		
		<tr>
			<td align="left">Host/IP</td>
			<td style="width: 2px; overflow-x: hidden;">:</td>
			<td colspan="2"><input type="text" name="<%=Keys.SERVER_HOSTIP%>" size="8" value="<%=server.getHostIp()%>"  style="width:320px;"></td>
		</tr>
		
		<tr>
			<td align="left">Agent Port</td>
			<td style="width: 2px; overflow-x: hidden;">:</td>
			<td colspan="2"><input type="text" name="<%=Keys.SERVER_AGENT_PORT%>" size="8" value="<%=server.getAgentPort()%>"  style="width:120px;"></td>
		</tr>
		
		<tr>
			<td align="left">Update Interval(Minutes)</td>
			<td style="width: 2px; overflow-x: hidden;">:</td>
			<td colspan="2"><input type="text" name="<%=Keys.SERVER_UPDATE_INTERVAL%>" size="8" value="<%=server.getUpdateInterval()/60%>"  style="width:120px;"></td>
		</tr>

		<tr> <td colspan="4" align="right"> <div class="jbutton"><input type="submit" name="SUBMIT" value="Update" style="width:80px;"></div></td></tr>
		</table>
		
		
		</form>



		</div>
		</div>
		<%@ include file="index-part3.jsp"%>
<%
	}else if(buttonName.equalsIgnoreCase("Update")){
		new ManageServer().update(request, response);
		response.sendRedirect("server.jsp");
	}else if(buttonName.equalsIgnoreCase("Submit")){
		new ManageServer().add(request, response);
		response.sendRedirect("server.jsp");
	}else if(buttonName.equalsIgnoreCase("Add")){
%>		
		<%@ include file="index-part1.jsp"%>
		
		<%@ include file="index-part2.jsp"%>

		<div id="dt_page">
		<div id="container">
		<h1>Add New Server:</h1>

		<form method="post" name="serverDetail" action="server.jsp"> 
		<table border="0" cellpadding="3" align="left" id="dt_table">
	
		<tr>
			<td align="left">Name</td>
			<td style="width: 2px; overflow-x: hidden;">:</td>
			<td colspan="2"><input type="text" name="<%=Keys.SERVER_NAME%>" size="8" value=""  style="width:320px;"></td>
		</tr>
		
		<tr>
			<td align="left">Host/IP</td>
			<td style="width: 2px; overflow-x: hidden;">:</td>
			<td colspan="2"><input type="text" name="<%=Keys.SERVER_HOSTIP%>" size="8" value=""  style="width:320px;"></td>
		</tr>
		
		<tr>
			<td align="left">Agent Port</td>
			<td style="width: 2px; overflow-x: hidden;">:</td>
			<td colspan="2"><input type="text" name="<%=Keys.SERVER_AGENT_PORT%>" size="8" value=""  style="width:120px;"></td>
		</tr>
		
		<tr>
			<td align="left">Update Interval(Minutes)</td>
			<td style="width: 2px; overflow-x: hidden;">:</td>
			<td colspan="2"><input type="text" name="<%=Keys.SERVER_UPDATE_INTERVAL%>" size="8" value=""  style="width:120px;"></td>
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
		
		$(".ajax").colorbox({rel:'ajax', scrolling:"true",width:"45%", height:"88%"});

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
            					.appendTo('#server-report');
						$('#server-report').submit();
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
			$( "input[Value='Update Now']" ).button( "option", "disabled", false );
			
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
		$( "input[Value='Update Now']" ).button( "option", "disabled", true );
		
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

<h1>Servers:</h1>
<form method="post" id="server-report" name="server" action="server.jsp" >
<table cellpadding="0" cellspacing="0" border="0" class="display" id="dt_table">
	<thead>
		<tr>
			<th></th>	
			<th>S.No</th>
			<th>Name</th>
			<th>Host/IP</th>
			<th>Platform</th>
            <th>Reachable</th>		
			<th>Agent</th>
			<th>Agent Port</th>
			<th>Update Interval</th>
			<th>Last Update</th>
		</tr>
	</thead>
	<tbody>

	<%
		ArrayList<Server> servers = new ServerTable().get();
		
		for(int i = 0; i<servers.size(); i++){			
			out.println("<tr>"
			+"<td align=\"center\"><input type=\"radio\" name=\""+Keys.SERVER_ID+"\" value=\""+servers.get(i).getId()+"\"></td>"
			+"<td align=\"left\">"+(i+1)+"</td>"
			+"<td align=\"left\"><a href=\"serverLiveBasicData.jsp?"+Keys.SERVER_ID+"="+servers.get(i).getId()+"\"/>"+servers.get(i).getName()+"</td>"
			+"<td align=\"left\">"+servers.get(i).getHostIp()+"</td>"
			+"<td align=\"center\"><a href=\"popUpServerBasicData.jsp?"+Keys.SERVER_ID+"="+servers.get(i).getId()+"\" class='ajax'><img width=\"16\" height=\"16\"  src='../images/icons/"+servers.get(i).getPlatform()+".png' alt='"+servers.get(i).getPlatform()+"'></td>"
			+"<td align=\"center\"><img width=\"16\" height=\"16\"  src='../images/icons/"+servers.get(i).isReachable()+".png' alt='"+servers.get(i).isReachable()+"'></td>"
			+"<td align=\"center\"><img width=\"16\" height=\"16\"  src='../images/icons/"+servers.get(i).isAgentStatus()+".png' alt='"+servers.get(i).isAgentStatus()+"'></td>"
			+"<td align=\"center\">"+servers.get(i).getAgentPort()+"</td>"
			+"<td align=\"center\">"+servers.get(i).getUpdateInterval()/60+"</td>"
			+"<td align=\"left\">"+General.getGuiDateTime(servers.get(i).getLocalTime())+"</td></tr>");	
		}

	%>
    </tbody>
</table>

<BR>
<div class="jbutton">
	<input type="submit" name="SUBMIT" value="Add" style="width:80px;"> <input type="submit" name="SUBMIT" value="Edit" style="width:80px;"> <input type="submit" name="SUBMIT" value="Delete" style="width:80px;"> <input type="submit" name="SUBMIT" value="Update Now" style="width:80px;">
</div>
</form>
<BR>
<BR>
<table cellpadding="0" cellspacing="0" border="0" id="dt_table">
<tr>
<td><img width="16" height="16"  src='../images/icons/true.png'  alt='Reachable'></td><td valign="top">- Reachable&nbsp;</td>
<td><img width="16" height="16"  src='../images/icons/false.png' alt='Not Reachable'></td><td valign="top">- Not Reachable&nbsp;</td> 
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


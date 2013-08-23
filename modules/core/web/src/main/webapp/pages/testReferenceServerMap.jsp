<%@ include file="include/re_jsp.jsp"%>

<%
String buttonName 	= (String)request.getParameter(Keys.SUBMIT);
String testReferenceId 	= (String)request.getParameter(Keys.TEST_REFERENCE_ID);
if(buttonName != null){
%>
<%@ include file="include/re_jsp.jsp"%>


<%
	if(buttonName.equalsIgnoreCase("Delete")){
		new ManageTestReference().delete(request, response);
		response.sendRedirect("testReferenceServerMap.jsp");
	}else if(buttonName.equalsIgnoreCase("Update")){
		new ManageTestReference().modifyTestServerMap(request, response);
		response.sendRedirect("testReferenceServerMap.jsp");
	}else if(buttonName.equalsIgnoreCase("Modify")){
		%>		
		<%@ include file="index-part1.jsp"%>
		
		<script type="text/javascript">
		
		$(document).ready(function(){
			$('.chosen1').chosen();
			$('.chosen2').chosen({ width: "450px" });
		});
		
</script>

		<%@ include file="index-part2.jsp"%>

		<div id="dt_page">
		<div id="container">
		<h1>Modify/Add Test Reference | Server(s) Map:</h1>

		<form method="post" name="testReferenceServerMap" action="testReferenceServerMap.jsp"> 
		<table border="0" cellpadding="3" align="left" id="dt_table">
		
		<tr>
			<td align="left">Reference</td>
			<td>:</td>
			<td colspan="2"><select data-placeholder="Choose a Reference..."  tabindex="1" class="chosen1" name="<%=Keys.TEST_REFERENCE_ID%>" id="<%=Keys.TEST_REFERENCE_ID%>"> <option value=""></option>   
			<%
 			ArrayList<TestReference> references = new TestReferenceTable().getAll();
			for(TestReference reference : references){
				out.println("<option value=\""+reference.getId()+"\">"+reference.getTestReference()+"</option>");
			}
 			%>			
			</select></td>	
		</tr>
		
		<tr>
			<td align="left">Server(s)</td>
			<td>:</td>
			<td colspan="2"><select data-placeholder="Choose a Server..."  tabindex="2" class="chosen2" multiple name="<%=Keys.SERVER_ID%>" id="<%=Keys.SERVER_ID%>"> <option value=""></option>   
			<%
 			ArrayList<Server> servers = new ServerTable().get();
			for(Server server : servers){
				out.println("<option value=\""+server.getId()+"\">"+server.getName()+" ["+server.getHostIp()+"]"+"</option>");
			}
 			%>			
			</select></td>	
		</tr>		
			
		
		<tr> <td colspan="4" align="right"> <div class="jbutton"><input type="submit" name="SUBMIT" value="Update" style="width:80px;"></div></td></tr>
		</table>
		
		
		</form>



		</div>
		</div>
		<%@ include file="index-part3.jsp"%>
<%
}}else{
%>
<%@ include file="index-part1.jsp"%>
<script type="text/javascript">
	function callMeOnTableChange(){
		
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
            					.appendTo('#testReferenceServerMap');
						$('#testReferenceServerMap').submit();
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
			$( "input[Value=Delete]" ).button( "option", "disabled", false );
		});

		//Unselect All radio buttons
		$('input[type=radio]').prop('checked', false);	


		//Call Confirmation once clicked Delete Button
		$("input[Value=Delete]").click(function() { 
			$("#deleteDialog").dialog('open');	 
			return false;
		});

		//Disable Delete Button on load
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

<h1>Test Reference | Server(s) Map:</h1>
<form method="post" id="testReferenceServerMap" name="testReferenceServerMap" action="testReferenceServerMap.jsp" >
<table cellpadding="0" cellspacing="0" border="0" class="display" id="dt_table">
	<thead>
		<tr>
			<th></th>
			<th>Test Reference</th>
			<th>Server(s)</th>
		</tr>
	</thead>
	<tbody>

	<%
		ArrayList<TestReferenceServerMap> testSuites = new TestReferenceServerMapTable().getTestSuites();
		TestReferenceServerMapTable testReferenceServerMapTable = new TestReferenceServerMapTable();
		for(TestReferenceServerMap testSuite: testSuites){
			out.println("<tr>");
			out.println("<td align=\"center\"><input type=\"radio\" name=\""+Keys.TEST_REFERENCE_ID+"\" value=\""+testSuite.getTestSuiteReferenceId()+"\"></td>");
			out.println("<td align=\"left\">"+testSuite.getTestSuiteReferenceName()+"</td>");
			out.println("<td align=\"left\">"+testReferenceServerMapTable.getServersName(testSuite.getTestSuiteReferenceId()).replaceAll(", ", "<BR>")+"</td>");
			out.println("</tr>");
		}

	%>
    </tbody>
</table>

<BR>
<div class="jbutton">
	<input type="submit" name="SUBMIT" value="Delete" style="width:80px;"> <input type="submit" name="SUBMIT" value="Modify" style="width:80px;"> 
</div>
</form>

<div id="deleteDialog" title="Delete Confirmation!">
	<p>&nbsp;You are about to delete this item.<br> 
	&nbsp;It cannot be restored at a later time! Continue?</p>
</div>
</div>
</div>
<%@ include file="index-part3.jsp"%>
<%
}
%>

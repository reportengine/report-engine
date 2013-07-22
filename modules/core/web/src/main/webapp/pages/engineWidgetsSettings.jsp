<%@ include file="index-part1.jsp"%>
<%
String buttonName 	= (String)request.getParameter("SUBMIT");
if(buttonName == null){
%>
		<%@ include file="index-part2.jsp"%>
		
		

		<div id="dt_page">
		<div id="container">		
		<form method="post" name="EngineWedgetsSettings" action="engineWidgetsSettings.jsp"> 
		<table border="0" cellpadding="3" align="left" id="dt_table">
		<tr><td colspan="5"><h1>Theme Settings:</h1></td></tr>
		<tr>
			<td align="left">Current Theme</td>
			<td>:</td>
			<td colspan="2">
				<%=ServerSettings.getCurrentWidget()%>
			</td>	
		</tr>
		
		<tr>
			<td align="left">Available Themes</td>
			<td>:</td>
			<td colspan="2">
				<select data-placeholder="Choose a Theme..."  tabindex="1" class="chosen" name="<%=ServerSettings.KEY_ENGINE_CURRENT_WIDGET%>" id="<%=ServerSettings.KEY_ENGINE_CURRENT_WIDGET%>" style="width:320px;">
				<%
				String[] themes = ServerSettings.getAvailableWidgets().split(",");
				for(String theme : themes){
					if(ServerSettings.getCurrentWidget().equalsIgnoreCase(theme)){
						out.print("<option value=\""+theme+"\" selected>"+theme+"</option>");
					}else{
						out.print("<option value=\""+theme+"\">"+theme+"</option>");
					}
				}%>
			</select>
			</td>	
		</tr>
		<tr><td colspan="5"><h1>Menu Style Settings:</h1></td></tr>
		<tr>
			<td align="left">Current Menu Style</td>
			<td>:</td>
			<td colspan="2">
				<%=ServerSettings.getCurrentMenuStyle()%>
			</td>	
		</tr>
		
		<tr>
			<td align="left">Available Menu Styles</td>
			<td>:</td>
			<td colspan="2">
				<select data-placeholder="Choose a Menu..."  tabindex="2" class="chosen" name="<%=ServerSettings.KEY_ENGINE_CURRENT_MENU_STYLE%>" id="<%=ServerSettings.KEY_ENGINE_CURRENT_MENU_STYLE%>" style="width:320px;">
				<%
				themes = ServerSettings.getAvailableMenuStyles().split(",");
				for(String theme : themes){
					if(ServerSettings.getCurrentMenuStyle().equalsIgnoreCase(theme)){
						out.print("<option value=\""+theme+"\" selected>"+theme+"</option>");
					}else{
						out.print("<option value=\""+theme+"\">"+theme+"</option>");
					}
				}%>
			</select>
			</td>	
		</tr>

		<tr> <td colspan="4" align="right"> <div class="jbutton"><input type="submit" name="SUBMIT" value="Update" style="width:80px;"></div></td></tr>
		</table>
		
		</form>

		</div>
		</div>

<%
	}else if(buttonName.equalsIgnoreCase("Update")){
		ServerSettings.updateWidgetsSettings(request, session);
		response.sendRedirect("engineWidgetsSettings.jsp");
}%>
<script type="text/javascript"> 
      $('.chosen').chosen();
  </script>
<%@ include file="index-part3.jsp"%>

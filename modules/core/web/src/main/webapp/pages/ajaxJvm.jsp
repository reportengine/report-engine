<%@ include file="include/re_jsp.jsp"%>
<%
String serverId = (String)request.getParameter(Keys.SERVER_ID);
if(serverId != null){
	AjaxServerInfo.getJVMlist(request, response);
}
%>
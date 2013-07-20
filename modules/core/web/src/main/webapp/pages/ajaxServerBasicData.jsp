<%@ include file="include/re_jsp.jsp"%>
<%
String resource = request.getParameter(Keys.RESOURCE_TYPE);
if(resource.equalsIgnoreCase(Keys.RESOURCE_OS)){
	new AjaxServerInfo().getOSInfo(request, response);	
}else if(resource.equalsIgnoreCase(Keys.RESOURCE_NETWORK)){
	new AjaxServerInfo().getNetworkInfo(request, response);	
}else if(resource.equalsIgnoreCase(Keys.RESOURCE_CPU)){
	new AjaxServerInfo().getCpuInfo(request, response);	
}else if(resource.equalsIgnoreCase(Keys.RESOURCE_MEMORY)){
	new AjaxServerInfo().getMemoryInfo(request, response);	
}
%>
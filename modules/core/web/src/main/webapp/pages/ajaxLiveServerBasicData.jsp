<%@ include file="include/re_jsp.jsp"%>
<%
String resource = request.getParameter(Keys.RESOURCE_TYPE);
if(resource.equalsIgnoreCase(Keys.RESOURCE_OS)){
	new AjaxServerInfo().getLiveOSInfo(request, response);	
}else if(resource.equalsIgnoreCase(Keys.RESOURCE_NETWORK)){
	new AjaxServerInfo().getLiveNetworkInfo(request, response);	
}else if(resource.equalsIgnoreCase(Keys.RESOURCE_CPU)){
	new AjaxServerInfo().getLiveCpuInfo(request, response);	
}else if(resource.equalsIgnoreCase(Keys.RESOURCE_MEMORY)){
	new AjaxServerInfo().getLiveMemoryInfo(request, response);	
}
%>
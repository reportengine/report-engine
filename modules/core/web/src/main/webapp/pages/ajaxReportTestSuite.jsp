<%@ include file="include/re_jsp.jsp"%>
<%
String subMenu = request.getParameter(Keys.SUB_MENU);
if(subMenu.equalsIgnoreCase(Keys.TEST_SUITE_DETAILS)){
	new AjaxTestSuiteInfo().getTestSuiteDetails(request, response);
}else if(subMenu.equalsIgnoreCase(Keys.RESOURCE_UTILIZATION)){
	new AjaxTestSuiteInfo().getResourceUtilization(request, response);	
}else if(subMenu.equalsIgnoreCase(Keys.TREND_REPORT)){
	new AjaxServerInfo().getCpuInfo(request, response);	
}
%>
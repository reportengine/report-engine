<%@ include file="include/re_jsp.jsp"%>
<%
String jobRef 	= (String)request.getParameter(Keys.JOB_REFERENCE);
if(jobRef != null){
	new AjaxScheduler().getReferencesKeyValue(request, response);
}
%>
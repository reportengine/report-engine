<%@ include file="include/re_jsp.jsp"%>
<%if(new Login().checkSessionState(session)){
response.sendRedirect("logout.jsp");
}else{%>
<%
String buttonName = (String)request.getParameter("SUBMIT");
if(buttonName == null)
{
%>
<%@ include file="loginHead.jsp"%>
<tr><td align="center"><font face="verdana,arial" size=5><%=Settings.getAppTitle()%></font></td></tr>
<%@ include file="loginNormal.jsp"%>
<%
}else  if(buttonName.equalsIgnoreCase("Log In")){
	String userId = (String)request.getParameter(Keys.USER_ID);
	if(new Login().checkLogin(request, session)){
		response.sendRedirect("index.jsp");
	}else{
		%>
		<%@ include file="loginHead.jsp"%>
		<tr><td align="center"><font face="verdana,arial" size=5>Report Engine</font></td></tr>
		<tr><td>
		<div class="ui-widget">
			<div class="ui-state-error ui-corner-all" style="padding: 0pt 0.7em;"> 
				<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span> 
				<strong>Error:</strong> Invalid user Id or Password! Please try again!</p>
			</div>
		</div>
		</td></tr>
		<%@ include file="loginNormal.jsp"%>
		<%
	}
}else{
	response.sendRedirect("login.jsp");
}
}
%>
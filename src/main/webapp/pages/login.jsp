<%@ include file="include/re_jsp.jsp"%>
<%if(new Login().checkSessionState(session)){
	String buttonName = (String)request.getParameter("SUBMIT");
	if(buttonName != null){
		if(buttonName.equalsIgnoreCase("Update Password")){
			new AuthenticationForgetPassword().updatePassword(request, session);
		}
	}
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
}else  if(buttonName.equalsIgnoreCase("Create Account")){
	%>
	<%@ include file="loginHead.jsp"%>
	<tr><td align="center"><font face="verdana,arial" size=5>Report Engine - Create Account</font></td></tr>
	<tr><td>
	</td></tr>
	<%@ include file="NewAccount.jsp"%>
	<%	
	
}else  if(buttonName.equalsIgnoreCase("Forget Password/ID")){
	%>
	<%@ include file="loginHead.jsp"%>
	<tr><td align="center"><font face="verdana,arial" size=5>Report Engine - Forget Password</font></td></tr>
	<tr><td>
	</td></tr>
	<%@ include file="ForgetPassword.jsp"%>
	<%	
	
}else  if(buttonName.equalsIgnoreCase("Create")){
	new AuthUserInternal().addUser(request, response);
	%>
	alert('New Account Created successfully!!');
	<%	
	response.sendRedirect("login.jsp");	
}else  if(buttonName.equalsIgnoreCase("ResetPassword")){
	new AuthenticationForgetPassword().getForgetPassword(request, session);
	response.sendRedirect("resetPassword.jsp");
}else  if(buttonName.equalsIgnoreCase("Request")){
	new AuthenticationForgetPassword().setForgetPassword(request, response);
	%>
	alert('[Password Sent to you email address...');
	<%	
	response.sendRedirect("login.jsp");	
}else{
	response.sendRedirect("login.jsp");
}
}
%>
<%@ include file="include/re_jsp.jsp"%>
<%if(new Login().checkSessionState(session)){%>
<%@ include file="loginHead.jsp"%>
<tr><td align="center"><font face="verdana,arial" size=5><%=Settings.getAppTitle()%> - Password Reset</font></td></tr>
<tr><td align="center">	
			<form method="post" name="loginform" action="login.jsp" >
		<table border="0" cellspacing="3" cellpadding="0" align="center">
    		
<tr><td><font size=2>New Password</td><td>:</td><td align="left"><input  style="width:330px;" type="password" name="<%=Keys.USER_PASSWORD%>" id="<%=Keys.USER_PASSWORD%>"></td></tr>
<tr><td><font size=2>Retype Password</td><td>:</td><td align="left"><input  style="width:330px;" type="password" name="<%=Keys.USER_PASSWORD_CONF%>" id="<%=Keys.USER_PASSWORD_CONF%>"></td></tr>

<tr><td>&nbsp;</td><td>&nbsp;</td><td align="right"><div class="jbutton"><input type="submit" name="SUBMIT" value="Update Password" style="width:120px;" onClick="return checkUpdatePasswordFields()">
</tr>
</table>
</form>
</table>
</div>
</div>
</body>
</html>
<%}else{response.sendRedirect("login.jsp");}%>
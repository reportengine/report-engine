<%@ include file="include/re_jsp.jsp"%>
<%@page import="com.redhat.reportengine.server.authentication.Authentication"%>
<%@page import="com.redhat.reportengine.server.dbmap.AuthUser"%>
<%@page import="com.redhat.reportengine.server.dbdata.LDAPTable"%>
<%@page import="com.redhat.reportengine.server.dbmap.LDAPDetails"%>
<%@page import="java.util.ArrayList"%>
<tr><td align="center">	
			<form method="post" name="loginform" action="login.jsp" >
		<table border="0" cellspacing="3" cellpadding="0" align="center">
    		
<tr><td><font size=2>User Name</td><td>:</td><td align="left"><input type="text" style="width:330px;" name="<%=Keys.USER_ID%>" id="<%=Keys.USER_ID%>"></td></tr>
<tr><td><font size=2>Email</td><td>:</td><td align="left"><input type="text" style="width:330px;" name="<%=Keys.USER_EMAIL%>" id="<%=Keys.USER_EMAIL%>"></td></tr>
<tr><td><font size=2>First Name</td><td>:</td><td align="left"><input type="text" style="width:330px;" name="<%=Keys.USER_FIRST_NAME%>" id="<%=Keys.USER_FIRST_NAME%>"></td></tr>
<tr><td><font size=2>Last Name</td><td>:</td><td align="left"><input type="text" style="width:330px;" name="<%=Keys.USER_LAST_NAME%>" id="<%=Keys.USER_LAST_NAME%>"></td></tr>
<tr><td><font size=2>Password</td><td>:</td><td align="left"><input  style="width:330px;" type="password" name="<%=Keys.USER_PASSWORD%>" id="<%=Keys.USER_PASSWORD%>"></td></tr>
<tr><td><font size=2>Retype Password</td><td>:</td><td align="left"><input  style="width:330px;" type="password" name="<%=Keys.USER_PASSWORD_CONF%>" id="<%=Keys.USER_PASSWORD_CONF%>"></td></tr>

<tr><td>&nbsp;</td><td>&nbsp;</td><td align="right"><div class="jbutton"><input type="submit" name="SUBMIT" value="Create" style="width:80px;" onClick="return checkCreateAccountFields()">
</tr>
</table>
</form>
</table>
</div>
</div>
</body>
</html>
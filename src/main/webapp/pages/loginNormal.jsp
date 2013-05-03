<%@page import="com.redhat.reportengine.server.authentication.Authentication"%>
<%@page import="com.redhat.reportengine.server.dbmap.AuthUser"%>
<%@page import="com.redhat.reportengine.server.dbdata.LDAPTable"%>
<%@page import="com.redhat.reportengine.server.dbmap.LDAPDetails"%>
<%@page import="java.util.ArrayList"%>
<tr><td align="center">	
			<form method="post" name="loginform" action="login.jsp" >
		<table border="0" cellspacing="3" cellpadding="0" align="center">
    		
<tr><td><font size=2>User Name</td><td>:</td><td align="left"><input type="text" style="width:330px;" name="<%=Keys.USER_ID%>" id="<%=Keys.USER_ID%>"></td></tr>
<tr><td><font size=2>Password</td><td>:</td><td align="left"><input  style="width:330px;" type="password" name="<%=Keys.USER_PASSWORD%>" id="<%=Keys.USER_PASSWORD%>"></td></tr>
<tr><td><font size=2>Domain</td><td>:</td><td align="left"><select name="<%=Keys.LOGIN_DB%>" id="<%=Keys.LOGIN_DB%>" style="width:330px;">
				<%
				out.print("<option value=\""+Authentication.AUTH_TYPE_INTERNAL+"\" selected>"+Authentication.AUTH_TYPE_INTERNAL+"</option>");
				ArrayList<LDAPDetails> ldaps = new LDAPTable().getEnabled();
				for(LDAPDetails ldap : ldaps){
					out.print("<option value=\""+ldap.getName()+"\">"+ldap.getName()+" [LDAP]</option>");
				}%>
			</select></font></td></tr>
<tr><td align="right" colspan="3"><div class="jbutton"><input type="submit" name="SUBMIT" value="Forget Password/ID" style="width:150px;"><input type="submit" name="SUBMIT" value="Create Account" style="width:120px;"><input type="submit" class="default" name="SUBMIT" value="Log In" style="width:80px;" onClick="return checkFields()">
</tr>
</table>
</form>
</table>
</div>
</div>
<script>
//Enable Create account and Forget password Button if we select local database
$('#<%=Keys.LOGIN_DB%>').change(function() { 
	if($(this).val() == '<%=Authentication.AUTH_TYPE_INTERNAL%>'){
		$( "input[Value='Create Account']" ).button( "option", "disabled", false );
		$( "input[Value='Forget Password/ID']" ).button( "option", "disabled", false );
	}else{
		$( "input[Value='Create Account']" ).button( "option", "disabled", true );
		$( "input[Value='Forget Password/ID']" ).button( "option", "disabled", true );
	}
	
});

</script>
</body>
</html>
		<tr><td align="center">	
			<form method="post" name="loginform" action="login.jsp" >
		<table border="0" cellspacing="3" cellpadding="0" align="center">
    		
 
<tr><td><font size=2>User ID</td><td>:</td><td align="left"><input type="text" style="width:330px;" name="<%=Keys.USER_ID%>" id="<%=Keys.USER_ID%>"></td></tr>
<tr><td><font size=2>Password</td><td>:</td><td align="left"><input  style="width:330px;" type="password" name="<%=Keys.USER_PASSWORD%>" id="<%=Keys.USER_PASSWORD%>"></td></tr>
<tr><td>&nbsp;</td><td>&nbsp;</td><td align="right"><div class="jbutton"><input type="submit" name="SUBMIT" value="Log In" style="width:80px;" onClick="return checkFields()">
</tr>
</table>
</form>
</table>
</div>
</div>
</body>
</html>
<tr><td align="center">	
			<form method="post" name="loginform" action="login.jsp" >
		<table border="0" cellspacing="3" cellpadding="0" align="center">
    		
<tr><td><font size=2>User Name</td><td>:</td><td align="left"><input type="text" style="width:330px;" name="<%=Keys.USER_ID%>" id="<%=Keys.USER_ID%>"></td></tr>
<tr><td align="center" colspan="3"><font size=3><b>(OR)</b></td></tr>
<tr><td><font size=2>Email</td><td>:</td><td align="left"><input type="text" style="width:330px;" name="<%=Keys.USER_EMAIL%>" id="<%=Keys.USER_EMAIL%>"></td></tr>
<tr><td>&nbsp;</td><td>&nbsp;</td><td align="right"><div class="jbutton"><input type="submit" name="SUBMIT" value="Request" style="width:80px;" onClick="return checkForgetPasswordFields()">
</tr>
</table>
</form>
</table>
</div>
</div>
</body>
</html>
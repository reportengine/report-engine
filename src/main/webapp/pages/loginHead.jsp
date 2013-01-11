<%@ include file="include/re_css_js.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "DTD/xhtml1-transitional.dtd">
<html>
<head>
<title><%=Settings.getAppTitle()%></title>
<script>
if (window.top != window.self) {
   window.top.location="login.jsp"
}

function checkFields(){ 
if (document.getElementById('<%=Keys.USER_ID%>').value == ""){ alert("Please enter User ID!"); return false;}
if (document.getElementById('<%=Keys.USER_PASSWORD%>').value == ""){ alert("Please enter password!"); return false;}
return true;
} 
</script>

</head>
<body bgColor=#D0D0D0 >
<div id="dt_page">
<div id="container">

	<table border="0" cellspacing="0"  cellpadding="0" align="center"> 
	<tr><td>&nbsp; </td></tr>
	<tr><td>&nbsp; </td></tr>
	<tr><td>&nbsp; </td></tr>
	<tr><td>&nbsp; </td></tr>
		<tr><td>&nbsp; </td></tr>
	<tr><td>&nbsp; </td></tr>
	<tr><td>&nbsp; </td></tr>
		<tr><td>&nbsp; </td></tr>
	<tr><td>&nbsp; </td></tr>
	<tr><td>&nbsp; </td></tr>
			<tr><td>&nbsp; </td></tr>
	<tr><td>&nbsp; </td></tr>
	<tr><td>&nbsp; </td></tr>
		<tr><td>&nbsp; </td></tr>
	<tr><td>&nbsp; </td></tr>
	<tr><td>&nbsp; </td></tr>
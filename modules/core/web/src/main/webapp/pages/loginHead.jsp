<%@page import="com.redhat.reportengine.server.authentication.Authentication"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "DTD/xhtml1-transitional.dtd">
<html>
<head>
<title><%=Settings.getAppTitle()%></title>
<%@ include file="include/re_css_js.jsp"%>
<script>

if (window.top != window.self) {
   window.top.location="login.jsp"
}

$(function() {
    $("form input").keypress(function (e) {
        if ((e.which && e.which == 13) || (e.keyCode && e.keyCode == 13)) {
            $('button[type=submit] .default').click();
            return false;
        } else {
            return true;
        }
    });
});

function checkFields(){ 
if (document.getElementById('<%=Keys.USER_ID%>').value == ""){ $.alert("Enter User Name!", 'Login'); return false;}
if (document.getElementById('<%=Keys.USER_PASSWORD%>').value == ""){ $.alert("Enter Password!", 'Login'); return false;}
} 

function checkCreateAccountFields(){ 
	if (document.getElementById('<%=Keys.USER_ID%>').value == ""){ alert("Enter User Name!"); return false;}
	if (document.getElementById('<%=Keys.USER_EMAIL%>').value == ""){ alert("Enter Email!"); return false;}
	if (document.getElementById('<%=Keys.USER_FIRST_NAME%>').value == ""){ alert("Enter First Name!"); return false;}
	if (document.getElementById('<%=Keys.USER_LAST_NAME%>').value == ""){ alert("Enter Last Name!"); return false;}
	if (document.getElementById('<%=Keys.USER_PASSWORD%>').value == ""){ alert("Enter Password!"); return false;}
	if (document.getElementById('<%=Keys.USER_PASSWORD_CONF%>').value == ""){ alert("Retype Password!"); return false;}	
} 

function checkForgetPasswordFields(){ 
	if ((document.getElementById('<%=Keys.USER_ID%>').value == "") && (document.getElementById('<%=Keys.USER_EMAIL%>').value == "")){ 
		alert("Enter User Name (OR) Email Id"); return false;
	}
} 

function checkUpdatePasswordFields(){ 
	if (document.getElementById('<%=Keys.USER_PASSWORD%>').value == ""){ alert("Enter New Password!"); return false;}
	if (document.getElementById('<%=Keys.USER_PASSWORD_CONF%>').value == ""){ alert("Retype New Password!"); return false;}	
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

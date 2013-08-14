<%@ page isErrorPage="true" import="java.io.*" %>
<%@ include file="include/re_jsp.jsp"%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<%@ include file="include/re_css_js.jsp"%>
</head>
<body>
<div id="dt_page">
<div id="container">
	<BR>

	<%
	// This is to capture error trace root
	
//out.println("<!--");
StringWriter sw = new StringWriter();
PrintWriter pw = new PrintWriter(sw);
exception.printStackTrace(pw);
//out.print(sw);
sw.close();
pw.close();
String rootTrace = sw.toString().replaceAll("\n", "<BR>");
//out.println("-->");
%>

<%-- Exception Handler --%>
<%
if(exception.toString().contains("The connection attempt failed..")){
%>
<div id="parent" style="width:80%; border: 2px solid red; padding: 10px;">
<font size="2" face="Verdana" color="red">
<IMG height=17 src="<%=General.HTML_ICONS_LOCATION%>error.png" width=17 align=top border=0>&nbsp;Database connection error!
<BR>&nbsp;&nbsp;&nbsp;&nbsp;Report Engine database service is not running.
<BR>&nbsp;&nbsp;&nbsp;&nbsp;Please start the service!
</font></DIV>
<%
}else if(exception.toString().contains("org.apache.http.conn.HttpHostConnectException:")){
	%>
	<div id="parent" style="width:80%; border: 2px solid red; padding: 10px;">
	<font size="2" face="Verdana" color="red">
	<IMG height=17 src="<%=General.HTML_ICONS_LOCATION%>error.png" width=17 align=top border=0>&nbsp;Agent or Server might be down!

	<BR>&nbsp;&nbsp;&nbsp;&nbsp;Sometimes on network failure...
	<BR>&nbsp;&nbsp;&nbsp;&nbsp;Please check the agent service on server machine or Is server reachable?
	</font></DIV>

<%
}else if(exception.toString().contains("This method has not been implemented on this platform")){
%>
	<div class="ui-widget">
		<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0pt 0.7em;"> 
			<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: 0.3em;"></span>
			<strong>Info: </strong>This method has not been implemented on this platform!</p>
		</div>
	</div>
	<%
}else if(exception.toString().contains("SQL Anywhere Error -141")){
%>
	<div class="ui-widget">
		<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0pt 0.7em;"> 
			<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: 0.3em;"></span>
			<strong>Info: </strong>There is no entries found for this server! Seems never scheduled a job for this server!</p>
		</div>
	</div>
	<%

}else if(exception.toString().contains("no such object in table")){
%>
	<div class="ui-widget">
		<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0pt 0.7em;"> 
			<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: 0.3em;"></span>
			<strong>Info: </strong>Please try again after few minutes! Agent might not be ready on this server!</p>
		</div>
	</div>
	<%

}
else{

%>
<div id="parent" style="width:80%; border: 2px solid red; padding: 10px; margin-right:auto;margin-left:auto;">
<font size="2" face="Verdana" color="red">
<IMG height=17 src="<%=General.HTML_ICONS_LOCATION%>error.png" width=17 align=top border=0>&nbsp;Error: <%= exception.toString().replaceAll("\n", "<BR>") %>
</font></DIV>
<BR>
<BR>

<div id="parent" style="width:80%; border: 2px solid red; padding: 10px; margin-right:auto;margin-left:auto;">
<font size="2" face="Verdana" color="black">
<B>Stack Trace:</B><div id="error_page"><pre><%=rootTrace%></pre></div>
</font></DIV>

<%
}
%>




</div>
</div>
</body>
</html>

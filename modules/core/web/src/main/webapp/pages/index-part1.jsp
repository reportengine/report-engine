<%@ include file="include/re_jsp.jsp"%>
<%if(new Login().checkSessionState(session)){%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "DTD/xhtml1-strict.dtd">
<html>
  <head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
  <title><%=Settings.getAppTitle()%></title>
  <%@ include file="include/re_css_js.jsp"%>

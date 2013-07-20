<%@ include file="include/re_jsp.jsp"%>
<html>
<head>
  
<%
int serverId = Integer.valueOf(request.getParameter(Keys.SERVER_ID));
Server server = new ServerTable().getById(serverId);
%>
<script type="text/javascript">
$(function() {
  $( "#tabs" ).tabs({
    beforeLoad: function( event, ui ) {
      ui.jqXHR.error(function() {
        ui.panel.html(
          "Couldn't load this tab. We'll try to fix this as soon as possible. Please rport a bug!" );
      });
    }
  });
});
</script>
</head>
<body>
<div id="dt_page">
<div id="container">
<h1>Server Details: <b><%=server.getName()%>(<%=server.getHostIp()%>)</b></h1>
<div id="tabs" style="overflow:hidden;">
  <ul>
    <li><a href="ajaxServerBasicData.jsp?<%=Keys.SERVER_ID%>=<%=serverId%>&<%=Keys.RESOURCE_TYPE%>=<%=Keys.RESOURCE_OS%>"><font color="#FFF">OS</font></a></li>
    <li><a href="ajaxServerBasicData.jsp?<%=Keys.SERVER_ID%>=<%=serverId%>&<%=Keys.RESOURCE_TYPE%>=<%=Keys.RESOURCE_NETWORK%>"><font color="#FFF">Network</font></a></li>
    <li><a href="ajaxServerBasicData.jsp?<%=Keys.SERVER_ID%>=<%=serverId%>&<%=Keys.RESOURCE_TYPE%>=<%=Keys.RESOURCE_CPU%>"><font color="#FFF">CPU</font></a></li>
    <li><a href="ajaxServerBasicData.jsp?<%=Keys.SERVER_ID%>=<%=serverId%>&<%=Keys.RESOURCE_TYPE%>=<%=Keys.RESOURCE_MEMORY%>"><font color="#FFF">Memory</font></a></li>
  </ul>
</div>

<BR>
<div><I>Last Update on, <%=General.getGuiDateTime(server.getLocalTime())%></I></div>
</div>
</div>
</body>
</html>

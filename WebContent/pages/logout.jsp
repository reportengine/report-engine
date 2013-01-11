<%@ include file="include/re_jsp.jsp"%>

<%

new Logout().doLogout(request, session);
response.sendRedirect("index.jsp");
%>
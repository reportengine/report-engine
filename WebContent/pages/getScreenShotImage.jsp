<%@ page trimDirectiveWhitespaces="true" %>
<%@ include file="include/re_jsp.jsp"%>
<%
String imageId = request.getParameter("imageId");
new GetAjaxData().getScreenShotImage(response, imageId);
%>

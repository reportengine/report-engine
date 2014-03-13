<%@ include file="include/re_jsp.jsp"%>
<html>
<head>
  
<%
int testSuiteId = Integer.valueOf(request.getParameter(Keys.TEST_SUITE_ID));
TestSuite testSuite = new TestSuiteReport().getTestSuiteDetails(testSuiteId);
%>
<script type="text/javascript">
$(function() {
  $( "#tabs-<%=testSuiteId%>" ).tabs({
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
<h1>Test Suite Details:</h1>
<div id="tabs-<%=testSuiteId%>" style="overflow:hidden;">
  <ul>
    <li><a href="ajaxReportTestSuite.jsp?<%=Keys.TEST_SUITE_ID%>=<%=testSuiteId%>&<%=Keys.SUB_MENU%>=<%=Keys.TEST_SUITE_DETAILS%>">Suite Details</a></li>
    <li><a href="ajaxReportTestSuite.jsp?<%=Keys.TEST_SUITE_ID%>=<%=testSuiteId%>&<%=Keys.SUB_MENU%>=<%=Keys.RESOURCE_UTILIZATION%>">Resource Utilization</a></li>
    <li><a href="ajaxReportTestSuite.jsp?<%=Keys.TEST_SUITE_ID%>=<%=testSuiteId%>&<%=Keys.SUB_MENU%>=<%=Keys.TREND_REPORT%>">Trend Report</a></li>
  </ul>
</div>

</div>
</div>
</body>
</html>

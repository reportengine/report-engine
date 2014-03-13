<%@ include file="index-part1.jsp"%>
<%
String idData = null;

String suiteId 	= request.getParameter(Keys.TEST_SUITE_ID);
String groupId 	= request.getParameter(Keys.TEST_GROUP_ID);
String caseId 	= request.getParameter(Keys.TEST_CASE_ID);
boolean suiteRunning = false;

ArrayList<TestLogs> testLogsArray = null;

if(caseId != null){
	testLogsArray = new TestLogsReport().getLogsByCaseId(Integer.valueOf(caseId));
	idData = Keys.TEST_CASE_ID+"="+caseId;
}else if(groupId != null){
	testLogsArray = new TestLogsReport().getLogsByGroupId(Integer.valueOf(groupId));
	idData = Keys.TEST_GROUP_ID+"="+groupId;
}else{
	testLogsArray = new TestLogsReport().getLogsBySuiteId(Integer.valueOf(suiteId));
	idData = Keys.TEST_SUITE_ID+"="+suiteId;
}

if(testLogsArray.size() > 0){
	TestSuite testSuite = new TestSuiteReport().getTestSuiteDetails(testLogsArray.get(0).getTestSuiteId());
	if(testSuite.getTestStatus().equalsIgnoreCase(TestSuite.RUNNING)){
		suiteRunning = true;
	}
}

%>

<%if(suiteRunning){%>

<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
	doUpdate();
});			
</script>

<script type="text/javascript">
	function doUpdate() {
		var keepPageToBottom = false;
		if(($(window).height()+$(this).scrollTop()) >= $(document).height()){
			keepPageToBottom = true;
			//alert('Window Hight:'+$(window).height()+'\nDocument Height:'+$(document).height()+'\nScrollTop:'+$(window).scrollTop()+'Total:'+($(window).height()+$(window).scrollTop()));
		}
		
  		$.ajax({type: "GET", url : 'ajaxReportTestLogDetails.jsp?<%=idData%>',
          	success: function (data) {
             		if (data.length > 4) {
			 	// Data are assumed to be in HTML format
                	 	// Return something like <p/> in case of no updates
				data = trim(data);
				if(data.length != 0){
					$("#log_page_ajax").append(data+'\n');
					if(keepPageToBottom){
						$(window).scrollTop($(document).height());
					}
				}
                		
             		}
             	setTimeout("doUpdate()", 3000);
           	}});
}
</script>
<%}%>

<%@ include file="index-part2.jsp"%>

<div id="dt_page">
<div id="container">

<h1>Test Logs Report:</h1>
<div id="log_page"><pre>
<%
		StringBuilder content = new StringBuilder();
		for(TestLogs testLogs : testLogsArray){
			content.append("\n<div id=\"LEVEL_").append(testLogs.getLogLevel()).append("\">[")
				   .append(General.getLogLevelStr(testLogs.getLogLevel())).append("] ").append(General.getGuiLogDateTime(testLogs.getLogTime()))
				   .append(" [").append(General.getNotNullString(testLogs.getSequenceNumber())).append("] ").append(testLogs.getClassName())
				   .append(".").append(testLogs.getMethodName()).append("\n").append(General.getThrowableString(testLogs.getMessage()))
				   .append("\n").append(General.getThrowableString(testLogs.getThrowable())).append("</div>");
			
			
			//out.print("\n<div id=\"LEVEL_"+testLogs.get(i).getLogLevel()+"\">["+General.getLogLevelStr(testLogs.get(i).getLogLevel())+"] "+General.getGuiLogDateTime(testLogs.get(i).getLogTime())+" ["+General.getNotNullString(testLogs.get(i).getSequenceNumber())+"] "+testLogs.get(i).getClassName()+"."+testLogs.get(i).getMethodName()+"\n"+General.getThrowableString(testLogs.get(i).getMessage())+"\n"+General.getThrowableString(testLogs.get(i).getThrowable())+"</div>");
		}
		out.print(content.toString());
		if(testLogsArray.size() > 0){
			session.setAttribute(Keys.TEST_LOG_AJAX_REF, testLogsArray.get(testLogsArray.size()-1).getId());
		}else{
			session.setAttribute(Keys.TEST_LOG_AJAX_REF, 0);
		}
		
		
%>
<%if(suiteRunning){%>
<div id="log_page_ajax"></div>
<img width="16" height="16"  src='<%=General.HTML_ICONS_LOCATION%>Running.png'  alt='Running'>
<%}%>
</pre>
</div>
</div>
</div>

<%@ include file="index-part3.jsp"%>


<%@ include file="index-part1.jsp"%>
<%
String idData = null;

String suiteId 	= request.getParameter("suiteId");
String groupId 	= request.getParameter("groupId");
String caseId 	= request.getParameter("caseId");
boolean suiteRunning = false;

ArrayList<TestLogs> testLogs = null;

if(caseId != null){
	testLogs = new TestLogsReport().getLogsByCaseId(Integer.valueOf(caseId));
	idData = "caseId="+caseId;
}else if(groupId != null){
	testLogs = new TestLogsReport().getLogsByGroupId(Integer.valueOf(groupId));
	idData = "groupId="+groupId;
}else{
	testLogs = new TestLogsReport().getLogsBySuiteId(Integer.valueOf(suiteId));
	idData = "suiteId="+suiteId;
}

if(testLogs.size() > 0){
	TestSuite testSuite = new TestSuiteReport().getTestSuiteDetails(testLogs.get(0).getTestSuiteId());
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
		int id = 0;
		for(int i = 0; i<testLogs.size(); i++){
			out.print("\n<div id=\"LEVEL_"+testLogs.get(i).getLogLevel()+"\">["+General.getLogLevelStr(testLogs.get(i).getLogLevel())+"] "+General.getGuiLogDateTime(testLogs.get(i).getLogTime())+" ["+General.getNotNullString(testLogs.get(i).getSequenceNumber())+"] "+testLogs.get(i).getClassName()+"."+testLogs.get(i).getMethodName()+"\n"+General.getThrowableString(testLogs.get(i).getMessage())+"\n"+General.getThrowableString(testLogs.get(i).getThrowable())+"</div>");
		id = testLogs.get(i).getId();
		}
		session.setAttribute(Keys.TEST_LOG_AJAX_REF, id);
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


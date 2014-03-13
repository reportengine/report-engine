<%@ include file="include/re_jsp.jsp"%>
<html>
<head>
  
<%
int testSuiteId = Integer.valueOf(request.getParameter(Keys.TEST_SUITE_ID));
TestSuite testSuite = new TestSuiteReport().getTestSuiteDetails(testSuiteId);
if(!testSuite.getTestStatus().equalsIgnoreCase(TestSuite.RUNNING)){
%>
<script type="text/javascript">
$(function () {
    Highcharts.setOptions({
        colors: ['#008000', '#FF0000', '#800000', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4']
    });
$(function () {
    var chart;
    $(document).ready(function() {
        chart = new Highcharts.Chart({
            chart: {
                renderTo: 'pieChartContainer',
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            title: {
            	   text: ''
            	},
            credits: {
                	enabled: false
               	},
            exporting: {
            		enabled: false
            	},
            tooltip: {
            	formatter: function() {
                    return ''+
                        this.point.name +': '+ this.y +' ('+ Math.round(this.percentage * 100)/100 +'%)'+'<br>Total: '+ this.point.total;
                }
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        color: '#000000',
                        connectorColor: '#000000',
                        formatter: function() {
                        	return '<b>'+ this.point.name +': </b>'+ this.y +' ('+ Math.round(this.percentage * 100)/100 +'%)';
                        }
                    }
                }
            },
            series: [{
                type: 'pie',
                data: [
                    ['Passed', <%=testSuite.getPassedCases()%>],
                    ['Failed', <%=testSuite.getFailedCases()%>],
                    ['Skipped', <%=testSuite.getSkippedCases()%>]
                ]
            }]
        });
    });
    
});


});
</script>

<%}

%>

</head>
<body>
<div id="dt_page">
<div id="container">
<h1>Test Suite Details:</h1>

	<table border="0" cellpadding="3" width="100%" align="left" id="dt_table">	

	<TR>
		<td align="left" style="width: 125px; overflow-x: hidden;">Test Suite Name</td>
		<TD style="width: 2px; overflow-x: hidden;">:</TD>
		<td align="left"><%=testSuite.getTestSuiteName()%></td>		
	</tr>
	<TR>
		<td align="left" valign="top">Build</td>
		<TD valign="top">:</TD>
		<td align="left"><%=General.getHtml(testSuite.getTestBuild())%></td>		
	</tr>
	<TR>
		<td align="left">Status</td>
		<TD>:</TD>
		<td align="left"><img width="16" height="16"  src='<%=General.HTML_ICONS_LOCATION%><%=testSuite.getTestStatus()%>.png' alt='<%=testSuite.getTestStatus()%>'></td>		
	</tr>
	<TR>
		<td align="left">Test Reference</td>
		<TD>:</TD>
		<td align="left"><%=testSuite.getTestReference()%></td>		
	</tr>
	<TR>
		<td align="left">Host (Test Bed)</td>
		<TD>:</TD>
		<td align="left"><%=General.getNotNullString(testSuite.getTestHost())%></td>		
	</tr>
	<TR>
		<td align="left">Local Start Time</td>
		<TD>:</TD>
		<td align="left"><%=General.getGuiDateTime(testSuite.getLocalStartTime())%></td>		
	</tr>
	<TR>
		<td align="left">Local End Time</td>
		<TD>:</TD>
		<td align="left"><%=General.getGuiDateTime(testSuite.getLocalEndTime())%></td>		
	</tr>
	<TR>
		<td align="left">Remote Start Time</td>
		<TD>:</TD>
		<td align="left"><%=General.getGuiDateTime(testSuite.getRemoteStartTime())%></td>		
	</tr>
	<TR>
		<td align="left">Remote End Time</td>
		<TD>:</TD>
		<td align="left"><%=General.getGuiDateTime(testSuite.getRemoteEndTime())%></td>		
	</tr>
	<TR>
		<td align="left">Comments</td>
		<TD>:</TD>
		<td align="left"><%=General.getHtml(testSuite.getTestComments())%></td>		
	</tr>
	<TR>
		<td align="left">Trend Report For Last</td>
		<TD>:</TD>
		<td align="left"><a class="alink" href="testTrendReport.jsp?references=<%=testSuite.getTestReferenceId()%>&chartType=area&<%=Keys.REPORT_FOR%>=<%=Keys.REPORT_FOR_LAST_7_DAYS%>&SUBMIT=Get">7 Days,</a>&nbsp;<a class="alink" href="testTrendReport.jsp?references=<%=testSuite.getTestReferenceId()%>&chartType=area&<%=Keys.REPORT_FOR%>=<%=Keys.REPORT_FOR_LAST_15_DAYS%>&SUBMIT=Get">15 Days,</a>&nbsp;<a class="alink" href="testTrendReport.jsp?references=<%=testSuite.getTestReferenceId()%>&chartType=area&<%=Keys.REPORT_FOR%>=<%=Keys.REPORT_FOR_LAST_30_DAYS%>&SUBMIT=Get">30 Days</a>&nbsp;<a class="alink" href="testTrendReport.jsp?references=<%=testSuite.getTestReferenceId()%>&chartType=area&<%=Keys.REPORT_FOR%>=<%=Keys.REPORT_FOR_LAST_60_DAYS%>&SUBMIT=Get">60 Days</a>&nbsp;<a class="alink" href="testTrendReport.jsp?references=<%=testSuite.getTestReferenceId()%>&chartType=area&<%=Keys.REPORT_FOR%>=<%=Keys.REPORT_FOR_LAST_90_DAYS%>&SUBMIT=Get">90 Days</a></td>		
	</tr>
	
	<%if(!testSuite.getTestStatus().equalsIgnoreCase(TestSuite.RUNNING)){%>
	<TR><td colspan="3" align="center"><div id="pieChartContainer" style="height: 250px; margin: 0 auto;"></div></td></TR>
	 <%}%>


	
</table>
<BR>
<h1></h1>
</div>
</div>
</body>
</html>

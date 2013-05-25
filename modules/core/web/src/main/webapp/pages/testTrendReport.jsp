
<%@ include file="index-part1.jsp"%>
<%
	String buttonName = (String) request.getParameter("SUBMIT");

		if (buttonName == null) {
%>
<%@ include file="index-part2.jsp"%>


<script type="text/javascript">
	function dateOption(){		
		if($('select[id=<%=Keys.REPORT_FOR%>]').val() == '<%=Keys.REPORT_FOR_CUSTOM%>'){
			$("#<%=Keys.REPORT_DATE_TR%>").show();
		}else{
			$("#<%=Keys.REPORT_DATE_TR%>").hide(); 
		}
	}
</script>
<script type="text/javascript">
	$(document).ready(function(){
		dateOption();
	});
</script>

<div id="dt_page">
	<div id="container">
		<h1>Trend Report:</h1>

		<form method="post" name="jobDetails" action="testTrendReport.jsp">
			<table border="0" cellpadding="3" align="left" id="dt_table">

				<tr>
					<td align="left">Test Reference</td>
					<td>:</td>
					<td colspan="2"><select id="references" name="references">
							<%
								ArrayList<TestReference> testReferences = new ManageTestReference()
												.getAllTestReference();
										for (TestReference testReference : testReferences) {
											out.println("<option value=\"" + testReference.getId()
													+ "\">" + testReference.getTestReference()
													+ "</option>");
										}
							%>
					</select></td>
				</tr>
				<tr>
					<td align="left">Chart Type</td>
					<td>:</td>
					<td colspan="2"><select id="chartType" name="chartType">
							<option value="area">Area</option>
							<option value="line">Line</option>
					</select></td>
				</tr>

				<tr>
					<td align="left">Report For</td>
					<td>:</td>
					<td colspan="2"><select id="<%=Keys.REPORT_FOR%>"
						name="<%=Keys.REPORT_FOR%>" onchange="dateOption(this)">
							<option value="<%=Keys.REPORT_FOR_LAST_7_DAYS%>">Last 7
								Days</option>
							<option value="<%=Keys.REPORT_FOR_LAST_15_DAYS%>">Last
								15 Days</option>
							<option value="<%=Keys.REPORT_FOR_LAST_30_DAYS%>">Last
								30 Days</option>
							<option value="<%=Keys.REPORT_FOR_LAST_60_DAYS%>">Last
								60 Days</option>
							<option value="<%=Keys.REPORT_FOR_LAST_90_DAYS%>">Last
								90 Days</option>
							<option value="<%=Keys.REPORT_FOR_CUSTOM%>">Custom</option>
					</select></td>
				</tr>

				<tr id="<%=Keys.REPORT_DATE_TR%>">
					<td align="left">Date Range</td>
					<td>:</td>
					<td align="left" colspan="2"><input type="text"
						id="<%=Keys.REPORT_DATE_FROM%>" name="<%=Keys.REPORT_DATE_FROM%>"
						size="8" readonly value="" style="width: 100px;"> to <input
						type="text" id="<%=Keys.REPORT_DATE_TO%>"
						name="<%=Keys.REPORT_DATE_TO%>" size="8" readonly value=""
						style="width: 100px;"></td>
				</tr>



				<tr>
					<td colspan="4" align="right">
						<div class="jbutton">
							<input type="submit" name="SUBMIT" value="Get"
								style="width: 80px;">
						</div>
					</td>
				</tr>
			</table>


		</form>

	</div>
</div>

<%
	} else if (buttonName.equalsIgnoreCase("Get")) {
			String chartType = (String) request
					.getParameter("chartType");
			ArrayList<TestSuite> testSuites = new ReportTrend()
					.getTrendReport(
							(String) request
									.getParameter(Keys.REPORT_FOR),
							Integer.parseInt((String) request
									.getParameter("references")),
							(String) request
									.getParameter(Keys.REPORT_DATE_FROM),
							(String) request
									.getParameter(Keys.REPORT_DATE_TO));
			String passedCases = null;
			String failedCases = null;
			String skippedCases = null;
			long timeStamp;

			String testRef = null;
			String suiteName = null;
			String reportFor = (String) request
					.getParameter(Keys.REPORT_FOR);
			String dateRange = "";
			if (reportFor.equalsIgnoreCase(Keys.REPORT_FOR_CUSTOM)) {
				dateRange = "{"+ (String) request.getParameter(Keys.REPORT_DATE_FROM);
				dateRange += " TO "+ (String) request.getParameter(Keys.REPORT_DATE_TO)+ "}";
			}
			if (testSuites.size() > 0) {
				testRef = testSuites.get(testSuites.size() - 1)
						.getTestReference();
				suiteName = testSuites.get(testSuites.size() - 1)
						.getTestSuiteName();
			}

			for (TestSuite tmpSuite : testSuites) {
				timeStamp = tmpSuite.getLocalStartTime().getTime();
				if (passedCases != null) {
					passedCases 	+= ",[" + timeStamp + ","+ tmpSuite.getPassedCases() + ","+tmpSuite.getId()+"]";
					failedCases 	+= ",[" + timeStamp + ","+ tmpSuite.getFailedCases() + ","+tmpSuite.getId()+"]";
					skippedCases 	+= ",[" + timeStamp + ","+ tmpSuite.getSkippedCases() + ","+tmpSuite.getId()+"]";
				} else {
					passedCases = "["+ timeStamp + ","+ tmpSuite.getPassedCases() +","+tmpSuite.getId()+"]";
					failedCases = "[" + timeStamp + ","+ tmpSuite.getFailedCases() + ","+tmpSuite.getId()+"]";
					skippedCases = "[" + timeStamp + ","+ tmpSuite.getSkippedCases() + ","+tmpSuite.getId()+"]";
				}
			}
%>

<script type="text/javascript">
		
		$(function () {
			function prepare(dataArray) {
			    return dataArray.map(function (item, index) {
			        return {x: item[0], y: item[1], myIndex: item[2]};
			    });
			};

		    $(document).ready(function() {
		    	Highcharts.setOptions({
				        colors: ['#008000', '#FF0000', '#800000', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4'],
				        chart: {
				            backgroundColor: {
				               linearGradient: [0, 0, 500, 500],
				               stops: [
				                  [0, 'rgb(255, 255, 255)'],
				                  [1, 'rgb(240, 240, 255)']
				               ]
				            },
				            borderWidth: 2,
				            plotBackgroundColor: 'rgba(255, 255, 255, .9)',
				            plotShadow: true,
				            plotBorderWidth: 1,
				            global : {
				    			useUTC : false
				    		}
				         }
				    });
		    		
		    	// Create the chart
				window.chart = new Highcharts.StockChart({
					chart : {
						renderTo : 'chartContainer'
					},
					yAxis :{
						min:0
					},
					 credits: {
			            	enabled: false
			         },
			         rangeSelector : {
							buttons: [{
								type: 'month',
								count: 1,
								text: '1m'
							}, {
								type: 'month',
								count: 2,
								text: '2m'
							}, {
								type: 'month',
								count: 3,
								text: '3m'
							}, {
								type: 'month',
								count: 6,
								text: '6m'
							}, {
								type: 'year',
								count: 1,
								text: '1y'
							}, {
								type: 'all',
								text: 'All'
							}],
							selected : 5 // all
						},
					title : {
						text : '<%=suiteName%><br>[</I>Reference: <I><%=testRef%>, </I>Date Range:</b><%=reportFor + dateRange%></I>]'
					},
					legend :{
						enabled:true,
						align: 'right',
		                x: 0,
		                verticalAlign: 'top',
		                y: 0,
		                floating: true,
		                backgroundColor: 'white',
		                borderColor: '#CCC',
		                borderWidth: 1,
		                shadow: false
					},
					exporting: {
							buttons:{
								exportButton:{
									align:"right",
									x: -300
								},
								printButton:{
									align: 'right',
									x: -265
								}
							},
		    				enabled: true,
		    				filename: "<%=suiteName%>"
		    		},
		            plotOptions: {
	    	            series: {
	    	                point: {
	    	                    events: {
	    	                        click: function () {
	    	                        	window.location.href = 'reportsTestGroups.jsp?id='+this.myIndex;
	    	                        }
	    	                    }
	    	                }
	    	            }
	    	        },
		    			series : [{
		    				name: 'Passed',
		    				data : prepare([<%=passedCases%>]),
		    				type : '<%=chartType%>',
		    				threshold : null
		    			},
		    			{
		    				name: 'Failed',
		    				data : prepare([<%=failedCases%>]),
		    				type : '<%=chartType%>',
		    				threshold : null
		    			},
		    			{
		    				name: 'Skipped',
		    				data : prepare([<%=skippedCases%>]),
		    				type : '<%=chartType%>',
		    				threshold : null
		    			}]
		    		});
			});

	});
		    	

				</script>
<%@ include file="index-part2.jsp"%>

<div id="dt_page">
	<div id="container">
		<h1>
			Trend Report: <font size="2">[<b>Test Suite: </b><I><%=suiteName%>,</I>
				<b>Reference:</b> <I><%=testRef%>, </I><b>Date Range:</b> <I><%=reportFor + dateRange%></I>]
			</font>
		</h1>
		<div id="chartContainer" style="min-width: 90%;"></div>
	</div>
</div>
<%
	}
%>

<%@ include file="index-part3.jsp"%>

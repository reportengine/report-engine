<%@ include file="index-part1.jsp"%>
<%
String buttonName = (String) request.getParameter(Keys.SUBMIT);
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
		<h1>CPU/Memory Report:</h1>

		<form method="post" name="cpuChart" action="chartCpuMemory.jsp">
			<table border="0" cellpadding="3" align="left" id="dt_table">

				<tr>
					<td align="left">Server</td>
					<td>:</td>
					<td colspan="2"><select data-placeholder="Choose a Server..."  tabindex="1" class="chosen" id="<%=Keys.SERVER_ID%>" name="<%=Keys.SERVER_ID%>">            <option value=""></option> 
							<%
								ArrayList<Server> servers = new ServerTable().get();
										for (Server server : servers) {
											out.println("<option value=\"" + server.getId() + "\">"
													+ server.getName() + " [" + server.getHostIp()
													+ "]" + "</option>");
										}
							%>
					</select></td>
				</tr>

				<tr>
					<td align="left">CPU</td>
					<td>:</td>
					<td colspan="2"><input type="checkbox" name="<%=Keys.RESOURCE_CPU%>" id="<%=Keys.RESOURCE_CPU%>" value="Enabled" checked disabled="disabled"></td>
				</tr>
				
				<tr>
					<td align="left">Actual Memory</td>
					<td>:</td>
					<td colspan="2"><input type="checkbox" name="<%=Keys.RESOURCE_MEMORY_AU%>" id="<%=Keys.RESOURCE_MEMORY_AU%>" value="Enabled" checked disabled="disabled"></td>
				</tr>
				
				<tr>
					<td align="left">Memory</td>
					<td>:</td>
					<td colspan="2"><input type="checkbox" name="<%=Keys.RESOURCE_MEMORY%>" id="<%=Keys.RESOURCE_MEMORY%>" value="Enabled"></td>
				</tr>
				
				<tr>
					<td align="left">CPUs</td>
					<td>:</td>
					<td colspan="2"><input type="checkbox" name="<%=Keys.RESOURCE_CPUS%>" id="<%=Keys.RESOURCE_CPUS%>" value="Enabled" ></td>
				</tr>
				
				<tr>
					<td align="left">Swap</td>
					<td>:</td>
					<td colspan="2"><input type="checkbox" name="<%=Keys.RESOURCE_SWAP%>" id="<%=Keys.RESOURCE_SWAP%>" value="Enabled" ></td>
				</tr>

				<tr>
					<td align="left">Report For</td>
					<td>:</td>
					<td colspan="2"><select data-placeholder="Choose a Server..."  tabindex="2" class="chosen" id="<%=Keys.REPORT_FOR%>"
						name="<%=Keys.REPORT_FOR%>" onchange="dateOption(this)">
							<option value="<%=Keys.REPORT_FOR_LAST_30_MINS%>">Last 30 Minutes</option>
							<option value="<%=Keys.REPORT_FOR_LAST_60_MINS%>">Last 60 Minutes</option>
							<option value="<%=Keys.REPORT_FOR_LAST_3_HOURS%>">Last 3 Hours</option>
							<option value="<%=Keys.REPORT_FOR_LAST_6_HOURS%>">Last 6 Hours</option>
							<option value="<%=Keys.REPORT_FOR_LAST_12_HOURS%>">Last 12 Hours</option>
							<option value="<%=Keys.REPORT_FOR_LAST_24_HOURS%>">Last 24 Hours</option>
							<option value="<%=Keys.REPORT_FOR_CUSTOM%>">Custom</option>
					</select></td>
				</tr>

				<tr id="<%=Keys.REPORT_DATE_TR%>">
					<td align="left">Date Range</td>
					<td>:</td>
					<td align="left" colspan="2"><input type="text"id="<%=Keys.REPORT_DATE_FROM%>" name="<%=Keys.REPORT_DATE_FROM%>"size="8" readonly value="" style="width: 100px;"> 
					<select name="<%=Keys.REPORT_HOUR_FROM%>" style="width:50px;"><option selected>00</option><option>01</option><option>02</option><option>03</option><option>04</option><option>05</option><option>06</option><option>07</option><option>08</option><option>09</option><option>10</option><option>11</option><option>12</option><option>13</option><option>14</option><option>15</option><option>16</option><option>17</option><option>18</option><option>19</option><option>20</option><option>21</option><option>22</option><option>23</option></select>
					: <select name="<%=Keys.REPORT_MINUTE_FROM%>" style="width:50px;" length="10"><option selected>00</option><option>01</option><option>02</option><option>03</option><option>04</option><option>05</option><option>06</option><option>07</option><option>08</option><option>09</option><option>10</option><option>11</option><option>12</option><option>13</option><option>14</option><option>15</option><option>16</option><option>17</option><option>18</option><option>19</option><option>20</option><option>21</option><option>22</option><option>23</option><option>24</option><option>25</option><option>26</option><option>27</option><option>28</option><option>29</option><option>30</option><option>31</option><option>32</option><option>33</option><option>34</option><option>35</option><option>36</option><option>37</option><option>38</option><option>39</option><option>40</option><option>41</option><option>42</option><option>43</option><option>44</option><option>45</option><option>46</option><option>47</option><option>48</option><option>49</option><option>50</option><option>51</option><option>52</option><option>53</option><option>54</option><option>55</option><option>56</option><option>57</option><option>58</option><option>59</option> </select>
					to <input type="text" id="<%=Keys.REPORT_DATE_TO%>"name="<%=Keys.REPORT_DATE_TO%>" size="8" readonly value="" style="width: 100px;">
					<select name="<%=Keys.REPORT_HOUR_TO%>" style="width:50px;"><option>00</option><option>01</option><option>02</option><option>03</option><option>04</option><option>05</option><option>06</option><option>07</option><option>08</option><option>09</option><option>10</option><option>11</option><option>12</option><option>13</option><option>14</option><option>15</option><option>16</option><option>17</option><option>18</option><option>19</option><option>20</option><option>21</option><option>22</option><option selected>23</option></select>
					: <select name="<%=Keys.REPORT_MINUTE_TO%>" style="width:50px;" length="10"><option>00</option><option>01</option><option>02</option><option>03</option><option>04</option><option>05</option><option>06</option><option>07</option><option>08</option><option>09</option><option>10</option><option>11</option><option>12</option><option>13</option><option>14</option><option>15</option><option>16</option><option>17</option><option>18</option><option>19</option><option>20</option><option>21</option><option>22</option><option>23</option><option>24</option><option>25</option><option>26</option><option>27</option><option>28</option><option>29</option><option>30</option><option>31</option><option>32</option><option>33</option><option>34</option><option>35</option><option>36</option><option>37</option><option>38</option><option>39</option><option>40</option><option>41</option><option>42</option><option>43</option><option>44</option><option>45</option><option>46</option><option>47</option><option>48</option><option>49</option><option>50</option><option>51</option><option>52</option><option>53</option><option>54</option><option>55</option><option>56</option><option>57</option><option>58</option><option selected>59</option> </select>
					</td>
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
<script type="text/javascript"> 
      $('.chosen').chosen({ width: "300px" });
  </script>


		</form>

	</div>
</div>

<%
}else if(buttonName.equalsIgnoreCase(Keys.TEST_SUITE) || buttonName.equalsIgnoreCase(Keys.TEST_CASE)){
	TestSuite testSuite = null;
		ChartData data = new ChartData();
	if(buttonName.equalsIgnoreCase(Keys.TEST_SUITE)){
		testSuite = new TestSuiteTable().get(Integer.valueOf(request.getParameter(Keys.TEST_SUITE_ID)));
		data.setReportDateFrom(testSuite.getLocalStartTime());
		data.setReportDateTo(testSuite.getLocalEndTime());
	}else if(buttonName.equalsIgnoreCase(Keys.TEST_CASE)){
		TestCase testCase = new TestCaseTable().getDetailed(Integer.valueOf(request.getParameter(Keys.TEST_CASE_ID)));
		data.setReportDateFrom(testCase.getLocalStartTime());
		data.setReportDateTo(testCase.getLocalEndTime());
		testSuite = new TestSuiteTable().get(testCase.getTestSuiteId());
	}
	%>
<script type="text/javascript">
$(function() {
  $( "#test-suite-resource-usage-tabs" ).tabs();
});
</script>
	<%@ include file="index-part2.jsp"%>
	<div id="dt_page">
		<div id="container">
		<h1>Resource Utilization: <font size="2">[<b>Test Suite: </b><I><%=testSuite.getTestSuiteName()%>,</I> <b>Build:</b> <I><%=testSuite.getTestBuild()%></I>]</font></h1>
		<%
		ArrayList<TestReferenceServerMap> serverMaps = new TestReferenceServerMapTable().getDetailByTestRefId(testSuite.getTestReferenceId());
		if(serverMaps.size() == 0){
			%>
			<div class="ui-widget">
  				<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0pt 0.7em;"> 
  				<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: 0.3em;"></span>
  				<div>There is no server mapped with this test suite...</div></p>
  				</div>
  			</div>
			<%
		}else{		
		%>
		<div id="test-suite-resource-usage-tabs" style="overflow:hidden;">
  			<%
  			StringBuilder tabHeader = new StringBuilder("<ul>\n");			
			for(TestReferenceServerMap server : serverMaps){
				new AjaxServerInfo().setKeyValueTabsHeading(tabHeader, server.getServerName());
			}			
			tabHeader.append("\n</ul>");
  			out.println(tabHeader.toString());
  			String type = request.getParameter(Keys.SUBMIT);
				
				data.setReportDateFrom(testSuite.getLocalStartTime());
				if(data.getReportDateTo() == null){
					data.setReportDateTo(new Date());
				}

				//data.setServerId(Integer.valueOf(request.getParameter(Keys.SERVER_ID)));
				data.setSelectedResources("'"+Keys.RESOURCE_CPU+"','"+Keys.RESOURCE_MEMORY_AU+"'");
				data.setReportFor(Keys.REPORT_FOR_CUSTOM);
				Server server = null;
  			for(TestReferenceServerMap serverMap : serverMaps){
  				data.setServerId(serverMap.getServerId());
  				%>
  				<div id="<%=serverMap.getServerName().toLowerCase().replaceAll("\\s+", "-").replaceAll("\\.", "-")%>">
  				<%
  				if(new ChartCpuMemory().isDataAvailable(data, RESOURCE_TYPE.CPU)){
  					server = new ServerTable().getById(data.getServerId());
  		  			%>  			  			
  		  			
  					<script type="text/javascript">
  					$(function() {
  						$('#error-info<%=data.getServerId()%>').hide();
  						var seriesOptions = [],
  						yAxisOptions = [],
  						seriesCounter = 0,
  						names = [<%=data.getSelectedResources()%>],
  						colors = Highcharts.getOptions().colors;
  						var err ;
  					$.each(names, function(i, name) {
  						$.getJSON($(location).attr('href').replace(new RegExp("chartCpuMemory.jsp.*"), '')+'ajaxChartCpuMemory.jsp?'+'<%=Keys.SERVER_ID%>=<%=data.getServerId()%>&<%=Keys.REPORT_FOR%>=<%=data.getReportFor()%>&<%=Keys.REPORT_DATE_FROM%>=<%=URLEncoder.encode(new SimpleDateFormat(General.guiInputDateTimeFormat).format(data.getReportDateFrom()), Keys.URL_ENCODE_UTF_8)%>&<%=Keys.REPORT_DATE_TO%>=<%=URLEncoder.encode(new SimpleDateFormat(General.guiInputDateTimeFormat).format(data.getReportDateTo()), Keys.URL_ENCODE_UTF_8)%>&<%=Keys.RESOURCE_TYPE%>='+ name).done(function(data) {
  				
  						seriesOptions[i] = {
  							name: name,
  							data: data
  						};

  						// As we're loading the data asynchronously, we don't know what order it will arrive. So
  						// we keep a counter and create the chart when all the data is loaded.
  						seriesCounter++;

  						if (seriesCounter == names.length) {
  							if(err == null){
  								createChart();
  							}
  						}
  					}).fail(function(jqxhr, textStatus, error){
  						err = textStatus + ', ' + error;
  						$('#error-info<%=data.getServerId()%>').show();
  					});
  				});

  				// create the chart when all data is loaded
  				function createChart() {
					$('#chartContainer<%=data.getServerId()%>').highcharts('StockChart', {
  				    	chart: {
  				    	},

  				    	title: {
  					    	text: 'CPU/Memory Report'
  						},
  						subtitle: {
  					    	text: 'Server: <%=server.getName()%> [<%=server.getHostIp()%>]'
  						},
  						credits: {
  			    			enabled: false
  					    },	
  						navigator:{
  							adaptToUpdatedData: false
  					    },
  						legend: {
  							enabled:true,
  			            	align: 'right',
  			            	x: -100,
  			            	verticalAlign: 'top',
  			            	y: 0,
  			            	floating: true,
  			            	backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColorSolid) || 'white',
  			            	borderColor: '#CCC',
  			            	borderWidth: 1,
  			            	shadow: false
  			        	},
  						rangeSelector: {
	  						enabled: false
  						},
	
  					    yAxis: {
  					        title: {
  					            text: 'Used %'
  					        },
  				    	    min: 0,
  				        	max: 100
	  				    },
  					    
  					    plotOptions: {
  					    	series: {
  					    		//compare: 'percent'
  				    		}
  				    	},
  				    
	  				    tooltip: {
  			        		xDateFormat: '%b %e, %H:%M:%S <%=new SimpleDateFormat("z").format(new Date())%> %Y',
  					    	pointFormat: '<span style="color:{series.color}">{series.name}</span>: {point.y}%<br/>',
  					    	valueDecimals: 2
  					    },
  				    	
	  				    series: seriesOptions
  					});
  				}
  			});
			</script>

  			<div id="chartContainer<%=data.getServerId()%>" style="min-width: 90%;"></div>
	  		
	  		<div id="error-info<%=data.getServerId()%>" class="ui-widget">
  				<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0pt 0.7em;"> 
  					<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: 0.3em;"></span>
  					<div id="error-info-msg<%=data.getServerId()%>">Data not available for this date/time range...</div></p>
  				</div>
  			</div>
  		</div>
  		  			<%
  				}else{
  					%>
  					<div id="error-info<%=data.getServerId()%>" class="ui-widget">
  						<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0pt 0.7em;"> 
  						<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: 0.3em;"></span>
  						<div id="error-info-msg<%=data.getServerId()%>">Data not available for this date/time range...</div></p>
  						</div>
  					</div>
  					</div>
  					<%
  				}
  				
    		}
		}
  			%>
  			</div>
  			</div>
			</div>


	<%
	
	
}else if (buttonName.equalsIgnoreCase("Get")) {
			int serverId = Integer.parseInt((String) request.getParameter(Keys.SERVER_ID));	
			Server server = new ServerTable().getById(serverId);
			String fromStrDate = request.getParameter(Keys.REPORT_DATE_FROM)+" "+request.getParameter(Keys.REPORT_HOUR_FROM)+":"+request.getParameter(Keys.REPORT_MINUTE_FROM)+":00";
			String toStrDate = request.getParameter(Keys.REPORT_DATE_TO)+" "+request.getParameter(Keys.REPORT_HOUR_TO)+":"+request.getParameter(Keys.REPORT_MINUTE_TO)+":59";
			String reportFor = (String) request.getParameter(Keys.REPORT_FOR);
			
			server = new ServerTable().getById(serverId);
			
			StringBuilder selectedResources = new StringBuilder();
			selectedResources.append("'").append(Keys.RESOURCE_CPU).append("'");
			selectedResources.append(",'").append(Keys.RESOURCE_MEMORY_AU).append("'");
			
			
			if(General.isCheckBoxEnabled((String)request.getParameter(Keys.RESOURCE_SWAP))){
				selectedResources.append(",'").append(Keys.RESOURCE_SWAP).append("'");
			}
			if(General.isCheckBoxEnabled((String)request.getParameter(Keys.RESOURCE_MEMORY))){
				selectedResources.append(",'").append(Keys.RESOURCE_MEMORY).append("'");
			}
			if(General.isCheckBoxEnabled((String)request.getParameter(Keys.RESOURCE_CPUS))){
				int cpuNos = new ServerCpuDetailTable().getByServerId(serverId).getTotalCores();
				for(int i=0; i<cpuNos; i++){
					selectedResources.append(",'").append(Keys.RESOURCE_CPU).append(Keys.RESOURCE_CPUS_SPLITER).append(i).append("'");
				}
			}
%>

<script type="text/javascript">
$(function() {
	$('#error-info').hide();
	var seriesOptions = [],
		yAxisOptions = [],
		seriesCounter = 0,
		names = [<%=selectedResources.toString()%>],
		colors = Highcharts.getOptions().colors;
	var err ;
	$.each(names, function(i, name) {
		$.getJSON($(location).attr('href').replace('chartCpuMemory.jsp','')+'ajaxChartCpuMemory.jsp?'+'<%=Keys.SERVER_ID%>=<%=serverId%>&<%=Keys.REPORT_FOR%>=<%=reportFor%>&<%=Keys.REPORT_DATE_FROM%>=<%=URLEncoder.encode(fromStrDate, Keys.URL_ENCODE_UTF_8)%>&<%=Keys.REPORT_DATE_TO%>=<%=URLEncoder.encode(toStrDate, Keys.URL_ENCODE_UTF_8)%>&<%=Keys.RESOURCE_TYPE%>='+ name).done(function(data) {
		
			seriesOptions[i] = {
				name: name,
				data: data
			};

			// As we're loading the data asynchronously, we don't know what order it will arrive. So
			// we keep a counter and create the chart when all the data is loaded.
			seriesCounter++;

			if (seriesCounter == names.length) {
				if(err == null){
					createChart();
				}
			}
		}).fail(function(jqxhr, textStatus, error){
			err = textStatus + ', ' + error;
			//$('#error-info-msg').html(err);
			$('#error-info').show();
		});
	});

	// create the chart when all data is loaded
	function createChart() {

		$('#chartContainer').highcharts('StockChart', {
		    chart: {
		    },

		    title: {
			    text: 'CPU/Memory Report'
			},
			subtitle: {
			    text: 'Server: <%=server.getName()%> [<%=server.getHostIp()%>]'
			},
			credits: {
	    			enabled: false
			    },	
			navigator:{
				adaptToUpdatedData: false
			    },
			legend: {
				enabled:true,
	            align: 'right',
	            x: -100,
	            verticalAlign: 'top',
	            y: 0,
	            floating: true,
	            backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColorSolid) || 'white',
	            borderColor: '#CCC',
	            borderWidth: 1,
	            shadow: false
	        },
			rangeSelector: {
				enabled: false
			},

		    yAxis: {
		        title: {
		            text: 'Used %'
		        },
		        min: 0,
		        max: 100
		    },
		    
		    plotOptions: {
		    	series: {
		    		//compare: 'percent'
		    	}
		    },
		    
		    tooltip: {
        		xDateFormat: '%b %e, %H:%M:%S <%=new SimpleDateFormat("z").format(new Date())%> %Y',
		    	pointFormat: '<span style="color:{series.color}">{series.name}</span>: {point.y}%<br/>',
		    	valueDecimals: 2
		    },
		    
		    series: seriesOptions
		});
	}
});

</script>

<%@ include file="index-part2.jsp"%>

<div id="dt_page">
	<div id="container">
		<h1>
			CPU/Memory Report: <font size="2">[<b>Server: </b><I><%=server.getName()%>,</I>
				<b>Host/IP:</b> <I><%=server.getHostIp()%>,</I> <b>Platform:</b> <I><%=server.getPlatform()%>,</I> <b>Time Range:</b> <I><%=reportFor%></I>]
			</font>
		</h1>
		<div id="chartContainer" style="min-width: 90%;"></div>
		<div id="error-info" class="ui-widget">
		<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0pt 0.7em;"> 
			<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: 0.3em;"></span>
			<div id="error-info-msg">Data not available for this date/time range...</div></p>
		</div>


	</div>
	</div>
</div>
<%
	}
%>

<%@ include file="index-part3.jsp"%>

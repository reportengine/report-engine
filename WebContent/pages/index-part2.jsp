 
</head>
<body>
<div class="<%=Settings.getMenuSkin()%>">  
<ul id="mega-menu" class="mega-menu">
	<li><a href="dashboard.jsp">Dashboard</a></li>
	<li><a href="#">Reports</a>
		<ul>
			<li><a href="#">Top 10 Reports</a>
			    <ul>
					<li><a href="reportsAllTestSuites.jsp?action=<%=Keys.ORDER_BY_LOCAL_START_TIME%>">Latest Runs</a></li>
					<li><a href="reportsAllTestSuites.jsp?action=<%=Keys.ORDER_BY_FAILED_CASES%>">Failed Cases</a></li>
					<li><a href="reportsAllTestSuites.jsp?action=<%=Keys.ORDER_BY_PASSED_CASES%>">Passed Cases</a></li>
					<li><a href="reportsAllTestSuites.jsp?action=<%=Keys.ORDER_BY_SKIPPED_CASES%>">Skipped Cases</a></li>
					<li><a href="reportsAllTestSuites.jsp?action=<%=Keys.ORDER_BY_TOTAL_CASES%>">Total Cases</a></li>
					<li><a href="reportsAllTestSuites.jsp?action=<%=Keys.ORDER_BY_TOTAL_DURATION%>">Total Duration</a></li>
				</ul>
			</li>
			<li><a href="#">Test Reports</a>
				<ul>
					<li><a href="reportsAllTestSuites.jsp">All Reports</a></li>
					<li><a href="testTrendReport.jsp">Trend Report</a></li>
				</ul>
			</li>	
		</ul>
	</li>
	
	<li><a href="#">Groups &amp; Schedules</a>
		<ul>
			<li><a href="#">Groups</a>
			    <ul>
			    	<li><a href="emailReport.jsp">Email Groups</a></li>
				</ul>
			</li>	
			<li><a href="#">Schedules</a>
			    <ul>
			    	<li><a href="scheduledJobsList.jsp">Schedules List</a></li>
				</ul>
			</li>	
		</ul>
	</li>

	<li><a href="#">Settings</a>
		<ul>
			<li><a href="#">Engine Settings</a>
				<ul>
					<li><a href="engineWidgetsSettings.jsp">Widgets</a></li>
					<li><a href="engineSettings.jsp">Common Settings</a></li>
				</ul>
	
			</li>
		</ul>
	</li>
	<li><a href="#">Help</a>
		<ul>
			<li><a href="#">Implementation</a>
			<ul>
				<li><a href="helpTestNGImplememtation.jsp">TestNG</a></li>
			</ul>
		</ul>
	</li>
	<li><a href="logout.jsp">Logout</a></li>
</ul>

</div>

</div>


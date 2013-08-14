<%@ include file="include/re_jsp.jsp"%>
</head>
<body>
	<div class="<%=Settings.getMenuSkin()%>">
		<ul id="mega-menu" class="mega-menu">
			<li><a href="dashboard.jsp">Dashboard</a></li>
			<li><a href="#">Servers</a>
				<ul>

					<li><a href="#">Servers</a>
						<ul>
							<li><a href="server.jsp">Available Servers</a></li>
						</ul></li>

					<li><a href="#">Graphical Reports</a>
						<ul>
							<li><a href="#">Server Availability (yet to implement)</a></li>
							<li><a href="chartCpuMemory.jsp">CPU/Memory</a></li>
						</ul></li>

					<li><a href="#">Live Data</a>
						<ul>
							<li><a href="serverLiveBasicData.jsp">Basic Info</a></li>
							<li><a href="serverLiveNetworkData.jsp">Network Info</a></li>
							<li><a href="serverLivePidData.jsp">Running Process</a></li>
							<li><a href="serverLiveDiskData.jsp">Disk Info</a></li>
						</ul></li>

				</ul></li>
			<li><a href="#">Test Suites</a>
				<ul>
					<li><a href="#">Top 15 Reports</a>
						<ul>
							<li><a href="reportsAllTestSuites.jsp?action=<%=Keys.ORDER_BY_LOCAL_START_TIME%>">Latest Runs</a></li>
							<li><a href="reportsAllTestSuites.jsp?action=<%=Keys.ORDER_BY_FAILED_CASES%>">Failed Cases</a></li>
							<li><a href="reportsAllTestSuites.jsp?action=<%=Keys.ORDER_BY_PASSED_CASES%>">Passed Cases</a></li>
							<li><a href="reportsAllTestSuites.jsp?action=<%=Keys.ORDER_BY_SKIPPED_CASES%>">Skipped Cases</a></li>
							<li><a href="reportsAllTestSuites.jsp?action=<%=Keys.ORDER_BY_TOTAL_CASES%>">Total Cases</a></li>
							<li><a href="reportsAllTestSuites.jsp?action=<%=Keys.ORDER_BY_TOTAL_DURATION%>">Total Duration</a></li>
						</ul></li>

					<li><a href="#">Test Reports</a>
						<ul>
							<li><a href="reportsAllTestSuites.jsp">All Reports</a></li>
							<li><a href="testTrendReport.jsp">Trend Report</a></li>
						</ul></li>

					<li><a href="#">Groups</a>
						<ul>
							<li><a href="emailReport.jsp">Email Groups</a></li>
						</ul></li>
				</ul></li>

			<li><a href="#">Scheduler</a>
				<ul>
					<li><a href="#">Schedules</a>
						<ul>
							<li><a href="scheduledJobsList.jsp?<%=Keys.TYPE%>=<%=JobClasses.TYPE.USER%>">User</a></li>
							<li><a href="scheduledJobsList.jsp?<%=Keys.TYPE%>=<%=JobClasses.TYPE.AGENT%>">Agent</a></li>
							<li><a href="scheduledJobsList.jsp?<%=Keys.TYPE%>=<%=JobClasses.TYPE.SERVER%>">Server</a></li>
							<li><a href="scheduledJobsList.jsp?<%=Keys.TYPE%>=<%=JobClasses.TYPE.SYSTEM%>">System</a></li>
						</ul></li>
				</ul></li>

			<li><a href="#">Settings</a>
				<ul>
					<li><a href="#">Engine Settings</a>
						<ul>
							<li><a href="engineWidgetsSettings.jsp">Themes</a></li>
							<li><a href="engineSettings.jsp">Common Settings</a></li>
						</ul></li>
				</ul></li>
			<li><a href="#">Help</a>
				<ul>
					<li><a href="#">Implementation</a>
						<ul>
							<li><a href="helpTestNGImplememtation.jsp">TestNG</a></li>
						</ul>
				</ul></li>
			<li><a href="logout.jsp">Logout</a></li>
		</ul>

	</div>

	</div>

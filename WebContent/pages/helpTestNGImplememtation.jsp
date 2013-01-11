<%@ include file="index-part1.jsp"%>
<%@ include file="index-part2.jsp"%>

 
<div id="dt_page">
<div id="container">
<h1>how to implement <B><I>Report Engine Client</I></B> with <B><I>TestNG</I></B> suite?</h1>

<h2><b>Add the following jar files on your class path:</b></h2>
- cajo.jar<BR>
- ReportEngineClient.jar<BR>
- ReportEngineClient.properties<BR>

<h2><b>Add a Report Engine Listener on your &lt;testng&gt;.xml file:</b></h2>
  	&lt;listeners&gt;<br>
  	  	&lt;!-- Listener to send report to Report Engine Server --&gt;<br>
  	  	&lt;listener class-name="com.redhat.reportengine.client.ReportEngineTestNGListner" /&gt;<br>
  	&lt;/listeners&gt;<br>

<h2><b>ReportEngineClient.properties file:</b></h2>
# Report Engine Client Property File<br>
#************************************<br>
#This file is mandatory for Report Engine Client! DO NOT DELETE THIS!<br>
#You can over-right all these setting by adding 'System.setProperty' on your code<br>
# This file should be placed with <report-engine-client>.jar location, <br>
#If you do not want to keep Report Engine settings here redirect you properties file from here. <br>
#(Example: ORIGINAL.FILE=/opt/conf/MyReportEngineClient.properties) Else keep this 'ORIGINAL.FILE=' as a blank<br>
ORIGINAL.FILE=<br>
<br>
#Report Engine Server URL<br>
REPORT.ENGINE.SERVER=10.65.201.40<br>
<br>
#Report Engine Server RMI Port<br>
REPORT.ENGINE.RMI.PORT=9011<br>
<br>
#Your Reference for Report Engine Server. It could be any string or numbers. <br>
#This reference is used to compare your current test result with previous run.<br>
REPORT.ENGINE.TEST.REFERENCE=RHQ-4.x-GUI-OVER-NIGHT-AUTOMATION-JOB<br>
<br>
#Temporary location for client, Could be used to store screen shot, etc. Files will be cleaned on normal conditions<br>
REPORT.ENGINE.CLIENT.TEMP=/tmp<br>
<br>
# Enabled/Disabled Screen shot option for on test failure, Normally used for GUI test automation<br>
REPORT.ENGINE.TAKE.SCREEN.SHOT=Enabled<br>
<br>
#Your internal System Property reference to get build information.<br>
REPORT.ENGINE.TEST.BUILD.VERSION.REFF=rhq.build.version<br>
<br>
<BR>
<h1></h1>

</div>
</div>

<%@ include file="index-part3.jsp"%>


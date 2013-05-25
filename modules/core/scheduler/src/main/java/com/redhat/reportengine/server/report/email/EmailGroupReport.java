package com.redhat.reportengine.server.report.email;

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import com.redhat.reportengine.server.cache.ServerSettings;
import com.redhat.reportengine.server.dbdata.TestGroupTable;
import com.redhat.reportengine.server.dbmap.TestGroup;
import com.redhat.reportengine.server.dbmap.TestSuite;
import com.redhat.reportengine.server.reports.General;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jun 20, 2012
 */
public class EmailGroupReport {
	private static Logger _logger = Logger.getLogger(EmailGroupReport.class);

	static String html_header = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" +
			"<html xmlns=\"http://www.w3.org/1999/xhtml\">" +
			"<head>" +
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />" +
			"<title>Report Engine - Email Report</title>" +
			"<style type=\"text/css\">" +
			"body" +
			"{" +
			"line-height: 1.6em;" +
			"font-family: \"Lucida Sans Unicode\", \"Lucida Grande\", Sans-Serif;" +
			"font-size: 12px;" +
			"color: #039;" +
			"}" +
			"#email-table-style-a" +
			"{" +
			"font-family: \"Lucida Sans Unicode\", \"Lucida Grande\", Sans-Serif;" +
			"font-size: 12px;" +
			"background: #fff;" +
			"margin: 35px;" +
			"width: 93%;" +
			"border-collapse: collapse;" +
			"text-align: left;" +
			"}" +
			"#email-table-style-a th" +
			"{" +
			"font-size: 14px;" +
			"font-weight: normal;" +
			"color: #039;" +
			"padding: 10px 8px;" +
			"border-bottom: 2px solid #6678b1;" +
			"border-top: 2px solid #6678b1;" +
			"}" +
			"#email-table-style-a tfoot" +
			"{" +
			"font-size: 14px;" +
			"font-weight: normal;" +
			"color: #039;" +
			"padding: 10px 8px;" +
			"border-bottom: 2px solid #6678b1;" +
			"border-top: 2px solid #6678b1;" +
			"}" +
			"#email-table-style-a tline" +
			"{" +
			"border-top: 2px solid #6678b1;" +
			"}" +
			"#email-table-style-a td" +
			"{" +
			"border-bottom: 1px solid #ccc;" +
			"color: #669;" +
			"padding: 6px 8px;" +
			"}" +
			"</style>" +
			"</head>" +
			"<body>";

	static String table_header = "<table id=\"email-table-style-a\" summary=\"Test Execution Summary Report\">" +
			"<thead>" +
			" 	<tr>" +
			"      	<th scope=\"col\">Test Suite Name</th>" +
			"      	<th scope=\"col\">Total</th>" +
			"      	<th scope=\"col\">Passed</th>" +
			"      	<th scope=\"col\">Failed</th>" +
			"      	<th scope=\"col\">Skipped</th>" +
			"      	<th scope=\"col\">Build</th>" +
			"      	<th scope=\"col\">Start Time</th>" +
			"      	<th scope=\"col\">End Time</th>" +
			"      	<th scope=\"col\">Duration</th>" +
			"  </tr>" +
			"</thead>" +
			"    <tbody>";

	static String table_header_group_details = "<table id=\"email-table-style-a\" summary=\"Test Suite Group Details\">" +
			"<thead>" +
			"   <tr>" +
			"        <th scope=\"col\">Group Name</th>" +
			"        <th scope=\"col\">Total</th>" +
			"        <th scope=\"col\">Passed</th>" +
			"        <th scope=\"col\">Failed</th>" +
			"        <th scope=\"col\">Skipped</th>" +
			"  </tr>" +
			"</thead>" +
			"    <tbody>";

	public String getReport(ArrayList<TestSuite> testSuites, String reportName, boolean isTestSuiteGroupEnabled) throws Exception{
		StringBuffer finalReport = new StringBuffer();
		finalReport.append(html_header);
		finalReport.append("Hello, This is auto generated email from Report Engine. It's based on <b>").append(reportName).append("</b>");
		finalReport.append(table_header);

		TestSuite tmpTestSuite = new TestSuite();
		for(TestSuite testSuite : testSuites){
			tmpTestSuite.setTotalCases(testSuite.getTotalCases() + tmpTestSuite.getTotalCases());
			tmpTestSuite.setPassedCases(testSuite.getPassedCases() + tmpTestSuite.getPassedCases());
			tmpTestSuite.setFailedCases(testSuite.getFailedCases() + tmpTestSuite.getFailedCases());
			tmpTestSuite.setSkippedCases(testSuite.getSkippedCases() + tmpTestSuite.getSkippedCases());
			tmpTestSuite.setTestDuration(testSuite.getTestDuration() + tmpTestSuite.getTestDuration());
			finalReport.append("<tr>");
			finalReport.append("<td>").append(testSuite.getTestSuiteName()).append("</td>");
			finalReport.append("<td>").append("<b>").append(testSuite.getTotalCases()).append("</b>").append(General.getColor(testSuite.getTotalChanges(), true)).append("</td>");
			finalReport.append("<td>").append("<font color=\"green\"><b>").append(testSuite.getPassedCases()).append("</font></b>").append(General.getColor(testSuite.getPassedChanges(), true)).append("</td>");
			finalReport.append("<td>").append("<font color=\"red\"><b>").append(testSuite.getFailedCases()).append("</font></b>").append(General.getColor(testSuite.getFailedChanges(), false)).append("</td>");
			finalReport.append("<td>").append("<font color=\"brown\"><b>").append(testSuite.getSkippedCases()).append("</font></b>").append(General.getColor(testSuite.getSkippedChanges(), false)).append("</td>");
			finalReport.append("<td>").append(General.getBuildDetails(testSuite.getTestBuild())).append("</td>");
			finalReport.append("<td>").append(General.getGuiDateTime(testSuite.getLocalStartTime())).append("</td>");
			finalReport.append("<td>").append(General.getGuiDateTime(testSuite.getLocalEndTime())).append("</td>");
			finalReport.append("<td>").append(General.getGuiDuration(testSuite.getTestDuration())).append("</td>");
			finalReport.append("</tr>");
		}
		finalReport.append("<tfoot><tr>");
		finalReport.append("<td><b>Summary:</b></td>");
		finalReport.append("<td>").append("<b>").append(tmpTestSuite.getTotalCases()).append("</b>").append("</td>");
		finalReport.append("<td>").append("<font color=\"green\"><b>").append(tmpTestSuite.getPassedCases()).append("</font></b>").append("</td>");
		finalReport.append("<td>").append("<font color=\"red\"><b>").append(tmpTestSuite.getFailedCases()).append("</font></b>").append("</td>");
		finalReport.append("<td>").append("<font color=\"brown\"><b>").append(tmpTestSuite.getSkippedCases()).append("</font></b>").append("</td>");
		finalReport.append("<td></td>");
		finalReport.append("<td></td>");
		finalReport.append("<td></td>");
		finalReport.append("<td>").append("<b>").append(General.getGuiDuration(tmpTestSuite.getTestDuration())).append("</b>").append("</td>");
		finalReport.append(" </tr></tfoot></tbody></table>");
		finalReport.append("</BR>");

		if(isTestSuiteGroupEnabled){
			finalReport.append("<b><u>Test Suite Detaild View:</u></b><BR><BR>");
			for(TestSuite testSuite : testSuites){
				ArrayList<TestGroup> testGroups = new TestGroupTable().getCount(testSuite.getId());
				finalReport.append("Test Suite: <b>").append(testSuite.getTestSuiteName()).append("</b>");
				finalReport.append(table_header_group_details);
				for(TestGroup testGroup : testGroups){
					finalReport.append("<tr>");
					finalReport.append("<td>").append(testGroup.getTestGroup()).append("</td>");
					finalReport.append("<td>").append("<b>").append(testGroup.getTotalCases()).append("</b></td>");
					finalReport.append("<td>").append("<font color=\"green\"><b>").append(testGroup.getPassedCases()).append("</font></b></td>");
					finalReport.append("<td>").append("<font color=\"red\"><b>").append(testGroup.getFailedCases()).append("</font></b></td>");
					finalReport.append("<td>").append("<font color=\"brown\"><b>").append(testGroup.getSkippedCases()).append("</font></b></td>");
					finalReport.append(" </tr>");          
				}  
				finalReport.append("<tfoot><tr>");
				finalReport.append("<td><b>Total:</b></td>");
				finalReport.append("<td>").append("<b>").append(testSuite.getTotalCases()).append("</b>").append("</td>");
				finalReport.append("<td>").append("<font color=\"green\"><b>").append(testSuite.getPassedCases()).append("</font></b>").append("</td>");
				finalReport.append("<td>").append("<font color=\"red\"><b>").append(testSuite.getFailedCases()).append("</font></b>").append("</td>");
				finalReport.append("<td>").append("<font color=\"brown\"><b>").append(testSuite.getSkippedCases()).append("</font></b>").append("</td>");
				finalReport.append(" </tr></tfoot></tbody></table>");
			}
		}

		finalReport.append("Report Engine URL: <a href=\"").append(ServerSettings.getEngineURL()).append("\">").append(ServerSettings.getEngineURL()).append("</a>");
		finalReport.append("</BR>");
		finalReport.append("Report Generated on: ").append(General.getGuiDateTime(new Date()));
		finalReport.append("</BR>");
		finalReport.append("</body></html>");
		_logger.debug(finalReport.toString());
		return finalReport.toString();		
	}
}

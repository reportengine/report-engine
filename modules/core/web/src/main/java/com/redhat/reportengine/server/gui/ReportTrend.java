package com.redhat.reportengine.server.gui;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.redhat.reportengine.server.dbdata.TestSuiteTable;
import com.redhat.reportengine.server.dbmap.TestSuite;
import com.redhat.reportengine.server.reports.General;
import com.redhat.reportengine.server.reports.Keys;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Sep 12, 2012
 */
public class ReportTrend {
	public ArrayList<TestSuite>  getTrendReport(String reportFor, int testReferenceId, String fromStrDate, String toStrDate) throws ParseException, SQLException{
		Calendar fromDate = Calendar.getInstance();
		fromDate.set(Calendar.AM_PM, 0);
		fromDate.set(Calendar.HOUR, 0);
		fromDate.set(Calendar.MINUTE, 0);
		fromDate.set(Calendar.SECOND, 0);
		fromDate.set(Calendar.MILLISECOND, 0); //Setting from time : 00:00:00.000
		Calendar toDate = (Calendar) fromDate.clone();
		toDate.set(Calendar.MILLISECOND, (24*60*60*1000)-1); //Setting to time till 23:59:59.999
		if(reportFor.equalsIgnoreCase(Keys.REPORT_FOR_LAST_7_DAYS)){
			fromDate.add(Calendar.DAY_OF_MONTH, -7);
		}else if(reportFor.equalsIgnoreCase(Keys.REPORT_FOR_LAST_15_DAYS)){
			fromDate.add(Calendar.DAY_OF_MONTH, -15);
		}else if(reportFor.equalsIgnoreCase(Keys.REPORT_FOR_LAST_30_DAYS)){
			fromDate.add(Calendar.DAY_OF_MONTH, -30);
		}else if(reportFor.equalsIgnoreCase(Keys.REPORT_FOR_LAST_60_DAYS)){
			fromDate.add(Calendar.DAY_OF_MONTH, -60);
		}else if(reportFor.equalsIgnoreCase(Keys.REPORT_FOR_LAST_90_DAYS)){
			fromDate.add(Calendar.DAY_OF_MONTH, -90);
		}else if(reportFor.equalsIgnoreCase(Keys.REPORT_FOR_CUSTOM)){
			fromDate.setTime(new SimpleDateFormat(General.guiInputDateFormat).parse(fromStrDate));
			toDate.setTime(new SimpleDateFormat(General.guiInputDateFormat).parse(toStrDate));
		}
		TestSuite testSuite = new TestSuite();
		testSuite.setFromTime(new SimpleDateFormat(General.dbDateTimeFormat).format(fromDate.getTime()));
		testSuite.setToTime(new SimpleDateFormat(General.dbDateTimeFormat).format(toDate.getTime()));
		testSuite.setTestReferenceId(testReferenceId);
		testSuite.setOrderBy(Keys.ORDER_BY_LOCAL_START_TIME);
		return new TestSuiteTable().getTrendReport(testSuite);
	}
}

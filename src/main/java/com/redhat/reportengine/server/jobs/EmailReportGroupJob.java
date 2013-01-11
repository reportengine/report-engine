package com.redhat.reportengine.server.jobs;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.redhat.reportengine.server.ServerSettings;
import com.redhat.reportengine.server.dbmap.ReportGroup;
import com.redhat.reportengine.server.dbmap.ReportGroupReference;
import com.redhat.reportengine.server.dbmap.TestSuite;
import com.redhat.reportengine.server.email.SendEmail;
import com.redhat.reportengine.server.gui.Keys;
import com.redhat.reportengine.server.report.email.EmailGroupReport;
import com.redhat.reportengine.server.sql.SqlMap;
import com.redhat.reportengine.server.sql.SqlQuery;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jun 20, 2012
 */
public class EmailReportGroupJob implements Job{
	private static Logger _logger = Logger.getLogger(EmailReportGroupJob.class);
	
	public void executeEmailGroupReport(int id) throws Exception{
		ReportGroup reportGroup = (ReportGroup) SqlMap.getSqlMapClient().queryForObject(SqlQuery.GET_REPORT_GROUP_BY_ID, id);
		ArrayList<ReportGroupReference> reportGroupReferences = (ArrayList<ReportGroupReference>) SqlMap.getSqlMapClient().queryForList(SqlQuery.GET_REPORT_GROUP_REFERENCE_BY_REPORT_GROUP_ID, id);
		String testSuiteReferences = null;
		for(ReportGroupReference reportGroupReference : reportGroupReferences){
			if(testSuiteReferences == null){
				testSuiteReferences = ""+reportGroupReference.getTestReferenceId();
			}else{
				testSuiteReferences += ","+reportGroupReference.getTestReferenceId();
			}
		}
		ArrayList<TestSuite> testSuites = (ArrayList<TestSuite>) SqlMap.getSqlMapClient().queryForList(SqlQuery.GET_TOP_TEST_SUITE_BY_TEST_REFERENCE_IDS, testSuiteReferences);
		String emailBody = new EmailGroupReport().getGroupReport(testSuites, reportGroup.getGroupName());
		new SendEmail().sendEmail(reportGroup.getEmailTo(), reportGroup.getEmailCc(), ServerSettings.getEmailFrom(), "Summary report for '"+reportGroup.getGroupName()+"'", emailBody, ServerSettings.getEmailServer(), ServerSettings.getEmailServerPort());
	}

	@Override
	public void execute(JobExecutionContext jobDetails) throws JobExecutionException {
		JobDataMap jobDataMap = (JobDataMap)jobDetails.getJobDetail().getJobDataMap();
		try {
			executeEmailGroupReport(Integer.valueOf(""+jobDataMap.get(Keys.DATA_REFERENCDE_ID)));
		} catch (Exception ex) {
			_logger.error("Exception, ", ex);
		}
	}

}

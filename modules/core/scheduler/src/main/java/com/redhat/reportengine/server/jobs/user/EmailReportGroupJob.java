package com.redhat.reportengine.server.jobs.user;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.redhat.reportengine.server.cache.ServerSettings;
import com.redhat.reportengine.server.dbdata.ReportGroupReferenceTable;
import com.redhat.reportengine.server.dbdata.ReportGroupTable;
import com.redhat.reportengine.server.dbdata.TestSuiteTable;
import com.redhat.reportengine.server.dbmap.ReportGroup;
import com.redhat.reportengine.server.dbmap.ReportGroupReference;
import com.redhat.reportengine.server.dbmap.TestSuite;
import com.redhat.reportengine.server.email.SendEmail;
import com.redhat.reportengine.server.report.email.EmailGroupReport;
import com.redhat.reportengine.server.reports.Keys;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jun 20, 2012
 */
public class EmailReportGroupJob implements Job{
	private static Logger _logger = Logger.getLogger(EmailReportGroupJob.class);
	
	public void executeEmailGroupReport(int id) throws Exception{
		ReportGroup reportGroup = new ReportGroupTable().get(id);
		ArrayList<ReportGroupReference> reportGroupReferences = new ReportGroupReferenceTable().getByReportGroupId(id);
		String testSuiteReferences = null;
		for(ReportGroupReference reportGroupReference : reportGroupReferences){
			if(testSuiteReferences == null){
				testSuiteReferences = ""+reportGroupReference.getTestReferenceId();
			}else{
				testSuiteReferences += ","+reportGroupReference.getTestReferenceId();
			}
		}
		ArrayList<TestSuite> testSuites = new TestSuiteTable().getTopNByRefIds(testSuiteReferences);
		String emailBody = new EmailGroupReport().getReport(testSuites, reportGroup.getGroupName(), reportGroup.isTestSuiteGroupEnabled());
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

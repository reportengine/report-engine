/**
 * 
 */
package com.redhat.reportengine.server.jobs;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.redhat.reportengine.server.ServerSettings;
import com.redhat.reportengine.server.dbmap.TestCase;
import com.redhat.reportengine.server.dbmap.TestSuite;
import com.redhat.reportengine.server.sql.SqlMap;
import com.redhat.reportengine.server.sql.SqlQuery;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Apr 26, 2012
 */
public class UpdateJobStatus implements Job{
	final private static Logger _logger = Logger.getLogger(UpdateJobStatus.class);

	private void updateNoResultSuites() throws SQLException{
		ArrayList<TestSuite> testSuites = (ArrayList<TestSuite>) SqlMap.getSqlMapClient().queryForList(SqlQuery.GET_TEST_SUITES_RUNNING);
		_logger.debug("Size of Running Jobs: "+testSuites.size());
		long lastUpdateTime;
		for(TestSuite testSuite: testSuites){
			TestCase testCase = (TestCase)SqlMap.getSqlMapClient().queryForObject(SqlQuery.GET_TEST_CASE_LATEST_BY_SUITE_ID, testSuite.getId());
			if(testCase != null){
				_logger.info("Test Suite Name:"+testSuite.getTestSuiteName()+", ID:" +testSuite.getId()+", Test case: "+testCase.getTestName()+", Time: "+testCase.getLocalStartTime());
				lastUpdateTime = testCase.getLocalStartTime().getTime();
			}else{
				_logger.info("Test Suite Name:"+testSuite.getTestSuiteName()+", ID:" +testSuite.getId()+", [There is no test cases on RUNNING state on this suite!]");
				lastUpdateTime = testSuite.getLocalStartTime().getTime();
			}
			//TODO: compare latest running timestamp and make decision 
			if((new Date().getTime() - lastUpdateTime) >= (ServerSettings.getTestSuiteInactiveTime()*1000) ){
				if(testCase != null){
					testCase.setTestResultNew(TestCase.NO_STATUS);
					SqlMap.getSqlMapClient().update(SqlQuery.UPDATE_TEST_CASE_RESULT_BY_SUITE_ID, testCase);
				}
				testSuite.setTestStatus(TestSuite.NO_STATUS);
				SqlMap.getSqlMapClient().update(SqlQuery.UPDATE_TEST_SUITE_STATUS_ID, testSuite);
			}
		}
		//TODO: call aggregation
	}
	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext jobDetails) throws JobExecutionException {
		try {
			updateNoResultSuites();
		} catch (SQLException ex) {
			_logger.error("Exception on Update Job Status, ", ex);
		}
		
	}

}

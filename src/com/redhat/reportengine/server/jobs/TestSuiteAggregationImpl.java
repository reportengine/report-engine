/**
 * 
 */
package com.redhat.reportengine.server.jobs;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.redhat.reportengine.server.dbmap.TestCase;
import com.redhat.reportengine.server.dbmap.TestSuite;
import com.redhat.reportengine.server.dbmap.TestSuiteAggregation;
import com.redhat.reportengine.server.sql.SqlMap;
import com.redhat.reportengine.server.sql.SqlQuery;


/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 22, 2012
 */
public class TestSuiteAggregationImpl implements Job{
	final static Logger _logger = Logger.getLogger(TestSuiteAggregationImpl.class);
	
	public void doAggregateTestSuite() {
		try{
			TestSuite aggregationRef;
			ArrayList<TestSuiteAggregation> preAggregations;
			ArrayList<TestSuite> testSuites= (ArrayList<TestSuite>) SqlMap.getSqlMapClient().queryForList(SqlQuery.GET_TEST_SUITES_NON_AGGREGATE);
			for(TestSuite testSuite : testSuites){
				aggregationRef = (TestSuite) SqlMap.getSqlMapClient().queryForObject(SqlQuery.GET_TEST_SUITE_AGGREGATION_BY_TEST_REFERENCE_ID, testSuite.getTestReferenceId());
				preAggregations = (ArrayList<TestSuiteAggregation>) SqlMap.getSqlMapClient().queryForList(SqlQuery.GET_TEST_SUITE_PRE_AGGREGATION_BY_TEST_SUITE_ID, testSuite.getId());
				for(TestSuiteAggregation preAggregation : preAggregations){
					if(preAggregation.getTestResult().equalsIgnoreCase(TestCase.PASSED)){
						testSuite.setPassedCases(preAggregation.getTestCount());
					}else if(preAggregation.getTestResult().equalsIgnoreCase(TestCase.FAILED)){
						testSuite.setFailedCases(preAggregation.getTestCount());
					}else if(preAggregation.getTestResult().equalsIgnoreCase(TestCase.SKIPPED)){
						testSuite.setSkippedCases(preAggregation.getTestCount());
					}else{
						_logger.warn("Unknown result --> "+preAggregation.getTestResult()+", Adding in to total");
						testSuite.setTotalCases(preAggregation.getTestCount());
					}
				}				
				testSuite.setTotalCases(testSuite.getTotalCases() + testSuite.getPassedCases() + testSuite.getFailedCases() + testSuite.getSkippedCases());
				
				if(aggregationRef == null){
					testSuite.setTotalChanges(0);
					testSuite.setPassedChanges(0);
					testSuite.setFailedChanges(0);
					testSuite.setSkippedChanges(0);
				}else{
					testSuite.setTotalChanges(testSuite.getTotalCases() - aggregationRef.getTotalCases());
					testSuite.setPassedChanges(testSuite.getPassedCases() - aggregationRef.getPassedCases());
					testSuite.setFailedChanges(testSuite.getFailedCases() - aggregationRef.getFailedCases());
					testSuite.setSkippedChanges(testSuite.getSkippedCases() - aggregationRef.getSkippedCases());					
				}
				
				if(testSuite.getLocalEndTime() != null){
					testSuite.setTestDuration(testSuite.getLocalEndTime().getTime() - testSuite.getLocalStartTime().getTime());
				}
				
				testSuite.setTestSuiteId(testSuite.getId());
				SqlMap.getSqlMapClient().insert(SqlQuery.INSERT_TEST_SUITE_AGGREGATION, testSuite);
				SqlMap.getSqlMapClient().update(SqlQuery.ENABLE_TEST_SUITE_AGGREGATION_STATUS, testSuite.getId());
			}
		}catch(Exception ex){
			_logger.error("Exception, ", ex);
		}
		
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		new TestSuiteAggregationImpl().doAggregateTestSuite();
		
	}
}

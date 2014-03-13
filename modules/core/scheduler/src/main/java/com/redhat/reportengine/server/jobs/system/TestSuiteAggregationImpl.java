/**
 * 
 */
package com.redhat.reportengine.server.jobs.system;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.redhat.reportengine.server.dbdata.TestSuiteAggregationTable;
import com.redhat.reportengine.server.dbdata.TestSuiteTable;
import com.redhat.reportengine.server.dbmap.TestCase;
import com.redhat.reportengine.server.dbmap.TestSuite;
import com.redhat.reportengine.server.dbmap.TestSuiteAggregation;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 22, 2012
 */
public class TestSuiteAggregationImpl implements Job{
	final static Logger _logger = Logger.getLogger(TestSuiteAggregationImpl.class);

	public static synchronized void doAggregateTestSuite() {
		try{
			TestSuite aggregationRef;
			ArrayList<TestSuiteAggregation> preAggregations;
			ArrayList<TestSuite> testSuites = new TestSuiteTable().getNonAggregate();
			for(TestSuite testSuite : testSuites){
				aggregationRef = new TestSuiteTable().getAggregation(testSuite.getTestReferenceId());
				//Remove existing Aggregation reference if any and get the previous run status
				if(aggregationRef != null && aggregationRef.getTestSuiteId() == testSuite.getId()){
					_logger.info("Removing old Aggregation reference...[TestSuite: "+testSuite.getTestSuiteName()+", TestSuiteId: "+aggregationRef.getTestSuiteId()+", AggregationId:"+aggregationRef.getId()+"]");
					new TestSuiteAggregationTable().remove(aggregationRef.getTestSuiteId());
					aggregationRef = new TestSuiteTable().getAggregation(testSuite.getTestReferenceId());
				}
				
				preAggregations = new TestSuiteAggregationTable().get(testSuite.getId());
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
				new TestSuiteTable().addAggregation(testSuite);
				testSuite.setAggregationStatus(true);
				new TestSuiteTable().enableAggregationStatus(testSuite.getId());
				
				//calculate Metric for this test suite
				CalculateMetric.updateMetric(testSuite);
			}
		}catch(Exception ex){
			_logger.error("Exception, ", ex);
		}

	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		TestSuiteAggregationImpl.doAggregateTestSuite();
	}
}

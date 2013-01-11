/**
 * 
 */
package com.redhat.reportengine.server.gui;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.redhat.reportengine.server.dbmap.TestSuite;
import com.redhat.reportengine.server.sql.SqlMap;
import com.redhat.reportengine.server.sql.SqlQuery;



/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 17, 2012
 */
public class TestSuiteReport {
	private static Logger _logger = Logger.getLogger(TestSuiteReport.class);
	
	public ArrayList<TestSuite> getAllSuites() throws SQLException{
		ArrayList<TestSuite> testSuites= (ArrayList<TestSuite>) SqlMap.getSqlMapClient().queryForList(SqlQuery.GET_TEST_SUITES);
		return testSuites;
	}
	
	public TestSuite getTestSuiteDetails(int id) throws SQLException{
		return (TestSuite) SqlMap.getSqlMapClient().queryForObject(SqlQuery.GET_TEST_SUITE_BY_ID, id);
	}
	
	public ArrayList<TestSuite> getTopNTestSuites(int topN, String orderBy, boolean includeRunning) throws SQLException{
		TestSuite testSuite = new TestSuite();
		testSuite.setTopN(topN);
		testSuite.setOrderBy(orderBy);
		if(includeRunning){
			return (ArrayList<TestSuite>) SqlMap.getSqlMapClient().queryForList(SqlQuery.GET_TOP_N_TEST_SUITES, testSuite);
		}else{
			return (ArrayList<TestSuite>) SqlMap.getSqlMapClient().queryForList(SqlQuery.GET_TOP_N_TEST_SUITES_EXCLUDE_RUNNING, testSuite);
		}		
	}
	
	public ArrayList<TestSuite> getTopNTestReferenceAggregationDetails(int topN, String orderBy, int testReferenceId) throws SQLException{
		TestSuite testSuite = new TestSuite();
		testSuite.setTopN(topN);
		testSuite.setOrderBy(orderBy);
		testSuite.setTestReferenceId(testReferenceId);
		return (ArrayList<TestSuite>) SqlMap.getSqlMapClient().queryForList(SqlQuery.GET_TOP_N_TEST_REFERENCE_AGGREGATION_EXCLUDE_RUNNING, testSuite);		
	}
	
	public ArrayList<TestSuite> getTopNTestSuites(int topN, String orderBy) throws SQLException{
		return getTopNTestSuites(topN, orderBy, true);
	}
	
	
}

/**
 * 
 */
package com.redhat.reportengine.server.gui;

import java.sql.SQLException;
import java.util.ArrayList;

import com.redhat.reportengine.server.dbmap.FileStorage;
import com.redhat.reportengine.server.dbmap.TestCase;
import com.redhat.reportengine.server.sql.SqlMap;
import com.redhat.reportengine.server.sql.SqlQuery;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 19, 2012
 */
public class TestCaseReport {
	//private static Logger _logger = Logger.getLogger(TestCaseReport.class);
	
	public ArrayList<TestCase> getCases(int testSuiteId, int testGroupId) throws SQLException{
		TestCase testCase = new TestCase();
		testCase.setTestSuiteId(testSuiteId);
		testCase.setTestGroupId(testGroupId);
		ArrayList<TestCase> testCases = (ArrayList<TestCase>) SqlMap.getSqlMapClient().queryForList(SqlQuery.GET_TEST_CASES_REPORT, testCase);
		return testCases;
	}
	
	public ArrayList<TestCase> getCasesBySuiteIdAndStatus(int testSuiteId, String testStatus) throws SQLException{
		TestCase testCase = new TestCase();
		testCase.setTestSuiteId(testSuiteId);
		testCase.setTestResult(testStatus);
		ArrayList<TestCase> testCases = (ArrayList<TestCase>) SqlMap.getSqlMapClient().queryForList(SqlQuery.GET_TEST_CASES_REPORT_BY_SUITE_ID_AND_STATUS, testCase);
		return testCases;
	}
	
	public ArrayList<TestCase> getCasesByGroupIdAndStatus(int testGroupId, String testStatus) throws SQLException{
		TestCase testCase = new TestCase();
		testCase.setTestGroupId(testGroupId);
		testCase.setTestResult(testStatus);
		ArrayList<TestCase> testCases = (ArrayList<TestCase>) SqlMap.getSqlMapClient().queryForList(SqlQuery.GET_TEST_CASES_REPORT_BY_GROUP_ID_AND_STATUS, testCase);
		return testCases;
	}
	
	public TestCase getTestCaseDetail(int testCaseId) throws SQLException{
		return (TestCase) SqlMap.getSqlMapClient().queryForObject(SqlQuery.GET_TEST_CASE_DETAIL, testCaseId);
	}
	
	public ArrayList<FileStorage> getScreenShotImages(int testCaseId) throws SQLException{
		return (ArrayList<FileStorage>) SqlMap.getSqlMapClient().queryForList(SqlQuery.GET_FILE_STORAGE_BY_TEST_CASE_ID_SCREEN_SHOT, testCaseId);		
	}
	
}

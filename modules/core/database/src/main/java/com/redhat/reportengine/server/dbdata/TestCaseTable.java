package com.redhat.reportengine.server.dbdata;

import java.sql.SQLException;
import java.util.ArrayList;

import com.redhat.reportengine.server.dbmap.TestCase;
import com.redhat.reportengine.server.sql.SqlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * May 16, 2013
 */
public class TestCaseTable {
	private final static String INSERT_TEST_CASE								= "insertTestCase";
	private final static String UPDATE_TEST_CASE_RESULT_BY_SUITE_ID				= "updateTestCaseResult";
	private final static String UPDATE_TEST_CASE								= "updateTestCase";
	private final static String GET_TEST_CASE_ID								= "getTestCaseID";
	private final static String GET_TEST_CASES									= "getTestCases";
	private final static String GET_TEST_CASES_REPORT_SUITE_ID_GROUP_ID			= "getTestCasesReport";
	private final static String GET_TEST_CASES_REPORT_BY_SUITE_ID_ALL			= "getTestCasesReportBySuiteIdAll";
	private final static String GET_TEST_CASES_REPORT_BY_SUITE_ID_AND_STATUS	= "getTestCasesReportBySuiteIdAndStatus";
	private final static String GET_TEST_CASES_REPORT_BY_GROUP_ID_ALL			= "getTestCasesReportByGroupId";
	private final static String GET_TEST_CASES_REPORT_BY_GROUP_ID_AND_STATUS	= "getTestCasesReportByGroupIdAndStatus";	
	private final static String GET_TEST_CASE_DETAIL_BY_TEST_CASE_ID			= "getTestCaseDetailById";	
	private final static String GET_TEST_CASES_SCREEN_SHOT_BY_TEST_SUITE_ID		= "getTestCasesScreenShot";
	private final static String GET_TEST_CASE_LATEST_BY_SUITE_ID				= "getLatestTestCaseBySuiteID";
	private final static String DELETE_TEST_CASE_BY_TEST_SUITE_ID				= "deleteTestCaseByTestSuiteId";
		
	public void add(TestCase testCase) throws SQLException{
		SqlMap.getSqlMapClient().insert(INSERT_TEST_CASE, testCase);
	}
	
	public void modify(TestCase testCase) throws SQLException{
		SqlMap.getSqlMapClient().update(UPDATE_TEST_CASE, testCase);
	}
	
	public void modifyResult(TestCase testCase) throws SQLException{
		SqlMap.getSqlMapClient().update(UPDATE_TEST_CASE_RESULT_BY_SUITE_ID, testCase);
	}
	
	public TestCase getId(TestCase testCase) throws SQLException{
		return (TestCase)SqlMap.getSqlMapClient().queryForObject(GET_TEST_CASE_ID, testCase);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestCase> get(TestCase testCase) throws SQLException{
		return (ArrayList<TestCase>)SqlMap.getSqlMapClient().queryForList(GET_TEST_CASES, testCase);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestCase> getScreenShotBySuiteId(Integer testSuiteId) throws SQLException{
		return (ArrayList<TestCase>)SqlMap.getSqlMapClient().queryForList(GET_TEST_CASES_SCREEN_SHOT_BY_TEST_SUITE_ID, testSuiteId);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestCase> getDetailedAllByTestGroupIdAndTestSuiteId(TestCase testCase) throws SQLException{
		return (ArrayList<TestCase>)SqlMap.getSqlMapClient().queryForList(GET_TEST_CASES_REPORT_SUITE_ID_GROUP_ID, testCase);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestCase> getDetailedAllByTestSuiteId(TestCase testCase) throws SQLException{
		return (ArrayList<TestCase>)SqlMap.getSqlMapClient().queryForList(GET_TEST_CASES_REPORT_BY_SUITE_ID_ALL, testCase);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestCase> getDetailedAllByTestSuiteIdAndStatus(TestCase testCase) throws SQLException{
		return (ArrayList<TestCase>)SqlMap.getSqlMapClient().queryForList(GET_TEST_CASES_REPORT_BY_SUITE_ID_AND_STATUS, testCase);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestCase> getDetailedAllByTestGroupId(TestCase testCase) throws SQLException{
		return (ArrayList<TestCase>)SqlMap.getSqlMapClient().queryForList(GET_TEST_CASES_REPORT_BY_GROUP_ID_ALL, testCase);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestCase> getDetailedAllByTestGroupIdAndStatus(TestCase testCase) throws SQLException{
		return (ArrayList<TestCase>)SqlMap.getSqlMapClient().queryForList(GET_TEST_CASES_REPORT_BY_GROUP_ID_AND_STATUS, testCase);
	}
	
	public TestCase getDetailed(Integer testCaseId) throws SQLException{
		return (TestCase)SqlMap.getSqlMapClient().queryForObject(GET_TEST_CASE_DETAIL_BY_TEST_CASE_ID, testCaseId);
	}
	
	public TestCase getLatestByTestSuiteId(Integer testSuiteId) throws SQLException{
		return (TestCase)SqlMap.getSqlMapClient().queryForObject(GET_TEST_CASE_LATEST_BY_SUITE_ID, testSuiteId);
	}
	
	public void remove(int testSuiteId) throws SQLException{
		SqlMap.getSqlMapClient().delete(DELETE_TEST_CASE_BY_TEST_SUITE_ID, testSuiteId);
	}
	
}

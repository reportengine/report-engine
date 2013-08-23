package com.redhat.reportengine.server.dbdata;

import java.sql.SQLException;
import java.util.ArrayList;

import com.redhat.reportengine.server.dbmap.TestLogs;
import com.redhat.reportengine.server.sql.SqlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * May 16, 2013
 */
public class TestLogsTable {
	private final static String INSERT_TEST_LOGS				= "insertTestLogs";
	private final static String GET_TEST_LOGS_BY_TEST_SUITE_ID 	= "getTestLogsByTestSuiteId";
	private final static String GET_TEST_LOGS_BY_TEST_GROUP_ID 	= "getTestLogsByTestGroupId";
	private final static String GET_TEST_LOGS_BY_TEST_CASE_ID 	= "getTestLogsByTestCaseId";
	private final static String GET_TEST_LOGS_BY_ID 			= "getTestLogsById";
	
	private final static String GET_TEST_LOGS_BY_TEST_SUITE_ID_AJAX = "getTestLogsByTestSuiteIdAjax";
	private final static String GET_TEST_LOGS_BY_TEST_GROUP_ID_AJAX = "getTestLogsByTestGroupIdAjax";
	private final static String GET_TEST_LOGS_BY_TEST_CASE_ID_AJAX 	= "getTestLogsByTestCaseIdAjax";
	private final static String DELETE_TEST_LOGS_BY_TEST_SUITE_ID			= "deleteTestLogsByTestSuiteId";
	
	public void remove(Integer id) throws SQLException{
		SqlMap.getSqlMapClient().delete(DELETE_TEST_LOGS_BY_TEST_SUITE_ID, id);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestLogs> getByTestSuiteId(Integer id) throws SQLException{
		return (ArrayList<TestLogs>) SqlMap.getSqlMapClient().queryForList(GET_TEST_LOGS_BY_TEST_SUITE_ID, id);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestLogs> getByTestGroupId(Integer id) throws SQLException{
		return (ArrayList<TestLogs>) SqlMap.getSqlMapClient().queryForList(GET_TEST_LOGS_BY_TEST_GROUP_ID, id);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestLogs> getByTestCaseId(Integer id) throws SQLException{
		return (ArrayList<TestLogs>) SqlMap.getSqlMapClient().queryForList(GET_TEST_LOGS_BY_TEST_CASE_ID, id);
	}

	public TestLogs get(Long id) throws SQLException{
		return (TestLogs) SqlMap.getSqlMapClient().queryForObject(GET_TEST_LOGS_BY_ID, id);
	}
	
	public void add(TestLogs testLogs) throws SQLException{
		SqlMap.getSqlMapClient().insert(INSERT_TEST_LOGS, testLogs);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestLogs> getByTestSuiteIdAjax(TestLogs testLogs) throws SQLException{
		return (ArrayList<TestLogs>) SqlMap.getSqlMapClient().queryForList(GET_TEST_LOGS_BY_TEST_SUITE_ID_AJAX, testLogs);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestLogs> getByTestGroupIdAjax(TestLogs testLogs) throws SQLException{
		return (ArrayList<TestLogs>) SqlMap.getSqlMapClient().queryForList(GET_TEST_LOGS_BY_TEST_GROUP_ID_AJAX, testLogs);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestLogs> getByTestCaseIdAjax(TestLogs testLogs) throws SQLException{
		return (ArrayList<TestLogs>) SqlMap.getSqlMapClient().queryForList(GET_TEST_LOGS_BY_TEST_CASE_ID_AJAX, testLogs);
	}
}

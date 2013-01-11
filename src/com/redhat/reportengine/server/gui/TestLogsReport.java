/**
 * 
 */
package com.redhat.reportengine.server.gui;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.redhat.reportengine.server.dbmap.TestLogs;
import com.redhat.reportengine.server.sql.SqlMap;
import com.redhat.reportengine.server.sql.SqlQuery;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 28, 2012
 */
public class TestLogsReport {
	private static Logger _logger = Logger.getLogger(TestLogsReport.class);
	
	public ArrayList<TestLogs> getLogsBySuiteIdAjax(Integer suiteId, Integer ajaxRef) throws SQLException{
		TestLogs testLog = new TestLogs();
		testLog.setId(ajaxRef);
		testLog.setTestSuiteId(suiteId);
		ArrayList<TestLogs> testLogs = (ArrayList<TestLogs>) SqlMap.getSqlMapClient().queryForList(SqlQuery.GET_TEST_LOGS_BY_TEST_SUITE_ID_AJAX, testLog);
		return testLogs;
	}
	
	
	public ArrayList<TestLogs> getLogsByGroupIdAjax(Integer groupId, Integer ajaxRef) throws SQLException{
		TestLogs testLog = new TestLogs();
		testLog.setId(ajaxRef);
		testLog.setTestGroupId(groupId);
		ArrayList<TestLogs> testLogs = (ArrayList<TestLogs>) SqlMap.getSqlMapClient().queryForList(SqlQuery.GET_TEST_LOGS_BY_TEST_GROUP_ID_AJAX, testLog);
		return testLogs;
	}
	
	public ArrayList<TestLogs> getLogsByCaseIdAjax(Integer caseId, Integer ajaxRef) throws SQLException{
		TestLogs testLog = new TestLogs();
		testLog.setId(ajaxRef);
		testLog.setTestCaseId(caseId);
		ArrayList<TestLogs> testLogs = (ArrayList<TestLogs>) SqlMap.getSqlMapClient().queryForList(SqlQuery.GET_TEST_LOGS_BY_TEST_CASE_ID_AJAX, testLog);
		return testLogs;
	}
	
	
	public ArrayList<TestLogs> getLogsBySuiteId(Integer id) throws SQLException{
		ArrayList<TestLogs> testLogs = (ArrayList<TestLogs>) SqlMap.getSqlMapClient().queryForList(SqlQuery.GET_TEST_LOGS_BY_TEST_SUITE_ID, id);
		return testLogs;
	}
	
	public ArrayList<TestLogs> getLogsByGroupId(Integer id) throws SQLException{
		ArrayList<TestLogs> testLogs = (ArrayList<TestLogs>) SqlMap.getSqlMapClient().queryForList(SqlQuery.GET_TEST_LOGS_BY_TEST_GROUP_ID, id);
		return testLogs;
	}
	
	public ArrayList<TestLogs> getLogsByCaseId(Integer id) throws SQLException{
		ArrayList<TestLogs> testLogs = (ArrayList<TestLogs>) SqlMap.getSqlMapClient().queryForList(SqlQuery.GET_TEST_LOGS_BY_TEST_CASE_ID, id);
		return testLogs;
	}
	
	public TestLogs getLogsById(Integer id) throws SQLException{
		return (TestLogs) SqlMap.getSqlMapClient().queryForObject(SqlQuery.GET_TEST_LOGS_BY_ID, id);
	}
	//HttpServletRequest request
}

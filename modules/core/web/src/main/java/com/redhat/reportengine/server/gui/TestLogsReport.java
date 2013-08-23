/**
 * 
 */
package com.redhat.reportengine.server.gui;

import java.sql.SQLException;
import java.util.ArrayList;
import com.redhat.reportengine.server.dbdata.TestLogsTable;
import com.redhat.reportengine.server.dbmap.TestLogs;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 28, 2012
 */
public class TestLogsReport {
	
	public ArrayList<TestLogs> getLogsBySuiteIdAjax(Integer suiteId, Long ajaxRef) throws SQLException{
		TestLogs testLog = new TestLogs();
		testLog.setId(ajaxRef);
		testLog.setTestSuiteId(suiteId);
		return new TestLogsTable().getByTestSuiteIdAjax(testLog);
	}
	
	public ArrayList<TestLogs> getLogsByGroupIdAjax(Integer groupId, Long ajaxRef) throws SQLException{
		TestLogs testLog = new TestLogs();
		testLog.setId(ajaxRef);
		testLog.setTestGroupId(groupId);
		return new TestLogsTable().getByTestGroupIdAjax(testLog);
	}
	
	public ArrayList<TestLogs> getLogsByCaseIdAjax(Integer caseId, Long ajaxRef) throws SQLException{
		TestLogs testLog = new TestLogs();
		testLog.setId(ajaxRef);
		testLog.setTestCaseId(caseId);
		return new TestLogsTable().getByTestCaseIdAjax(testLog);
	}	
	
	public ArrayList<TestLogs> getLogsBySuiteId(Integer id) throws SQLException{
		return new TestLogsTable().getByTestSuiteId(id);
	}
	
	public ArrayList<TestLogs> getLogsByGroupId(Integer id) throws SQLException{
		return new TestLogsTable().getByTestGroupId(id);
	}
	
	public ArrayList<TestLogs> getLogsByCaseId(Integer id) throws SQLException{
		return new TestLogsTable().getByTestCaseId(id);
	}
	
	public TestLogs getLogsById(Long id) throws SQLException{
		return new TestLogsTable().get(id);
	}
}

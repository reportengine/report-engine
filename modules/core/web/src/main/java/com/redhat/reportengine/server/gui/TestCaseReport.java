/**
 * 
 */
package com.redhat.reportengine.server.gui;

import java.sql.SQLException;
import java.util.ArrayList;

import com.redhat.reportengine.server.dbdata.FileStorageTable;
import com.redhat.reportengine.server.dbdata.TestCaseTable;
import com.redhat.reportengine.server.dbmap.FileStorage;
import com.redhat.reportengine.server.dbmap.TestCase;

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
		ArrayList<TestCase> testCases = new TestCaseTable().getDetailedAllByTestGroupId(testCase);
		return testCases;
	}
	
	public ArrayList<TestCase> getCasesBySuiteIdAndStatus(int testSuiteId, String testStatus) throws SQLException{
		TestCase testCase = new TestCase();
		testCase.setTestSuiteId(testSuiteId);
		testCase.setTestResult(testStatus);
		return new TestCaseTable().getDetailedAllByTestSuiteIdAndStatus(testCase);
	}
	
	public ArrayList<TestCase> getCasesByGroupIdAndStatus(int testGroupId, String testStatus) throws SQLException{
		TestCase testCase = new TestCase();
		testCase.setTestGroupId(testGroupId);
		testCase.setTestResult(testStatus);
		return new TestCaseTable().getDetailedAllByTestGroupIdAndStatus(testCase);
	}
	
	public TestCase getTestCaseDetail(int testCaseId) throws SQLException{
		return new TestCaseTable().getDetailed(testCaseId);
	}
	
	public ArrayList<FileStorage> getScreenShotImages(int testCaseId) throws SQLException{
		return new FileStorageTable().getScreenShotFile(testCaseId);
	}
	
}

/**
 * 
 */
package com.redhat.reportengine.server.api;

import java.sql.SQLException;
import java.util.Date;
import org.apache.log4j.Logger;
import com.redhat.reportengine.server.dbmap.FileStorage;
import com.redhat.reportengine.server.dbmap.TestCase;
import com.redhat.reportengine.server.dbmap.TestGroup;
import com.redhat.reportengine.server.dbmap.TestLogs;
import com.redhat.reportengine.server.dbmap.TestReference;
import com.redhat.reportengine.server.dbmap.TestSuite;
import com.redhat.reportengine.server.jobs.TestSuiteAggregationImpl;
import com.redhat.reportengine.server.queue.TestLogsQueue;
import com.redhat.reportengine.server.sql.SqlMap;
import com.redhat.reportengine.server.sql.SqlQuery;


/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 8, 2012
 */
public class TestResult {
	private static Logger _logger = Logger.getLogger(TestResult.class);
		
	public Date getServerTime(){
		return new Date();
	}
	
	// Test Suites
	
	public void insertTestSuite(TestSuite testSuite) throws TestResultException {		
		try {
			//Check Reference already there in our reference table
			TestReference testReference = new TestReference();
			testReference.setTestReference(testSuite.getTestReference());
			testReference = (TestReference) SqlMap.getSqlMapClient().queryForObject(SqlQuery.GET_TEST_REFERENCE_BY_NAME, testReference);
			if(testReference == null){
				testReference = new TestReference();
				int id = (Integer) SqlMap.getSqlMapClient().queryForObject(SqlQuery.GET_TEST_REFERENCE_NEXT_ID);
				testReference.setId(id);
				testReference.setTestReference(testSuite.getTestReference());
				testReference.setCreationTime(new Date());
				SqlMap.getSqlMapClient().insert(SqlQuery.INSERT_TEST_REFERENCE, testReference);
			}
			//Commented this as we are passing ID, no need get it again
			//testReference = (TestReference) SqlMap.getSqlMapClient().queryForObject(SqlQuery.GET_TEST_REFERENCE_BY_NAME, testReference);
			//Now our test reference ID ready in test reference table			
			testSuite.setTestReferenceId(testReference.getId());
			testSuite.setLocalStartTime(new Date());
			_logger.debug("Test Suite Status: "+testSuite.getTestStatus());
			SqlMap.getSqlMapClient().insert(SqlQuery.INSERT_TEST_SUITE, testSuite);
		} catch (SQLException ex) {
			_logger.error("Exception, ", ex);
			throw new TestResultException(ex.getMessage());
		}
	}
	
	public void updateTestSuiteName(TestSuite testSuite) throws TestResultException {		
		try {
			_logger.debug("Test Suite Status: "+testSuite.getTestSuiteName());
			SqlMap.getSqlMapClient().insert(SqlQuery.UPDATE_TEST_SUITE_NAME, testSuite);
		} catch (SQLException ex) {
			_logger.error("Exception, ", ex);
			throw new TestResultException(ex.getMessage());
		}
	}
	
	public void updateTestSuite(TestSuite testSuite) throws TestResultException {		
		try {
			_logger.debug("Test Suite Status: "+testSuite.getTestStatus());
			testSuite.setLocalEndTime(new Date());
			SqlMap.getSqlMapClient().insert(SqlQuery.UPDATE_TEST_SUITE, testSuite);
			if(testSuite.getTestStatus() != null){
				if(!testSuite.getTestStatus().equalsIgnoreCase(TestSuite.RUNNING)){
					new TestSuiteAggregationImpl().doAggregateTestSuite();
				}
			}
		} catch (SQLException ex) {
			_logger.error("Exception, ", ex);
			throw new TestResultException(ex.getMessage());
		}
	}
	
	
	public int getTestSuiteNextAvailableId() throws TestResultException{
		try {
			return (Integer) SqlMap.getSqlMapClient().queryForObject(SqlQuery.GET_TEST_SUITE_NEXT_ID);
		} catch (SQLException ex) {
			_logger.error("Exception, ", ex);
			throw new TestResultException(ex.getMessage());
		}		
	}
	
	public TestSuite getTestSuite(int id) throws TestResultException{
		try {
			_logger.debug("Test ID: "+id);
			return (TestSuite)SqlMap.getSqlMapClient().queryForObject(SqlQuery.GET_TEST_SUITE_BY_ID, id);
		} catch (SQLException ex) {
			_logger.error("Exception, ", ex);
			throw new TestResultException(ex.getMessage());
		}
	}
	
	public TestSuite getTestSuite(TestSuite testSuite) throws TestResultException{
		try {
			_logger.debug("Test Suite: "+testSuite.getTestSuiteName());
			return (TestSuite)SqlMap.getSqlMapClient().queryForObject(SqlQuery.GET_TEST_SUITE_BY_REFF, testSuite);
		} catch (SQLException ex) {
			_logger.error("Exception, ", ex);
			throw new TestResultException(ex.getMessage());
		}		
	}
	
	//Test Groups
	
	public TestGroup insertTestGroup(TestGroup testGroup) throws TestResultException{
		try {
			_logger.debug("Input --> ID: "+testGroup.getId()+", TestSuiteId: "+testGroup.getTestSuiteId()+", TestGroup: "+testGroup.getTestGroup());
			TestGroup tmpGroup = (TestGroup) SqlMap.getSqlMapClient().queryForObject(SqlQuery.GET_TEST_GROUP_BY_SUITE_ID_NAME, testGroup);
			if(tmpGroup == null){
				testGroup.setLocalTime(new Date());
				SqlMap.getSqlMapClient().insert(SqlQuery.INSERT_TEST_GROUP, testGroup);
				tmpGroup = (TestGroup) SqlMap.getSqlMapClient().queryForObject(SqlQuery.GET_TEST_GROUP_BY_SUITE_ID_NAME, testGroup);
			}else{
				_logger.debug("Specified Group already available! --> ID: "+tmpGroup.getId()+", TestTitleId: "+tmpGroup.getTestSuiteId()+", TestGroup: "+tmpGroup.getTestGroup());
			}
			return tmpGroup;
		} catch (SQLException ex) {
			_logger.error("Exception, ", ex);
			throw new TestResultException(ex.getMessage());
		}		
	}
	
	//Test Case
	
	public int insertTestCase(TestCase testCase) throws TestResultException{
		try {
			testCase.setLocalStartTime(new Date());
			SqlMap.getSqlMapClient().insert(SqlQuery.INSERT_TEST_CASE, testCase);
			testCase = (TestCase) SqlMap.getSqlMapClient().queryForObject(SqlQuery.GET_TEST_CASE_ID, testCase);
			return testCase.getId();
		} catch (SQLException ex) {
			_logger.error("Exception, ", ex);
			throw new TestResultException(ex.getMessage());
		}				
	}

	public void updateTestCase(TestCase testCase) throws TestResultException{
		try {
			testCase.setLocalEndTime(new Date());
			if(testCase.getScreenShotFileName() != null){
				_logger.debug("Screen Shot File Name: "+testCase.getScreenShotFileName());
				
				//added code to insert screen shots in to database.
				FileStorage fileStorage = new FileStorage();
				fileStorage.setCreationTime(new Date());
				fileStorage.setFileName(testCase.getScreenShotFileName());
				fileStorage.setTestCaseId(testCase.getId());
				fileStorage.setScreenShot(true);
				fileStorage.setFileByte(testCase.getScreenShotFileByte());
				SqlMap.getSqlMapClient().insert(SqlQuery.INSERT_FILE_STORAGE, fileStorage);
				
				/*//Old code to store files on disk...
				try {
					byte[] screenShotFileByte = testCase.getScreenShotFileByte();					
					File destFile = new File(ServerSettings.getBaseLocation()+"/"+ServerSettings.getScreenShotFileLocation()+"/_"+new Date().getTime()+"_"+testCase.getScreenShotFileName());
					if (!destFile.exists()) {
		                destFile.createNewFile();
		        }
					FileOutputStream out = new FileOutputStream(destFile);
					out.write(screenShotFileByte);
					out.close();
					testCase.setTestGuiFiles(destFile.getName());
				
				} catch (Exception ex) {
					_logger.error("File Write Error, ", ex);
				}	*/
			}
			if((testCase.getTestComments()!= null) && (testCase.getTestComments().length() > 10000)){
				testCase.setTestComments(testCase.getTestComments().substring(0, (10000-1)));
				_logger.warn("Test Case comments size has been truncated to 10000 chars, Portion of data has been dropped!!");
			}
			SqlMap.getSqlMapClient().update(SqlQuery.UPDATE_TEST_CASE, testCase);
		} catch (SQLException ex) {
			_logger.error("Exception, ", ex);
			throw new TestResultException(ex.getMessage());
		}
	}
	
	public void insertTestLog(TestLogs testLogs) throws TestResultException{
		/*
		try{
			testLogs.setLocalTime(new Date());
			SqlMap.getSqlMapClient().insert(SqlQuery.INSERT_TEST_LOGS, testLogs);
		}catch(Exception ex){
			_logger.error("Exception, ", ex);
			throw new TestResultException(ex.getMessage());
		}*/
		testLogs.setLocalTime(new Date());
		TestLogsQueue.addTestLog(testLogs);
	}
	
}

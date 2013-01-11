/**
 * 
 */
package com.redhat.reportengine.server.gui;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.redhat.reportengine.server.ServerSettings;
import com.redhat.reportengine.server.dbmap.TestCase;
import com.redhat.reportengine.server.sql.SqlMap;
import com.redhat.reportengine.server.sql.SqlQuery;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 29, 2012
 */
public class PurgeLogs {
	Logger _logger = Logger.getLogger(PurgeLogs.class);
	
	public void deleteSuiteLogs(Integer id) throws SQLException{
		/*
		ArrayList<TestCase> testCases = (ArrayList<TestCase>) SqlMap.getSqlMapClient().queryForList(SqlQuery.GET_TEST_CASES_SCREEN_SHOT_BY_ID, id);
		for(TestCase testCase : testCases){
			if(!new File(ServerSettings.getBaseLocation()+"/"+ServerSettings.getScreenShotFileLocation()+"/"+testCase.getTestGuiFiles()).delete()){
				_logger.error("Failed to delete the Screen Shot file: "+ServerSettings.getBaseLocation()+"/"+ServerSettings.getScreenShotFileLocation()+"/"+testCase.getTestGuiFiles());
			}else{
				_logger.debug("Successfully Deleted Screen Shot file: "+ServerSettings.getBaseLocation()+"/"+ServerSettings.getScreenShotFileLocation()+"/"+testCase.getTestGuiFiles());
			}
		}
		SqlMap.getSqlMapClient().delete(SqlQuery.DELETE_TEST_LOGS_BY_TEST_SUITE_ID, id);
		SqlMap.getSqlMapClient().delete(SqlQuery.DELETE_TEST_CASE_BY_TEST_SUITE_ID, id);
		SqlMap.getSqlMapClient().delete(SqlQuery.DELETE_TEST_GROUP_BY_TEST_SUITE_ID, id);
		SqlMap.getSqlMapClient().delete(SqlQuery.DELETE_TEST_SUITE_AGGREGSTION_BY_TEST_SUITE_ID, id);*/
		SqlMap.getSqlMapClient().delete(SqlQuery.DELETE_TEST_SUITE_BY_ID, id);		
	}
}

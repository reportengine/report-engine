/**
 * 
 */
package com.redhat.reportengine.server.gui;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.redhat.reportengine.server.dbmap.TestGroup;
import com.redhat.reportengine.server.sql.SqlMap;
import com.redhat.reportengine.server.sql.SqlQuery;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 19, 2012
 */
public class TestGroupReport {
	private static Logger _logger = Logger.getLogger(TestGroupReport.class);
	
	public ArrayList<TestGroup> getGroups(int testSuiteId) throws SQLException{
		ArrayList<TestGroup> testGroups = (ArrayList<TestGroup>) SqlMap.getSqlMapClient().queryForList(SqlQuery.GET_TEST_GROUPS_BY_ID_DETAILED_COUNT, testSuiteId);
		return testGroups;
	}
	
	public TestGroup getTestGroupDetails(int groupId) throws SQLException{
		return (TestGroup) SqlMap.getSqlMapClient().queryForObject(SqlQuery.GET_TEST_GROUP_SUITE_BY_ID, groupId);
	}
}

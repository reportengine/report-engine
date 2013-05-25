package com.redhat.reportengine.server.dbdata;

import java.sql.SQLException;
import java.util.ArrayList;

import com.redhat.reportengine.server.dbmap.TestGroup;
import com.redhat.reportengine.server.sql.SqlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * May 16, 2013
 */
public class TestGroupTable {
	private final static String GET_TEST_GROUP_BY_SUITE_ID_GROUP_NAME	= "getTestGroupWithTestSuiteIdTestGroup";
	private final static String INSERT_TEST_GROUP						= "insertTestGroup";
	private final static String GET_TEST_GROUP_SUITE_BY_ID 				= "getTestGroupSuiteById";
	private final static String GET_TEST_GROUPS_BY_ID_DETAILED_COUNT	= "getTestGroupsByIdDetailedCount";
	private final static String DELETE_TEST_GROUP_BY_TEST_SUITE_ID				= "deleteTestGroupByTestSuiteId";
	
	public void add(TestGroup testGroup) throws SQLException{
		SqlMap.getSqlMapClient().insert(INSERT_TEST_GROUP, testGroup);
	}
	
	public TestGroup getByTestSuiteId(Integer testSuiteId) throws SQLException{
		return (TestGroup)SqlMap.getSqlMapClient().queryForObject(GET_TEST_GROUP_SUITE_BY_ID, testSuiteId);
	}
	
	public TestGroup getByTestSuiteIdAndGroupName(TestGroup testGroup) throws SQLException{
		return (TestGroup)SqlMap.getSqlMapClient().queryForObject(GET_TEST_GROUP_BY_SUITE_ID_GROUP_NAME, testGroup);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestGroup> getCount(Integer testGroupId) throws SQLException{
		return (ArrayList<TestGroup>)SqlMap.getSqlMapClient().queryForList(GET_TEST_GROUPS_BY_ID_DETAILED_COUNT, testGroupId);
	}
	
	public void remove(int testSuiteId) throws SQLException{
		SqlMap.getSqlMapClient().delete(DELETE_TEST_GROUP_BY_TEST_SUITE_ID, testSuiteId);
	}
}

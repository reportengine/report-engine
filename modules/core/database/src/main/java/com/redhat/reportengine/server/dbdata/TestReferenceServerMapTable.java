package com.redhat.reportengine.server.dbdata;

import java.sql.SQLException;
import java.util.ArrayList;

import com.redhat.reportengine.server.dbmap.TestReferenceServerMap;
import com.redhat.reportengine.server.sql.SqlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Aug 16, 2013
 */
public class TestReferenceServerMapTable {
	private final static String GET_TEST_SERVER_REFERENCE_ALL				= "getTestServerMapReferenceAll";
	private final static String GET_TEST_SERVER_REFERENCE_BY_TEST_REF_ID	= "getTestServerMapReferenceByTestReferenceId";
	private final static String GET_TEST_SERVER_REFERENCE_DETAIL_BY_TEST_REF_ID	= "getTestServerMapReferenceDetailByTestReferenceId";	
	private final static String GET_TEST_SERVER_REFERENCE_BY_SERVER_ID		= "getTestServerMapReferenceByServerId";
	private final static String INSERT_TEST_SERVER_REFERENCE				= "insertTestServerMapReference";
	private final static String DELETE_TEST_SERVER_REFERENCE_BY_TEST_REF_ID = "deleteTestServerMapReferenceByTestId";
	private final static String DELETE_TEST_SERVER_REFERENCE_BY_SERVER_ID 	= "deleteTestServerMapReferenceByServerId";
	private final static String GET_SELECTED_TEST_SUITES 	= "getTestServerMap-TestSuites";
	private final static String GET_SERVER_NAME_BY_TEST_SUITE_ID 	= "getTestServerMap-ServersByTestSuiteId";
	
	
		
	public void add(TestReferenceServerMap testReferenceServerMap) throws SQLException{
		SqlMap.getSqlMapClient().insert(INSERT_TEST_SERVER_REFERENCE, testReferenceServerMap);
	}
	
	public void removeByTestRefId(Integer id) throws SQLException{
		SqlMap.getSqlMapClient().delete(DELETE_TEST_SERVER_REFERENCE_BY_TEST_REF_ID, id);
	}
	
	public void removeByTestServerId(Integer id) throws SQLException{
		SqlMap.getSqlMapClient().delete(DELETE_TEST_SERVER_REFERENCE_BY_SERVER_ID, id);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestReferenceServerMap> getTestSuites() throws SQLException{
		return (ArrayList<TestReferenceServerMap>) SqlMap.getSqlMapClient().queryForList(GET_SELECTED_TEST_SUITES);
	}
	
	public String getServersName(int testSuiteId) throws SQLException{
		return (String) SqlMap.getSqlMapClient().queryForObject(GET_SERVER_NAME_BY_TEST_SUITE_ID, testSuiteId);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestReferenceServerMap> getAll() throws SQLException{
		return (ArrayList<TestReferenceServerMap>) SqlMap.getSqlMapClient().queryForList(GET_TEST_SERVER_REFERENCE_ALL);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestReferenceServerMap> getByTestRefId(Integer id) throws SQLException{
		return (ArrayList<TestReferenceServerMap>) SqlMap.getSqlMapClient().queryForList(GET_TEST_SERVER_REFERENCE_BY_TEST_REF_ID, id);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestReferenceServerMap> getDetailByTestRefId(Integer id) throws SQLException{
		return (ArrayList<TestReferenceServerMap>) SqlMap.getSqlMapClient().queryForList(GET_TEST_SERVER_REFERENCE_DETAIL_BY_TEST_REF_ID, id);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestReferenceServerMap> getByServerId(Integer id) throws SQLException{
		return (ArrayList<TestReferenceServerMap>) SqlMap.getSqlMapClient().queryForList(GET_TEST_SERVER_REFERENCE_BY_SERVER_ID, id);
	}
	
}

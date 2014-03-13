/**
 * 
 */
package com.redhat.reportengine.server.dbdata;

import java.sql.SQLException;
import java.util.ArrayList;

import com.redhat.reportengine.server.dbmap.TestSuiteResourceMetric;
import com.redhat.reportengine.server.sql.SqlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 4, 2014
 */
public class TestSuiteResourceMetricTable {
	private final static String INSERT_TEST_SUITE_RESOURCE_METRIC 					= "insertTestSuiteResourceMetric";
	private final static String GET_TEST_SUITE_RESOURCE_METRIC_BY_ID 				= "getTestSuiteResourceMetricById";
	private final static String GET_TEST_SUITE_RESOURCE_METRIC_BY_DTN_ID 			= "getTestSuiteResourceMetricByDtnId";
	private final static String GET_TEST_SUITE_RESOURCE_METRIC_BY_TEST_SUITE_ID 	= "getTestSuiteResourceMetricByTestSuiteId";
	private final static String GET_TEST_SUITE_RESOURCE_METRIC_BY_TEST_REFERENCE_ID	= "getTestSuiteResourceMetricByTestReferenceId";
	private final static String GET_TEST_SUITE_RESOURCE_METRIC_BY_TEST_GROUP_ID 	= "getTestSuiteResourceMetricByTestGroupId";
	private final static String GET_TEST_SUITE_RESOURCE_METRIC_BY_TEST_CASE_ID 		= "getTestSuiteResourceMetricByTestCaseId";	
	private final static String DELETE_TEST_SUITE_RESOURCE_METRIC_BY_ID				= "deletetTestSuiteResourceMetricById";
	private final static String DELETE_TEST_SUITE_RESOURCE_METRIC_BY_TEST_SUITE_ID	= "deletetTestSuiteResourceMetricByTestSuiteId";
	private final static String DELETE_TEST_SUITE_RESOURCE_METRIC_BY_DTN_ID			= "deletetTestSuiteResourceMetricByDtnId";
	
	private final static String GET_TOP_TEST_SUITE_RESOURCE_METRIC_BY_TEST_REFERENCE_ID	= "getTopTestSuiteResourceMetricByTestReferenceId";
	
	
	private final static String GET_TEST_METRIC 			= "getResourceMetric";
	private final static String GET_TEST_METRIC_CPU_USED 			= "getResourceMetricCpuUsed";
	
	public TestSuiteResourceMetric getTopByTestReferenceId(TestSuiteResourceMetric testSuiteResourceMetric) throws SQLException{
		return (TestSuiteResourceMetric) SqlMap.getSqlMapClient().queryForObject(GET_TOP_TEST_SUITE_RESOURCE_METRIC_BY_TEST_REFERENCE_ID, testSuiteResourceMetric);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestSuiteResourceMetric> getById(int id) throws SQLException{
		return (ArrayList<TestSuiteResourceMetric>) SqlMap.getSqlMapClient().queryForList(GET_TEST_SUITE_RESOURCE_METRIC_BY_ID, id);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestSuiteResourceMetric> getByDtnId(Integer dtnId) throws SQLException{
		return (ArrayList<TestSuiteResourceMetric>) SqlMap.getSqlMapClient().queryForList(GET_TEST_SUITE_RESOURCE_METRIC_BY_DTN_ID, dtnId);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestSuiteResourceMetric> getByTestSuiteId(Integer testSuiteId) throws SQLException{
		return (ArrayList<TestSuiteResourceMetric>) SqlMap.getSqlMapClient().queryForList(GET_TEST_SUITE_RESOURCE_METRIC_BY_TEST_SUITE_ID, testSuiteId);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestSuiteResourceMetric> getByTestReferenceId(Integer testReferenceId) throws SQLException{
		return (ArrayList<TestSuiteResourceMetric>) SqlMap.getSqlMapClient().queryForList(GET_TEST_SUITE_RESOURCE_METRIC_BY_TEST_REFERENCE_ID, testReferenceId);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestSuiteResourceMetric> getByTestGroupId(Integer testGroupId) throws SQLException{
		return (ArrayList<TestSuiteResourceMetric>) SqlMap.getSqlMapClient().queryForList(GET_TEST_SUITE_RESOURCE_METRIC_BY_TEST_GROUP_ID, testGroupId);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestSuiteResourceMetric> getByTestCaseId(Integer testCaseId) throws SQLException{
		return (ArrayList<TestSuiteResourceMetric>) SqlMap.getSqlMapClient().queryForList(GET_TEST_SUITE_RESOURCE_METRIC_BY_TEST_CASE_ID, testCaseId);
	}

	public void add(TestSuiteResourceMetric testSuiteResourceMetric) throws SQLException{
		SqlMap.getSqlMapClient().insert(INSERT_TEST_SUITE_RESOURCE_METRIC, testSuiteResourceMetric);
	}
	
	public void removeById(int id) throws SQLException{
		SqlMap.getSqlMapClient().delete(DELETE_TEST_SUITE_RESOURCE_METRIC_BY_ID, id);
	}
	
	public void removeByTestSuiteId(int testSuiteId) throws SQLException{
		SqlMap.getSqlMapClient().delete(DELETE_TEST_SUITE_RESOURCE_METRIC_BY_TEST_SUITE_ID, testSuiteId);
	}
	
	public void removeByTestDntId(int dtnId) throws SQLException{
		SqlMap.getSqlMapClient().delete(DELETE_TEST_SUITE_RESOURCE_METRIC_BY_DTN_ID, dtnId);
	}
	public static TestSuiteResourceMetric getMetric(TestSuiteResourceMetric resourceMetric) throws SQLException{
		return (TestSuiteResourceMetric) SqlMap.getSqlMapClient().queryForObject(GET_TEST_METRIC, resourceMetric);
	}
	public static TestSuiteResourceMetric getMetricCpuUsed(TestSuiteResourceMetric resourceMetric) throws SQLException{
		return (TestSuiteResourceMetric) SqlMap.getSqlMapClient().queryForObject(GET_TEST_METRIC_CPU_USED, resourceMetric);
	}
	
}

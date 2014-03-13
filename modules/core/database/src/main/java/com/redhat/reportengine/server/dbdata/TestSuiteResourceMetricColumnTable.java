/**
 * 
 */
package com.redhat.reportengine.server.dbdata;

import java.sql.SQLException;
import java.util.ArrayList;

import com.redhat.reportengine.server.dbmap.TestSuiteResourceMetricColumn;
import com.redhat.reportengine.server.sql.SqlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 5, 2014
 */
public class TestSuiteResourceMetricColumnTable {
	private final static String INSERT_TEST_SUITE_RESOURCE_METRIC_COLUMN 					= "insertTestSuiteResourceMetricColumn";
	private final static String GET_TEST_SUITE_RESOURCE_METRIC_COLUMN		 				= "getTestSuiteResourceMetricColumn";
	private final static String GET_TEST_SUITE_RESOURCE_METRIC_COLUMN_BY_ID 				= "getTestSuiteResourceMetricColumnById";
	private final static String GET_TEST_SUITE_RESOURCE_METRIC_COLUMN_BY_TABLE_TYPE 		= "getTestSuiteResourceMetricColumnByTableType";
	private final static String GET_TEST_SUITE_RESOURCE_METRIC_COLUMN_BY_COLUMN_NAME 		= "getTestSuiteResourceMetricColumnByColumnName";
	private final static String DELETE_TEST_SUITE_RESOURCE_METRIC_COLUMN_BY_ID				= "deletetTestSuiteResourceMetricColumnById";
	private final static String DELETE_TEST_SUITE_RESOURCE_METRIC_COLUMN_BY_TABLE_TYPE		= "deletetTestSuiteResourceMetricColumnByTableType";
	private final static String DELETE_TEST_SUITE_RESOURCE_METRIC_COLUMN_BY_COLUMN_NAME		= "deletetTestSuiteResourceMetricColumnByColumnName";
	private final static String DELETE_TEST_SUITE_RESOURCE_METRIC_COLUMN_BY_SUB_TYPE		= "deletetTestSuiteResourceMetricColumnBySubType";

	
	public void add(TestSuiteResourceMetricColumn testSuiteResourceMetricColumn) throws SQLException{
		SqlMap.getSqlMapClient().insert(INSERT_TEST_SUITE_RESOURCE_METRIC_COLUMN, testSuiteResourceMetricColumn);
	}
	
	public void removeById(int id) throws SQLException{
		SqlMap.getSqlMapClient().delete(DELETE_TEST_SUITE_RESOURCE_METRIC_COLUMN_BY_ID, id);
	}
	
	public void removeByTableType(String tableType) throws SQLException{
		SqlMap.getSqlMapClient().delete(DELETE_TEST_SUITE_RESOURCE_METRIC_COLUMN_BY_TABLE_TYPE, tableType);
	}
	
	public void removeByColumnName(String columnName) throws SQLException{
		SqlMap.getSqlMapClient().delete(DELETE_TEST_SUITE_RESOURCE_METRIC_COLUMN_BY_COLUMN_NAME, columnName);
	}
	
	public void removeBySubType(String subType) throws SQLException{
		SqlMap.getSqlMapClient().delete(DELETE_TEST_SUITE_RESOURCE_METRIC_COLUMN_BY_SUB_TYPE, subType);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestSuiteResourceMetricColumn> get() throws SQLException{
		return (ArrayList<TestSuiteResourceMetricColumn>) SqlMap.getSqlMapClient().queryForList(GET_TEST_SUITE_RESOURCE_METRIC_COLUMN);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestSuiteResourceMetricColumn> getById(int id) throws SQLException{
		return (ArrayList<TestSuiteResourceMetricColumn>) SqlMap.getSqlMapClient().queryForList(GET_TEST_SUITE_RESOURCE_METRIC_COLUMN_BY_ID, id);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestSuiteResourceMetricColumn> getByTableType(String tableType) throws SQLException{
		return (ArrayList<TestSuiteResourceMetricColumn>) SqlMap.getSqlMapClient().queryForList(GET_TEST_SUITE_RESOURCE_METRIC_COLUMN_BY_TABLE_TYPE, tableType);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestSuiteResourceMetricColumn> getByColumnName(String columnName) throws SQLException{
		return (ArrayList<TestSuiteResourceMetricColumn>) SqlMap.getSqlMapClient().queryForList(GET_TEST_SUITE_RESOURCE_METRIC_COLUMN_BY_COLUMN_NAME, columnName);
	}
		
	
}

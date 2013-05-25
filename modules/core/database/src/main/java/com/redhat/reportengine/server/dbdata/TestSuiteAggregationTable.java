package com.redhat.reportengine.server.dbdata;

import java.sql.SQLException;
import java.util.ArrayList;

import com.redhat.reportengine.server.dbmap.TestSuiteAggregation;
import com.redhat.reportengine.server.sql.SqlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * May 16, 2013
 */
public class TestSuiteAggregationTable {
	private final static String GET_TEST_SUITE_PRE_AGGREGATION_BY_TEST_SUITE_ID 		= "getTestSuitePreAggregationByTestSuiteId";
	private final static String DELETE_TEST_SUITE_AGGREGSTION_BY_TEST_SUITE_ID			= "deleteTestSuiteAggregationByTestSuiteId";
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestSuiteAggregation> get(int testSuiteId) throws SQLException{
		return (ArrayList<TestSuiteAggregation>)SqlMap.getSqlMapClient().queryForList(GET_TEST_SUITE_PRE_AGGREGATION_BY_TEST_SUITE_ID, testSuiteId);
	}
	
	public void remove(int testSuiteId) throws SQLException{
		SqlMap.getSqlMapClient().delete(DELETE_TEST_SUITE_AGGREGSTION_BY_TEST_SUITE_ID, testSuiteId);
	}
}

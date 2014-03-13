package com.redhat.reportengine.server.dbdata;

import java.sql.SQLException;
import java.util.ArrayList;

import com.redhat.reportengine.server.dbmap.TestReference;
import com.redhat.reportengine.server.dbmap.TestSuite;
import com.redhat.reportengine.server.sql.SqlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * May 16, 2013
 */
public class TestSuiteTable {
	private final static String GET_TEST_SUITE_BY_ID 					= "getTestSuiteById";
	private final static String GET_TEST_SUITE_BY_TEST_REFERENCE_ID 			= "getTestSuiteByTestReferenceId";
	private final static String GET_TEST_SUITE_BY_NAME_AND_REFF			= "getTestSuiteByNameRef";
	private final static String GET_TEST_SUITES 						= "getTestSuites";
	private final static String GET_TOP_N_TEST_SUITES 					= "getTopNTestSuites";
	private final static String GET_TOP_N_TEST_SUITES_EXCLUDE_RUNNING 	= "getTopNTestSuitesExcludeRunning";
	private final static String GET_TREND_REPORT 						= "getTrendReportExcludeRunning";
	private final static String GET_TOP_N_TEST_REFERENCE_AGGREGATION_EXCLUDE_RUNNING	="getTopNTestReferenceAggregationExcludeRunning";
	private final static String GET_TEST_SUITES_NON_AGGREGATE 							= "getTestSuitesNonAggregate";
	private final static String GET_TEST_SUITES_RUNNING						= "getTestSuitesRunning";
	private final static String GET_TOP_TEST_SUITE_BY_TEST_REFERENCE_IDS	= "getTopTestSuitesByTestReferenceIds";
	private final static String GET_TEST_SUITE_AGGREGATION_BY_TEST_REFERENCE_ID	 	= "getTestSuiteAggregationByReferenceId";
	private final static String INSERT_TEST_SUITE_AGGREGATION				 			= "insertTestSuiteAggregation";
	private final static String ENABLE_TEST_SUITE_AGGREGATION_STATUS		= "enableTestSuiteAggregationStatus";
	private final static String UPDATE_TEST_SUITE 			= "updateTestSuite";
	private final static String UPDATE_TEST_SUITE_NAME 	= "updateTestSuiteName";
	private final static String UPDATE_TEST_SUITE_STATUS_ID					= "updateTestSuiteStatusById";
	private final static String INSERT_TEST_SUITE 			= "insertTestSuite";
	private final static String GET_TEST_SUITE_NEXT_SEQ_ID 	= "getTestSuiteNextId";
	private final static String DELETE_TEST_SUITE_BY_ID						= "deleteTestSuiteById";
	private final static String DELETE_TEST_SUITE_BY_IDS						= "deleteTestSuiteByIds";
	
	
	public TestSuite get(int id) throws SQLException{
		return (TestSuite)SqlMap.getSqlMapClient().queryForObject(GET_TEST_SUITE_BY_ID, id);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestSuite> getByReferenceId(int testReferenceId) throws SQLException{
		return (ArrayList<TestSuite>)SqlMap.getSqlMapClient().queryForList(GET_TEST_SUITE_BY_TEST_REFERENCE_ID, testReferenceId);
	}
	
	public TestSuite getByNameAndRef(TestSuite testSuite) throws SQLException{
		return (TestSuite)SqlMap.getSqlMapClient().queryForObject(GET_TEST_SUITE_BY_NAME_AND_REFF, testSuite);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestSuite> getAll() throws SQLException {
		 return (ArrayList<TestSuite>) SqlMap.getSqlMapClient().queryForList(GET_TEST_SUITES);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestSuite> getTopN(TestSuite testSuite, boolean includeRunning) throws SQLException{
		if(includeRunning){
			return (ArrayList<TestSuite>) SqlMap.getSqlMapClient().queryForList(GET_TOP_N_TEST_SUITES, testSuite);
		}else{
			return (ArrayList<TestSuite>) SqlMap.getSqlMapClient().queryForList(GET_TOP_N_TEST_SUITES_EXCLUDE_RUNNING, testSuite);
		}
	}	
	
	public ArrayList<TestSuite> getTopN(int topN, String orderBy, boolean includeRunning) throws SQLException{
		TestSuite testSuite = new TestSuite();
		testSuite.setTopN(topN);
		testSuite.setOrderBy(orderBy);
		return getTopN(testSuite, includeRunning);
	}
	
	public ArrayList<TestSuite> getTopN(int topN, String orderBy) throws SQLException{
		return getTopN(topN, orderBy, true);
	}
		
	@SuppressWarnings("unchecked")
	public ArrayList<TestSuite> getTrendReport(TestSuite testSuite) throws SQLException {
		 return (ArrayList<TestSuite>) SqlMap.getSqlMapClient().queryForList(GET_TREND_REPORT, testSuite);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestSuite> getTopNRefAggr(TestSuite testSuite) throws SQLException {
		 return (ArrayList<TestSuite>) SqlMap.getSqlMapClient().queryForList(GET_TOP_N_TEST_REFERENCE_AGGREGATION_EXCLUDE_RUNNING, testSuite);
	}
	
	public ArrayList<TestSuite> getTopNRefAggr(int topN, String orderBy, int testReferenceId) throws SQLException{
		TestSuite testSuite = new TestSuite();
		testSuite.setTopN(topN);
		testSuite.setOrderBy(orderBy);
		testSuite.setTestReferenceId(testReferenceId);
		return getTopNRefAggr(testSuite);		
	} 
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestSuite> getTopNByRefIds(String referenceIds) throws SQLException {
		 return (ArrayList<TestSuite>) SqlMap.getSqlMapClient().queryForList(GET_TOP_TEST_SUITE_BY_TEST_REFERENCE_IDS, referenceIds);
	}
	
	public ArrayList<TestSuite> getTopNByRefIds() throws SQLException {
		StringBuilder refids = new StringBuilder();
		ArrayList<TestReference> referenceIds = new TestReferenceTable().getAll();
		for(TestReference reference: referenceIds){
			refids.append(reference.getId()).append(",");
		}
		refids.setLength(refids.length()-1);
		return getTopNByRefIds(refids.toString());
	}
	
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestSuite> getNonAggregate() throws SQLException {
		return (ArrayList<TestSuite>) SqlMap.getSqlMapClient().queryForList(GET_TEST_SUITES_NON_AGGREGATE);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestSuite> getRunning() throws SQLException {
		return (ArrayList<TestSuite>) SqlMap.getSqlMapClient().queryForList(GET_TEST_SUITES_RUNNING);
	}
	
	public TestSuite getAggregation(Integer testReferenceId) throws SQLException {
		return (TestSuite) SqlMap.getSqlMapClient().queryForObject(GET_TEST_SUITE_AGGREGATION_BY_TEST_REFERENCE_ID, testReferenceId);
	}
	
	public void add(TestSuite testSuite) throws SQLException {
		SqlMap.getSqlMapClient().insert(INSERT_TEST_SUITE, testSuite);
	}
	
	public void enableAggregationStatus(Integer testSuiteId) throws SQLException {
		SqlMap.getSqlMapClient().update(ENABLE_TEST_SUITE_AGGREGATION_STATUS, testSuiteId);
	}
	
	public void modify(TestSuite testSuite) throws SQLException {
		SqlMap.getSqlMapClient().update(UPDATE_TEST_SUITE, testSuite);
	}
	
	public void modifyName(TestSuite testSuite) throws SQLException {
		SqlMap.getSqlMapClient().update(UPDATE_TEST_SUITE_NAME, testSuite);
	}
	
	public void modifyStatus(TestSuite testSuite) throws SQLException {
		SqlMap.getSqlMapClient().update(UPDATE_TEST_SUITE_STATUS_ID, testSuite);
	}
	
	public void addAggregation(TestSuite testSuiteAggregation) throws SQLException {
		SqlMap.getSqlMapClient().insert(INSERT_TEST_SUITE_AGGREGATION, testSuiteAggregation);
	}
	
	public Integer getNextSeqId() throws SQLException{
		return (Integer) SqlMap.getSqlMapClient().queryForObject(GET_TEST_SUITE_NEXT_SEQ_ID);
	}
	
	public void remove(int testSuiteId) throws SQLException{
		SqlMap.getSqlMapClient().delete(DELETE_TEST_SUITE_BY_ID, testSuiteId);
	}
	
	public void remove(String testSuiteIds) throws SQLException{
		SqlMap.getSqlMapClient().delete(DELETE_TEST_SUITE_BY_IDS, testSuiteIds);
	}
	
}

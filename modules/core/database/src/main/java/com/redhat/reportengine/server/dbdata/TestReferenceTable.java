package com.redhat.reportengine.server.dbdata;

import java.sql.SQLException;
import java.util.ArrayList;

import com.redhat.reportengine.server.dbmap.TestReference;
import com.redhat.reportengine.server.sql.SqlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * May 10, 2013
 */
public class TestReferenceTable {
	private final static String GET_TEST_REFERENCE_BY_NAME						= "getTestReferenceByName";
	private final static String GET_TEST_REFERENCE_ALL							= "getTestReferenceAll";
	private final static String GET_TEST_REFERENCE_NEXT_ID						= "getTestReferenceNextId";
	private final static String INSERT_TEST_REFERENCE							= "insertTestReference";
	
	public void add(TestReference testReference) throws SQLException{
		SqlMap.getSqlMapClient().insert(INSERT_TEST_REFERENCE, testReference);
	}
	
	public TestReference get(TestReference testReference) throws SQLException{
		return (TestReference) SqlMap.getSqlMapClient().queryForObject(GET_TEST_REFERENCE_BY_NAME, testReference);
	}
	
	public Integer getNextSeqId() throws SQLException{
		return (Integer) SqlMap.getSqlMapClient().queryForObject(GET_TEST_REFERENCE_NEXT_ID);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestReference> getAll() throws SQLException{
		return (ArrayList<TestReference>) SqlMap.getSqlMapClient().queryForList(GET_TEST_REFERENCE_ALL);
	}
}

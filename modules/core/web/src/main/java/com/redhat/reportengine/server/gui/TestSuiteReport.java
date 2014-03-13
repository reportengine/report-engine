/**
 * 
 */
package com.redhat.reportengine.server.gui;

import java.sql.SQLException;
import java.util.ArrayList;
import com.redhat.reportengine.server.dbdata.TestSuiteTable;
import com.redhat.reportengine.server.dbmap.TestSuite;



/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 17, 2012
 */
public class TestSuiteReport {
	
	public ArrayList<TestSuite> getAllSuites() throws SQLException{
		return new TestSuiteTable().getAll();
	}
	
	public TestSuite getTestSuiteDetails(int id) throws SQLException{
		return new TestSuiteTable().get(id);
	}
	
	public ArrayList<TestSuite> getTopNTestSuites(int topN, String orderBy, boolean includeRunning) throws SQLException{
		return new TestSuiteTable().getTopN(topN, orderBy, includeRunning);
	}
	
	public ArrayList<TestSuite> getTopNTestReferenceAggregationDetails(int topN, String orderBy, int testReferenceId) throws SQLException{
		return new TestSuiteTable().getTopNRefAggr(topN, orderBy, testReferenceId);
	}
	
	public ArrayList<TestSuite> getAllByTestReferenceId(int testReferenceId) throws SQLException{
		return new TestSuiteTable().getByReferenceId(testReferenceId);
	}
	
	public ArrayList<TestSuite> getTopNTestSuites(int topN, String orderBy) throws SQLException{
		return getTopNTestSuites(topN, orderBy, true);
	}
	
	
}

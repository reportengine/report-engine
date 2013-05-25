/**
 * 
 */
package com.redhat.reportengine.server.gui;

import java.sql.SQLException;
import java.util.ArrayList;

import com.redhat.reportengine.server.dbdata.TestGroupTable;
import com.redhat.reportengine.server.dbmap.TestGroup;


/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 19, 2012
 */
public class TestGroupReport {

	public ArrayList<TestGroup> getGroups(int testSuiteId) throws SQLException{
		return new TestGroupTable().getCount(testSuiteId);
	}
	
	public TestGroup getTestGroupDetails(int groupId) throws SQLException{
		return new TestGroupTable().getByTestSuiteId(groupId);
	}
}

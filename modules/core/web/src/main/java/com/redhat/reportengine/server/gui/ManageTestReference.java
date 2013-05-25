package com.redhat.reportengine.server.gui;

import java.sql.SQLException;
import java.util.ArrayList;

import com.redhat.reportengine.server.dbdata.TestReferenceTable;
import com.redhat.reportengine.server.dbmap.TestReference;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jun 19, 2012
 */
public class ManageTestReference {
	public ArrayList<TestReference> getAllTestReference() throws SQLException{
		return new TestReferenceTable().getAll();
	}
}

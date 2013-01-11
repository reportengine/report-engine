package com.redhat.reportengine.server.gui;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.redhat.reportengine.server.dbmap.TestReference;
import com.redhat.reportengine.server.sql.SqlMap;
import com.redhat.reportengine.server.sql.SqlQuery;

public class ManageTestReference {
	private static Logger _logger = Logger.getLogger(ManageTestReference.class);
	public ArrayList<TestReference> getAllTestReference() throws SQLException{
		return (ArrayList<TestReference>) SqlMap.getSqlMapClient().queryForList(SqlQuery.GET_TEST_REFERENCE_ALL);
	}
}

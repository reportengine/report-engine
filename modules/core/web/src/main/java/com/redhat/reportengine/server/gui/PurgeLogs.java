/**
 * 
 */
package com.redhat.reportengine.server.gui;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import com.redhat.reportengine.server.dbdata.TestSuiteTable;
import com.redhat.reportengine.server.reports.Keys;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 29, 2012
 */
public class PurgeLogs {
	Logger _logger = Logger.getLogger(PurgeLogs.class);
	
	public void deleteSuiteLogs(Integer id) throws SQLException{
		new TestSuiteTable().remove(id);		
	}
	
	public void deleteSuiteLogs(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		String[] idArray =request.getParameterValues(Keys.DELETE_REPORTS);
		StringBuilder ids = new StringBuilder();
		for(String id : idArray){
			ids.append(id).append(",");
		}
		ids.setLength(ids.length()-1);
		new TestSuiteTable().remove(ids.toString());
	}
}

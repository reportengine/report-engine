package com.redhat.reportengine.server.dbdata;

import java.sql.SQLException;
import java.util.ArrayList;

import com.redhat.reportengine.server.dbmap.ReportGroupReference;
import com.redhat.reportengine.server.sql.SqlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * May 16, 2013
 */
public class ReportGroupReferenceTable {
	private final static String GET_REPORT_GROUP_REFERENCE_ALL						= "getReportGroupReferenceAll";
	private final static String GET_REPORT_GROUP_REFERENCE_BY_REPORT_GROUP_ID		= "getReportGroupReferenceByReportGroupId";
	private final static String INSERT_REPORT_REFERENCE_GROUP						= "insertReportGroupReference";
	private final static String DELETE_REPORT_GROUP_REFERENCE_BY_REPORT_GROUP_ID  	= "deleteReportGroupReferenceByReportGroupId";	
		
	public void add(ReportGroupReference reportGroupReference) throws SQLException{
		SqlMap.getSqlMapClient().insert(INSERT_REPORT_REFERENCE_GROUP, reportGroupReference);
	}
	
	public void remove(Integer id) throws SQLException{
		SqlMap.getSqlMapClient().delete(DELETE_REPORT_GROUP_REFERENCE_BY_REPORT_GROUP_ID, id);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ReportGroupReference> getAll() throws SQLException{
		return (ArrayList<ReportGroupReference>) SqlMap.getSqlMapClient().queryForList(GET_REPORT_GROUP_REFERENCE_ALL);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ReportGroupReference> getByReportGroupId(Integer id) throws SQLException{
		return (ArrayList<ReportGroupReference>) SqlMap.getSqlMapClient().queryForList(GET_REPORT_GROUP_REFERENCE_BY_REPORT_GROUP_ID, id);
	}
	
}

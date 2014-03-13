package com.redhat.reportengine.server.dbdata;

import java.sql.SQLException;
import java.util.ArrayList;

import com.redhat.reportengine.server.dbmap.ReportGroupResourceMetricReference;
import com.redhat.reportengine.server.sql.SqlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * May 16, 2013
 */
public class ReportGroupResourceMetricReferenceTable {
	private final static String GET_REPORT_GROUP_RESOURCE_METRIC_REFERENCE_ALL						= "getReportGroupResourceMetricReferenceAll";
	private final static String GET_REPORT_GROUP_RESOURCE_METRIC_REFERENCE_BY_REPORT_GROUP_ID		= "getReportGroupResourceMetricReferenceByReportGroupId";
	private final static String INSERT_REPORT_RESOURCE_METRIC_REFERENCE_GROUP						= "insertReportGroupResourceMetricReference";
	private final static String DELETE_REPORT_GROUP_RESOURCE_METRIC_REFERENCE_BY_REPORT_GROUP_ID  	= "deleteReportGroupResourceMetricReferenceByReportGroupId";	
		
	public void add(ReportGroupResourceMetricReference reportGroupResourceMetricReference) throws SQLException{
		SqlMap.getSqlMapClient().insert(INSERT_REPORT_RESOURCE_METRIC_REFERENCE_GROUP, reportGroupResourceMetricReference);
	}
	
	public void remove(Integer reportGroupId) throws SQLException{
		SqlMap.getSqlMapClient().delete(DELETE_REPORT_GROUP_RESOURCE_METRIC_REFERENCE_BY_REPORT_GROUP_ID, reportGroupId);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ReportGroupResourceMetricReference> getAll() throws SQLException{
		return (ArrayList<ReportGroupResourceMetricReference>) SqlMap.getSqlMapClient().queryForList(GET_REPORT_GROUP_RESOURCE_METRIC_REFERENCE_ALL);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ReportGroupResourceMetricReference> getByReportGroupId(Integer id) throws SQLException{
		return (ArrayList<ReportGroupResourceMetricReference>) SqlMap.getSqlMapClient().queryForList(GET_REPORT_GROUP_RESOURCE_METRIC_REFERENCE_BY_REPORT_GROUP_ID, id);
	}
	
}

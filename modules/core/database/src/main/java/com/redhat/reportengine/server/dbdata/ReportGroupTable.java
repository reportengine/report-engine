package com.redhat.reportengine.server.dbdata;

import java.sql.SQLException;
import java.util.ArrayList;

import com.redhat.reportengine.server.dbmap.ReportGroup;
import com.redhat.reportengine.server.sql.SqlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * May 16, 2013
 */
public class ReportGroupTable {
	private final static String GET_REPORT_GROUP_SEQ_ID						= "getReportGroupNextId";
	private final static String GET_REPORT_GROUP_ALL						= "getReportGroupAll";
	private final static String GET_REPORT_GROUP_BY_ID						= "getReportGroupById";
	private final static String INSERT_REPORT_GROUP							= "insertReportGroup";
	private final static String UPDATE_REPORT_GROUP							= "updateReportGroup";
	private final static String DELETE_REPORT_GROUP							= "deleteReportGroup";
	
	public void add(ReportGroup reportGroup) throws SQLException{
		SqlMap.getSqlMapClient().insert(INSERT_REPORT_GROUP, reportGroup);
	}
	
	public void modify(ReportGroup reportGroup) throws SQLException{
		SqlMap.getSqlMapClient().update(UPDATE_REPORT_GROUP, reportGroup);
	}
	
	public void remove(int id) throws SQLException{
		SqlMap.getSqlMapClient().delete(DELETE_REPORT_GROUP, id);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ReportGroup> getAll() throws SQLException{
		return (ArrayList<ReportGroup>) SqlMap.getSqlMapClient().queryForList(GET_REPORT_GROUP_ALL);
	}
	
	public ReportGroup get(Integer id) throws SQLException{
		return (ReportGroup) SqlMap.getSqlMapClient().queryForObject(GET_REPORT_GROUP_BY_ID, id);
	}
	
	public Integer getNextSeqId() throws SQLException{
		return (Integer) SqlMap.getSqlMapClient().queryForObject(GET_REPORT_GROUP_SEQ_ID);
	}
	
}

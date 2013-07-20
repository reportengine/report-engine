package com.redhat.reportengine.server.dbdata;

import java.sql.SQLException;
import java.util.ArrayList;

import com.redhat.reportengine.server.dbmap.ServerJob;
import com.redhat.reportengine.server.sql.SqlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 18, 2013
 */
public class ServerJobTable {
	public static final String INSERT_SERVER_JOB_DETAIL 			= "insertServerJobDetail";
	public static final String GET_SERVER_JOB_DETAIL_ALL 			= "getServerJobDetailAll";
	public static final String GET_SERVER_JOB_DETAILS_BY_SERVER_ID	= "getServerJobDetailByServerId";
	public static final String GET_SERVER_JOB_DETAILS_BY_JOB_ID		= "getServerJobDetailByJobId";
	public static final String DELETE_SERVER_JOB_BY_SERVER_ID		= "deleteServerJobDetailByServerId";
	public static final String DELETE_SERVER_JOB_BY_JOB_ID			= "deleteServerJobDetailByJobId";

	

	public void add(ServerJob serverJob) throws SQLException{
		SqlMap.getSqlMapClient().insert(INSERT_SERVER_JOB_DETAIL, serverJob);		
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ServerJob> get() throws SQLException{
		return (ArrayList<ServerJob>) SqlMap.getSqlMapClient().queryForList(GET_SERVER_JOB_DETAIL_ALL);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ServerJob> getByServerId(Integer serverId) throws SQLException{
		return (ArrayList<ServerJob>) SqlMap.getSqlMapClient().queryForList(GET_SERVER_JOB_DETAILS_BY_SERVER_ID, serverId);
	}
	
	public ServerJob getByJobId(Integer jobId) throws SQLException{
		return (ServerJob) SqlMap.getSqlMapClient().queryForObject(GET_SERVER_JOB_DETAILS_BY_SERVER_ID, jobId);
	}
	
	public void removeByServerId(Integer serverId) throws SQLException{
		SqlMap.getSqlMapClient().delete(DELETE_SERVER_JOB_BY_SERVER_ID, serverId);
	}
	
	public void removeByJobId(Integer jobId) throws SQLException{
		SqlMap.getSqlMapClient().delete(DELETE_SERVER_JOB_BY_JOB_ID, jobId);
	}
}

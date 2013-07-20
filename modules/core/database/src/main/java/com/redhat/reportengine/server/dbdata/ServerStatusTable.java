package com.redhat.reportengine.server.dbdata;

import java.sql.SQLException;
import java.util.ArrayList;

import com.redhat.reportengine.server.dbmap.ServerStatus;
import com.redhat.reportengine.server.sql.SqlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 09, 2013
 */
public class ServerStatusTable {
	public static final String INSERT_SERVER_STATUS_DETAIL 			= "insertServerStatusDetail";
	public static final String GET_SERVER_STATUS_BY_SERVER_ID 		= "getServerStatusDetailByServerId";
	public static final String GET_SERVER_STATUS_BY_TIME_RANGE 		= "getServerStatusDetailByTimeRange";
	public static final String DELETE_SERVER_STATUS_BY_SERVER_ID 	= "deleteServerStatusDetailByServerId";
	
	public void add(ServerStatus serverStatus) throws SQLException{
		SqlMap.getSqlMapClient().insert(INSERT_SERVER_STATUS_DETAIL, serverStatus);
	}
	
	public void remove(ServerStatus serverStatus) throws SQLException{
		SqlMap.getSqlMapClient().insert(DELETE_SERVER_STATUS_BY_SERVER_ID, serverStatus);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ServerStatus> getByServerId(String serverId) throws SQLException{
		return (ArrayList<ServerStatus>) SqlMap.getSqlMapClient().queryForList(GET_SERVER_STATUS_BY_SERVER_ID, serverId);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ServerStatus> getByTime(ServerStatus serverStatus) throws SQLException{
		return (ArrayList<ServerStatus>) SqlMap.getSqlMapClient().queryForList(GET_SERVER_STATUS_BY_TIME_RANGE, serverStatus);
	}
	
}

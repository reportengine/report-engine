package com.redhat.reportengine.server.dbdata;

import java.sql.SQLException;

import com.redhat.reportengine.server.dbmap.ServerOsDetail;
import com.redhat.reportengine.server.sql.SqlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 19, 2013
 */
public class ServerOsDetailTable {
	public static final String INSERT_SERVER_OS_DETAIL 					= "insertServerOsDetail";
	public static final String UPDATE_SERVER_OS_DETAIL 					= "updateServerOsDetail";
	public static final String GET_SERVER_OS_DETAIL_BY_SERVER_ID 		= "getServerOsDetailByServerId";
	public static final String DELETE_SERVER_OS_DETAIL_BY_SERVER_ID 	= "deleteServerOsDetailByServerId";
	
	public void add(ServerOsDetail serverOsDetail) throws SQLException{
		SqlMap.getSqlMapClient().insert(INSERT_SERVER_OS_DETAIL, serverOsDetail);
	}
	
	public void update(ServerOsDetail serverOsDetail) throws SQLException{
		SqlMap.getSqlMapClient().update(UPDATE_SERVER_OS_DETAIL, serverOsDetail);
	}
	
	public void remove(int serverId) throws SQLException{
		SqlMap.getSqlMapClient().insert(DELETE_SERVER_OS_DETAIL_BY_SERVER_ID, serverId);
	}
	
	public ServerOsDetail getByServerId(int serverId) throws SQLException{
		return (ServerOsDetail) SqlMap.getSqlMapClient().queryForObject(GET_SERVER_OS_DETAIL_BY_SERVER_ID, serverId);
	}
	
}

package com.redhat.reportengine.server.dbdata;

import java.sql.SQLException;

import com.redhat.reportengine.server.dbmap.ServerCpuDetail;
import com.redhat.reportengine.server.sql.SqlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 15, 2013
 */
public class ServerCpuDetailTable {
	public static final String INSERT_SERVER_CPU_DETAIL 				= "insertServerCpuDetail";
	public static final String UPDATE_SERVER_CPU_DETAIL 				= "updateServerCpuDetail";
	public static final String GET_SERVER_CPU_DETAIL_BY_SERVER_ID 		= "getServerCpuDetailByServerId";
	public static final String DELETE_SERVER_CPU_DETAIL_BY_SERVER_ID 	= "deleteServerCpuDetailByServerId";
	
	public void add(ServerCpuDetail serverCpuDetail) throws SQLException{
		SqlMap.getSqlMapClient().insert(INSERT_SERVER_CPU_DETAIL, serverCpuDetail);
	}
	
	public void update(ServerCpuDetail serverCpuDetail) throws SQLException{
		SqlMap.getSqlMapClient().update(UPDATE_SERVER_CPU_DETAIL, serverCpuDetail);
	}
	
	public void remove(int serverId) throws SQLException{
		SqlMap.getSqlMapClient().insert(DELETE_SERVER_CPU_DETAIL_BY_SERVER_ID, serverId);
	}
	
	public ServerCpuDetail getByServerId(int serverId) throws SQLException{
		return (ServerCpuDetail) SqlMap.getSqlMapClient().queryForObject(GET_SERVER_CPU_DETAIL_BY_SERVER_ID, serverId);
	}
	
}

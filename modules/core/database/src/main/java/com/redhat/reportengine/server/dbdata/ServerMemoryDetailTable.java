package com.redhat.reportengine.server.dbdata;

import java.sql.SQLException;

import com.redhat.reportengine.server.dbmap.ServerMemoryDetail;
import com.redhat.reportengine.server.sql.SqlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 19, 2013
 */
public class ServerMemoryDetailTable {
	public static final String INSERT_SERVER_MEMORY_DETAIL 					= "insertServerMemoryDetail";
	public static final String UPDATE_SERVER_MEMORY_DETAIL 					= "updateServerMemoryDetail";
	public static final String GET_SERVER_MEMORY_DETAIL_BY_SERVER_ID 		= "getServerMemoryDetailByServerId";
	public static final String DELETE_SERVER_MEMORY_DETAIL_BY_SERVER_ID 	= "deleteServerMemoryDetailByServerId";
	
	public void add(ServerMemoryDetail serverMemoryDetail) throws SQLException{
		SqlMap.getSqlMapClient().insert(INSERT_SERVER_MEMORY_DETAIL, serverMemoryDetail);
	}
	
	public void update(ServerMemoryDetail serverMemoryDetail) throws SQLException{
		SqlMap.getSqlMapClient().update(UPDATE_SERVER_MEMORY_DETAIL, serverMemoryDetail);
	}
	
	public void remove(int serverId) throws SQLException{
		SqlMap.getSqlMapClient().insert(DELETE_SERVER_MEMORY_DETAIL_BY_SERVER_ID, serverId);
	}
	
	public ServerMemoryDetail getByServerId(int serverId) throws SQLException{
		return (ServerMemoryDetail) SqlMap.getSqlMapClient().queryForObject(GET_SERVER_MEMORY_DETAIL_BY_SERVER_ID, serverId);
	}
	
}

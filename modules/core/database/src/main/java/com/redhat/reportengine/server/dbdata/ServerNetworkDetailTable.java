package com.redhat.reportengine.server.dbdata;

import java.sql.SQLException;

import com.redhat.reportengine.server.dbmap.ServerNetworkDetail;
import com.redhat.reportengine.server.sql.SqlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 19, 2013
 */
public class ServerNetworkDetailTable {
	public static final String INSERT_SERVER_NETWORK_DETAIL 				= "insertServerNetworkDetail";
	public static final String UPDATE_SERVER_NETWORK_DETAIL 				= "updateServerNetworkDetail";
	public static final String GET_SERVER_NETWORK_DETAIL_BY_SERVER_ID 		= "getServerNetworkDetailByServerId";
	public static final String DELETE_SERVER_NETWORK_DETAIL_BY_SERVER_ID 	= "deleteServerNetworkDetailByServerId";
	
	public void add(ServerNetworkDetail serverNetworkDetail) throws SQLException{
		SqlMap.getSqlMapClient().insert(INSERT_SERVER_NETWORK_DETAIL, serverNetworkDetail);
	}
	
	public void update(ServerNetworkDetail serverNetworkDetail) throws SQLException{
		SqlMap.getSqlMapClient().update(UPDATE_SERVER_NETWORK_DETAIL, serverNetworkDetail);
	}
	
	public void remove(int serverId) throws SQLException{
		SqlMap.getSqlMapClient().insert(DELETE_SERVER_NETWORK_DETAIL_BY_SERVER_ID, serverId);
	}
	
	public ServerNetworkDetail getByServerId(int serverId) throws SQLException{
		return (ServerNetworkDetail) SqlMap.getSqlMapClient().queryForObject(GET_SERVER_NETWORK_DETAIL_BY_SERVER_ID, serverId);
	}
	
}

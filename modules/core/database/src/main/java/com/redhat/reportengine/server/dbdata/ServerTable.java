package com.redhat.reportengine.server.dbdata;

import java.sql.SQLException;
import java.util.ArrayList;

import com.redhat.reportengine.server.dbmap.Server;
import com.redhat.reportengine.server.sql.SqlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 08, 2013
 */
public class ServerTable {
	public static final String INSERT_SERVER_DETAIL 			= "insertServerDetail";
	public static final String UPDATE_SERVER_DETAIL 			= "updateServerDetail";
	public static final String GET_SERVER_DETAIL_ALL 			= "getServerDetailAll";
	public static final String GET_SERVER_DETAILS_BY_ID			= "getServerDetailById";
	public static final String GET_SERVER_DETAILS_BY_NAME		= "getServerDetailByName";
	public static final String GET_SERVER_DETAILS_BY_HOST_IP	= "getServerDetailByHostId";
	public static final String DELETE_SERVER_DETAILS_BY_ID		= "deleteServerDetailById";

	

	public void add(Server server) throws SQLException{
		SqlMap.getSqlMapClient().insert(INSERT_SERVER_DETAIL, server);		
	}
	
	public void update(Server server) throws SQLException{
		SqlMap.getSqlMapClient().update(UPDATE_SERVER_DETAIL, server);		
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Server> get() throws SQLException{
		return (ArrayList<Server>) SqlMap.getSqlMapClient().queryForList(GET_SERVER_DETAIL_ALL);
	}
	
	public Server getById(Integer id) throws SQLException{
		return (Server) SqlMap.getSqlMapClient().queryForObject(GET_SERVER_DETAILS_BY_ID, id);
	}
	
	public Server getByName(String serverName) throws SQLException{
		return (Server) SqlMap.getSqlMapClient().queryForObject(GET_SERVER_DETAILS_BY_NAME, serverName);
	}
	
	public Server getByHostIp(String serverHostIp) throws SQLException{
		return (Server) SqlMap.getSqlMapClient().queryForObject(GET_SERVER_DETAILS_BY_HOST_IP, serverHostIp);
	}
	
	public void remove(Integer id) throws SQLException{
		SqlMap.getSqlMapClient().delete(DELETE_SERVER_DETAILS_BY_ID, id);
	}
}

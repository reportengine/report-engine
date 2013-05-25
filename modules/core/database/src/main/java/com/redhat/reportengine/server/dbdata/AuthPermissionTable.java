package com.redhat.reportengine.server.dbdata;

import java.sql.SQLException;
import java.util.ArrayList;

import com.redhat.reportengine.server.dbmap.AuthPermission;
import com.redhat.reportengine.server.sql.SqlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * May 03, 2013
 */
public class AuthPermissionTable{

	public static final String GET_AUTH_PERMISSION_ALL 		= "getAuthpermissionAll";
	public static final String GET_AUTH_PERMISSION_BY_NAME 	= "getAuthpermissionByName";
	public static final String GET_AUTH_PERMISSION_BY_ID 		= "getAuthpermissionById";
	public static final String INSERT_AUTH_PERMISSION 		= "insertAuthpermission";
	public static final String UPDATE_AUTH_PERMISSION		 	= "updateAuthpermission";
	public static final String DELETE_AUTH_PERMISSION_BY_ID 	= "deleteAuthpermissionById";
	
	@SuppressWarnings("unchecked")
	public ArrayList<AuthPermission> getAll() throws SQLException{
		return (ArrayList<AuthPermission>)SqlMap.getSqlMapClient().queryForList(GET_AUTH_PERMISSION_ALL);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<AuthPermission> getById(AuthPermission permission) throws SQLException{
		return (ArrayList<AuthPermission>)SqlMap.getSqlMapClient().queryForList(GET_AUTH_PERMISSION_BY_ID, permission);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<AuthPermission> getByName(AuthPermission permission) throws SQLException{
		return (ArrayList<AuthPermission>)SqlMap.getSqlMapClient().queryForList(GET_AUTH_PERMISSION_BY_NAME, permission);
	}
	
	public void add(AuthPermission permission) throws SQLException{
		SqlMap.getSqlMapClient().insert(INSERT_AUTH_PERMISSION, permission);
	}
	
	public void update(AuthPermission permission) throws SQLException{
		SqlMap.getSqlMapClient().insert(UPDATE_AUTH_PERMISSION, permission);
	}
	
	public void deleteById(AuthPermission permission) throws SQLException{
		SqlMap.getSqlMapClient().insert(DELETE_AUTH_PERMISSION_BY_ID, permission);
	}
}

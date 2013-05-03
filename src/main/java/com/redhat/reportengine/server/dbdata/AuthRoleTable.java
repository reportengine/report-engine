package com.redhat.reportengine.server.dbdata;

import java.sql.SQLException;
import java.util.ArrayList;

import com.redhat.reportengine.server.dbmap.AuthPermission;
import com.redhat.reportengine.server.dbmap.AuthRole;
import com.redhat.reportengine.server.dbmap.AuthRolePermissionMap;
import com.redhat.reportengine.server.sql.SqlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * May 03, 2013
 */
public class AuthRoleTable {
	public static final String GET_AUTH_ROLE_ALL 		= "getAuthRoleAll";
	public static final String GET_AUTH_ROLE_BY_NAME 	= "getAuthRoleByName";
	public static final String GET_AUTH_ROLE_BY_ID 		= "getAuthRoleById";
	public static final String INSERT_AUTH_ROLE 		= "insertAuthRole";
	public static final String UPDATE_AUTH_ROLE		 	= "updateAuthRole";
	public static final String DELETE_AUTH_ROLE_BY_ID 	= "deleteAuthRoleById";
	
	public static final String INSERT_AUTH_ROLE_PERMISSION_MAP 					= "insertAuthRolePermissionMap";
	public static final String DELETE_AUTH_ROLE_PERMISSION_MAP_BY_ROLE_ID 		= "deleteAuthRolePermissionMapByRoleId";
	public static final String GET_AUTH_ROLE_PERMISSION_MAP_ALL 				= "getAuthRolePermissionMapAll";
	public static final String GET_AUTH_ROLE_PERMISSION_MAP_BY_ROLE_ID 			= "getAuthRolePermissionMapByRoleId";
	public static final String GET_AUTH_ROLE_PERMISSION_MAP_BY_PERMISSION_ID 	= "getAuthRolePermissionMapByPermissionId";
	
	public ArrayList<AuthRole> getAll() throws SQLException{
		return (ArrayList<AuthRole>)SqlMap.getSqlMapClient().queryForList(GET_AUTH_ROLE_ALL);
	}
	
	public ArrayList<AuthRole> getById(AuthRole role) throws SQLException{
		return (ArrayList<AuthRole>)SqlMap.getSqlMapClient().queryForList(GET_AUTH_ROLE_BY_ID, role);
	}
	
	public ArrayList<AuthRole> getByName(AuthRole role) throws SQLException{
		return (ArrayList<AuthRole>)SqlMap.getSqlMapClient().queryForList(GET_AUTH_ROLE_BY_NAME, role);
	}
	
	public void add(AuthRole role) throws SQLException{
		SqlMap.getSqlMapClient().insert(INSERT_AUTH_ROLE, role);
		AuthRolePermissionMap rolePermissionMap = new AuthRolePermissionMap();
		role = (AuthRole) SqlMap.getSqlMapClient().queryForObject(GET_AUTH_ROLE_BY_NAME, role);
		rolePermissionMap.setRoleId(role.getId());
		for(AuthPermission permission : role.getPermissions()){
			rolePermissionMap.setPermissionId(permission.getId());
			SqlMap.getSqlMapClient().insert(INSERT_AUTH_ROLE_PERMISSION_MAP, rolePermissionMap);
		}		
	}
	
	public void update(AuthRole role) throws SQLException{
		SqlMap.getSqlMapClient().insert(UPDATE_AUTH_ROLE, role);
		AuthRolePermissionMap rolePermissionMap = new AuthRolePermissionMap();
		rolePermissionMap.setRoleId(role.getId());
		// Delete available Role Permission Map
		SqlMap.getSqlMapClient().delete(DELETE_AUTH_ROLE_PERMISSION_MAP_BY_ROLE_ID, rolePermissionMap);
		// Create New Role Permission Map
		for(AuthPermission permission : role.getPermissions()){
			rolePermissionMap.setPermissionId(permission.getId());
			SqlMap.getSqlMapClient().insert(INSERT_AUTH_ROLE_PERMISSION_MAP, rolePermissionMap);
		}
	}
	
	public void deleteById(AuthRole role) throws SQLException{
		SqlMap.getSqlMapClient().insert(DELETE_AUTH_ROLE_BY_ID, role);
	}
}

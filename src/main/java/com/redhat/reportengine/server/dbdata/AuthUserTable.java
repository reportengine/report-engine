package com.redhat.reportengine.server.dbdata;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * May 01, 2013
 */
import java.sql.SQLException;
import java.util.ArrayList;

import com.redhat.reportengine.server.dbmap.AuthUser;
import com.redhat.reportengine.server.dbmap.LDAPDetails;
import com.redhat.reportengine.server.sql.SqlMap;

public class AuthUserTable {
	public static final String GET_AUTH_USER_ALL 		= "getAuthUserAll";
	public static final String GET_AUTH_USER_ENABLED 	= "getAuthUserEnabled";
	public static final String GET_AUTH_USER_BY_NAME 	= "getAuthUserByName";
	public static final String GET_AUTH_USER_BY_ID 		= "getAuthUserById";
	public static final String GET_AUTH_USER_BY_EMAIL	= "getAuthUserByEmail";
	public static final String ADD_AUTH_USER 			= "insertAuthUser";
	public static final String ADD_AUTH_USER_PASSWORD	= "insertAuthUserPassword";
	public static final String UPDATE_AUTH_USER_PASSWORD	= "updateAuthUserPassword";
	
	
	public ArrayList<AuthUser> getAll() throws SQLException {
		return (ArrayList<AuthUser>)SqlMap.getSqlMapClient().queryForList(GET_AUTH_USER_ALL);
	}
	
	public ArrayList<AuthUser> getEnabled() throws SQLException {
		return (ArrayList<AuthUser>)SqlMap.getSqlMapClient().queryForList(GET_AUTH_USER_ENABLED);
	}

	public AuthUser getById(AuthUser authUser) throws SQLException {
		return (AuthUser)SqlMap.getSqlMapClient().queryForObject(GET_AUTH_USER_BY_ID, authUser);
	}

	public AuthUser getByName(String userName) throws SQLException {
		AuthUser user = new AuthUser();
		user.setUserName(userName);
		return this.getByName(user);
	}
	
	public AuthUser getByName(AuthUser authUser) throws SQLException {
		return (AuthUser)SqlMap.getSqlMapClient().queryForObject(GET_AUTH_USER_BY_NAME, authUser);
	}
	
	public AuthUser getByEmail(AuthUser authUser) throws SQLException {
		return (AuthUser)SqlMap.getSqlMapClient().queryForObject(GET_AUTH_USER_BY_EMAIL, authUser);
	}
	
	public void add(AuthUser authUser) throws SQLException {
		SqlMap.getSqlMapClient().insert(ADD_AUTH_USER, authUser);
		if(authUser.isInternal()){
			AuthUser newUser = this.getByName(authUser);			
			authUser.setUserid(newUser.getId());
			SqlMap.getSqlMapClient().insert(ADD_AUTH_USER_PASSWORD, authUser);
		}
	}
	//TODO: Add user role map code
	
	public void updatePassword(AuthUser user) throws SQLException{
			SqlMap.getSqlMapClient().update(UPDATE_AUTH_USER_PASSWORD, user);
	}
}

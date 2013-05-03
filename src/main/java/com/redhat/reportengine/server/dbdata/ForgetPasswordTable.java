package com.redhat.reportengine.server.dbdata;

import java.sql.SQLException;
import java.util.ArrayList;

import com.redhat.reportengine.server.dbmap.ForgetPassword;
import com.redhat.reportengine.server.sql.SqlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * May 03, 2013
 */
public class ForgetPasswordTable {
	public static final String INSERT_FORGET_PASSWORD					= "insertForgetPassword";
	public static final String GET_FORGET_PASSWORD_ALL					= "getForgetPasswordAll";
	public static final String GET_FORGET_PASSWORD_BY_USER_ID			= "getForgetPasswordByUserId";
	public static final String GET_FORGET_PASSWORD_BY_ID				= "getForgetPasswordById";
	public static final String DELETE_FORGET_PASSWORD_BY_USER_ID		= "deleteForgetPasswordByUserId";
	public static final String DELETE_FORGET_PASSWORD_BY_ID				= "deleteForgetPasswordById";
	public static final String DELETE_FORGET_PASSWORD_BY_CREATION_TIME	= "deleteForgetPasswordByCreationTime";
	
	
	public ArrayList<ForgetPassword> getAll() throws SQLException{
		return (ArrayList<ForgetPassword>) SqlMap.getSqlMapClient().queryForList(GET_FORGET_PASSWORD_ALL);
	}
	
	public ForgetPassword getById(ForgetPassword forgetPassword) throws SQLException{
		return (ForgetPassword) SqlMap.getSqlMapClient().queryForObject(GET_FORGET_PASSWORD_BY_ID, forgetPassword);
	}
	
	public ForgetPassword getByUserId(ForgetPassword forgetPassword) throws SQLException{
		return (ForgetPassword) SqlMap.getSqlMapClient().queryForObject(GET_FORGET_PASSWORD_BY_USER_ID, forgetPassword);
	}
	
	public ForgetPassword getByUserId(int userId) throws SQLException{
		ForgetPassword fpassword = new ForgetPassword();
		fpassword.setUserId(userId);
		return getByUserId(fpassword);
	}
	
	public void add(ForgetPassword forgetPassword) throws SQLException{
		SqlMap.getSqlMapClient().insert(INSERT_FORGET_PASSWORD, forgetPassword);
	}
	
	public void deleteById(ForgetPassword forgetPassword) throws SQLException{
		SqlMap.getSqlMapClient().delete(DELETE_FORGET_PASSWORD_BY_ID, forgetPassword);
	}
	
	public void deleteByUserId(int userId) throws SQLException{
		ForgetPassword fpassword = new ForgetPassword();
		fpassword.setUserId(userId);
		this.deleteByUserId(fpassword);
	}
	
	public void deleteByUserId(ForgetPassword forgetPassword) throws SQLException{
		SqlMap.getSqlMapClient().delete(DELETE_FORGET_PASSWORD_BY_USER_ID, forgetPassword);
	}
	
	public void deleteByCreationTime(ForgetPassword forgetPassword) throws SQLException{
		SqlMap.getSqlMapClient().delete(DELETE_FORGET_PASSWORD_BY_CREATION_TIME, forgetPassword);
	}
}

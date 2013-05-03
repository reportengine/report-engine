package com.redhat.reportengine.server.dbdata;

import java.sql.SQLException;
import java.util.ArrayList;

import com.redhat.reportengine.server.dbmap.LDAPDetails;
import com.redhat.reportengine.server.sql.SqlMap;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * May 01, 2013
 */
public class LDAPTable {

	public static final String GET_LDAP_ALL 	= "getLdapAll";
	public static final String GET_LDAP_ENABLED = "getLdapEnabled";
	public static final String GET_LDAP_BY_NAME = "getLdapByName";
	public static final String GET_LDAP_BY_ID 	= "getLdapById";
	
	public ArrayList<LDAPDetails> getAll() throws SQLException {
		return (ArrayList<LDAPDetails>)SqlMap.getSqlMapClient().queryForList(GET_LDAP_ALL);
	}
	
	public ArrayList<LDAPDetails> getEnabled() throws SQLException {
		return (ArrayList<LDAPDetails>)SqlMap.getSqlMapClient().queryForList(GET_LDAP_ENABLED);
	}

	public LDAPDetails getById(LDAPDetails ldap) throws SQLException {
		return getById(ldap.getId());
	}
	
	public LDAPDetails getById(Integer ldapId) throws SQLException {
		return (LDAPDetails)SqlMap.getSqlMapClient().queryForObject(GET_LDAP_BY_ID, ldapId);
	}

	public LDAPDetails getByName(String ldapName) throws SQLException {
		return (LDAPDetails)SqlMap.getSqlMapClient().queryForObject(GET_LDAP_BY_NAME, ldapName);
	}
	
	public LDAPDetails getByName(LDAPDetails ldap) throws SQLException {
		return getByName(ldap.getName());
	}

}

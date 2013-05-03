package com.redhat.reportengine.server.authentication;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.apache.log4j.Logger;

import com.redhat.reportengine.server.dbdata.AuthUserTable;
import com.redhat.reportengine.server.dbdata.LDAPTable;
import com.redhat.reportengine.server.dbmap.AuthUser;
import com.redhat.reportengine.server.dbmap.LDAPDetails;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 26, 2013
 */
public class Authentication {
	private static Logger _logger = Logger.getLogger(Authentication.class);
	public static final String AUTH_TYPE_INTERNAL 	= "Internal";
	public static final String AUTH_TYPE_LDAP 		= "LDAP";

	public boolean authenticate(String userName, String password, String authType) throws SQLException, NoSuchAlgorithmException{
		if(authType.equalsIgnoreCase(AUTH_TYPE_INTERNAL)){
			return internalDB(userName, password);
		}else{
			return ldap(userName, password, authType);
		}
	}
	public boolean ldap(String userName, String password, String ldapName) throws SQLException{
		LDAPDetails ldap = new LDAPTable().getByName(ldapName);
		boolean authenticated = false;

		// Hashtable to store environment for the ldap connection
		Hashtable<String, String> environment = new Hashtable<String, String>(10);
		// Ldap context object
		LdapContext ldapContext = null;
		// modifying username to cn=uname
		try{
			if(ldap.getUrl().startsWith("ldaps")){
				environment.put(Context.SECURITY_AUTHENTICATION, "ssl");
			}
			environment.put(Context.SECURITY_AUTHENTICATION, "simple");
			environment.put(Context.SECURITY_PRINCIPAL, "uid=" + userName + "," + ldap.getBaseDn());// User
			environment.put(Context.SECURITY_CREDENTIALS, password);// Password
			environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");// this class is in rt.jar
			environment.put(Context.PROVIDER_URL, ldap.getUrl());

			// Create the initial directory context
			ldapContext = new InitialLdapContext(environment, null);
			if (ldapContext != null){
				_logger.debug("Login Success["+userName+"], LDAP: "+ldap.getName());
				authenticated = true;
			}				
		} 
		catch (NamingException ex){
			_logger.info("Login Failed["+userName+"], LDAP: "+ldap.getName()+", "+ex.getMessage());
		}
		return authenticated;
	}

	public boolean internalDB(String userName, String password) throws SQLException, NoSuchAlgorithmException{
		AuthUser internalUser = new AuthUser();
		internalUser.setUserName(userName);
		internalUser = new AuthUserTable().getByName(internalUser);
		if(internalUser == null){
			_logger.info("Internal Login Failed["+userName+"], User not in DB!");
			return false;
		}else if(internalUser.getPassword().equals(new Password().getSaltedPassword(password, internalUser.getPasswordSalt()))){
			_logger.info("Internal Login Success["+userName+"]");
			return true;
		}
		_logger.info("Internal Login Failed["+userName+"], Wrong Password!");
		return false;
	}
}

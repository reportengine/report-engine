/**
 * 
 */
package com.redhat.reportengine.server.gui;

import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import org.apache.log4j.Logger;


/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jun 18, 2012
 */
public class Login {
	
	private static Logger _logger = Logger.getLogger(Login.class);
	private String user = null;
	private String password = null;
	private String url = null;
	private String basedn = null;
	// Hashtable to store environment for the ldap connection
	private Hashtable<String, String> environment = new Hashtable<String, String>(10);
	// Ldap context object
	private LdapContext ldapContext = null;
	
	public boolean checkSessionState(HttpSession session){
		String userName = (String) session.getAttribute(Keys.SESSION_USER_ID);
		if(userName != null){
			if(Boolean.parseBoolean(""+session.getAttribute(Keys.SESSION_STATUS))){
				return true;
			}else{
				return false;
			}
		}
		_logger.info("Invalid session: Session ID: "+session.getId());
		return false;
	}
	
	public boolean checkLogin(HttpServletRequest request, HttpSession session) {
		String loginUser 	= (String)request.getParameter(Keys.USER_ID);
		String loginPasswd 	= (String)request.getParameter(Keys.USER_PASSWORD);
		//boolean authenticated = false;
		boolean authenticated =  authenticate(loginUser, loginPasswd);
		if(authenticated){
			session.setAttribute(Keys.SESSION_USER_ID, loginUser);
			session.setAttribute(Keys.SESSION_STATUS, true);
			_logger.debug("Log in success. Userid: "+loginUser);
			
		}
		else{
			session.setAttribute("TMP_USER", loginUser);
			_logger.info("Log in failed. Userid: "+loginUser+", Host: "+request.getRemoteAddr());
			//session.invalidate();
			
		}
		return authenticated;
	}
	
	// Every Authentication will initialize the ldapcontext to the authenticated
		// user.
		private boolean authenticate(String uname, String passwd) 
		{
			/*this.basedn 	= Common.getLdapBase(); //Base DN (BASE_DN)
			this.url 		= Common.getLdapUrl(); //Login url (URL)
			 */
			this.url	 	= "ldaps://ldap01.intranet.prod.int.sin2.redhat.com:636";
			this.basedn 	= "ou=Users,dc=redhat,dc=com";
			
			boolean authenticated = false;
			//try {
				this.environment.put(Context.SECURITY_AUTHENTICATION, "ssl");
				this.environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
				// this class is in rt.jar
				this.environment.put(Context.PROVIDER_URL, this.url);
				// Create the initial directory context
				//ldapContext = new InitialLdapContext(this.environment, null);
				/*}
			catch (NamingException ex) 
			{
				if(ex.getMessage().equalsIgnoreCase("UnknownHostException"))
				{
					
				}
				_logger.error("Exception in LDAP connection method!", ex);
			}*/
			// Hashtable to store environment for the ldap connection

			// modifying username to cn=uname
			this.user = "uid=" + uname + "," + basedn;
			this.password = passwd;
			try 
			{
				this.environment.put(Context.SECURITY_AUTHENTICATION, "ssl");
				this.environment.put(Context.SECURITY_AUTHENTICATION, "simple");
				this.environment.put(Context.SECURITY_PRINCIPAL, this.user);// User
				this.environment.put(Context.SECURITY_CREDENTIALS, this.password);// Password
				this.environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");// this class is in rt.jar
				this.environment.put(Context.PROVIDER_URL, this.url);
				
				// Close the older ldapcontext if any.
				if (ldapContext != null) {
					ldapContext.close();
					ldapContext = null;
				}
				// Create the initial directory context
				//logger.debug("Key Set: "+this.environment.keySet());
				ldapContext = new InitialLdapContext(this.environment, null);
				if (ldapContext != null)
				{
					_logger.debug("Login passed: "+this.user);
					authenticated = true;
				}				
			} 
			catch (NamingException ex) 
			{
				_logger.info("Login Failed! "+ex.getMessage());
			}finally{
				this.user = null;
				this.password = null;
				this.ldapContext = null;
			}
			return authenticated;
			
		}






}

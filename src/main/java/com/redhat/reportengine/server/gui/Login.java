/**
 * 
 */
package com.redhat.reportengine.server.gui;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import com.redhat.reportengine.server.authentication.Authentication;


/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jun 18, 2012
 */
public class Login {

	private static Logger _logger = Logger.getLogger(Login.class);

	public boolean checkSessionState(HttpSession session){
		String userName = (String) session.getAttribute(Keys.SESSION_USER_ID);
		if(userName != null){
			if(Boolean.parseBoolean(""+session.getAttribute(Keys.SESSION_STATUS))){
				return true;
			}else{
				_logger.debug("Invalid session["+session.getId()+"] (or) User["+userName+"] session timed out.");
				return false;
			}
		}
		_logger.debug("Invalid session["+session.getId()+"].");
		return false;
	}
	
	public boolean checkPasswordResetState(HttpSession session){
		if(checkSessionState(session)){
			if(Boolean.parseBoolean(""+session.getAttribute(Keys.SESSION_PASSWORD_RESET))){
				return true;
			}
		}
		return false;
	}

	public boolean checkLogin(HttpServletRequest request, HttpSession session) throws NoSuchAlgorithmException, SQLException {
		String loginUser 	= (String)request.getParameter(Keys.USER_ID);
		String loginPasswd 	= (String)request.getParameter(Keys.USER_PASSWORD);
		String database 	= (String)request.getParameter(Keys.LOGIN_DB);

		boolean authenticated = false;
		if(database.equals(Authentication.AUTH_TYPE_INTERNAL)){
			authenticated =  new Authentication().internalDB(loginUser, loginPasswd);
		}else{
			authenticated =  new Authentication().ldap(loginUser, loginPasswd, database);
		}		
		if(authenticated){
			session.setAttribute(Keys.SESSION_USER_ID, loginUser);
			session.setAttribute(Keys.SESSION_STATUS, true);
			_logger.info("Log in success. Userid["+loginUser+"], Host["+request.getRemoteAddr()+"], Database["+database+"]");
		}else{
			session.setAttribute(Keys.TMP_USER_ID, loginUser);
			session.setAttribute(Keys.TMP_LOGIN_DB, database);
			_logger.info("Log in failed. User["+loginUser+"], Host["+request.getRemoteAddr()+"], Database["+database+"]");
			//session.invalidate();
		}
		return authenticated;
	}
}

package com.redhat.reportengine.server.gui;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.redhat.reportengine.server.authentication.Password;
import com.redhat.reportengine.server.authentication.RandomData;
import com.redhat.reportengine.server.cache.ServerSettings;
import com.redhat.reportengine.server.dbdata.AuthUserTable;
import com.redhat.reportengine.server.dbdata.ForgetPasswordTable;
import com.redhat.reportengine.server.dbmap.AuthUser;
import com.redhat.reportengine.server.dbmap.ForgetPassword;
import com.redhat.reportengine.server.email.SendEmail;
import com.redhat.reportengine.server.reports.Keys;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * May 03, 2013
 */
public class AuthenticationForgetPassword {
	private static final int MAX_PASSWORD_REF_SIZE = 50;
	private static Logger _logger = Logger.getLogger(AuthenticationForgetPassword.class);

	private AuthUser getAuthUserFullDetails(HttpServletRequest request) throws SQLException{
		AuthUser user = new AuthUser();
		user.setUserName((String)request.getParameter(Keys.USER_ID));
		user.setEmail((String)request.getParameter(Keys.USER_EMAIL));
		if(user.getUserName() != null && user.getUserName().length() != 0){
			user = new AuthUserTable().getByName(user);
		}else if(user.getEmail() != null && user.getEmail().length() != 0){
			user = new AuthUserTable().getByEmail(user);
		}
		return user;
	}

	public void setForgetPassword(HttpServletRequest request, HttpServletResponse response) throws Exception{
		AuthUser user = getAuthUserFullDetails(request);
		if(user != null){
			ForgetPassword forgetPassword = new ForgetPassword();
			forgetPassword.setUserId(user.getId());
			forgetPassword.setReference(new Date().getTime()+new RandomData().getString(MAX_PASSWORD_REF_SIZE));
			new ForgetPasswordTable().deleteByUserId(user.getId());
			new ForgetPasswordTable().add(forgetPassword);
			this.sendForgetPassword(user, forgetPassword.getReference());
			_logger.info("Email["+user.getEmail()+"] sent to User["+user.getUserName()+"]...");
		}else{
			//_logger.info("User["+user.getUserName()+"] not available!");
			//TODO: invalid user details
		}
	}

	public void getForgetPassword(HttpServletRequest request, HttpSession session) throws SQLException{
		String passwordReference = (String)request.getParameter(Keys.USER_FORGET_PASSWORD_REFERENCE);
		if(passwordReference != null){
			AuthUser user = getAuthUserFullDetails(request);
			_logger.info("UserName: "+user.getUserName()+", Email: "+user.getEmail()+", User ID: "+user.getId());
			if(user != null && user.getUserid() != null){
				ForgetPassword forgetPassword = new ForgetPasswordTable().getByUserId(user.getId());
				if(forgetPassword != null && forgetPassword.getReference().equals(passwordReference)){
					session.setAttribute(Keys.SESSION_PASSWORD_RESET, true);
					session.setAttribute(Keys.SESSION_USER_ID, user.getUserName());
					session.setAttribute(Keys.SESSION_STATUS, true);
				}else{
					_logger.info("Reset Password Reference miss-match!");
				}
			}else{
				//_logger.info("User["+user.getUserName()+"] not available!");
				//TODO: invalid user details
			}
		}		
	}

	public void updatePassword(HttpServletRequest request, HttpSession session) throws NoSuchAlgorithmException, SQLException{
		AuthUser user = new AuthUser();
		user.setUserName((String)session.getAttribute((Keys.SESSION_USER_ID)));
		user = new AuthUserTable().getByName(user.getUserName());
		user.setPasswordSalt(new Password().getRandomSalt());
		user.setPassword(new Password().getSaltedPassword((String)request.getParameter(Keys.USER_PASSWORD), user.getPasswordSalt()));
		_logger.info("Password: "+user.getUserName()+"ID: "+user.getUserid()+", Pa: "+user.getPassword());
		new AuthUserTable().updatePassword(user);
		new ForgetPasswordTable().deleteByUserId(user.getId());
		session.invalidate();
	}

	private void sendForgetPassword(AuthUser user, String reference) throws Exception{
		String subject = "Report Engine - Forget Password URL";
		StringBuffer message = new StringBuffer();
		message.append("Url to reset your password\n\n");
		message.append(ServerSettings.getEngineURL()).append("/pages/login.jsp?SUBMIT=ResetPassword&");
		message.append(Keys.USER_ID).append("=").append(user.getUserName());
		message.append("&").append(Keys.USER_FORGET_PASSWORD_REFERENCE).append("=").append(reference);
		new SendEmail().sendEmail(user.getEmail(), null, ServerSettings.getEmailFrom(), subject, message.toString(), ServerSettings.getEmailServer(), ServerSettings.getEmailServerPort());
	}
}

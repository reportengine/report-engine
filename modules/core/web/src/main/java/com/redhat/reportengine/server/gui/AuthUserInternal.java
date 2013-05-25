package com.redhat.reportengine.server.gui;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.redhat.reportengine.server.authentication.Password;
import com.redhat.reportengine.server.dbdata.AuthUserTable;
import com.redhat.reportengine.server.dbmap.AuthUser;
import com.redhat.reportengine.server.reports.Keys;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Apr 30, 2013
 */
public class AuthUserInternal {
	
	public void addUser(HttpServletRequest request, HttpServletResponse response) throws NoSuchAlgorithmException, SQLException{
		AuthUser user = new AuthUser();
		user.setEnabled(true);
		user.setInternal(true);
		user.setFirstName((String)request.getParameter(Keys.USER_FIRST_NAME));
		user.setLastName((String)request.getParameter(Keys.USER_LAST_NAME));
		user.setUserName((String)request.getParameter(Keys.USER_ID));
		user.setEmail((String)request.getParameter(Keys.USER_EMAIL));
		user.setCreationTime(new Date());
		user.setLastEditTime(new Date());
		user.setPasswordSalt(new Password().getRandomSalt());
		user.setPassword(new Password().getSaltedPassword((String)request.getParameter(Keys.USER_PASSWORD), user.getPasswordSalt()));
		new AuthUserTable().add(user);		
	}

}

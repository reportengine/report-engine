package com.redhat.reportengine.server.gui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author jkandasa (Jeeva Kandasamy)
 * Jun 19, 2012
 */
public class Logout {
	public void doLogout(HttpServletRequest request, HttpSession session){
		String userName = (String) session.getAttribute(Keys.SESSION_USER_ID);
		if(userName != null){
			//String hostIP = (String) request.getRemoteAddr();
			session.invalidate();				
		}
		
	}
}

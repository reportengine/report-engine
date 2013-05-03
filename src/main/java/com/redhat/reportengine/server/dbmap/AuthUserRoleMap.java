package com.redhat.reportengine.server.dbmap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * May 03, 2013
 */
public class AuthUserRoleMap {
	private int userId;
	private int roleId;
	/**
	 * @return the userid
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * @param userid the userid to set
	 */
	public void setUserId(int userid) {
		this.userId = userid;
	}
	/**
	 * @return the roleid
	 */
	public int getRoleId() {
		return roleId;
	}
	/**
	 * @param roleid the roleid to set
	 */
	public void setRoleId(int roleid) {
		this.roleId = roleid;
	}
}

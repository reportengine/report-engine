package com.redhat.reportengine.server.dbmap;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * May 03, 2013
 */
public class AuthRolePermissionMap {
	private int roleId;
	private int permissionId;
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
	/**
	 * @return the permissionid
	 */
	public int getPermissionId() {
		return permissionId;
	}
	/**
	 * @param permissionid the permissionid to set
	 */
	public void setPermissionId(int permissionid) {
		this.permissionId = permissionid;
	}
}

package com.redhat.reportengine.server.dbmap;

import java.util.LinkedList;


/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jan 31, 2013
 */
public class AuthRole {
	private int id;
	private String name;
	private String description;
	private LinkedList<AuthPermission> permissions;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the permissions
	 */
	public LinkedList<AuthPermission> getPermissions() {
		return permissions;
	}
	/**
	 * @param permissions the permissions to set
	 */
	public void setPermissions(LinkedList<AuthPermission> permissions) {
		this.permissions = permissions;
	}
}

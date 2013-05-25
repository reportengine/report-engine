package com.redhat.reportengine.server.dbmap;


/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * May 03, 2013
 */
public class AuthPermission {
	private int id;
	private boolean systemLevel;
	private String name;
	private String description;
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
	 * @return the systemLevel
	 */
	public boolean isSystemLevel() {
		return systemLevel;
	}
	/**
	 * @param systemLevel the systemLevel to set
	 */
	public void setSystemLevel(boolean systemLevel) {
		this.systemLevel = systemLevel;
	}
}

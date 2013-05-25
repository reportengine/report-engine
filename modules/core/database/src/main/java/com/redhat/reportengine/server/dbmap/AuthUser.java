package com.redhat.reportengine.server.dbmap;

import java.util.Date;
import java.util.Set;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jan 31, 2013
 */
public class AuthUser {
	private int id;
	private Integer userid;
	private boolean enabled;
	private boolean internal;
	private Integer ldapId;
	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String passwordSalt;
	private Date creationTime;
	private Date lastEditTime;
	private Set<AuthRole> roles;
	
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
		this.userid = id;
	}
	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the passwordSalt
	 */
	public String getPasswordSalt() {
		return passwordSalt;
	}
	/**
	 * @param passwordSalt the passwordSalt to set
	 */
	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}
	/**
	 * @return the creationTime
	 */
	public Date getCreationTime() {
		return creationTime;
	}
	/**
	 * @param creationTime the creationTime to set
	 */
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	/**
	 * @return the lastEditTime
	 */
	public Date getLastEditTime() {
		return lastEditTime;
	}
	/**
	 * @param lastEditTime the lastEditTime to set
	 */
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<AuthRole> roles) {
		this.roles = roles;
	}
	/**
	 * @return the roles
	 */
	public Set<AuthRole> getRoles() {
		return roles;
	}
	/**
	 * @return the username
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param username the username to set
	 */
	public void setUserName(String username) {
		this.userName = username;
	}
	/**
	 * @return the ldaiId
	 */
	public Integer getLdapId() {
		return ldapId;
	}
	/**
	 * @param ldaiId the ldaiId to set
	 */
	public void setLdapId(Integer ldaiId) {
		this.ldapId = ldaiId;
	}
	/**
	 * @return the internal
	 */
	public boolean isInternal() {
		return internal;
	}
	/**
	 * @param internal the internal to set
	 */
	public void setInternal(boolean internal) {
		this.internal = internal;
	}
	/**
	 * @return the userid
	 */
	public Integer getUserid() {
		return userid;
	}
	/**
	 * @param userid the userid to set
	 */
	public void setUserid(Integer userid) {
		this.userid = userid;
	}

}

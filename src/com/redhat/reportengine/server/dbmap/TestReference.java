package com.redhat.reportengine.server.dbmap;

import java.io.Serializable;
import java.util.Date;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jun 16, 2012
 */
public class TestReference implements Serializable{

	private static final long serialVersionUID = 1727108410881588881L;
	
	private int id;
	private String testReference;
	private Date creationTime;
	/**
	 * @return the testReference
	 */
	public String getTestReference() {
		return testReference;
	}
	/**
	 * @param testReference the testReference to set
	 */
	public void setTestReference(String testReference) {
		this.testReference = testReference;
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
}

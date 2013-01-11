package com.redhat.reportengine.server.dbmap;

import java.util.Date;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jun 17, 2012
 */
public class FileStorage {
	private int id;
	private int testCaseId;
	private String fileName;
	private Date creationTime;
	private boolean screenShot = false;
	private byte[] fileByte;
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
	 * @return the testCaseId
	 */
	public int getTestCaseId() {
		return testCaseId;
	}
	/**
	 * @param testCaseId the testCaseId to set
	 */
	public void setTestCaseId(int testCaseId) {
		this.testCaseId = testCaseId;
	}
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	 * @return the scroonShot
	 */
	public boolean isScreenShot() {
		return screenShot;
	}
	/**
	 * @param scroonShot the scroonShot to set
	 */
	public void setScreenShot(boolean scroonShot) {
		this.screenShot = scroonShot;
	}
	/**
	 * @return the fileByte
	 */
	public byte[] getFileByte() {
		return fileByte;
	}
	/**
	 * @param fileByte the fileByte to set
	 */
	public void setFileByte(byte[] fileByte) {
		this.fileByte = fileByte;
	}
}

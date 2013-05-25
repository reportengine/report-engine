/**
 * 
 */
package com.redhat.reportengine.server.dbmap;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 16, 2012
 */
@XmlRootElement
public class TestGroup  implements Serializable{

	private static final long serialVersionUID = 7589079025543629879L;
	
	private int id;
	private int testSuiteId;
	private String testGroup;
	private String testComments;
	private Date localTime;
	private Date remoteTime;
	
	private int totalCases;
	private int passedCases;
	private int failedCases;
	private int skippedCases;
	
	private String testSuiteName;
	private String testBuild;
	
	/**
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the testTitleId
	 */
	public int getTestSuiteId() {
		return this.testSuiteId;
	}
	/**
	 * @param testTitleId the testTitleId to set
	 */
	public void setTestSuiteId(int testTitleId) {
		this.testSuiteId = testTitleId;
	}
	/**
	 * @return the testGroup
	 */
	public String getTestGroup() {
		return this.testGroup;
	}
	/**
	 * @param testGroup the testGroup to set
	 */
	public void setTestGroup(String testGroup) {
		this.testGroup = testGroup;
	}
	/**
	 * @return the testComments
	 */
	public String getTestComments() {
		return this.testComments;
	}
	/**
	 * @param testComments the testComments to set
	 */
	public void setTestComments(String testComments) {
		this.testComments = testComments;
	}
	/**
	 * @return the localTime
	 */
	public Date getLocalTime() {
		return this.localTime;
	}
	/**
	 * @param localTime the localTime to set
	 */
	public void setLocalTime(Date localTime) {
		this.localTime = localTime;
	}
	/**
	 * @return the remoteTime
	 */
	public Date getRemoteTime() {
		return this.remoteTime;
	}
	/**
	 * @param remoteTime the remoteTime to set
	 */
	public void setRemoteTime(Date remoteTime) {
		this.remoteTime = remoteTime;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * @return the totalCases
	 */
	public int getTotalCases() {
		return this.totalCases;
	}
	/**
	 * @param totalCases the totalCases to set
	 */
	public void setTotalCases(int totalCases) {
		this.totalCases = totalCases;
	}
	/**
	 * @return the passedCases
	 */
	public int getPassedCases() {
		return this.passedCases;
	}
	/**
	 * @param passedCases the passedCases to set
	 */
	public void setPassedCases(int passedCases) {
		this.passedCases = passedCases;
	}
	/**
	 * @return the failedCases
	 */
	public int getFailedCases() {
		return this.failedCases;
	}
	/**
	 * @param failedCases the failedCases to set
	 */
	public void setFailedCases(int failedCases) {
		this.failedCases = failedCases;
	}
	/**
	 * @return the skippedCases
	 */
	public int getSkippedCases() {
		return this.skippedCases;
	}
	/**
	 * @param skippedCases the skippedCases to set
	 */
	public void setSkippedCases(int skippedCases) {
		this.skippedCases = skippedCases;
	}
	/**
	 * @return the testSuiteName
	 */
	public String getTestSuiteName() {
		return this.testSuiteName;
	}
	/**
	 * @param testSuiteName the testSuiteName to set
	 */
	public void setTestSuiteName(String testSuiteName) {
		this.testSuiteName = testSuiteName;
	}
	/**
	 * @return the testBuild
	 */
	public String getTestBuild() {
		return this.testBuild;
	}
	/**
	 * @param testBuild the testBuild to set
	 */
	public void setTestBuild(String testBuild) {
		this.testBuild = testBuild;
	}

}

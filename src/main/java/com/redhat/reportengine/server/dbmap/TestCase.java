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
public class TestCase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1389244443632019231L;
	
	public static final String PASSED 	= "Passed";
	public static final String FAILED 	= "Failed";
	public static final String SKIPPED 	= "Skipped";
	public static final String RUNNING 	= "Running";
	public static final String NO_STATUS= "NoStatus";
	
	private int id;
	private int testSuiteId;
	private int testGroupId;
	private String testName;
	private String testArguments;
	private String testResult;
	private String testComments;
	private String testFiles;
	private String testGuiFiles;
	private Date localStartTime;
	private Date remoteStartTime;
	private Date localEndTime;
	private Date remoteEndTime;
	
	private long testDuration;
	
	private byte[] screenShotFileByte;
	private String screenShotFileName;
	
	private String testSuiteName;
	private String testBuild;
	private String testGroup;
	
	private String testResultNew;
	
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
	 * @return the testGroupId
	 */
	public int getTestGroupId() {
		return this.testGroupId;
	}
	/**
	 * @param testGroupId the testGroupId to set
	 */
	public void setTestGroupId(int testGroupId) {
		this.testGroupId = testGroupId;
	}
	/**
	 * @return the testName
	 */
	public String getTestName() {
		return this.testName;
	}
	/**
	 * @param testName the testName to set
	 */
	public void setTestName(String testName) {
		this.testName = testName;
	}
	/**
	 * @return the testArguments
	 */
	public String getTestArguments() {
		return this.testArguments;
	}
	/**
	 * @param testArguments the testArguments to set
	 */
	public void setTestArguments(String testArguments) {
		this.testArguments = testArguments;
	}
	/**
	 * @return the testResult
	 */
	public String getTestResult() {
		return this.testResult;
	}
	/**
	 * @param testResult the testResult to set
	 */
	public void setTestResult(String testResult) {
		this.testResult = testResult;
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
	 * @return the testFiles
	 */
	public String getTestFiles() {
		return this.testFiles;
	}
	/**
	 * @param testFiles the testFiles to set
	 */
	public void setTestFiles(String testFiles) {
		this.testFiles = testFiles;
	}
	/**
	 * @return the testGuiFiles
	 */
	public String getTestGuiFiles() {
		return this.testGuiFiles;
	}
	/**
	 * @param testGuiFiles the testGuiFiles to set
	 */
	public void setTestGuiFiles(String testGuiFiles) {
		this.testGuiFiles = testGuiFiles;
	}
	/**
	 * @return the localStartTime
	 */
	public Date getLocalStartTime() {
		return this.localStartTime;
	}
	/**
	 * @param localStartTime the localStartTime to set
	 */
	public void setLocalStartTime(Date localStartTime) {
		this.localStartTime = localStartTime;
	}
	/**
	 * @return the remoteStartTime
	 */
	public Date getRemoteStartTime() {
		return this.remoteStartTime;
	}
	/**
	 * @param remoteStartTime the remoteStartTime to set
	 */
	public void setRemoteStartTime(Date remoteStartTime) {
		this.remoteStartTime = remoteStartTime;
	}
	/**
	 * @return the localEndTime
	 */
	public Date getLocalEndTime() {
		return this.localEndTime;
	}
	/**
	 * @param localEndTime the localEndTime to set
	 */
	public void setLocalEndTime(Date localEndTime) {
		this.localEndTime = localEndTime;
	}
	/**
	 * @return the remoteEndTime
	 */
	public Date getRemoteEndTime() {
		return this.remoteEndTime;
	}
	/**
	 * @param remoteEndTime the remoteEndTime to set
	 */
	public void setRemoteEndTime(Date remoteEndTime) {
		this.remoteEndTime = remoteEndTime;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * @return the test_duration
	 */
	public long getTestDuration() {
		return this.testDuration;
	}
	/**
	 * @param test_duration the test_duration to set
	 */
	public void setTestDuration(long test_duration) {
		this.testDuration = test_duration;
	}
	/**
	 * @return the screenShotFileName
	 */
	public String getScreenShotFileName() {
		return this.screenShotFileName;
	}
	/**
	 * @param screenShotFileName the screenShotFileName to set
	 */
	public void setScreenShotFileName(String screenShotFileName) {
		this.screenShotFileName = screenShotFileName;
	}
	public byte[] getScreenShotFileByte() {
		return screenShotFileByte;
	}
	public void setScreenShotFileByte(byte[] screenShotFileByte) {
		this.screenShotFileByte = screenShotFileByte;
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
	/**
	 * @return the testGroupName
	 */
	public String getTestGroup() {
		return this.testGroup;
	}
	/**
	 * @param testGroupName the testGroupName to set
	 */
	public void setTestGroup(String testGroupName) {
		this.testGroup = testGroupName;
	}
	public String getTestResultNew() {
		return testResultNew;
	}
	public void setTestResultNew(String testResultNew) {
		this.testResultNew = testResultNew;
	}

}

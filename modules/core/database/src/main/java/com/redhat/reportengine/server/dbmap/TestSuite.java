/**
 * 
 */
package com.redhat.reportengine.server.dbmap;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 15, 2012
 */
@XmlRootElement
public class TestSuite implements Serializable{

	private static final long serialVersionUID = -3302362588599338309L;
	
	public static final String RUNNING 		= "Running";
	public static final String COMPLETED 	= "Completed";
	public static final String NO_STATUS	= "NoStatus";
		
	private int id;
	private int testSuiteId;
	private String testSuiteName;
	private String testStatus;
	private String testBuild;
	private String testComments;
	private Date localStartTime;
	private Date remoteStartTime;
	private Date localEndTime;
	private Date remoteEndTime;
	private String testReference;
	private int testReferenceId;
	private String testHost;
	private boolean aggregationStatus;
	
	private int totalCases;
	private int passedCases;
	private int failedCases;
	private int skippedCases;
	private int totalChanges;
	private int passedChanges;
	private int failedChanges;
	private int skippedChanges;
	private long testDuration;
	
	//For Top N reports
	
	private int topN;
	private String orderBy;
	private String fromTime;
	private String toTime;
	
	/*
	public Object clone()
    {
        try
    {
            return super.clone();
        }
    catch( CloneNotSupportedException e )
    {
            return null;
        }
    } 
	*/
	
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
	 * @return the testTitle
	 */
	public String getTestSuiteName() {
		return this.testSuiteName;
	}
	/**
	 * @param testTitle the testTitle to set
	 */
	public void setTestSuiteName(String testSuite) {
		this.testSuiteName = testSuite;
	}
	/**
	 * @return the testStatus
	 */
	public String getTestStatus() {
		return this.testStatus;
	}
	/**
	 * @param testStatus the testStatus to set
	 */
	public void setTestStatus(String testStatus) {
		this.testStatus = testStatus;
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
	public Date getLocalStartTime() {
		return this.localStartTime;
	}
	/**
	 * @param localTime the localTime to set
	 */
	public void setLocalStartTime(Date localTime) {
		this.localStartTime = localTime;
	}
	/**
	 * @return the remoteTime
	 */
	public Date getRemoteStartTime() {
		return this.remoteStartTime;
	}
	/**
	 * @param remoteTime the remoteTime to set
	 */
	public void setRemoteStartTime(Date remoteTime) {
		this.remoteStartTime = remoteTime;
	}
	/**
	 * @return the testReference
	 */
	public String getTestReference() {
		return this.testReference;
	}
	/**
	 * @param testReference the testReference to set
	 */
	public void setTestReference(String testReference) {
		this.testReference = testReference;
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
	 * @return the aggregationStatus
	 */
	public boolean isAggregationStatus() {
		return this.aggregationStatus;
	}
	/**
	 * @param aggregationStatus the aggregationStatus to set
	 */
	public void setAggregationStatus(boolean aggregationStatus) {
		this.aggregationStatus = aggregationStatus;
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
	 * @return the totalChanges
	 */
	public int getTotalChanges() {
		return this.totalChanges;
	}
	/**
	 * @param totalChanges the totalChanges to set
	 */
	public void setTotalChanges(int totalChanges) {
		this.totalChanges = totalChanges;
	}
	/**
	 * @return the passedChanges
	 */
	public int getPassedChanges() {
		return this.passedChanges;
	}
	/**
	 * @param passedChanges the passedChanges to set
	 */
	public void setPassedChanges(int passedChanges) {
		this.passedChanges = passedChanges;
	}
	/**
	 * @return the failedChanges
	 */
	public int getFailedChanges() {
		return this.failedChanges;
	}
	/**
	 * @param failedChanges the failedChanges to set
	 */
	public void setFailedChanges(int failedChanges) {
		this.failedChanges = failedChanges;
	}
	/**
	 * @return the skippedChanges
	 */
	public int getSkippedChanges() {
		return this.skippedChanges;
	}
	/**
	 * @param skippedChanges the skippedChanges to set
	 */
	public void setSkippedChanges(int skippedChanges) {
		this.skippedChanges = skippedChanges;
	}
	/**
	 * @return the duration
	 */
	public long getTestDuration() {
		return this.testDuration;
	}
	/**
	 * @param duration the duration to set
	 */
	public void setTestDuration(long duration) {
		this.testDuration = duration;
	}
	/**
	 * @return the testHost
	 */
	public String getTestHost() {
		return this.testHost;
	}
	/**
	 * @param testHost the testHost to set
	 */
	public void setTestHost(String testHost) {
		this.testHost = testHost;
	}
	public int getTestSuiteId() {
		return testSuiteId;
	}
	public void setTestSuiteId(int testSuiteId) {
		this.testSuiteId = testSuiteId;
	}
	/**
	 * @return the topN
	 */
	public int getTopN() {
		return this.topN;
	}
	/**
	 * @param topN the topN to set
	 */
	public void setTopN(int topN) {
		this.topN = topN;
	}
	/**
	 * @return the orderBy
	 */
	public String getOrderBy() {
		return this.orderBy;
	}
	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	/**
	 * @return the testReferenceId
	 */
	public int getTestReferenceId() {
		return testReferenceId;
	}
	/**
	 * @param testReferenceId the testReferenceId to set
	 */
	public void setTestReferenceId(int testReferenceId) {
		this.testReferenceId = testReferenceId;
	}
	/**
	 * @return the fromTime
	 */
	public String getFromTime() {
		return fromTime;
	}
	/**
	 * @param fromTime the fromTime to set
	 */
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}
	/**
	 * @return the toTime
	 */
	public String getToTime() {
		return toTime;
	}
	/**
	 * @param toTime the toTime to set
	 */
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

}

/**
 * 
 */
package com.redhat.reportengine.server.dbmap;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 28, 2012
 */
@XmlRootElement
public class TestLogs  implements Serializable {

	private static final long serialVersionUID = 6606194412789419601L;
	
	private Integer id;
	private Integer testSuiteId;
	private Integer testGroupId;
	private Integer testCaseId;
	private Long sequenceNumber;
	private String logLevel;
	private Date logTime;
	private Date localTime;
	private String className;
	private String methodName;
	private String message;
	private String throwable;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return this.id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the testSuiteId
	 */
	public Integer getTestSuiteId() {
		return this.testSuiteId;
	}
	/**
	 * @param testSuiteId the testSuiteId to set
	 */
	public void setTestSuiteId(Integer testSuiteId) {
		this.testSuiteId = testSuiteId;
	}
	/**
	 * @return the testGroupId
	 */
	public Integer getTestGroupId() {
		return this.testGroupId;
	}
	/**
	 * @param testGroupId the testGroupId to set
	 */
	public void setTestGroupId(Integer testGroupId) {
		this.testGroupId = testGroupId;
	}
	/**
	 * @return the testCaseId
	 */
	public Integer getTestCaseId() {
		return this.testCaseId;
	}
	/**
	 * @param testCaseId the testCaseId to set
	 */
	public void setTestCaseId(Integer testCaseId) {
		this.testCaseId = testCaseId;
	}
	/**
	 * @return the sequenceNumber
	 */
	public Long getSequenceNumber() {
		return this.sequenceNumber;
	}
	/**
	 * @param sequenceNumber the sequenceNumber to set
	 */
	public void setSequenceNumber(Long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	/**
	 * @return the logLevel
	 */
	public String getLogLevel() {
		return this.logLevel;
	}
	/**
	 * @param logLevel the logLevel to set
	 */
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	/**
	 * @return the logTime
	 */
	public Date getLogTime() {
		return this.logTime;
	}
	/**
	 * @param logTime the logTime to set
	 */
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
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
	 * @return the className
	 */
	public String getClassName() {
		return this.className;
	}
	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return this.methodName;
	}
	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return this.message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the throwable
	 */
	public String getThrowable() {
		return this.throwable;
	}
	/**
	 * @param throwable the throwable to set
	 */
	public void setThrowable(String throwable) {
		this.throwable = throwable;
	}
}

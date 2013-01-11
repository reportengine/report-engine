/**
 * 
 */
package com.redhat.reportengine.client;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 16, 2012
 */
public class ClientCommon {
	
	private String serverIp;
	private String serverRMIPort;	
	private Object serverObject;	
	private Integer testSuiteId;
	private Integer testGroupId;
	private Integer testCaseId;
	
	private String testReference;
	private String testGroupName = "";
	
	private String clientTempLocation;
	private boolean takeScreenShot;
	private String screenShotFileName;
	private String buildVersionReference;	
	private boolean loggerEnabled;
	private String loggerType;
	private String logLevel;
	
	/**
	 * @return the serverIp
	 */
	public String getServerIp() {
		return this.serverIp;
	}
	/**
	 * @param serverIp the serverIp to set
	 */
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	/**
	 * @return the serverRMIPort
	 */
	public String getServerRMIPort() {
		return this.serverRMIPort;
	}
	/**
	 * @param serverRMIPort the serverRMIPort to set
	 */
	public void setServerRMIPort(String serverRMIPort) {
		this.serverRMIPort = serverRMIPort;
	}
	/**
	 * @return the serverObject
	 */
	public Object getServerObject() {
		return this.serverObject;
	}
	/**
	 * @param serverObject the serverObject to set
	 */
	public void setServerObject(Object serverObject) {
		this.serverObject = serverObject;
	}
	/**
	 * @return the testTitleId
	 */
	public Integer getTestSuiteId() {
		return this.testSuiteId;
	}
	/**
	 * @param testTitleId the testTitleId to set
	 */
	public void setTestSuiteId(Integer testTitleId) {
		this.testSuiteId = testTitleId;
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
	 * @return the testGroupName
	 */
	public String getTestGroupName() {
		return this.testGroupName;
	}
	/**
	 * @param testGroupName the testGroupName to set
	 */
	public void setTestGroupName(String testGroupName) {
		this.testGroupName = testGroupName;
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
	 * @return the clientTempLocation
	 */
	public String getClientTempLocation() {
		return this.clientTempLocation;
	}
	/**
	 * @param clientTempLocation the clientTempLocation to set
	 */
	public void setClientTempLocation(String clientTempLocation) {
		if(clientTempLocation != null && !clientTempLocation.endsWith("/")){
			clientTempLocation += "/";
		}
		this.clientTempLocation = clientTempLocation;
	}
	/**
	 * @return the takeScreenShot
	 */
	public boolean isTakeScreenShot() {
		return this.takeScreenShot;
	}
	/**
	 * @param takeScreenShot the takeScreenShot to set
	 */
	public void setTakeScreenShot(boolean takeScreenShot) {
		this.takeScreenShot = takeScreenShot;
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
	/**
	 * @return the buildVersionReference
	 */
	public String getBuildVersionReference() {
		return this.buildVersionReference;
	}
	/**
	 * @param buildVersionReference the buildVersionReference to set
	 */
	public void setBuildVersionReference(String buildVersionReference) {
		this.buildVersionReference = buildVersionReference;
	}
	/**
	 * @return the loggerEnabled
	 */
	public boolean isLoggerEnabled() {
		return this.loggerEnabled;
	}
	/**
	 * @param loggerEnabled the loggerEnabled to set
	 */
	public void setLoggerEnabled(boolean loggerEnabled) {
		this.loggerEnabled = loggerEnabled;
	}
	/**
	 * @return the loggerType
	 */
	public String getLoggerType() {
		return this.loggerType;
	}
	/**
	 * @param loggerType the loggerType to set
	 */
	public void setLoggerType(String loggerType) {
		this.loggerType = loggerType;
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
	
	public static String toString(Throwable th) {
		if(th != null){
			 StringWriter sw = new StringWriter();
			    PrintWriter pw = new PrintWriter(sw);
			    th.printStackTrace(pw);
			    return sw.toString();
		}else{
			return null;
		}
	   
	  }
	
}

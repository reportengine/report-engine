package com.redhat.reportengine.server.dbmap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Aug 16, 2013
 */
public class TestReferenceServerMap {
	private int testSuiteReferenceId;
	private int serverId;
	private String serverName;
	private String testSuiteReferenceName;
	
	public int getServerId() {
		return serverId;
	}
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public int getTestSuiteReferenceId() {
		return testSuiteReferenceId;
	}
	public void setTestSuiteReferenceId(int testSuiteReferenceId) {
		this.testSuiteReferenceId = testSuiteReferenceId;
	}
	public String getTestSuiteReferenceName() {
		return testSuiteReferenceName;
	}
	public void setTestSuiteReferenceName(String testSuiteReferenceName) {
		this.testSuiteReferenceName = testSuiteReferenceName;
	}
	
}

/**
 * 
 */
package com.redhat.reportengine.server.dbmap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 22, 2012
 */
public class TestSuiteAggregation {
	private String testResult;
	private int testCount;
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
	 * @return the testCount
	 */
	public int getTestCount() {
		return this.testCount;
	}
	/**
	 * @param testCount the testCount to set
	 */
	public void setTestCount(int testCount) {
		this.testCount = testCount;
	}
}

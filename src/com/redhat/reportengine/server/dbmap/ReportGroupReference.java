package com.redhat.reportengine.server.dbmap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jun 20, 2012
 */
public class ReportGroupReference {
	private int reportGroupId;
	private int testReferenceId;
	/**
	 * @return the reportGroupId
	 */
	public int getReportGroupId() {
		return reportGroupId;
	}
	/**
	 * @param reportGroupId the reportGroupId to set
	 */
	public void setReportGroupId(int reportGroupId) {
		this.reportGroupId = reportGroupId;
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
}

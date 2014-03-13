package com.redhat.reportengine.server.dbmap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 05, 2014
 */
public class ReportGroupResourceMetricReference {
	private int reportGroupId;
	private int metricReferenceId;
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
	public int getMetricReferenceId() {
		return metricReferenceId;
	}
	public void setMetricReferenceId(int metricReferenceId) {
		this.metricReferenceId = metricReferenceId;
	}

}

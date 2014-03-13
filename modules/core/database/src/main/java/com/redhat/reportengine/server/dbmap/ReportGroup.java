package com.redhat.reportengine.server.dbmap;

import java.util.Date;
import java.util.List;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jun 19, 2012
 */
public class ReportGroup {

	private int id;
	private String groupName;
	private String emailTo;
	private String emailCc;
	private String owner;
	private Date creationTime;
	private boolean testSuiteGroupEnabled;
	private boolean resourceMetricEnabled;
	private List<ReportGroupReference> reportGroupReference = null;
	private List<ReportGroupResourceMetricReference> resourceMetricReference = null;
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
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/**
	 * @return the emailTo
	 */
	public String getEmailTo() {
		return emailTo;
	}
	/**
	 * @param emailTo the emailTo to set
	 */
	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}
	/**
	 * @return the emailCc
	 */
	public String getEmailCc() {
		return emailCc;
	}
	/**
	 * @param emailCc the emailCc to set
	 */
	public void setEmailCc(String emailCc) {
		this.emailCc = emailCc;
	}
	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
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
	public List<ReportGroupReference> getReportGroupReference() {
		return reportGroupReference;
	}
	public void setReportGroupReference(List<ReportGroupReference> reportGroupReference) {
		this.reportGroupReference = reportGroupReference;
	}
	/**
	 * @return the testSuiteGroupEnabled
	 */
	public boolean isTestSuiteGroupEnabled() {
		return testSuiteGroupEnabled;
	}
	/**
	 * @param testSuiteGroupEnabled the testSuiteGroupEnabled to set
	 */
	public void setTestSuiteGroupEnabled(boolean testSuiteGroupEnabled) {
		this.testSuiteGroupEnabled = testSuiteGroupEnabled;
	}
	public boolean isResourceMetricEnabled() {
		return resourceMetricEnabled;
	}
	public void setResourceMetricEnabled(boolean resourceMetricEnabled) {
		this.resourceMetricEnabled = resourceMetricEnabled;
	}
	public List<ReportGroupResourceMetricReference> getResourceMetricReference() {
		return resourceMetricReference;
	}
	public void setResourceMetricReference(
			List<ReportGroupResourceMetricReference> resourceMetricReference) {
		this.resourceMetricReference = resourceMetricReference;
	}
}

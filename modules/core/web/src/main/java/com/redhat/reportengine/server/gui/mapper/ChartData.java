package com.redhat.reportengine.server.gui.mapper;

import java.util.Date;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Aug 21, 2013
 */
public class ChartData {
	private int serverId;
	private String reportFor;
	private Date reportDateFrom;
	private Date reportDateTo;
	private String resourceType;
	private String selectedResources;
	
	public int getServerId() {
		return serverId;
	}
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	public String getReportFor() {
		return reportFor;
	}
	public void setReportFor(String reportFor) {
		this.reportFor = reportFor;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getSelectedResources() {
		return selectedResources;
	}
	public void setSelectedResources(String selectedResources) {
		this.selectedResources = selectedResources;
	}
	public Date getReportDateFrom() {
		return reportDateFrom;
	}
	public void setReportDateFrom(Date reportDateFrom) {
		this.reportDateFrom = reportDateFrom;
	}
	public Date getReportDateTo() {
		return reportDateTo;
	}
	public void setReportDateTo(Date reportDateTo) {
		this.reportDateTo = reportDateTo;
	}
}

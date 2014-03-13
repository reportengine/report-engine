/**
 * 
 */
package com.redhat.reportengine.server.dbmap;

import java.util.Date;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 4, 2014
 */
public class TestSuiteResourceMetric {
	public enum COLUMNS_NOT_IN_DB{
		cpu_used("used");
		private String name;
		private COLUMNS_NOT_IN_DB(String n) {
			name = n;
		}
		public String getName() {
			return name;
		}
	}


	private int id;
	private int dtnId;
	private int testReferenceId;
	private int testSuiteId;
	private Integer testGroupId;
	private Integer testCaseId;
	private int columnNameId;
	private Double minimum;
	private Double maximum;
	private Double average;
	private Double minimumChanges;
	private Double maximumChanges;
	private Double averageChanges;
	private Date localTime;

	//Server Mapping - View

	private String resourceName;
	private int serverId;
	private String serverName;
	private String serverIp;
	private String tableType;

	//Used to calculate metric
	private Date fromTime;
	private Date toTime;
	private String columnName;
	private String subType;
	private String subQuery;


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDtnId() {
		return dtnId;
	}
	public void setDtnId(int dtnId) {
		this.dtnId = dtnId;
	}
	public int getTestSuiteId() {
		return testSuiteId;
	}
	public void setTestSuiteId(int testSuiteId) {
		this.testSuiteId = testSuiteId;
	}
	public Integer getTestGroupId() {
		return testGroupId;
	}
	public void setTestGroupId(Integer testGroupId) {
		this.testGroupId = testGroupId;
	}
	public Integer getTestCaseId() {
		return testCaseId;
	}
	public void setTestCaseId(Integer testCaseId) {
		this.testCaseId = testCaseId;
	}

	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public int getServerId() {
		return serverId;
	}
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	public Double getMinimum() {
		return minimum;
	}
	public void setMinimum(Double minimum) {
		this.minimum = minimum;
	}
	public Double getMaximum() {
		return maximum;
	}
	public void setMaximum(Double maximum) {
		this.maximum = maximum;
	}
	public Double getAverage() {
		return average;
	}
	public void setAverage(Double average) {
		this.average = average;
	}
	public Date getFromTime() {
		return fromTime;
	}
	public void setFromTime(Date fromTime) {
		this.fromTime = fromTime;
	}
	public Date getToTime() {
		return toTime;
	}
	public void setToTime(Date toTime) {
		this.toTime = toTime;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public Date getLocalTime() {
		return localTime;
	}
	public void setLocalTime(Date localTime) {
		this.localTime = localTime;
	}
	public int getColumnNameId() {
		return columnNameId;
	}
	public void setColumnNameId(int columnNameId) {
		this.columnNameId = columnNameId;
	}
	public String getTableType() {
		return tableType;
	}
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getSubQuery() {
		return subQuery;
	}
	public void setSubQuery(String subQuery) {
		this.subQuery = subQuery;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	public int getTestReferenceId() {
		return testReferenceId;
	}
	public void setTestReferenceId(int testReferenceId) {
		this.testReferenceId = testReferenceId;
	}
	public Double getMinimumChanges() {
		return minimumChanges;
	}
	public void setMinimumChanges(Double minimumChanges) {
		this.minimumChanges = minimumChanges;
	}
	public Double getMaximumChanges() {
		return maximumChanges;
	}
	public void setMaximumChanges(Double maximumChanges) {
		this.maximumChanges = maximumChanges;
	}
	public Double getAverageChanges() {
		return averageChanges;
	}
	public void setAverageChanges(Double averageChanges) {
		this.averageChanges = averageChanges;
	}

}

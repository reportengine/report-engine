/**
 * 
 */
package com.redhat.reportengine.server.dbmap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 5, 2014
 */
public class TestSuiteResourceMetricColumn {
	private int id;
	private String tableType;
	private String columnName;
	private String subType;
	private String subQuery;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTableType() {
		return tableType;
	}
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("{[ID: ").append(this.id).append("],");
		builder.append("[Table Type: ").append(this.tableType).append("],");
		builder.append("[Column Name: ").append(this.columnName).append("]}");
		return builder.toString();
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
}

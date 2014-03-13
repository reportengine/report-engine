/**
 * 
 */
package com.redhat.reportengine.server.dbmap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Feb 3, 2014
 */
public class DynamicTableName {
	
	public enum TYPE{
		JVM_MEMORY,CPU,CPUS,MEMORY
	}	
	private int id;
	private String name;
	private int serverId;
	private String tableType;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String originalName) {
		this.name = originalName;
	}
	public int getServerId() {
		return serverId;
	}
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	public String getTableType() {
		return tableType;
	}
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}
}

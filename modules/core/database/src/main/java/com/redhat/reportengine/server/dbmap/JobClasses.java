package com.redhat.reportengine.server.dbmap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 08, 2013
 */
public class JobClasses {
	private Integer id;
	private String targetClass;
	private String targetClassDescription;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTargetClass() {
		return targetClass;
	}
	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}
	public String getTargetClassDescription() {
		return targetClassDescription;
	}
	public void setTargetClassDescription(String targetClassDescription) {
		this.targetClassDescription = targetClassDescription;
	}
}

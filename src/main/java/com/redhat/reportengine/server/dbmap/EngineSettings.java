package com.redhat.reportengine.server.dbmap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Oct 11, 2012
 */
public class EngineSettings {
	private String key;
	private String value;
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
}

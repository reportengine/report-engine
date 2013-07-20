package com.redhat.reportengine.agent.rest.mapper;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

import org.hyperic.sigar.OperatingSystem;


/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 19, 2013
 */
@XmlRootElement
public class OsDetail  extends AgentBaseMap implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8984812176338293117L;
	
	private long time;
	private OperatingSystem operatingSystem;
	
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public OperatingSystem getOperatingSystem() {
		return operatingSystem;
	}
	public void setOperatingSystem(OperatingSystem operatingSystem) {
		this.operatingSystem = operatingSystem;
	}
	
}

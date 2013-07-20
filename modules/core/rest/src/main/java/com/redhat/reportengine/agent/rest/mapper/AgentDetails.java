package com.redhat.reportengine.agent.rest.mapper;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * May 14, 2013
 */
@XmlRootElement
public class AgentDetails  extends AgentBaseMap implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8984812176338293117L;
	
	private Date date;
	private String ip;
	private String hostName;
	private String name;
	private CpuInformation cpu;
	private SigarDetail sigarDetail;
	

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	/**
	 * @return the hostName
	 */
	public String getHostName() {
		return hostName;
	}


	/**
	 * @param hostName the hostName to set
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}


	/**
	 * @return the sigarDetail
	 */
	public SigarDetail getSigarDetail() {
		return sigarDetail;
	}


	/**
	 * @param sigarDetail the sigarDetail to set
	 */
	public void setSigarDetail(SigarDetail sigarDetail) {
		this.sigarDetail = sigarDetail;
	}

	public CpuInformation getCpu() {
		return cpu;
	}

	public void setCpu(CpuInformation cpu) {
		this.cpu = cpu;
	}

	
}

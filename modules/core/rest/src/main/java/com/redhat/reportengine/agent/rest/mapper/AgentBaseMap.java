package com.redhat.reportengine.agent.rest.mapper;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jun 27, 2013
 */
@XmlRootElement
public class AgentBaseMap implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7965432223121329449L;
	private String agentReference;
	

	public String getAgentReference() {
		return agentReference;
	}

	public void setAgentReference(String agentReference) {
		this.agentReference = agentReference;
	}
}

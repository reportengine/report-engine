package com.redhat.reportengine.agent.rest.mapper;

import java.io.Serializable;
import java.util.List;

public class PidList extends AgentBaseMap implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7732338439888839140L;
	private List<PidDetail> pidDetail;
	
	public List<PidDetail> getPidDetail() {
		return pidDetail;
	}
	public void setPidDetail(List<PidDetail> pidDetail) {
		this.pidDetail = pidDetail;
	}

	
}

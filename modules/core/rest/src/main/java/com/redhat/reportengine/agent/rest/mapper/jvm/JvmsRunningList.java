package com.redhat.reportengine.agent.rest.mapper.jvm;

import java.io.Serializable;
import java.util.List;

import com.redhat.reportengine.agent.rest.mapper.AgentBaseMap;

public class JvmsRunningList extends AgentBaseMap implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1977539903239618030L;
	
	private List<JvmVirtualMachineDescriptor> list;
	public List<JvmVirtualMachineDescriptor> getList() {
		return list;
	}
	public void setList(List<JvmVirtualMachineDescriptor> list) {
		this.list = list;
	}
	
}
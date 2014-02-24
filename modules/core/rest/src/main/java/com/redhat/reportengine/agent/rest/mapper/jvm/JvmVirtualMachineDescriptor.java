package com.redhat.reportengine.agent.rest.mapper.jvm;

import java.io.Serializable;

import com.redhat.reportengine.agent.rest.mapper.AgentBaseMap;
import com.sun.tools.attach.VirtualMachineDescriptor;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jan 23, 2014
 */
public class JvmVirtualMachineDescriptor extends AgentBaseMap implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1735580312565862000L;
	private String id;
	private String displayName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String dusplayName) {
		this.displayName = dusplayName;
	}
	
	public JvmVirtualMachineDescriptor(){
		
	}
	public JvmVirtualMachineDescriptor(VirtualMachineDescriptor virtualMachineDescriptor){
		this.displayName = virtualMachineDescriptor.displayName();
		this.id = virtualMachineDescriptor.id();
	}
	public String toString(){
		return new StringBuilder("ID: ").append(this.id).append(", Display Name: ").append(displayName).toString();
	}
}

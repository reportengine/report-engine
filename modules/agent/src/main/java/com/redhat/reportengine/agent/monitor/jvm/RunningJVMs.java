package com.redhat.reportengine.agent.monitor.jvm;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.redhat.reportengine.agent.rest.mapper.jvm.JvmVirtualMachineDescriptor;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jan 23, 2014
 */
public class RunningJVMs {
	private static final Logger _logger = Logger.getLogger(RunningJVMs.class);

	public static List<JvmVirtualMachineDescriptor>  getList() {
		List<JvmVirtualMachineDescriptor> vmdescs = new ArrayList<JvmVirtualMachineDescriptor>();
		try {
			JvmVirtualMachineDescriptor vmdes ;
			for (VirtualMachineDescriptor vmdesc : VirtualMachine.list()) {
				vmdes = new JvmVirtualMachineDescriptor(vmdesc);				
				_logger.debug("VM ID: "+vmdes.getId()+", Name: "+vmdes.getDisplayName());
				vmdescs.add(vmdes);
			}
		} catch (Exception ex) {
			_logger.error("Exception, ", ex);
		}
		return vmdescs;
	}
}
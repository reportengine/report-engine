package com.redhat.reportengine.agent.api;

import java.util.Date;

import org.hyperic.sigar.SigarException;

import com.redhat.reportengine.agent.rest.mapper.UsageMemory;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jun 27, 2013
 */
public class Memory {

	public static UsageMemory getMemoryUsage() throws SigarException{
		UsageMemory memory = new UsageMemory();
		memory.setMemory(SigarUtils.getSigar().getMem());
		memory.setSwap(SigarUtils.getSigar().getSwap());
		memory.setTime(new Date().getTime());
		return memory;
	}
	
}

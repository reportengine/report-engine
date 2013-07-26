package com.redhat.reportengine.agent.jobs;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.hyperic.sigar.SigarException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.redhat.reportengine.agent.SendUdpData;
import com.redhat.reportengine.agent.rest.AgentResource;
import com.redhat.reportengine.agent.rest.mapper.UsageCpuMemory;
import com.redhat.reportengine.udppacket.MSG_TYPE;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 23, 2013
 */
public class SendCpuMemorySwapInfo implements Job{
	private static Logger _logger = Logger.getLogger(SendCpuMemorySwapInfo.class);

	public static void sendData() throws JsonGenerationException, JsonMappingException, IOException, SigarException{
		UsageCpuMemory usageCpuMemory = AgentResource.getUsageCpuMemory();		
		SendUdpData.sendToServer(usageCpuMemory, MSG_TYPE.USAGE_CPU_MEMORY);
	}
	

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {		
		try {
			sendData();
		} catch (Exception ex) {
			_logger.error("Exception while sending CPU/Memory Info...", ex);
		}
	}

}

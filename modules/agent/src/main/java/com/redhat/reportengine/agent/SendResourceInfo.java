package com.redhat.reportengine.agent;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.hyperic.sigar.SigarException;
import org.quartz.JobDataMap;

import com.redhat.reportengine.agent.api.JVM;
import com.redhat.reportengine.agent.rest.AgentResource;
import com.redhat.reportengine.server.reports.Keys;
import com.redhat.reportengine.udppacket.Formatter;
import com.redhat.reportengine.udppacket.MSG_TYPE;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 31, 2013
 */
public class SendResourceInfo implements Runnable{
	private static Logger _logger = Logger.getLogger(SendResourceInfo.class);

	private MSG_TYPE type; 
	private JobDataMap dataMap;
	public SendResourceInfo(MSG_TYPE type, JobDataMap dataMap){
		super();
		this.type = type;
		this.dataMap = dataMap;
	}
	public void sendData(MSG_TYPE type) throws JsonGenerationException, JsonMappingException, IOException, SigarException{
		switch(type){
		case USAGE_CPU: 		SendUdpData.sendToServer(Formatter.getJsonString(AgentResource.getUsageCpu()), type); break;
		case USAGE_CPUS: 		SendUdpData.sendToServer(Formatter.getJsonString(AgentResource.getUsageCpus()), type); break; 
		case USAGE_MEMORY: 		SendUdpData.sendToServer(Formatter.getJsonString(AgentResource.getUsageMemory()), type); break;
		case USAGE_CPU_MEMORY: 	SendUdpData.sendToServer(Formatter.getJsonString(AgentResource.getUsageCpuMemory()), type); break;
		case USAGE_JVM_MEMORY: 	SendUdpData.sendToServer(Formatter.getJsonString(JVM.getJvmMemoryMXBeanByName(this.dataMap.getString(Keys.JOB_DATA))), type); break;
		}
	}

	@Override
	public void run() {
		try {
			this.sendData(type);
		} catch (Exception ex) {
			_logger.error("Exception, ", ex);
		}		
	}

	public MSG_TYPE getType() {
		return type;
	}

	public void setType(MSG_TYPE type) {
		this.type = type;
	}
	public JobDataMap getDataMap() {
		return dataMap;
	}
	public void setDataMap(JobDataMap dataMap) {
		this.dataMap = dataMap;
	}

}

package com.redhat.reportengine.server.jobs.user;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.redhat.reportengine.agent.rest.mapper.URIReferenceAgent;
import com.redhat.reportengine.agent.rest.mapper.UsageCpuMemory;
import com.redhat.reportengine.agent.rest.mapper.UsageCpus;
import com.redhat.reportengine.agent.rest.mapper.UsageMemory;
import com.redhat.reportengine.server.insert.InsertCpusUsage;
import com.redhat.reportengine.server.insert.InsertMemoryUsage;
import com.redhat.reportengine.server.reports.Keys;
import com.redhat.reportengine.server.restclient.agent.AgentsConnection;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 15, 2013
 */
public class MeasureCpuMemoryCpusSwap implements Job{
	private static Logger _logger = Logger.getLogger(MeasureCpuMemoryCpusSwap.class);

	public void updateMeasurements(int serverId){
		UsageCpuMemory usageCpuMemory;
		try {
			usageCpuMemory = (UsageCpuMemory) AgentsConnection.getRestJSONclient(serverId).get(URIReferenceAgent.USAGE_CPU_MEMORY, UsageCpuMemory.class);
			
			//Update Cpu/Cpus Usage
			UsageCpus usageCpus = usageCpuMemory.getUsageCpus();
			usageCpus.setTime(usageCpuMemory.getTime());
			new Thread(new InsertCpusUsage(serverId, usageCpuMemory.getUsageCpus())).start();

			//Update Memory/swap Usage
			UsageMemory usageMemory = usageCpuMemory.getUsageMemory();
			usageMemory.setTime(usageCpuMemory.getTime());
			new Thread(new InsertMemoryUsage(serverId, usageMemory)).start();
			
		}catch (Exception ex) {
			if(!ex.getMessage().contains("refused")){
				_logger.error("Exception, ", ex);
			}
		}
	}

	@Override
	public void execute(JobExecutionContext context)throws JobExecutionException {
		JobDataMap jobDataMap = (JobDataMap)context.getJobDetail().getJobDataMap();
		try {
			updateMeasurements(jobDataMap.getInt(Keys.DATA_REFERENCDE_ID));
		} catch (Exception ex) {
			_logger.error("Exception, ", ex);
		}
	}
}

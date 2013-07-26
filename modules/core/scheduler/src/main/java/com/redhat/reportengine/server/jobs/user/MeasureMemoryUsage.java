package com.redhat.reportengine.server.jobs.user;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.redhat.reportengine.agent.rest.mapper.UsageMemory;
import com.redhat.reportengine.restapi.urimap.AgentRestUriMap;
import com.redhat.reportengine.server.insert.InsertMemoryUsage;
import com.redhat.reportengine.server.reports.Keys;
import com.redhat.reportengine.server.restclient.agent.AgentsConnection;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 10, 2013
 */
public class MeasureMemoryUsage implements Job{
	private static Logger _logger = Logger.getLogger(MeasureMemoryUsage.class);

	
	public void updateMemorySwapUsage(int serverId){
		UsageMemory usageMemory = null;
		try {
			usageMemory = (UsageMemory) AgentsConnection.getRestJSONclient(serverId).get(AgentRestUriMap.USAGE_MEMORY, UsageMemory.class);
			new Thread(new InsertMemoryUsage(serverId, usageMemory)).start();
		}catch (Exception ex) {
			if(!ex.getMessage().contains("refused")){
				_logger.error("Exception, ", ex);
			}
		}
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = (JobDataMap)context.getJobDetail().getJobDataMap();
		try {
			updateMemorySwapUsage(jobDataMap.getInt(Keys.DATA_REFERENCDE_ID));
		} catch (Exception ex) {
			_logger.error("Exception, ", ex);
		}
	}


}

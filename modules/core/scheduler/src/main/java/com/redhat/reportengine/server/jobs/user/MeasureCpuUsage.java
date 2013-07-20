package com.redhat.reportengine.server.jobs.user;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.redhat.reportengine.agent.rest.mapper.URIReferenceAgent;
import com.redhat.reportengine.agent.rest.mapper.UsageCpu;
import com.redhat.reportengine.server.insert.InsertCpuUsage;
import com.redhat.reportengine.server.reports.Keys;
import com.redhat.reportengine.server.restclient.agent.AgentsConnection;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 10, 2013
 */
public class MeasureCpuUsage implements Job {
	private static Logger _logger = Logger.getLogger(MeasureCpuUsage.class);
	
	public void updateCpuUsage(int serverId){
		UsageCpu usageCpu =null;
		try {
			usageCpu = (UsageCpu) AgentsConnection.getRestJSONclient(serverId).get(URIReferenceAgent.USAGE_CPU, UsageCpu.class);
			new Thread(new InsertCpuUsage(serverId, usageCpu)).start();
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
			updateCpuUsage(jobDataMap.getInt(Keys.DATA_REFERENCDE_ID));
		} catch (Exception ex) {
			_logger.error("Exception, ", ex);
		}

	}

}

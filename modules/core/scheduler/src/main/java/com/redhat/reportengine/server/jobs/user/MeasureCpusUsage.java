package com.redhat.reportengine.server.jobs.user;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.redhat.reportengine.agent.rest.mapper.URIReferenceAgent;
import com.redhat.reportengine.agent.rest.mapper.UsageCpus;
import com.redhat.reportengine.server.insert.InsertCpusUsage;
import com.redhat.reportengine.server.reports.Keys;
import com.redhat.reportengine.server.restclient.agent.AgentsConnection;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 11, 2013
 */
public class MeasureCpusUsage implements Job{
	private static Logger _logger = Logger.getLogger(MeasureCpusUsage.class);
	
	public void updateCpusUsage(int serverId){
		
		UsageCpus usageCpus =null;

		try {
			usageCpus = (UsageCpus) AgentsConnection.getRestJSONclient(serverId).get(URIReferenceAgent.USAGE_CPUS, UsageCpus.class);
			new Thread(new InsertCpusUsage(serverId, usageCpus)).start();
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
			updateCpusUsage(jobDataMap.getInt(Keys.DATA_REFERENCDE_ID));
		} catch (Exception ex) {
			_logger.error("Exception, ", ex);
		}

	}

}

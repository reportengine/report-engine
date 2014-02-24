package com.redhat.reportengine.agent.jobs;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.redhat.reportengine.agent.SendResourceInfo;
import com.redhat.reportengine.udppacket.MSG_TYPE;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Aug 1, 2013
 */
public class SendCpuInfo implements Job{
	private static Logger _logger = Logger.getLogger(SendCpuInfo.class);

	@Override
	public void execute(JobExecutionContext context){
		try{
			new Thread(new SendResourceInfo(MSG_TYPE.USAGE_CPU, context.getJobDetail().getJobDataMap())).start();
		}catch(Exception ex){
			_logger.error("Exception,", ex);
		}		
	}

}

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
public class SendCpuCpusMemorySwapInfo implements Job{
	private static Logger _logger = Logger.getLogger(SendCpuCpusMemorySwapInfo.class);

	@Override
	public void execute(JobExecutionContext context){
		try{
			new Thread(new SendResourceInfo(MSG_TYPE.USAGE_CPU_MEMORY, context)).start();
		}catch(Exception ex){
			_logger.error("Exception,", ex);
		}		
	}
}

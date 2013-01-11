/**
 * 
 */
package com.redhat.reportengine.server.gui;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Apr 19, 2012
 */
public class RunMe implements Job{
	final static Logger _logger = Logger.getLogger(RunMe.class);

	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		_logger.info(new Date()+" - called me");
		
	}

}

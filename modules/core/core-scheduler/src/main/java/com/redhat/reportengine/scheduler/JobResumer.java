/**
 * 
 */
package com.redhat.reportengine.scheduler;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.SchedulerException;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 9, 2014
 */
public class JobResumer implements Job{
	private static Logger _logger = Logger.getLogger(JobResumer.class);
	public void resumeJob(JobKey jobKey){
		try {
			ManageScheduler.resumeJob(jobKey);
			for(FailedJob job: ManageFailedJobs.getFailedJobs()){
				if(job.getJobKey().equals(jobKey)){
					job.setPhasedAt(null);
					job.setFailCount(0);
					job.setPhased(false);
					return;
				}
			}
			_logger.warn("failed to update jobfailed status, "+jobKey);
		} catch (SchedulerException e) {
			_logger.warn("Unable to resume the job, "+jobKey);
		}
	}
	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		this.resumeJob((JobKey) context.getJobDetail().getJobDataMap().get(ManageFailedJobs.JOB_DATA_MAP_KEY));
		
	}

}

/**
 * 
 */
package com.redhat.reportengine.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobKey;
import org.quartz.SchedulerException;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 9, 2014
 */
public class FailedJob {
	private static Logger _logger = Logger.getLogger(FailedJob.class);
	private JobKey jobKey;
	private boolean phased=false;
	private int failCount=0;
	private Date phasedAt;
	
	public JobKey getJobKey() {
		return jobKey;
	}
	public void setJobKey(JobKey jobKey) {
		this.jobKey = jobKey;
	}
	public boolean isPhased() {
		return phased;
	}
	public void setPhased(boolean phased) {
		this.phased = phased;
	}
	public int getFailCount() {
		return failCount;
	}
	public void setFailCount(int failCount) {
		this.failCount = failCount;
		_logger.info("JobKey ["+jobKey+"] current fail count: "+this.failCount+" of "+ManageFailedJobs.getJobMaxFailCount());
		if(this.failCount >= ManageFailedJobs.getJobMaxFailCount()){
			if(this.phased){
				_logger.warn("Selected job["+this.jobKey+"] is in phased mode... nothing to do...");
			}else{
				if(! ManageScheduler.addJob(ManageFailedJobs.getJobResumerDetails(this.jobKey))){
					_logger.warn("Failed to add resumer job for, "+jobKey);
				}else{
					try {
						ManageScheduler.phaseJob(jobKey);
						this.setFailCount(0);
						this.setPhased(true);
						this.setPhasedAt(new Date());
					} catch (SchedulerException e) {
						_logger.warn("Unable to phase a job, "+jobKey);
					}
				}
			}
		}
	}
	public Date getPhasedAt() {
		return phasedAt;
	}
	public void setPhasedAt(Date phasedAt) {
		this.phasedAt = phasedAt;
	}
}

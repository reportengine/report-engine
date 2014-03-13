/**
 * 
 */
package com.redhat.reportengine.scheduler;

import java.util.Date;
import java.util.LinkedList;

import org.quartz.JobDataMap;
import org.quartz.JobKey;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 9, 2014
 */
public class ManageFailedJobs{
	public static String JOB_RESUMER_NAME = "Job Resumer";
	public static String JOB_RESUMER_GROUP = "Job Resumer Group";
	public static String JOB_DATA_MAP_KEY	= "jobDataMapKey";
	private static LinkedList<FailedJob> failedJobs = new LinkedList<FailedJob>();
	private static int jobMaxFailCount = 3;
	private static int jobResumeDelay = 60; //in seconds
	
	public static LinkedList<FailedJob> getFailedJobs() {
		return failedJobs;
	}
	public static FailedJob getFailedJob(JobKey jobKey) {
		for(FailedJob job: failedJobs){
			if(job.getJobKey().equals(jobKey)){
				return job;
			}
		}
		return null;
	}	
	public static void setFailedJobs(LinkedList<FailedJob> failedJobs) {
		ManageFailedJobs.failedJobs = failedJobs;
	}
	public static int getJobMaxFailCount() {
		return jobMaxFailCount;
	}
	public static void setJobMaxFailCount(int jobMaxFailCount) {
		ManageFailedJobs.jobMaxFailCount = jobMaxFailCount;
	}
	public static int getJobResumeDelay() {
		return jobResumeDelay;
	}
	public static void setJobResumeDelay(int jobResumeDelay) {
		ManageFailedJobs.jobResumeDelay = jobResumeDelay;
	}
	
	public static JobDetails getJobResumerDetails(JobKey jobKey){
		JobDetails jobDetails = new JobDetails();
		jobDetails.setEnabled(true);
		jobDetails.setName(JOB_RESUMER_NAME+ "::"+jobKey.getName());
		jobDetails.setGroup(JOB_RESUMER_GROUP);
		jobDetails.setJobTargetClass("com.redhat.reportengine.scheduler.JobResumer");
		long startTime = (System.currentTimeMillis()+(1000l*jobResumeDelay));
		jobDetails.setJobFromTime(new Date(startTime));
		JobDataMap dataMap = new JobDataMap();
		dataMap.put(JOB_DATA_MAP_KEY, jobKey);
		jobDetails.setJobDataMap(dataMap);
		return jobDetails;
	}
}
